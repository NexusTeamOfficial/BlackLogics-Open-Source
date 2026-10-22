
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class ZeroRegisterDecodedInstruction extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    public ZeroRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                          int index, IndexType indexType, int target, long literal) {
        super(format, opcode, index, indexType, target, literal);
    }

    @Override
    public int getRegisterCount() {
        return 0;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        return new ZeroRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral());
    }
}
