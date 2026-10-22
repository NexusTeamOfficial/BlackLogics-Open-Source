
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.IntList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;

public class LocalVariableExtractor {
    private final mod.agus.jcoderz.dx.ssa.SsaMethod method;

    private final ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks;

    private final LocalVariableInfo resultInfo;

    private final BitSet workSet;

    public static LocalVariableInfo extract(mod.agus.jcoderz.dx.ssa.SsaMethod method) {
        LocalVariableExtractor lve = new LocalVariableExtractor(method);
        return lve.doit();
    }

    private LocalVariableExtractor(SsaMethod method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks = method.getBlocks();

        this.method = method;
        this.blocks = blocks;
        this.resultInfo = new LocalVariableInfo(method);
        this.workSet = new BitSet(blocks.size());
    }

    private LocalVariableInfo doit() {


        if (method.getRegCount() > 0 ) {
            for (int bi = method.getEntryBlockIndex();
                 bi >= 0;
                 bi = workSet.nextSetBit(0)) {
                workSet.clear(bi);
                processBlock(bi);
            }
        }

        resultInfo.setImmutable();
        return resultInfo;
    }

    private void processBlock(int blockIndex) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet primaryState
                = resultInfo.mutableCopyOfStarts(blockIndex);
        SsaBasicBlock block = blocks.get(blockIndex);
        List<mod.agus.jcoderz.dx.ssa.SsaInsn> insns = block.getInsns();
        int insnSz = insns.size();


        if (blockIndex == method.getExitBlockIndex()) {
            return;
        }

        mod.agus.jcoderz.dx.ssa.SsaInsn lastInsn = insns.get(insnSz - 1);
        boolean hasExceptionHandlers
                = lastInsn.getOriginalRopInsn().getCatches().size() !=0 ;
        boolean canThrowDuringLastInsn = hasExceptionHandlers
                && (lastInsn.getResult() != null);
        int freezeSecondaryStateAt = insnSz - 1;
        mod.agus.jcoderz.dx.rop.code.RegisterSpecSet secondaryState = primaryState;


        for (int i = 0; i < insnSz; i++) {
            if (canThrowDuringLastInsn && (i == freezeSecondaryStateAt)) {

                primaryState.setImmutable();
                primaryState = primaryState.mutableCopy();
            }

            SsaInsn insn = insns.get(i);
            mod.agus.jcoderz.dx.rop.code.RegisterSpec result;

            result = insn.getLocalAssignment();

            if (result == null) {


                result = insn.getResult();

                if (result != null && primaryState.get(result.getReg()) != null) {
                    primaryState.remove(primaryState.get(result.getReg()));
                }
                continue;
            }

            result = result.withSimpleType();

            mod.agus.jcoderz.dx.rop.code.RegisterSpec already = primaryState.get(result);
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


        IntList successors = block.getSuccessorList();
        int succSz = successors.size();
        int primarySuccessor = block.getPrimarySuccessorIndex();

        for (int i = 0; i < succSz; i++) {
            int succ = successors.get(i);
            RegisterSpecSet state = (succ == primarySuccessor) ?
                primaryState : secondaryState;

            if (resultInfo.mergeStarts(succ, state)) {
                workSet.set(succ);
            }
        }
    }
}
