
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.IntList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;

import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.type.Type;

public class SsaRenamer implements Runnable {
    private static final boolean DEBUG = false;

    private final mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;

    private int nextSsaReg;

    private final int ropRegCount;

    private int threshold;

    private final mod.agus.jcoderz.dx.rop.code.RegisterSpec[][] startsForBlocks;

    private final ArrayList<mod.agus.jcoderz.dx.rop.code.LocalItem> ssaRegToLocalItems;

    private IntList ssaRegToRopReg;

    public SsaRenamer(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth) {
        ropRegCount = ssaMeth.getRegCount();

        this.ssaMeth = ssaMeth;

        nextSsaReg = ropRegCount;
        threshold = 0;
        startsForBlocks = new mod.agus.jcoderz.dx.rop.code.RegisterSpec[ssaMeth.getBlocks().size()][];

        ssaRegToLocalItems = new ArrayList<mod.agus.jcoderz.dx.rop.code.LocalItem>();

        if (DEBUG) {
            ssaRegToRopReg = new IntList(ropRegCount);
        }



        mod.agus.jcoderz.dx.rop.code.RegisterSpec[] initialRegMapping = new mod.agus.jcoderz.dx.rop.code.RegisterSpec[ropRegCount];
        for (int i = 0; i < ropRegCount; i++) {

            initialRegMapping[i] = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(i, mod.agus.jcoderz.dx.rop.type.Type.VOID);

            if (DEBUG) {
                ssaRegToRopReg.add(i);
            }
        }


        startsForBlocks[ssaMeth.getEntryBlockIndex()] = initialRegMapping;
    }

   public SsaRenamer(SsaMethod ssaMeth, int thresh) {
       this(ssaMeth);
       threshold = thresh;
   }

    @Override
    public void run() {

        ssaMeth.forEachBlockDepthFirstDom(new mod.agus.jcoderz.dx.ssa.SsaBasicBlock.Visitor() {
            @Override
            public void visitBlock (mod.agus.jcoderz.dx.ssa.SsaBasicBlock block,
                                    mod.agus.jcoderz.dx.ssa.SsaBasicBlock unused) {
                new BlockRenamer(block).process();
            }
        });

        ssaMeth.setNewRegCount(nextSsaReg);
        ssaMeth.onInsnsChanged();

        if (DEBUG) {
            System.out.println("SSA\tRop");
            int[] versions = new int[ropRegCount];

            int sz = ssaRegToRopReg.size();
            for (int i = 0; i < sz; i++) {
                int ropReg = ssaRegToRopReg.get(i);
                System.out.println(i + "\t" + ropReg + "["
                        + versions[ropReg] + "]");
                versions[ropReg]++;
            }
        }
    }

    private static  mod.agus.jcoderz.dx.rop.code.RegisterSpec[] dupArray(mod.agus.jcoderz.dx.rop.code.RegisterSpec[] orig) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpec[] copy = new mod.agus.jcoderz.dx.rop.code.RegisterSpec[orig.length];

        System.arraycopy(orig, 0, copy, 0, orig.length);

        return copy;
    }

    private mod.agus.jcoderz.dx.rop.code.LocalItem getLocalForNewReg(int ssaReg) {
        if (ssaReg < ssaRegToLocalItems.size()) {
            return ssaRegToLocalItems.get(ssaReg);
        } else {
            return null;
        }
    }

    private void setNameForSsaReg(mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaReg) {
        int reg = ssaReg.getReg();
        mod.agus.jcoderz.dx.rop.code.LocalItem local = ssaReg.getLocalItem();

        ssaRegToLocalItems.ensureCapacity(reg + 1);
        while (ssaRegToLocalItems.size() <= reg) {
            ssaRegToLocalItems.add(null);
        }

        ssaRegToLocalItems.set(reg, local);
    }

    private boolean isBelowThresholdRegister(int ssaReg) {
        return ssaReg < threshold;
    }

    private boolean isVersionZeroRegister(int ssaReg) {
        return ssaReg < ropRegCount;
    }

    private static boolean equalsHandlesNulls(Object a, Object b) {
        return a == b ||  (a != null && a.equals(b));
    }

    private class BlockRenamer implements mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor {
        private final mod.agus.jcoderz.dx.ssa.SsaBasicBlock block;

        private final mod.agus.jcoderz.dx.rop.code.RegisterSpec[] currentMapping;

        private final HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn> movesToKeep;

        private final HashMap<mod.agus.jcoderz.dx.ssa.SsaInsn, mod.agus.jcoderz.dx.ssa.SsaInsn> insnsToReplace;

        private final RenamingMapper mapper;

        BlockRenamer(final mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
            this.block = block;
            currentMapping = startsForBlocks[block.getIndex()];
            movesToKeep = new HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn>();
            insnsToReplace = new HashMap<mod.agus.jcoderz.dx.ssa.SsaInsn, mod.agus.jcoderz.dx.ssa.SsaInsn>();
            mapper =  new RenamingMapper();


            startsForBlocks[block.getIndex()] = null;
        }

        private class RenamingMapper extends RegisterMapper {
            public RenamingMapper() {

            }

            @Override
            public int getNewRegisterCount() {
                return nextSsaReg;
            }

            @Override
            public mod.agus.jcoderz.dx.rop.code.RegisterSpec map(mod.agus.jcoderz.dx.rop.code.RegisterSpec registerSpec) {
                if (registerSpec == null) return null;

                int reg = registerSpec.getReg();


                if (DEBUG) {
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec newVersion = currentMapping[reg];
                    if (newVersion.getBasicType() != Type.BT_VOID
                            && registerSpec.getBasicFrameType()
                                != newVersion.getBasicFrameType()) {

                        throw new RuntimeException(
                                "mapping registers of incompatible types! "
                                + registerSpec
                                + " " + currentMapping[reg]);
                    }
                }

                return registerSpec.withReg(currentMapping[reg].getReg());
            }
        }

        public void process() {

            block.forEachInsn(this);

            updateSuccessorPhis();


            ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> insns = block.getInsns();
            int szInsns = insns.size();

            for (int i = szInsns - 1; i >= 0 ; i--) {
                mod.agus.jcoderz.dx.ssa.SsaInsn insn = insns.get(i);
                mod.agus.jcoderz.dx.ssa.SsaInsn replaceInsn;

                replaceInsn = insnsToReplace.get(insn);

                if (replaceInsn != null) {
                    insns.set(i, replaceInsn);
                } else if (insn.isNormalMoveInsn()
                        && !movesToKeep.contains(insn)) {
                    insns.remove(i);
                }
            }


            boolean first = true;
            for (mod.agus.jcoderz.dx.ssa.SsaBasicBlock child : block.getDomChildren()) {
                if (child != block) {

                    mod.agus.jcoderz.dx.rop.code.RegisterSpec[] childStart = first ? currentMapping
                        : dupArray(currentMapping);

                    startsForBlocks[child.getIndex()] = childStart;
                    first = false;
                }
            }


        }

        private void addMapping(int ropReg, mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaReg) {
            int ssaRegNum = ssaReg.getReg();
            mod.agus.jcoderz.dx.rop.code.LocalItem ssaRegLocal = ssaReg.getLocalItem();

            currentMapping[ropReg] = ssaReg;

            for (int i = currentMapping.length - 1; i >= 0; i--) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec cur = currentMapping[i];

                if (ssaRegNum == cur.getReg()) {
                    currentMapping[i] = ssaReg;
                }
            }


            if (ssaRegLocal == null) {
                return;
            }


            setNameForSsaReg(ssaReg);


            for (int i = currentMapping.length - 1; i >= 0; i--) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec cur = currentMapping[i];

                if (ssaRegNum != cur.getReg()
                        && ssaRegLocal.equals(cur.getLocalItem())) {
                    currentMapping[i] = cur.withLocalItem(null);
                }
            }
        }

        @Override
        public void visitPhiInsn(mod.agus.jcoderz.dx.ssa.PhiInsn phi) {
            processResultReg(phi);
        }

        @Override
        public void visitMoveInsn(NormalSsaInsn insn) {

            mod.agus.jcoderz.dx.rop.code.RegisterSpec ropResult = insn.getResult();
            int ropResultReg = ropResult.getReg();
            int ropSourceReg = insn.getSources().get(0).getReg();

            insn.mapSourceRegisters(mapper);
            int ssaSourceReg = insn.getSources().get(0).getReg();

            mod.agus.jcoderz.dx.rop.code.LocalItem sourceLocal
                = currentMapping[ropSourceReg].getLocalItem();
            mod.agus.jcoderz.dx.rop.code.LocalItem resultLocal = ropResult.getLocalItem();


            mod.agus.jcoderz.dx.rop.code.LocalItem newLocal
                = (resultLocal == null) ? sourceLocal : resultLocal;
            LocalItem associatedLocal = getLocalForNewReg(ssaSourceReg);

            boolean onlyOneAssociatedLocal
                    = associatedLocal == null || newLocal == null
                    || newLocal.equals(associatedLocal);

            mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaReg
                    = mod.agus.jcoderz.dx.rop.code.RegisterSpec.makeLocalOptional(
                        ssaSourceReg, ropResult.getType(), newLocal);

            if (!Optimizer.getPreserveLocals() || (onlyOneAssociatedLocal
                    && equalsHandlesNulls(newLocal, sourceLocal)) &&
                    threshold == 0) {

                addMapping(ropResultReg, ssaReg);
            } else if (onlyOneAssociatedLocal && sourceLocal == null &&
                    threshold == 0) {

                mod.agus.jcoderz.dx.rop.code.RegisterSpecList ssaSources = RegisterSpecList.make(
                        mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(ssaReg.getReg(),
                                ssaReg.getType(), newLocal));

                mod.agus.jcoderz.dx.ssa.SsaInsn newInsn
                        = mod.agus.jcoderz.dx.ssa.SsaInsn.makeFromRop(
                            new PlainInsn(Rops.opMarkLocal(ssaReg),
                            SourcePosition.NO_INFO, null, ssaSources),block);

                insnsToReplace.put(insn, newInsn);


                addMapping(ropResultReg, ssaReg);
            } else {
                processResultReg(insn);

                movesToKeep.add(insn);
            }
        }

        @Override
        public void visitNonMoveInsn(NormalSsaInsn insn) {
            insn.mapSourceRegisters(mapper);

            processResultReg(insn);
        }

        void processResultReg(SsaInsn insn) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec ropResult = insn.getResult();

            if (ropResult == null) {
                return;
            }

            int ropReg = ropResult.getReg();
            if (isBelowThresholdRegister(ropReg)) {
                return;
            }

            insn.changeResultReg(nextSsaReg);
            addMapping(ropReg, insn.getResult());

            if (DEBUG) {
                ssaRegToRopReg.add(ropReg);
            }

            nextSsaReg++;
        }

        private void updateSuccessorPhis() {
            mod.agus.jcoderz.dx.ssa.PhiInsn.Visitor visitor = new mod.agus.jcoderz.dx.ssa.PhiInsn.Visitor() {
                @Override
                public void visitPhiInsn (PhiInsn insn) {
                    int ropReg;

                    ropReg = insn.getRopResultReg();
                    if (isBelowThresholdRegister(ropReg)) {
                        return;
                    }


                    RegisterSpec stackTop = currentMapping[ropReg];
                    if (!isVersionZeroRegister(stackTop.getReg())) {
                        insn.addPhiOperand(stackTop, block);
                    }
                }
            };

            BitSet successors = block.getSuccessors();
            for (int i = successors.nextSetBit(0); i >= 0;
                    i = successors.nextSetBit(i + 1)) {
                SsaBasicBlock successor = ssaMeth.getBlocks().get(i);
                successor.forEachPhiInsn(visitor);
            }
        }
    }
}
