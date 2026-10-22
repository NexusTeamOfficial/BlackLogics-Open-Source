
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.IntIterator;
import java.util.ArrayList;
import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RopMethod;

public class SsaConverter {
    public static final boolean DEBUG = false;

    public static mod.agus.jcoderz.dx.ssa.SsaMethod convertToSsaMethod(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth,
                                                                       int paramWidth, boolean isStatic) {
        mod.agus.jcoderz.dx.ssa.SsaMethod result
            = mod.agus.jcoderz.dx.ssa.SsaMethod.newFromRopMethod(rmeth, paramWidth, isStatic);

        edgeSplit(result);

        LocalVariableInfo localInfo = LocalVariableExtractor.extract(result);

        placePhiFunctions(result, localInfo, 0);
        new mod.agus.jcoderz.dx.ssa.SsaRenamer(result).run();

        result.makeExitBlock();

        return result;
    }

    public static void updateSsaMethod(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth, int threshold) {
        LocalVariableInfo localInfo = LocalVariableExtractor.extract(ssaMeth);
        placePhiFunctions(ssaMeth, localInfo, threshold);
        new SsaRenamer(ssaMeth, threshold).run();
    }

    public static mod.agus.jcoderz.dx.ssa.SsaMethod testEdgeSplit (mod.agus.jcoderz.dx.rop.code.RopMethod rmeth, int paramWidth,
                                                                   boolean isStatic) {
        mod.agus.jcoderz.dx.ssa.SsaMethod result;

        result = mod.agus.jcoderz.dx.ssa.SsaMethod.newFromRopMethod(rmeth, paramWidth, isStatic);

        edgeSplit(result);
        return result;
    }

    public static mod.agus.jcoderz.dx.ssa.SsaMethod testPhiPlacement (RopMethod rmeth, int paramWidth,
                                                                      boolean isStatic) {
        mod.agus.jcoderz.dx.ssa.SsaMethod result;

        result = mod.agus.jcoderz.dx.ssa.SsaMethod.newFromRopMethod(rmeth, paramWidth, isStatic);

        edgeSplit(result);

        LocalVariableInfo localInfo = LocalVariableExtractor.extract(result);

        placePhiFunctions(result, localInfo, 0);
        return result;
    }

    private static void edgeSplit(mod.agus.jcoderz.dx.ssa.SsaMethod result) {
        edgeSplitPredecessors(result);
        edgeSplitMoveExceptionsAndResults(result);
        edgeSplitSuccessors(result);
    }

    private static void edgeSplitPredecessors(mod.agus.jcoderz.dx.ssa.SsaMethod result) {
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks = result.getBlocks();

        for (int i = blocks.size() - 1; i >= 0; i-- ) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock block = blocks.get(i);
            if (nodeNeedsUniquePredecessor(block)) {
                block.insertNewPredecessor();
            }
        }
    }

    private static boolean nodeNeedsUniquePredecessor(mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {

        int countPredecessors = block.getPredecessors().cardinality();
        int countSuccessors = block.getSuccessors().cardinality();

        return  (countPredecessors > 1 && countSuccessors > 1);
    }

    private static void edgeSplitMoveExceptionsAndResults(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth) {
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks = ssaMeth.getBlocks();

        for (int i = blocks.size() - 1; i >= 0; i-- ) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock block = blocks.get(i);

            if (!block.isExitBlock()
                    && block.getPredecessors().cardinality() > 1
                    && block.getInsns().get(0).isMoveException()) {


                BitSet preds = (BitSet)block.getPredecessors().clone();
                for (int j = preds.nextSetBit(0); j >= 0;
                     j = preds.nextSetBit(j + 1)) {
                    mod.agus.jcoderz.dx.ssa.SsaBasicBlock predecessor = blocks.get(j);
                    mod.agus.jcoderz.dx.ssa.SsaBasicBlock zNode
                        = predecessor.insertNewSuccessor(block);

                    zNode.getInsns().add(0, block.getInsns().get(0).clone());
                }


                block.getInsns().remove(0);
            }
        }
    }

    private static void edgeSplitSuccessors(mod.agus.jcoderz.dx.ssa.SsaMethod result) {
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> blocks = result.getBlocks();

        for (int i = blocks.size() - 1; i >= 0; i-- ) {
            mod.agus.jcoderz.dx.ssa.SsaBasicBlock block = blocks.get(i);


            BitSet successors = (BitSet)block.getSuccessors().clone();
            for (int j = successors.nextSetBit(0);
                 j >= 0; j = successors.nextSetBit(j+1)) {

                mod.agus.jcoderz.dx.ssa.SsaBasicBlock succ = blocks.get(j);

                if (needsNewSuccessor(block, succ)) {
                    block.insertNewSuccessor(succ);
                }
            }
        }
    }

    private static boolean needsNewSuccessor(mod.agus.jcoderz.dx.ssa.SsaBasicBlock block,
                                             mod.agus.jcoderz.dx.ssa.SsaBasicBlock succ) {
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> insns = block.getInsns();
        mod.agus.jcoderz.dx.ssa.SsaInsn lastInsn = insns.get(insns.size() - 1);







        if (block.getSuccessors().cardinality() > 1 && succ.getPredecessors().cardinality() > 1) {
            return true;
        }

        return ((lastInsn.getResult() != null)
                    || (lastInsn.getSources().size() > 0))
                && succ.getPredecessors().cardinality() > 1;
    }

    private static void placePhiFunctions (SsaMethod ssaMeth,
                                           LocalVariableInfo localInfo, int threshold) {
        ArrayList<mod.agus.jcoderz.dx.ssa.SsaBasicBlock> ssaBlocks;
        int regCount;
        int blockCount;

        ssaBlocks = ssaMeth.getBlocks();
        blockCount = ssaBlocks.size();
        regCount = ssaMeth.getRegCount() - threshold;

        mod.agus.jcoderz.dx.ssa.DomFront df = new mod.agus.jcoderz.dx.ssa.DomFront(ssaMeth);
        DomFront.DomInfo[] domInfos = df.run();


        BitSet[] defsites = new BitSet[regCount];


        BitSet[] phisites = new BitSet[regCount];

        for (int i = 0; i < regCount; i++) {
            defsites[i] = new BitSet(blockCount);
            phisites[i] = new BitSet(blockCount);
        }

        for (int bi = 0, s = ssaBlocks.size(); bi < s; bi++) {
            SsaBasicBlock b = ssaBlocks.get(bi);

            for (SsaInsn insn : b.getInsns()) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec rs = insn.getResult();

                if (rs != null && rs.getReg() - threshold >= 0) {
                    defsites[rs.getReg() - threshold].set(bi);
                }
            }
        }

        if (DEBUG) {
            System.out.println("defsites");

            for (int i = 0; i < regCount; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append('v').append(i).append(": ");
                sb.append(defsites[i].toString());
                System.out.println(sb);
            }
        }

        BitSet worklist;

        for (int reg = 0, s = regCount; reg < s; reg++) {
            int workBlockIndex;


            worklist = (BitSet) (defsites[reg].clone());

            while (0 <= (workBlockIndex = worklist.nextSetBit(0))) {
                worklist.clear(workBlockIndex);
                IntIterator dfIterator
                    = domInfos[workBlockIndex].dominanceFrontiers.iterator();

                while (dfIterator.hasNext()) {
                    int dfBlockIndex = dfIterator.next();

                    if (!phisites[reg].get(dfBlockIndex)) {
                        phisites[reg].set(dfBlockIndex);

                        int tReg = reg + threshold;
                        RegisterSpec rs
                            = localInfo.getStarts(dfBlockIndex).get(tReg);

                        if (rs == null) {
                            ssaBlocks.get(dfBlockIndex).addPhiInsnForReg(tReg);
                        } else {
                            ssaBlocks.get(dfBlockIndex).addPhiInsnForReg(rs);
                        }

                        if (!defsites[reg].get(dfBlockIndex)) {
                            worklist.set(dfBlockIndex);
                        }
                    }
                }
            }
        }

        if (DEBUG) {
            System.out.println("phisites");

            for (int i = 0; i < regCount; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append('v').append(i).append(": ");
                sb.append(phisites[i].toString());
                System.out.println(sb);
            }
        }
    }
}
