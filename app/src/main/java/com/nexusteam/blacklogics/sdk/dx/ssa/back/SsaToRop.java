
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.ssa.BasicRegisterMapper;
import mod.agus.jcoderz.dx.ssa.PhiInsn;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;

import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.InsnList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.Rops;

public class SsaToRop {
    private static final boolean DEBUG = false;

    private final SsaMethod ssaMeth;

    private final boolean minimizeRegisters;

    private final InterferenceGraph interference;

    public static mod.agus.jcoderz.dx.rop.code.RopMethod convertToRopMethod(SsaMethod ssaMeth,
                                                                            boolean minimizeRegisters) {
        return new SsaToRop(ssaMeth, minimizeRegisters).convert();
    }

    private SsaToRop(SsaMethod ssaMethod, boolean minimizeRegisters) {
        this.minimizeRegisters = minimizeRegisters;
        this.ssaMeth = ssaMethod;
        this.interference =
            LivenessAnalyzer.constructInterferenceGraph(ssaMethod);
    }

    private mod.agus.jcoderz.dx.rop.code.RopMethod convert() {
        if (DEBUG) {
            interference.dumpToStdout();
        }





        RegisterAllocator allocator =
            new FirstFitLocalCombiningAllocator(ssaMeth, interference,
                    minimizeRegisters);

        RegisterMapper mapper = allocator.allocateRegisters();

        if (DEBUG) {
            System.out.println("Printing reg map");
            System.out.println(((BasicRegisterMapper)mapper).toHuman());
        }

        ssaMeth.setBackMode();

        ssaMeth.mapRegisters(mapper);

        removePhiFunctions();

        if (allocator.wantsParamsMovedHigh()) {
            moveParametersToHighRegisters();
        }

        removeEmptyGotos();

        mod.agus.jcoderz.dx.rop.code.RopMethod ropMethod = new RopMethod(convertBasicBlocks(),
                ssaMeth.blockIndexToRopLabel(ssaMeth.getEntryBlockIndex()));
        ropMethod = new IdenticalBlockCombiner(ropMethod).process();

        return ropMethod;
    }

    private void removeEmptyGotos() {
        final ArrayList<SsaBasicBlock> blocks = ssaMeth.getBlocks();

        ssaMeth.forEachBlockDepthFirst(false, new SsaBasicBlock.Visitor() {
            @Override
            public void visitBlock(SsaBasicBlock b, SsaBasicBlock parent) {
                ArrayList<SsaInsn> insns = b.getInsns();

                if ((insns.size() == 1)
                        && (insns.get(0).getOpcode() == mod.agus.jcoderz.dx.rop.code.Rops.GOTO)) {
                    BitSet preds = (BitSet) b.getPredecessors().clone();

                    for (int i = preds.nextSetBit(0); i >= 0;
                            i = preds.nextSetBit(i + 1)) {
                        SsaBasicBlock pb = blocks.get(i);
                        pb.replaceSuccessor(b.getIndex(),
                                b.getPrimarySuccessorIndex());
                    }
                }
            }
        });
    }

    private void removePhiFunctions() {
        ArrayList<SsaBasicBlock> blocks = ssaMeth.getBlocks();

        for (SsaBasicBlock block : blocks) {

            block.forEachPhiInsn(new PhiVisitor(blocks));


            block.removeAllPhiInsns();
        }

        for (SsaBasicBlock block : blocks) {
            block.scheduleMovesFromPhis();
        }
    }

    private static class PhiVisitor implements PhiInsn.Visitor {
        private final ArrayList<SsaBasicBlock> blocks;

        public PhiVisitor(ArrayList<SsaBasicBlock> blocks) {
            this.blocks = blocks;
        }

        @Override
        public void visitPhiInsn(PhiInsn insn) {
            RegisterSpecList sources = insn.getSources();
            mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
            int sz = sources.size();

            for (int i = 0; i < sz; i++) {
                RegisterSpec source = sources.get(i);
                SsaBasicBlock predBlock = blocks.get(
                        insn.predBlockIndexForSourcesIndex(i));

                predBlock.addMoveToEnd(result, source);
            }
        }
    }

    private void moveParametersToHighRegisters() {
        int paramWidth = ssaMeth.getParamWidth();
        BasicRegisterMapper mapper
                = new BasicRegisterMapper(ssaMeth.getRegCount());
        int regCount = ssaMeth.getRegCount();

        for (int i = 0; i < regCount; i++) {
            if (i < paramWidth) {
                mapper.addMapping(i, regCount - paramWidth + i, 1);
            } else {
                mapper.addMapping(i, i - paramWidth, 1);
            }
        }

        if (DEBUG) {
            System.out.printf("Moving %d registers from 0 to %d\n",
                    paramWidth, regCount - paramWidth);
        }

        ssaMeth.mapRegisters(mapper);
    }

    private mod.agus.jcoderz.dx.rop.code.BasicBlockList convertBasicBlocks() {
        ArrayList<SsaBasicBlock> blocks = ssaMeth.getBlocks();


        SsaBasicBlock exitBlock = ssaMeth.getExitBlock();

        BitSet reachable = ssaMeth.computeReachability();
        int ropBlockCount = reachable.cardinality();


        if (exitBlock != null && reachable.get(exitBlock.getIndex())) {
            ropBlockCount -= 1;
        }

        mod.agus.jcoderz.dx.rop.code.BasicBlockList result = new BasicBlockList(ropBlockCount);


        int ropBlockIndex = 0;
        for (SsaBasicBlock b : blocks) {
            if (reachable.get(b.getIndex()) && b != exitBlock) {
                result.set(ropBlockIndex++, convertBasicBlock(b));
            }
        }


        if (exitBlock != null && !exitBlock.getInsns().isEmpty()) {
            throw new RuntimeException(
                    "Exit block must have no insns when leaving SSA form");
        }

        return result;
    }

    private void verifyValidExitPredecessor(SsaBasicBlock b) {
        ArrayList<SsaInsn> insns = b.getInsns();
        SsaInsn lastInsn = insns.get(insns.size() - 1);
        mod.agus.jcoderz.dx.rop.code.Rop opcode = lastInsn.getOpcode();

        if (opcode.getBranchingness() != Rop.BRANCH_RETURN
                && opcode != Rops.THROW) {
            throw new RuntimeException("Exit predecessor must end"
                    + " in valid exit statement.");
        }
    }

    private mod.agus.jcoderz.dx.rop.code.BasicBlock convertBasicBlock(SsaBasicBlock block) {
        IntList successorList = block.getRopLabelSuccessorList();
        int primarySuccessorLabel = block.getPrimarySuccessorRopLabel();




        SsaBasicBlock exitBlock = ssaMeth.getExitBlock();
        int exitRopLabel = (exitBlock == null) ? -1 : exitBlock.getRopLabel();

        if (successorList.contains(exitRopLabel)) {
            if (successorList.size() > 1) {
                throw new RuntimeException(
                        "Exit predecessor must have no other successors"
                                + Hex.u2(block.getRopLabel()));
            } else {
                successorList = IntList.EMPTY;
                primarySuccessorLabel = -1;

                verifyValidExitPredecessor(block);
            }
        }

        successorList.setImmutable();

        mod.agus.jcoderz.dx.rop.code.BasicBlock result = new BasicBlock(
                block.getRopLabel(), convertInsns(block.getInsns()),
                successorList,
                primarySuccessorLabel);

        return result;
    }

    private mod.agus.jcoderz.dx.rop.code.InsnList convertInsns(ArrayList<SsaInsn> ssaInsns) {
        int insnCount = ssaInsns.size();
        mod.agus.jcoderz.dx.rop.code.InsnList result = new InsnList(insnCount);

        for (int i = 0; i < insnCount; i++) {
            result.set(i, ssaInsns.get(i).toRopInsn());
        }

        result.setImmutable();

        return result;
    }

    public int[] getRegistersByFrequency() {
        int regCount = ssaMeth.getRegCount();
        Integer[] ret = new Integer[regCount];

        for (int i = 0; i < regCount; i++) {
            ret[i] = i;
        }

        Arrays.sort(ret, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return ssaMeth.getUseListForRegister(o2).size()
                        - ssaMeth.getUseListForRegister(o1).size();
            }
        });

        int result[] = new int[regCount];

        for (int i = 0; i < regCount; i++) {
            result[i] = ret[i];
        }

        return result;
    }
}
