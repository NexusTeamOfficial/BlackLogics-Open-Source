
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class RegisterRangeDecodedInstruction extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    private final int a;

    private final int registerCount;

    public RegisterRangeDecodedInstruction(InstructionCodec format, int opcode,
                                           int index, IndexType indexType, int target, long literal,
                                           int a, int registerCount) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.registerCount = registerCount;
    }

    @Override
    public int getRegisterCount() {
        return registerCount;
    }

    @Override
    public int getA() {
        return a;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        return new RegisterRangeDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, registerCount);
    }
}
