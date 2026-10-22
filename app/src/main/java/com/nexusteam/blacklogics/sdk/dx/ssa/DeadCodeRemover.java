
package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;

public class DeadCodeRemover {
    private final mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;

    private final int regCount;

    private final BitSet worklist;

    private final ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[] useList;

    public static void process(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMethod) {
        DeadCodeRemover dc = new DeadCodeRemover(ssaMethod);
        dc.run();
    }

    private DeadCodeRemover(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;

        regCount = ssaMethod.getRegCount();
        worklist = new BitSet(regCount);
        useList = ssaMeth.getUseListCopy();
    }

    private void run() {
        pruneDeadInstructions();

        HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn> deletedInsns = new HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn>();

        ssaMeth.forEachInsn(new NoSideEffectVisitor(worklist));

        int regV;

        while ( 0 <= (regV = worklist.nextSetBit(0)) ) {
            worklist.clear(regV);

            if (useList[regV].size() == 0
                    || isCircularNoSideEffect(regV, null)) {

                mod.agus.jcoderz.dx.ssa.SsaInsn insnS = ssaMeth.getDefinitionForRegister(regV);


                if (deletedInsns.contains(insnS)) {
                    continue;
                }

                mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = insnS.getSources();

                int sz = sources.size();
                for (int i = 0; i < sz; i++) {

                    mod.agus.jcoderz.dx.rop.code.RegisterSpec source = sources.get(i);
                    useList[source.getReg()].remove(insnS);

                    if (!hasSideEffect(
                            ssaMeth.getDefinitionForRegister(
                                    source.getReg()))) {
                        worklist.set(source.getReg());
                    }
                }


                deletedInsns.add(insnS);
            }
        }

        ssaMeth.deleteInsns(deletedInsns);
    }

    private void pruneDeadInstructions() {
        HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn> deletedInsns = new HashSet<mod.agus.jcoderz.dx.ssa.SsaInsn>();

        BitSet reachable = ssaMeth.computeReachability();
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks = ssaMeth.getBlocks();
        int blockIndex = 0;

        while ((blockIndex = reachable.nextClearBit(blockIndex)) < blocks.size()) {
            SsaBasicBlock block = blocks.get(blockIndex);
            blockIndex++;


            for (int i = 0; i < block.getInsns().size(); i++) {
                mod.agus.jcoderz.dx.ssa.SsaInsn insn = block.getInsns().get(i);
                RegisterSpecList sources = insn.getSources();
                int sourcesSize = sources.size();


                if (sourcesSize != 0) {
                    deletedInsns.add(insn);
                }


                for (int j = 0; j < sourcesSize; j++) {
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec source = sources.get(j);
                    useList[source.getReg()].remove(insn);
                }


                mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
                if (result == null) continue;
                for (mod.agus.jcoderz.dx.ssa.SsaInsn use : useList[result.getReg()]) {
                    if (use instanceof mod.agus.jcoderz.dx.ssa.PhiInsn) {
                        mod.agus.jcoderz.dx.ssa.PhiInsn phiUse = (mod.agus.jcoderz.dx.ssa.PhiInsn) use;
                        phiUse.removePhiRegister(result);
                    }
                }
            }
        }

        ssaMeth.deleteInsns(deletedInsns);
    }

    private boolean isCircularNoSideEffect(int regV, BitSet set) {
        if ((set != null) && set.get(regV)) {
            return true;
        }

        for (mod.agus.jcoderz.dx.ssa.SsaInsn use : useList[regV]) {
            if (hasSideEffect(use)) {
                return false;
            }
        }

        if (set == null) {
            set = new BitSet(regCount);
        }


        set.set(regV);

        for (mod.agus.jcoderz.dx.ssa.SsaInsn use : useList[regV]) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec result = use.getResult();

            if (result == null
                    || !isCircularNoSideEffect(result.getReg(), set)) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasSideEffect(mod.agus.jcoderz.dx.ssa.SsaInsn insn) {
        if (insn == null) {
            return true;
        }

        return insn.hasSideEffect();
    }

    static private class NoSideEffectVisitor implements SsaInsn.Visitor {
        BitSet noSideEffectRegs;

        public NoSideEffectVisitor(BitSet noSideEffectRegs) {
            this.noSideEffectRegs = noSideEffectRegs;
        }

        @Override
        public void visitMoveInsn (NormalSsaInsn insn) {

            if (!hasSideEffect(insn)) {
                noSideEffectRegs.set(insn.getResult().getReg());
            }
        }

        @Override
        public void visitPhiInsn (PhiInsn phi) {

            if (!hasSideEffect(phi)) {
                noSideEffectRegs.set(phi.getResult().getReg());
            }
        }

        @Override
        public void visitNonMoveInsn (NormalSsaInsn insn) {
            RegisterSpec result = insn.getResult();
            if (!hasSideEffect(insn) && result != null) {
                noSideEffectRegs.set(result.getReg());
            }
        }
    }
}
