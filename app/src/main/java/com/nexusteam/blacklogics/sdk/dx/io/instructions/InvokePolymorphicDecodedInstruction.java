
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public class InvokePolymorphicDecodedInstruction extends DecodedInstruction {

    private final int protoIndex;
    private final int[] registers;

    public InvokePolymorphicDecodedInstruction(
            InstructionCodec format,
            int opcode,
            int methodIndex,
            IndexType indexType,
            int protoIndex,
            int[] registers) {
        super(format, opcode, methodIndex, indexType, 0, 0);
        if (protoIndex != (short) protoIndex) {
          throw new IllegalArgumentException("protoIndex doesn't fit in a short: " + protoIndex);
        }
        this.protoIndex = protoIndex;
        this.registers = registers;
    }

    @Override
    public int getRegisterCount() {
        return registers.length;
    }

    @Override
    public DecodedInstruction withIndex(int newIndex) {
        throw new UnsupportedOperationException(
                "use withProtoIndex to update both the method and proto indices for"
                        + " invoke-polymorphic");
    }

    @Override
    public DecodedInstruction withProtoIndex(int newIndex, int newProtoIndex) {
        return new InvokePolymorphicDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(), newProtoIndex, registers);
    }

    @Override
    public int getC() {
        return registers.length > 0 ? registers[0] : 0;
    }

    @Override
    public int getD() {
        return registers.length > 1 ? registers[1] : 0;
    }

    @Override
    public int getE() {
        return registers.length > 2 ? registers[2] : 0;
    }

    public int getF() {
        return registers.length > 3 ? registers[3] : 0;
    }

    public int getG() {
        return registers.length > 4 ? registers[4] : 0;
    }

    @Override
    public short getProtoIndex() {
        return (short) protoIndex;
    }
}
