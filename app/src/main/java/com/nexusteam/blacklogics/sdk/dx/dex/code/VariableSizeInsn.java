
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public abstract class VariableSizeInsn extends mod.agus.jcoderz.dx.dex.code.DalvInsn {
    public VariableSizeInsn(SourcePosition position,
                            RegisterSpecList registers) {
        super(Dops.SPECIAL_FORMAT, position, registers);
    }

    @Override
    public final mod.agus.jcoderz.dx.dex.code.DalvInsn withOpcode(Dop opcode) {
        throw new RuntimeException("unsupported");
    }

    @Override
    public final DalvInsn withRegisterOffset(int delta) {
        return withRegisters(getRegisters().withOffset(delta));
    }
}
