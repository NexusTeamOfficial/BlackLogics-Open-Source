
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class FourRegisterDecodedInstruction extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    private final int a;

    private final int b;

    private final int c;

    private final int d;

    public FourRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                          int index, IndexType indexType, int target, long literal,
                                          int a, int b, int c, int d) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public int getRegisterCount() {
        return 4;
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
    public int getD() {
        return d;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        return new FourRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, b, c, d);
    }
}
