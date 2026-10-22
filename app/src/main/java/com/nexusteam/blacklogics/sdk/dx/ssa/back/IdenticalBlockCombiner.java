
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.util.IntList;
import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RopMethod;

public class IdenticalBlockCombiner {
    private final mod.agus.jcoderz.dx.rop.code.RopMethod ropMethod;
    private final mod.agus.jcoderz.dx.rop.code.BasicBlockList blocks;
    private final BasicBlockList newBlocks;

    public IdenticalBlockCombiner(mod.agus.jcoderz.dx.rop.code.RopMethod rm) {
        ropMethod = rm;
        blocks = ropMethod.getBlocks();
        newBlocks = blocks.getMutableCopy();
    }

    public mod.agus.jcoderz.dx.rop.code.RopMethod process() {
        int szBlocks = blocks.size();

        BitSet toDelete = new BitSet(blocks.getMaxLabel());


        for (int bindex = 0; bindex < szBlocks; bindex++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock b = blocks.get(bindex);

            if (toDelete.get(b.getLabel())) {

                continue;
            }

            IntList preds = ropMethod.labelToPredecessors(b.getLabel());


            int szPreds = preds.size();
            for (int i = 0; i < szPreds; i++) {
                int iLabel = preds.get(i);

                mod.agus.jcoderz.dx.rop.code.BasicBlock iBlock = blocks.labelToBlock(iLabel);

                if (toDelete.get(iLabel)
                        || iBlock.getSuccessors().size() > 1
                        || iBlock.getFirstInsn().getOpcode().getOpcode() ==
                            RegOps.MOVE_RESULT) {
                    continue;
                }

                IntList toCombine = new IntList();


                for (int j = i + 1; j < szPreds; j++) {
                    int jLabel = preds.get(j);
                    mod.agus.jcoderz.dx.rop.code.BasicBlock jBlock = blocks.labelToBlock(jLabel);

                    if (jBlock.getSuccessors().size() == 1
                            && compareInsns(iBlock, jBlock)) {

                        toCombine.add(jLabel);
                        toDelete.set(jLabel);
                    }
                }

                combineBlocks(iLabel, toCombine);
            }
        }

        for (int i = szBlocks - 1; i >= 0; i--) {
            if (toDelete.get(newBlocks.get(i).getLabel())) {
                newBlocks.set(i, null);
            }
        }

        newBlocks.shrinkToFit();
        newBlocks.setImmutable();

        return new RopMethod(newBlocks, ropMethod.getFirstLabel());
    }

    private static boolean compareInsns(mod.agus.jcoderz.dx.rop.code.BasicBlock a, mod.agus.jcoderz.dx.rop.code.BasicBlock b) {
        return a.getInsns().contentEquals(b.getInsns());
    }

    private void combineBlocks(int alphaLabel, IntList betaLabels) {
        int szBetas = betaLabels.size();

        for (int i = 0; i < szBetas; i++) {
            int betaLabel = betaLabels.get(i);
            mod.agus.jcoderz.dx.rop.code.BasicBlock bb = blocks.labelToBlock(betaLabel);
            IntList preds = ropMethod.labelToPredecessors(bb.getLabel());
            int szPreds = preds.size();

            for (int j = 0; j < szPreds; j++) {
                mod.agus.jcoderz.dx.rop.code.BasicBlock predBlock = newBlocks.labelToBlock(preds.get(j));
                replaceSucc(predBlock, betaLabel, alphaLabel);
            }
        }
    }

    private void replaceSucc(mod.agus.jcoderz.dx.rop.code.BasicBlock block, int oldLabel, int newLabel) {
        IntList newSuccessors = block.getSuccessors().mutableCopy();
        int newPrimarySuccessor;

        newSuccessors.set(newSuccessors.indexOf(oldLabel), newLabel);
        newPrimarySuccessor = block.getPrimarySuccessor();

        if (newPrimarySuccessor == oldLabel) {
            newPrimarySuccessor = newLabel;
        }

        newSuccessors.setImmutable();

        mod.agus.jcoderz.dx.rop.code.BasicBlock newBB = new BasicBlock(block.getLabel(),
                block.getInsns(), newSuccessors, newPrimarySuccessor);

        newBlocks.set(newBlocks.indexOfLabel(block.getLabel()), newBB);
    }
}
