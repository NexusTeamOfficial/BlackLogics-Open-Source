
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class FiveRegisterDecodedInstruction extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    private final int a;

    private final int b;

    private final int c;

    private final int d;

    private final int e;

    public FiveRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                          int index, IndexType indexType, int target, long literal,
                                          int a, int b, int c, int d, int e) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    @Override
    public int getRegisterCount() {
        return 5;
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
    public int getE() {
        return e;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        return new FiveRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, b, c, d, e);
    }
}
