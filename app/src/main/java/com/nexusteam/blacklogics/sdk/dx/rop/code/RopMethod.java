
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public final class RopMethod {
    private final BasicBlockList blocks;

    private final int firstLabel;

    private IntList[] predecessors;

    private IntList exitPredecessors;

    public RopMethod(BasicBlockList blocks, int firstLabel) {
        if (blocks == null) {
            throw new NullPointerException("blocks == null");
        }

        if (firstLabel < 0) {
            throw new IllegalArgumentException("firstLabel < 0");
        }

        this.blocks = blocks;
        this.firstLabel = firstLabel;

        this.predecessors = null;
        this.exitPredecessors = null;
    }

    public BasicBlockList getBlocks() {
        return blocks;
    }

    public int getFirstLabel() {
        return firstLabel;
    }

    public IntList labelToPredecessors(int label) {
        if (exitPredecessors == null) {
            calcPredecessors();
        }

        IntList result = predecessors[label];

        if (result == null) {
            throw new RuntimeException("no such block: " + Hex.u2(label));
        }

        return result;
    }

    public IntList getExitPredecessors() {
        if (exitPredecessors == null) {
            calcPredecessors();
        }

        return exitPredecessors;
    }


    public RopMethod withRegisterOffset(int delta) {
        RopMethod result = new RopMethod(blocks.withRegisterOffset(delta),
                                         firstLabel);

        if (exitPredecessors != null) {
            result.exitPredecessors = exitPredecessors;
            result.predecessors = predecessors;
        }

        return result;
    }

    private void calcPredecessors() {
        int maxLabel = blocks.getMaxLabel();
        IntList[] predecessors = new IntList[maxLabel];
        IntList exitPredecessors = new IntList(10);
        int sz = blocks.size();

        for (int i = 0; i < sz; i++) {
            BasicBlock one = blocks.get(i);
            int label = one.getLabel();
            IntList successors = one.getSuccessors();
            int ssz = successors.size();
            if (ssz == 0) {

                exitPredecessors.add(label);
            } else {
                for (int j = 0; j < ssz; j++) {
                    int succLabel = successors.get(j);
                    IntList succPreds = predecessors[succLabel];
                    if (succPreds == null) {
                        succPreds = new IntList(10);
                        predecessors[succLabel] = succPreds;
                    }
                    succPreds.add(label);
                }
            }
        }


        for (int i = 0; i < maxLabel; i++) {
            IntList preds = predecessors[i];
            if (preds != null) {
                preds.sort();
                preds.setImmutable();
            }
        }

        exitPredecessors.sort();
        exitPredecessors.setImmutable();

        if (predecessors[firstLabel] == null) {
            predecessors[firstLabel] = IntList.EMPTY;
        }

        this.predecessors = predecessors;
        this.exitPredecessors = exitPredecessors;
    }
}
