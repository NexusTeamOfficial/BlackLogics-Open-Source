
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.IntList;

public final class LocalVariableExtractor {
    private final mod.agus.jcoderz.dx.rop.code.RopMethod method;

    private final BasicBlockList blocks;

    private final LocalVariableInfo resultInfo;

    private final int[] workSet;

    public static LocalVariableInfo extract(mod.agus.jcoderz.dx.rop.code.RopMethod method) {
        LocalVariableExtractor lve = new LocalVariableExtractor(method);
        return lve.doit();
    }

    private LocalVariableExtractor(RopMethod method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        BasicBlockList blocks = method.getBlocks();
        int maxLabel = blocks.getMaxLabel();

        this.method = method;
        this.blocks = blocks;
        this.resultInfo = new LocalVariableInfo(method);
        this.workSet = Bits.makeBitSet(maxLabel);
    }

    private LocalVariableInfo doit() {
        for (int label = method.getFirstLabel();
             label >= 0;
             label = Bits.findFirst(workSet, 0)) {
            Bits.clear(workSet, label);
            processBlock(label);
        }

        resultInfo.setImmutable();
        return resultInfo;
    }

    private void processBlock(int label) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet primaryState = resultInfo.mutableCopyOfStarts(label);
        BasicBlock block = blocks.labelToBlock(label);
        InsnList insns = block.getInsns();
        int insnSz = insns.size();

        boolean canThrowDuringLastInsn = block.hasExceptionHandlers() &&
            (insns.getLast().getResult() != null);
        int freezeSecondaryStateAt = insnSz - 1;
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet secondaryState = primaryState;


        for (int i = 0; i < insnSz; i++) {
            if (canThrowDuringLastInsn && (i == freezeSecondaryStateAt)) {

                primaryState.setImmutable();
                primaryState = primaryState.mutableCopy();
            }

            Insn insn = insns.get(i);
            RegisterSpec result;

            result = insn.getLocalAssignment();

            if (result == null) {

                result = insn.getResult();

                if (result != null
                        && primaryState.get(result.getReg()) != null) {
                    primaryState.remove(primaryState.get(result.getReg()));
                }
                continue;
            }

            result = result.withSimpleType();

            RegisterSpec already = primaryState.get(result);
            if (!result.equals(already)) {
                RegisterSpec previous
                        = primaryState.localItemToSpec(result.getLocalItem());

                if (previous != null
                        && (previous.getReg() != result.getReg())) {

                    primaryState.remove(previous);
                }

                resultInfo.addAssignment(insn, result);
                primaryState.put(result);
            }
        }

        primaryState.setImmutable();


        IntList successors = block.getSuccessors();
        int succSz = successors.size();
        int primarySuccessor = block.getPrimarySuccessor();

        for (int i = 0; i < succSz; i++) {
            int succ = successors.get(i);
            RegisterSpecSet state = (succ == primarySuccessor) ?
                primaryState : secondaryState;

            if (resultInfo.mergeStarts(succ, state)) {
                Bits.set(workSet, succ);
            }
        }
    }
}
