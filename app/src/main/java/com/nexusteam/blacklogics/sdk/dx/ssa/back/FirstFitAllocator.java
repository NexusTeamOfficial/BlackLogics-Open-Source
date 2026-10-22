
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.ssa.BasicRegisterMapper;
import mod.agus.jcoderz.dx.ssa.NormalSsaInsn;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.BitIntSet;
import mod.agus.jcoderz.dx.util.IntSet;
import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.CstInsn;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;

public class FirstFitAllocator extends RegisterAllocator {
    private static final boolean PRESLOT_PARAMS = true;

    private final BitSet mapped;

    public FirstFitAllocator(
            final SsaMethod ssaMeth, final InterferenceGraph interference) {
        super(ssaMeth, interference);

        mapped = new BitSet(ssaMeth.getRegCount());
    }

    @Override
    public boolean wantsParamsMovedHigh() {
        return PRESLOT_PARAMS;
    }

    @Override
    public RegisterMapper allocateRegisters() {
        int oldRegCount = ssaMeth.getRegCount();

        BasicRegisterMapper mapper
                = new BasicRegisterMapper(oldRegCount);

        int nextNewRegister = 0;

        if (PRESLOT_PARAMS) {

            nextNewRegister = ssaMeth.getParamWidth();
        }

        for (int i = 0; i < oldRegCount; i++) {
            if (mapped.get(i)) {

                continue;
            }

            int maxCategory = getCategoryForSsaReg(i);
            IntSet current = new BitIntSet(oldRegCount);

            interference.mergeInterferenceSet(i, current);

            boolean isPreslotted = false;
            int newReg = 0;

            if (PRESLOT_PARAMS && isDefinitionMoveParam(i)) {

                NormalSsaInsn defInsn = (NormalSsaInsn)
                       ssaMeth.getDefinitionForRegister(i);

                newReg = paramNumberFromMoveParam(defInsn);

                mapper.addMapping(i, newReg, maxCategory);
                isPreslotted = true;
            } else {
                mapper.addMapping(i, nextNewRegister, maxCategory);
                newReg = nextNewRegister;
            }

            for (int j = i + 1; j < oldRegCount; j++) {
                if (mapped.get(j) || isDefinitionMoveParam(j)) {
                    continue;
                }

                if (!current.has(j)
                        && !(isPreslotted
                            && (maxCategory < getCategoryForSsaReg(j)))) {

                    interference.mergeInterferenceSet(j, current);

                    maxCategory = Math.max(maxCategory,
                            getCategoryForSsaReg(j));

                    mapper.addMapping(j, newReg, maxCategory);
                    mapped.set(j);
                }
            }

            mapped.set(i);
            if (!isPreslotted) {
                nextNewRegister += maxCategory;
            }
        }

        return mapper;
    }

    private int paramNumberFromMoveParam(NormalSsaInsn ndefInsn) {
        mod.agus.jcoderz.dx.rop.code.CstInsn origInsn = (CstInsn) ndefInsn.getOriginalRopInsn();

        return ((CstInteger) origInsn.getConstant()).getValue();
    }
}
