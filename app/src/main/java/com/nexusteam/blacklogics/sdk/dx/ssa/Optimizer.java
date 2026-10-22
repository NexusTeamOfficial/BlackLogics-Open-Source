
package mod.agus.jcoderz.dx.ssa;

import java.util.EnumSet;

import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.ssa.back.LivenessAnalyzer;
import mod.agus.jcoderz.dx.ssa.back.SsaToRop;

public class Optimizer {
    private static boolean preserveLocals = true;

    private static mod.agus.jcoderz.dx.rop.code.TranslationAdvice advice;

    public enum OptionalStep {
        MOVE_PARAM_COMBINER, SCCP, LITERAL_UPGRADE, CONST_COLLECTOR,
            ESCAPE_ANALYSIS
    }

    public static boolean getPreserveLocals() {
        return preserveLocals;
    }

    public static mod.agus.jcoderz.dx.rop.code.TranslationAdvice getAdvice() {
        return advice;
    }

    public static mod.agus.jcoderz.dx.rop.code.RopMethod optimize(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth, int paramWidth,
                                                                  boolean isStatic, boolean inPreserveLocals,
                                                                  mod.agus.jcoderz.dx.rop.code.TranslationAdvice inAdvice) {

        return optimize(rmeth, paramWidth, isStatic, inPreserveLocals, inAdvice,
                EnumSet.allOf(OptionalStep.class));
    }

    public static mod.agus.jcoderz.dx.rop.code.RopMethod optimize(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth, int paramWidth,
                                                                  boolean isStatic, boolean inPreserveLocals,
                                                                  mod.agus.jcoderz.dx.rop.code.TranslationAdvice inAdvice, EnumSet<OptionalStep> steps) {
        mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth = null;

        preserveLocals = inPreserveLocals;
        advice = inAdvice;

        ssaMeth = SsaConverter.convertToSsaMethod(rmeth, paramWidth, isStatic);
        runSsaFormSteps(ssaMeth, steps);

        mod.agus.jcoderz.dx.rop.code.RopMethod resultMeth = mod.agus.jcoderz.dx.ssa.back.SsaToRop.convertToRopMethod(ssaMeth, false);

        if (resultMeth.getBlocks().getRegCount()
                > advice.getMaxOptimalRegisterCount()) {

            resultMeth = optimizeMinimizeRegisters(rmeth, paramWidth, isStatic,
                    steps);
        }
        return resultMeth;
    }

    private static mod.agus.jcoderz.dx.rop.code.RopMethod optimizeMinimizeRegisters(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth,
                                                                                    int paramWidth, boolean isStatic,
                                                                                    EnumSet<OptionalStep> steps) {
        mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;
        mod.agus.jcoderz.dx.rop.code.RopMethod resultMeth;

        ssaMeth = SsaConverter.convertToSsaMethod(
                rmeth, paramWidth, isStatic);

        EnumSet<OptionalStep> newSteps = steps.clone();

        newSteps.remove(OptionalStep.CONST_COLLECTOR);

        runSsaFormSteps(ssaMeth, newSteps);

        resultMeth = SsaToRop.convertToRopMethod(ssaMeth, true);
        return resultMeth;
    }

    private static void runSsaFormSteps(mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth,
                                        EnumSet<OptionalStep> steps) {
        boolean needsDeadCodeRemover = true;

        if (steps.contains(OptionalStep.MOVE_PARAM_COMBINER)) {
            MoveParamCombiner.process(ssaMeth);
        }

        if (steps.contains(OptionalStep.SCCP)) {
            SCCP.process(ssaMeth);
            DeadCodeRemover.process(ssaMeth);
            needsDeadCodeRemover = false;
        }

        if (steps.contains(OptionalStep.LITERAL_UPGRADE)) {
            LiteralOpUpgrader.process(ssaMeth);
            DeadCodeRemover.process(ssaMeth);
            needsDeadCodeRemover = false;
        }

        steps.remove(OptionalStep.ESCAPE_ANALYSIS);
        if (steps.contains(OptionalStep.ESCAPE_ANALYSIS)) {
            EscapeAnalysis.process(ssaMeth);
            DeadCodeRemover.process(ssaMeth);
            needsDeadCodeRemover = false;
        }

        if (steps.contains(OptionalStep.CONST_COLLECTOR)) {
            ConstCollector.process(ssaMeth);
            DeadCodeRemover.process(ssaMeth);
            needsDeadCodeRemover = false;
        }


        if (needsDeadCodeRemover) {
            DeadCodeRemover.process(ssaMeth);
        }

        PhiTypeResolver.process(ssaMeth);
    }

    public static mod.agus.jcoderz.dx.ssa.SsaMethod debugEdgeSplit(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth, int paramWidth,
                                                                   boolean isStatic, boolean inPreserveLocals,
                                                                   mod.agus.jcoderz.dx.rop.code.TranslationAdvice inAdvice) {

        preserveLocals = inPreserveLocals;
        advice = inAdvice;

        return SsaConverter.testEdgeSplit(rmeth, paramWidth, isStatic);
    }

    public static mod.agus.jcoderz.dx.ssa.SsaMethod debugPhiPlacement(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth, int paramWidth,
                                                                      boolean isStatic, boolean inPreserveLocals,
                                                                      mod.agus.jcoderz.dx.rop.code.TranslationAdvice inAdvice) {

        preserveLocals = inPreserveLocals;
        advice = inAdvice;

        return SsaConverter.testPhiPlacement(rmeth, paramWidth, isStatic);
    }

    public static mod.agus.jcoderz.dx.ssa.SsaMethod debugRenaming(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth, int paramWidth,
                                                                  boolean isStatic, boolean inPreserveLocals,
                                                                  mod.agus.jcoderz.dx.rop.code.TranslationAdvice inAdvice) {

        preserveLocals = inPreserveLocals;
        advice = inAdvice;

        return SsaConverter.convertToSsaMethod(rmeth, paramWidth, isStatic);
    }

    public static mod.agus.jcoderz.dx.ssa.SsaMethod debugDeadCodeRemover(mod.agus.jcoderz.dx.rop.code.RopMethod rmeth,
                                                                         int paramWidth, boolean isStatic, boolean inPreserveLocals,
                                                                         mod.agus.jcoderz.dx.rop.code.TranslationAdvice inAdvice) {

        mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;

        preserveLocals = inPreserveLocals;
        advice = inAdvice;

        ssaMeth = SsaConverter.convertToSsaMethod(rmeth, paramWidth, isStatic);
        DeadCodeRemover.process(ssaMeth);

        return ssaMeth;
    }

    public static mod.agus.jcoderz.dx.ssa.SsaMethod debugNoRegisterAllocation(RopMethod rmeth,
                                                                              int paramWidth, boolean isStatic, boolean inPreserveLocals,
                                                                              TranslationAdvice inAdvice, EnumSet<OptionalStep> steps) {

        SsaMethod ssaMeth;

        preserveLocals = inPreserveLocals;
        advice = inAdvice;

        ssaMeth = SsaConverter.convertToSsaMethod(rmeth, paramWidth, isStatic);

        runSsaFormSteps(ssaMeth, steps);

        LivenessAnalyzer.constructInterferenceGraph(ssaMeth);

        return ssaMeth;
    }
}
