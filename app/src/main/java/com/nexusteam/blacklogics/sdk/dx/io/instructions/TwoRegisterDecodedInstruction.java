
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class TwoRegisterDecodedInstruction extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    private final int a;

    private final int b;

    public TwoRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                         int index, IndexType indexType, int target, long literal,
                                         int a, int b) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.b = b;
    }

    @Override
    public int getRegisterCount() {
        return 2;
    }

    @Override
    public int getA() {
        return a;
    }

    @Override
    public int getB() {
        return b;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        return new TwoRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, b);
    }
}
