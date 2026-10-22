
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class OneRegisterDecodedInstruction extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    private final int a;

    public OneRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                         int index, IndexType indexType, int target, long literal,
                                         int a) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
    }

    @Override
    public int getRegisterCount() {
        return 1;
    }

    @Override
    public int getA() {
        return a;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        return new OneRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a);
    }
}
