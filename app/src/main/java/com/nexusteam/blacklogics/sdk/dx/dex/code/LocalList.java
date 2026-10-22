
package mod.agus.jcoderz.dx.dex.code;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class LocalList extends FixedSizeList {
    public static final LocalList EMPTY = new LocalList(0);

    private static final boolean DEBUG = false;

    public LocalList(int size) {
        super(size);
    }

    public Entry get(int n) {
        return (Entry) get0(n);
    }

    public void set(int n, Entry entry) {
        set0(n, entry);
    }

    public void debugPrint(PrintStream out, String prefix) {
        int sz = size();

        for (int i = 0; i < sz; i++) {
            out.print(prefix);
            out.println(get(i));
        }
    }

    public static enum Disposition {
        START,

        END_SIMPLY,

        END_REPLACED,

        END_MOVED,

        END_CLOBBERED_BY_PREV,

        END_CLOBBERED_BY_NEXT;
    }

    public static class Entry implements Comparable<Entry> {
        private final int address;

        private final Disposition disposition;

        private final mod.agus.jcoderz.dx.rop.code.RegisterSpec spec;

        private final mod.agus.jcoderz.dx.rop.cst.CstType type;

        public Entry(int address, Disposition disposition, mod.agus.jcoderz.dx.rop.code.RegisterSpec spec) {
            if (address < 0) {
                throw new IllegalArgumentException("address < 0");
            }

            if (disposition == null) {
                throw new NullPointerException("disposition == null");
            }

            try {
                if (spec.getLocalItem() == null) {
                    throw new NullPointerException(
                            "spec.getLocalItem() == null");
                }
            } catch (NullPointerException ex) {

                throw new NullPointerException("spec == null");
            }

            this.address = address;
            this.disposition = disposition;
            this.spec = spec;
            this.type = mod.agus.jcoderz.dx.rop.cst.CstType.intern(spec.getType());
        }

        @Override
        public String toString() {
            return Integer.toHexString(address) + " " + disposition + " " +
                spec;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Entry)) {
                return false;
            }

            return (compareTo((Entry) other) == 0);
        }

        @Override
        public int compareTo(Entry other) {
            if (address < other.address) {
                return -1;
            } else if (address > other.address) {
                return 1;
            }

            boolean thisIsStart = isStart();
            boolean otherIsStart = other.isStart();

            if (thisIsStart != otherIsStart) {
                return thisIsStart ? 1 : -1;
            }

            return spec.compareTo(other.spec);
        }

        public int getAddress() {
            return address;
        }

        public Disposition getDisposition() {
            return disposition;
        }

        public boolean isStart() {
            return disposition == Disposition.START;
        }

        public mod.agus.jcoderz.dx.rop.cst.CstString getName() {
            return spec.getLocalItem().getName();
        }

        public CstString getSignature() {
            return spec.getLocalItem().getSignature();
        }

        public CstType getType() {
            return type;
        }

        public int getRegister() {
            return spec.getReg();
        }

        public mod.agus.jcoderz.dx.rop.code.RegisterSpec getRegisterSpec() {
            return spec;
        }

        public boolean matches(mod.agus.jcoderz.dx.rop.code.RegisterSpec otherSpec) {
            return spec.equalsUsingSimpleType(otherSpec);
        }

        public boolean matches(Entry other) {
            return matches(other.spec);
        }

        public Entry withDisposition(Disposition disposition) {
            if (disposition == this.disposition) {
                return this;
            }

            return new Entry(address, disposition, spec);
        }
    }

    public static LocalList make(DalvInsnList insns) {
        int sz = insns.size();


        MakeState state = new MakeState(sz);

        for (int i = 0; i < sz; i++) {
            DalvInsn insn = insns.get(i);

            if (insn instanceof mod.agus.jcoderz.dx.dex.code.LocalSnapshot) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpecSet snapshot =
                    ((LocalSnapshot) insn).getLocals();
                state.snapshot(insn.getAddress(), snapshot);
            } else if (insn instanceof LocalStart) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec local = ((LocalStart) insn).getLocal();
                state.startLocal(insn.getAddress(), local);
            }
        }

        LocalList result = state.finish();

        if (DEBUG) {
            debugVerify(result);
        }

        return result;
    }

    private static void debugVerify(LocalList locals) {
        try {
            debugVerify0(locals);
        } catch (RuntimeException ex) {
            int sz = locals.size();
            for (int i = 0; i < sz; i++) {
                System.err.println(locals.get(i));
            }
            throw ex;
        }

    }

    private static void debugVerify0(LocalList locals) {
        int sz = locals.size();
        Entry[] active = new Entry[65536];

        for (int i = 0; i < sz; i++) {
            Entry e = locals.get(i);
            int reg = e.getRegister();

            if (e.isStart()) {
                Entry already = active[reg];

                if ((already != null) && e.matches(already)) {
                    throw new RuntimeException("redundant start at " +
                            Integer.toHexString(e.getAddress()) + ": got " +
                            e + "; had " + already);
                }

                active[reg] = e;
            } else {
                if (active[reg] == null) {
                    throw new RuntimeException("redundant end at " +
                            Integer.toHexString(e.getAddress()));
                }

                int addr = e.getAddress();
                boolean foundStart = false;

                for (int j = i + 1; j < sz; j++) {
                    Entry test = locals.get(j);
                    if (test.getAddress() != addr) {
                        break;
                    }
                    if (test.getRegisterSpec().getReg() == reg) {
                        if (test.isStart()) {
                            if (e.getDisposition()
                                    != Disposition.END_REPLACED) {
                                throw new RuntimeException(
                                        "improperly marked end at " +
                                        Integer.toHexString(addr));
                            }
                            foundStart = true;
                        } else {
                            throw new RuntimeException(
                                    "redundant end at " +
                                    Integer.toHexString(addr));
                        }
                    }
                }

                if (!foundStart &&
                        (e.getDisposition() == Disposition.END_REPLACED)) {
                    throw new RuntimeException(
                            "improper end replacement claim at " +
                            Integer.toHexString(addr));
                }

                active[reg] = null;
            }
        }
    }

    public static class MakeState {
        private final ArrayList<Entry> result;

        private int nullResultCount;

        private mod.agus.jcoderz.dx.rop.code.RegisterSpecSet regs;

        private int[] endIndices;

        private final int lastAddress;

        public MakeState(int initialSize) {
            result = new ArrayList<Entry>(initialSize);
            nullResultCount = 0;
            regs = null;
            endIndices = null;
            lastAddress = 0;
        }

        private void aboutToProcess(int address, int reg) {
            boolean first = (endIndices == null);

            if ((address == lastAddress) && !first) {
                return;
            }

            if (address < lastAddress) {
                throw new RuntimeException("shouldn't happen");
            }

            if (first || (reg >= endIndices.length)) {
                int newSz = reg + 1;
                mod.agus.jcoderz.dx.rop.code.RegisterSpecSet newRegs = new mod.agus.jcoderz.dx.rop.code.RegisterSpecSet(newSz);
                int[] newEnds = new int[newSz];
                Arrays.fill(newEnds, -1);

                if (!first) {
                    newRegs.putAll(regs);
                    System.arraycopy(endIndices, 0, newEnds, 0,
                            endIndices.length);
                }

                regs = newRegs;
                endIndices = newEnds;
            }
        }

        public void snapshot(int address, RegisterSpecSet specs) {
            if (DEBUG) {
                System.err.printf("%04x snapshot %s\n", address, specs);
            }

            int sz = specs.getMaxSize();
            aboutToProcess(address, sz - 1);

            for (int i = 0; i < sz; i++) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec oldSpec = regs.get(i);
                mod.agus.jcoderz.dx.rop.code.RegisterSpec newSpec = filterSpec(specs.get(i));

                if (oldSpec == null) {
                    if (newSpec != null) {
                        startLocal(address, newSpec);
                    }
                } else if (newSpec == null) {
                    endLocal(address, oldSpec);
                } else if (! newSpec.equalsUsingSimpleType(oldSpec)) {
                    endLocal(address, oldSpec);
                    startLocal(address, newSpec);
                }
            }

            if (DEBUG) {
                System.err.printf("%04x snapshot done\n", address);
            }
        }

        public void startLocal(int address, mod.agus.jcoderz.dx.rop.code.RegisterSpec startedLocal) {
            if (DEBUG) {
                System.err.printf("%04x start %s\n", address, startedLocal);
            }

            int regNum = startedLocal.getReg();

            startedLocal = filterSpec(startedLocal);
            aboutToProcess(address, regNum);

            mod.agus.jcoderz.dx.rop.code.RegisterSpec existingLocal = regs.get(regNum);

            if (startedLocal.equalsUsingSimpleType(existingLocal)) {

                return;
            }

            mod.agus.jcoderz.dx.rop.code.RegisterSpec movedLocal = regs.findMatchingLocal(startedLocal);
            if (movedLocal != null) {
                addOrUpdateEnd(address, Disposition.END_MOVED, movedLocal);
            }

            int endAt = endIndices[regNum];

            if (existingLocal != null) {
                add(address, Disposition.END_REPLACED, existingLocal);
            } else if (endAt >= 0) {
                Entry endEntry = result.get(endAt);
                if (endEntry.getAddress() == address) {
                    if (endEntry.matches(startedLocal)) {
                        result.set(endAt, null);
                        nullResultCount++;
                        regs.put(startedLocal);
                        endIndices[regNum] = -1;
                        return;
                    } else {
                        endEntry = endEntry.withDisposition(
                                Disposition.END_REPLACED);
                        result.set(endAt, endEntry);
                    }
                }
            }


            if (regNum > 0) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec justBelow = regs.get(regNum - 1);
                if ((justBelow != null) && justBelow.isCategory2()) {
                    addOrUpdateEnd(address,
                            Disposition.END_CLOBBERED_BY_NEXT,
                            justBelow);
                }
            }

            if (startedLocal.isCategory2()) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec justAbove = regs.get(regNum + 1);
                if (justAbove != null) {
                    addOrUpdateEnd(address,
                            Disposition.END_CLOBBERED_BY_PREV,
                            justAbove);
                }
            }


            add(address, Disposition.START, startedLocal);
        }

        public void endLocal(int address, mod.agus.jcoderz.dx.rop.code.RegisterSpec endedLocal) {
            endLocal(address, endedLocal, Disposition.END_SIMPLY);
        }

        public void endLocal(int address, mod.agus.jcoderz.dx.rop.code.RegisterSpec endedLocal,
                Disposition disposition) {
            if (DEBUG) {
                System.err.printf("%04x end %s\n", address, endedLocal);
            }

            int regNum = endedLocal.getReg();

            endedLocal = filterSpec(endedLocal);
            aboutToProcess(address, regNum);

            int endAt = endIndices[regNum];

            if (endAt >= 0) {
                return;
            }


            if (checkForEmptyRange(address, endedLocal)) {
                return;
            }

            add(address, disposition, endedLocal);
        }

        private boolean checkForEmptyRange(int address,
                mod.agus.jcoderz.dx.rop.code.RegisterSpec endedLocal) {
            int at = result.size() - 1;
            Entry entry;


            for (; at >= 0; at--) {
                entry = result.get(at);

                if (entry == null) {
                    continue;
                }

                if (entry.getAddress() != address) {

                    return false;
                }

                if (entry.matches(endedLocal)) {
                    break;
                }
            }


            regs.remove(endedLocal);
            result.set(at, null);
            nullResultCount++;

            int regNum = endedLocal.getReg();
            boolean found = false;
            entry = null;


            for (at--; at >= 0; at--) {
                entry = result.get(at);

                if (entry == null) {
                    continue;
                }

                if (entry.getRegisterSpec().getReg() == regNum) {
                    found = true;
                    break;
                }
            }

            if (found) {

                endIndices[regNum] = at;

                if (entry.getAddress() == address) {
                    result.set(at,
                            entry.withDisposition(Disposition.END_SIMPLY));
                }
            }

            return true;
        }

        private static mod.agus.jcoderz.dx.rop.code.RegisterSpec filterSpec(mod.agus.jcoderz.dx.rop.code.RegisterSpec orig) {
            if ((orig != null) && (orig.getType() == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL)) {
                return orig.withType(Type.OBJECT);
            }

            return orig;
        }

        private void add(int address, Disposition disposition,
                mod.agus.jcoderz.dx.rop.code.RegisterSpec spec) {
            int regNum = spec.getReg();

            result.add(new Entry(address, disposition, spec));

            if (disposition == Disposition.START) {
                regs.put(spec);
                endIndices[regNum] = -1;
            } else {
                regs.remove(spec);
                endIndices[regNum] = result.size() - 1;
            }
        }

        private void addOrUpdateEnd(int address, Disposition disposition,
                RegisterSpec spec) {
            if (disposition == Disposition.START) {
                throw new RuntimeException("shouldn't happen");
            }

            int regNum = spec.getReg();
            int endAt = endIndices[regNum];

            if (endAt >= 0) {

                Entry endEntry = result.get(endAt);
                if ((endEntry.getAddress() == address) &&
                        endEntry.getRegisterSpec().equals(spec)) {
                    result.set(endAt, endEntry.withDisposition(disposition));
                    regs.remove(spec);
                    return;
                }
            }

            endLocal(address, spec, disposition);
        }

        public LocalList finish() {
            aboutToProcess(Integer.MAX_VALUE, 0);

            int resultSz = result.size();
            int finalSz = resultSz - nullResultCount;

            if (finalSz == 0) {
                return EMPTY;
            }


            Entry[] resultArr = new Entry[finalSz];

            if (resultSz == finalSz) {
                result.toArray(resultArr);
            } else {
                int at = 0;
                for (Entry e : result) {
                    if (e != null) {
                        resultArr[at++] = e;
                    }
                }
            }

            Arrays.sort(resultArr);

            LocalList resultList = new LocalList(finalSz);

            for (int i = 0; i < finalSz; i++) {
                resultList.set(i, resultArr[i]);
            }

            resultList.setImmutable();
            return resultList;
        }
    }
}
