
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class FixedSizeInsn extends mod.agus.jcoderz.dx.dex.code.DalvInsn {
    public FixedSizeInsn(Dop opcode, SourcePosition position,
                         RegisterSpecList registers) {
        super(opcode, position, registers);
    }

    @Override
    public final int codeSize() {
        return getOpcode().getFormat().codeSize();
    }

    @Override
    public final void writeTo(AnnotatedOutput out) {
        getOpcode().getFormat().writeTo(out, this);
    }

    @Override
    public final DalvInsn withRegisterOffset(int delta) {
        return withRegisters(getRegisters().withOffset(delta));
    }

    @Override
    protected final String listingString0(boolean noteIndices) {
        return getOpcode().getFormat().listingString(this, noteIndices);
    }
}
