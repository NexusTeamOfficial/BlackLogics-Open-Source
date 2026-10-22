
package mod.agus.jcoderz.dx.dex.cf;

import mod.agus.jcoderz.dx.dex.code.DalvCode;

import java.io.PrintStream;

import mod.agus.jcoderz.dx.rop.code.RopMethod;

public final class CodeStatistics {
    private static final boolean DEBUG = false;

    public int runningDeltaRegisters = 0;

    public int runningDeltaInsns = 0;

    public int runningTotalInsns = 0;

    public int dexRunningDeltaRegisters = 0;

    public int dexRunningDeltaInsns = 0;

    public int dexRunningTotalInsns = 0;

    public int runningOriginalBytes = 0;

    public void updateOriginalByteCount(int count) {
        runningOriginalBytes += count;
    }

    public void updateDexStatistics(DalvCode nonOptCode,
            DalvCode code) {
        if (DEBUG) {
            System.err.println("dex insns (old/new) "
                    + nonOptCode.getInsns().codeSize()
                    + "/" + code.getInsns().codeSize()
                    + " regs (o/n) "
                    + nonOptCode.getInsns().getRegistersSize()
                    + "/" + code.getInsns().getRegistersSize()
            );
        }

        dexRunningDeltaInsns
            += (code.getInsns().codeSize()
                - nonOptCode.getInsns().codeSize());

        dexRunningDeltaRegisters
            += (code.getInsns().getRegistersSize()
                - nonOptCode.getInsns().getRegistersSize());

        dexRunningTotalInsns += code.getInsns().codeSize();
    }

    public void updateRopStatistics(mod.agus.jcoderz.dx.rop.code.RopMethod nonOptRmeth,
                                    RopMethod rmeth) {
        int oldCountInsns
                = nonOptRmeth.getBlocks().getEffectiveInstructionCount();
        int oldCountRegs = nonOptRmeth.getBlocks().getRegCount();

        if (DEBUG) {
            System.err.println("insns (old/new): "
                    + oldCountInsns + "/"
                    + rmeth.getBlocks().getEffectiveInstructionCount()
                    + " regs (o/n):" + oldCountRegs
                    + "/"  +  rmeth.getBlocks().getRegCount());
        }

        int newCountInsns
                = rmeth.getBlocks().getEffectiveInstructionCount();

        runningDeltaInsns
            += (newCountInsns - oldCountInsns);

        runningDeltaRegisters
            += (rmeth.getBlocks().getRegCount() - oldCountRegs);

        runningTotalInsns += newCountInsns;
    }

    public void dumpStatistics(PrintStream out) {
        out.printf("Optimizer Delta Rop Insns: %d total: %d "
                + "(%.2f%%) Delta Registers: %d\n",
                runningDeltaInsns,
                runningTotalInsns,
                (100.0 * (((float) runningDeltaInsns)
                        / (runningTotalInsns + Math.abs(runningDeltaInsns)))),
                runningDeltaRegisters);

        out.printf("Optimizer Delta Dex Insns: Insns: %d total: %d "
                + "(%.2f%%) Delta Registers: %d\n",
                dexRunningDeltaInsns,
                dexRunningTotalInsns,
                (100.0 * (((float) dexRunningDeltaInsns)
                        / (dexRunningTotalInsns
                                + Math.abs(dexRunningDeltaInsns)))),
                dexRunningDeltaRegisters);

        out.printf("Original bytecode byte count: %d\n",
                runningOriginalBytes);
    }
}
