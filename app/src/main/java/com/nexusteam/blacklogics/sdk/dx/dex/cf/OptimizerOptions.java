
package mod.agus.jcoderz.dx.dex.cf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;

import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.ssa.Optimizer;

public class OptimizerOptions {
    private HashSet<String> optimizeList;

    private HashSet<String> dontOptimizeList;

    private boolean optimizeListsLoaded;


    public void loadOptimizeLists(String optimizeListFile,
            String dontOptimizeListFile) {
        if (optimizeListsLoaded) {
            return;
        }

        if (optimizeListFile != null && dontOptimizeListFile != null) {
            throw new RuntimeException("optimize and don't optimize lists "
                    + " are mutually exclusive.");
        }

        if (optimizeListFile != null) {
            optimizeList = loadStringsFromFile(optimizeListFile);
        }

        if (dontOptimizeListFile != null) {
            dontOptimizeList = loadStringsFromFile(dontOptimizeListFile);
        }

        optimizeListsLoaded = true;
    }

    private static HashSet<String> loadStringsFromFile(String filename) {
        HashSet<String> result = new HashSet<String>();

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);

            String line;

            while (null != (line = bfr.readLine())) {
                result.add(line);
            }

            fr.close();
        } catch (IOException ex) {

            throw new RuntimeException("Error with optimize list: " +
                    filename, ex);
        }

        return result;
    }

    public void compareOptimizerStep(mod.agus.jcoderz.dx.rop.code.RopMethod nonOptRmeth,
                                     int paramSize, boolean isStatic, CfOptions args,
                                     TranslationAdvice advice, mod.agus.jcoderz.dx.rop.code.RopMethod rmeth) {
        EnumSet<mod.agus.jcoderz.dx.ssa.Optimizer.OptionalStep> steps;

        steps = EnumSet.allOf(mod.agus.jcoderz.dx.ssa.Optimizer.OptionalStep.class);


        steps.remove(mod.agus.jcoderz.dx.ssa.Optimizer.OptionalStep.CONST_COLLECTOR);

        RopMethod skipRopMethod
                = Optimizer.optimize(nonOptRmeth,
                        paramSize, isStatic, args.localInfo, advice, steps);

        int normalInsns
                = rmeth.getBlocks().getEffectiveInstructionCount();
        int skipInsns
                = skipRopMethod.getBlocks().getEffectiveInstructionCount();

        System.err.printf(
                "optimize step regs:(%d/%d/%.2f%%)"
                + " insns:(%d/%d/%.2f%%)\n",
                rmeth.getBlocks().getRegCount(),
                skipRopMethod.getBlocks().getRegCount(),
                100.0 * ((skipRopMethod.getBlocks().getRegCount()
                        - rmeth.getBlocks().getRegCount())
                        / (float) skipRopMethod.getBlocks().getRegCount()),
                normalInsns, skipInsns,
                100.0 * ((skipInsns - normalInsns) / (float) skipInsns));
    }

    public boolean shouldOptimize(String canonicalMethodName) {

        if (optimizeList != null) {
            return optimizeList.contains(canonicalMethodName);
        }


        if (dontOptimizeList != null) {
            return !dontOptimizeList.contains(canonicalMethodName);
        }


        return true;
    }
}
