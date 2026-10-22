
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.ssa.PhiInsn;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;

public class LivenessAnalyzer {
    private final BitSet visitedBlocks;

    private final BitSet liveOutBlocks;

    private final int regV;

    private final SsaMethod ssaMeth;

    private final mod.agus.jcoderz.dx.ssa.back.InterferenceGraph interference;

    private SsaBasicBlock blockN;

    private int statementIndex;

    private NextFunction nextFunction;

    private static enum NextFunction {
        LIVE_IN_AT_STATEMENT,
            LIVE_OUT_AT_STATEMENT,
            LIVE_OUT_AT_BLOCK,
            DONE;
    }

    public static mod.agus.jcoderz.dx.ssa.back.InterferenceGraph constructInterferenceGraph(
            SsaMethod ssaMeth) {
        int szRegs = ssaMeth.getRegCount();
        mod.agus.jcoderz.dx.ssa.back.InterferenceGraph interference = new mod.agus.jcoderz.dx.ssa.back.InterferenceGraph(szRegs);

        for (int i = 0; i < szRegs; i++) {
            new LivenessAnalyzer(ssaMeth, i, interference).run();
        }

        coInterferePhis(ssaMeth, interference);

        return interference;
    }

    private LivenessAnalyzer(SsaMethod ssaMeth, int reg,
            mod.agus.jcoderz.dx.ssa.back.InterferenceGraph interference) {
        int blocksSz = ssaMeth.getBlocks().size();

        this.ssaMeth = ssaMeth;
        this.regV = reg;
        visitedBlocks = new BitSet(blocksSz);
        liveOutBlocks = new BitSet(blocksSz);
        this.interference = interference;
    }

    private void handleTailRecursion() {
        while (nextFunction != NextFunction.DONE) {
            switch (nextFunction) {
                case LIVE_IN_AT_STATEMENT:
                    nextFunction = NextFunction.DONE;
                    liveInAtStatement();
                    break;

                case LIVE_OUT_AT_STATEMENT:
                    nextFunction = NextFunction.DONE;
                    liveOutAtStatement();
                    break;

                case LIVE_OUT_AT_BLOCK:
                    nextFunction = NextFunction.DONE;
                    liveOutAtBlock();
                    break;

                default:
            }
        }
    }

    public void run() {
        List<SsaInsn> useList = ssaMeth.getUseListForRegister(regV);

        for (SsaInsn insn : useList) {
            nextFunction = NextFunction.DONE;

            if (insn instanceof PhiInsn) {

                PhiInsn phi = (PhiInsn) insn;

                for (SsaBasicBlock pred :
                         phi.predBlocksForReg(regV, ssaMeth)) {
                    blockN = pred;

                    nextFunction = NextFunction.LIVE_OUT_AT_BLOCK;
                    handleTailRecursion();
                }
            } else {
                blockN = insn.getBlock();
                statementIndex = blockN.getInsns().indexOf(insn);

                if (statementIndex < 0) {
                    throw new RuntimeException(
                            "insn not found in it's own block");
                }

                nextFunction = NextFunction.LIVE_IN_AT_STATEMENT;
                handleTailRecursion();
            }
        }

        int nextLiveOutBlock;
        while ((nextLiveOutBlock = liveOutBlocks.nextSetBit(0)) >= 0) {
            blockN = ssaMeth.getBlocks().get(nextLiveOutBlock);
            liveOutBlocks.clear(nextLiveOutBlock);
            nextFunction = NextFunction.LIVE_OUT_AT_BLOCK;
            handleTailRecursion();
        }
    }

    private void liveOutAtBlock() {
        if (! visitedBlocks.get(blockN.getIndex())) {
            visitedBlocks.set(blockN.getIndex());

            blockN.addLiveOut(regV);

            ArrayList<SsaInsn> insns;

            insns = blockN.getInsns();


            statementIndex = insns.size() - 1;
            nextFunction = NextFunction.LIVE_OUT_AT_STATEMENT;
        }
    }

    private void liveInAtStatement() {

        if (statementIndex == 0) {

            blockN.addLiveIn(regV);

            BitSet preds = blockN.getPredecessors();

            liveOutBlocks.or(preds);
        } else {

            statementIndex -= 1;
            nextFunction = NextFunction.LIVE_OUT_AT_STATEMENT;
        }
    }

    private void liveOutAtStatement() {
        SsaInsn statement = blockN.getInsns().get(statementIndex);
        mod.agus.jcoderz.dx.rop.code.RegisterSpec rs = statement.getResult();

        if (!statement.isResultReg(regV)) {
            if (rs != null) {
                interference.add(regV, rs.getReg());
            }
            nextFunction = NextFunction.LIVE_IN_AT_STATEMENT;
        }
    }

    private static void coInterferePhis(SsaMethod ssaMeth,
            mod.agus.jcoderz.dx.ssa.back.InterferenceGraph interference) {
        for (SsaBasicBlock b : ssaMeth.getBlocks()) {
            List<SsaInsn> phis = b.getPhiInsns();

            int szPhis = phis.size();

            for (int i = 0; i < szPhis; i++) {
                for (int j = 0; j < szPhis; j++) {
                    if (i == j) {
                        continue;
                    }

                    SsaInsn first = phis.get(i);
                    SsaInsn second = phis.get(j);
                    coInterferePhiRegisters(interference, first.getResult(), second.getSources());
                    coInterferePhiRegisters(interference, second.getResult(), first.getSources());
                    interference.add(first.getResult().getReg(), second.getResult().getReg());
                }
            }
        }
    }

    private static void coInterferePhiRegisters(InterferenceGraph interference, RegisterSpec result,
                                                RegisterSpecList sources) {
        int resultReg = result.getReg();
        for (int i = 0; i < sources.size(); ++i) {
            interference.add(resultReg, sources.get(i).getReg());
        }
    }
}
