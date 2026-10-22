
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class ThreeRegisterDecodedInstruction extends DecodedInstruction {
    private final int a;

    private final int b;

    private final int c;

    public ThreeRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                           int index, IndexType indexType, int target, long literal,
                                           int a, int b, int c) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public int getRegisterCount() {
        return 3;
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
    public int getC() {
        return c;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        return new ThreeRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, b, c);
    }
}
