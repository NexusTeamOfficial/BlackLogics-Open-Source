
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class SimpleInsn extends FixedSizeInsn {
    public SimpleInsn(mod.agus.jcoderz.dx.dex.code.Dop opcode, SourcePosition position,
                      mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers) {
        super(opcode, position, registers);
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withOpcode(Dop opcode) {
        return new SimpleInsn(opcode, getPosition(), getRegisters());
    }

    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new SimpleInsn(getOpcode(), getPosition(), registers);
    }

    @Override
    protected String argString() {
        return null;
    }
}
