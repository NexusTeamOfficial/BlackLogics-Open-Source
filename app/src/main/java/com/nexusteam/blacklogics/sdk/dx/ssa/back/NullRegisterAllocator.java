
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.ssa.BasicRegisterMapper;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaMethod;

public class NullRegisterAllocator extends RegisterAllocator {
    public NullRegisterAllocator(SsaMethod ssaMeth,
            InterferenceGraph interference) {
        super(ssaMeth, interference);
    }

    @Override
    public boolean wantsParamsMovedHigh() {

        return false;
    }

    @Override
    public RegisterMapper allocateRegisters() {
        int oldRegCount = ssaMeth.getRegCount();

        BasicRegisterMapper mapper = new BasicRegisterMapper(oldRegCount);

        for (int i = 0; i < oldRegCount; i++) {
            mapper.addMapping(i, i*2, 2);
        }

        return mapper;
    }
}
