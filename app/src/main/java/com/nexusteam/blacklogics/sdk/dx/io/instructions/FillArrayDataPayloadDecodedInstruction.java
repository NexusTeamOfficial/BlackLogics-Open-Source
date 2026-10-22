
package mod.agus.jcoderz.dx.io.instructions;

public final class FillArrayDataPayloadDecodedInstruction
        extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {
    private final Object data;

    private final int size;

    private final int elementWidth;

    private FillArrayDataPayloadDecodedInstruction(mod.agus.jcoderz.dx.io.instructions.InstructionCodec format,
                                                   int opcode, Object data, int size, int elementWidth) {
        super(format, opcode, 0, null, 0, 0L);

        this.data = data;
        this.size = size;
        this.elementWidth = elementWidth;
    }

    public FillArrayDataPayloadDecodedInstruction(mod.agus.jcoderz.dx.io.instructions.InstructionCodec format,
                                                  int opcode, byte[] data) {
        this(format, opcode, data, data.length, 1);
    }

    public FillArrayDataPayloadDecodedInstruction(mod.agus.jcoderz.dx.io.instructions.InstructionCodec format,
                                                  int opcode, short[] data) {
        this(format, opcode, data, data.length, 2);
    }

    public FillArrayDataPayloadDecodedInstruction(mod.agus.jcoderz.dx.io.instructions.InstructionCodec format,
                                                  int opcode, int[] data) {
        this(format, opcode, data, data.length, 4);
    }

    public FillArrayDataPayloadDecodedInstruction(InstructionCodec format,
                                                  int opcode, long[] data) {
        this(format, opcode, data, data.length, 8);
    }

    @Override
    public int getRegisterCount() {
        return 0;
    }

    public short getElementWidthUnit() {
        return (short) elementWidth;
    }

    public int getSize() {
        return size;
    }

    public Object getData() {
        return data;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        throw new UnsupportedOperationException("no index in instruction");
    }
}
