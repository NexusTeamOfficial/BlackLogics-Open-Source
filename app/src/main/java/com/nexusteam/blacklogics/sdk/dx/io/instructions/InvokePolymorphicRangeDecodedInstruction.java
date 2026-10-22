
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public class InvokePolymorphicRangeDecodedInstruction extends mod.agus.jcoderz.dx.io.instructions.DecodedInstruction {

    private final int c;
    private final int registerCount;
    private final int protoIndex;

    public InvokePolymorphicRangeDecodedInstruction(
            InstructionCodec format,
            int opcode,
            int methodIndex,
            IndexType indexType,
            int c,
            int registerCount,
            int protoIndex) {
        super(format, opcode, methodIndex, indexType, 0, 0);
        if (protoIndex != (short) protoIndex) {
          throw new IllegalArgumentException("protoIndex doesn't fit in a short: " + protoIndex);
        }
        this.c = c;
        this.registerCount = registerCount;
        this.protoIndex = protoIndex;
    }

    @Override
    public int getRegisterCount() {
        return registerCount;
    }

    @Override
    public int getC() {
        return c;
    }

    @Override
    public mod.agus.jcoderz.dx.io.instructions.DecodedInstruction withProtoIndex(int newIndex, int newProtoIndex) {
        return new InvokePolymorphicRangeDecodedInstruction(
                getFormat(),
                getOpcode(),
                newIndex,
                getIndexType(),
                c,
                registerCount,
                newProtoIndex);
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        throw new UnsupportedOperationException(
                "use withProtoIndex to update both the method and proto indices for "
                        + "invoke-polymorphic/range");
    }

    @Override
    public short getProtoIndex() {
        return (short) protoIndex;
    }
}
