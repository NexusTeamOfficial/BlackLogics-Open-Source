
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public final class SwitchData extends VariableSizeInsn {
    private final mod.agus.jcoderz.dx.dex.code.CodeAddress user;

    private final mod.agus.jcoderz.dx.util.IntList cases;

    private final mod.agus.jcoderz.dx.dex.code.CodeAddress[] targets;

    private final boolean packed;

    public SwitchData(SourcePosition position, mod.agus.jcoderz.dx.dex.code.CodeAddress user,
                      mod.agus.jcoderz.dx.util.IntList cases, CodeAddress[] targets) {
        super(position, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

        if (user == null) {
            throw new NullPointerException("user == null");
        }

        if (cases == null) {
            throw new NullPointerException("cases == null");
        }

        if (targets == null) {
            throw new NullPointerException("targets == null");
        }

        int sz = cases.size();

        if (sz != targets.length) {
            throw new IllegalArgumentException("cases / targets mismatch");
        }

        if (sz > 65535) {
            throw new IllegalArgumentException("too many cases");
        }

        this.user = user;
        this.cases = cases;
        this.targets = targets;
        this.packed = shouldPack(cases);
    }

    @Override
    public int codeSize() {
        return packed ? (int) packedCodeSize(cases) :
            (int) sparseCodeSize(cases);
    }

    @Override
    public void writeTo(AnnotatedOutput out) {
        int baseAddress = user.getAddress();
        int defaultTarget = Dops.PACKED_SWITCH.getFormat().codeSize();
        int sz = targets.length;

        if (packed) {
            int firstCase = (sz == 0) ? 0 : cases.get(0);
            int lastCase = (sz == 0) ? 0 : cases.get(sz - 1);
            int outSz = lastCase - firstCase + 1;

            out.writeShort(mod.agus.jcoderz.dx.io.Opcodes.PACKED_SWITCH_PAYLOAD);
            out.writeShort(outSz);
            out.writeInt(firstCase);

            int caseAt = 0;
            for (int i = 0; i < outSz; i++) {
                int outCase = firstCase + i;
                int oneCase = cases.get(caseAt);
                int relTarget;

                if (oneCase > outCase) {
                    relTarget = defaultTarget;
                } else {
                    relTarget = targets[caseAt].getAddress() - baseAddress;
                    caseAt++;
                }

                out.writeInt(relTarget);
            }
        } else {
            out.writeShort(Opcodes.SPARSE_SWITCH_PAYLOAD);
            out.writeShort(sz);

            for (int i = 0; i < sz; i++) {
                out.writeInt(cases.get(i));
            }

            for (int i = 0; i < sz; i++) {
                int relTarget = targets[i].getAddress() - baseAddress;
                out.writeInt(relTarget);
            }
        }
    }

    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new SwitchData(getPosition(), user, cases, targets);
    }

    public boolean isPacked() {
        return packed;
    }

    @Override
    protected String argString() {
        StringBuilder sb = new StringBuilder(100);

        int sz = targets.length;
        for (int i = 0; i < sz; i++) {
            sb.append("\n    ");
            sb.append(cases.get(i));
            sb.append(": ");
            sb.append(targets[i]);
        }

        return sb.toString();
    }

    @Override
    protected String listingString0(boolean noteIndices) {
        int baseAddress = user.getAddress();
        StringBuilder sb = new StringBuilder(100);
        int sz = targets.length;

        sb.append(packed ? "packed" : "sparse");
        sb.append("-switch-payload // for switch @ ");
        sb.append(mod.agus.jcoderz.dx.util.Hex.u2(baseAddress));

        for (int i = 0; i < sz; i++) {
            int absTarget = targets[i].getAddress();
            int relTarget = absTarget - baseAddress;
            sb.append("\n  ");
            sb.append(cases.get(i));
            sb.append(": ");
            sb.append(mod.agus.jcoderz.dx.util.Hex.u4(absTarget));
            sb.append(" // ");
            sb.append(Hex.s4(relTarget));
        }

        return sb.toString();
    }

    private static long packedCodeSize(mod.agus.jcoderz.dx.util.IntList cases) {
        int sz = cases.size();
        long low = cases.get(0);
        long high = cases.get(sz - 1);
        long result = ((high - low + 1)) * 2 + 4;

        return (result <= 0x7fffffff) ? result : -1;
    }

    private static long sparseCodeSize(mod.agus.jcoderz.dx.util.IntList cases) {
        int sz = cases.size();

        return (sz * 4L) + 2;
    }

    private static boolean shouldPack(IntList cases) {
        int sz = cases.size();

        if (sz < 2) {
            return true;
        }

        long packedSize = packedCodeSize(cases);
        long sparseSize = sparseCodeSize(cases);

        return (packedSize >= 0) && (packedSize <= ((sparseSize * 5) / 4));
    }
}
