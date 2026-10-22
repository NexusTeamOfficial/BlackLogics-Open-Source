
package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dx.io.IndexType;
import mod.agus.jcoderz.dx.io.OpcodeInfo;
import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.util.Hex;

import java.io.EOFException;

public abstract class DecodedInstruction {
    private final mod.agus.jcoderz.dx.io.instructions.InstructionCodec format;

    private final int opcode;

    private final int index;

    private final mod.agus.jcoderz.dx.io.IndexType indexType;

    private final int target;

    private final long literal;

    public static DecodedInstruction decode(CodeInput in) throws EOFException {
        int opcodeUnit = in.read();
        int opcode = mod.agus.jcoderz.dx.io.Opcodes.extractOpcodeFromUnit(opcodeUnit);
        mod.agus.jcoderz.dx.io.instructions.InstructionCodec format = OpcodeInfo.getFormat(opcode);

        return format.decode(opcodeUnit, in);
    }

    public static DecodedInstruction[] decodeAll(short[] encodedInstructions) {
        int size = encodedInstructions.length;
        DecodedInstruction[] decoded = new DecodedInstruction[size];
        mod.agus.jcoderz.dx.io.instructions.ShortArrayCodeInput in = new ShortArrayCodeInput(encodedInstructions);

        try {
            while (in.hasMore()) {
                decoded[in.cursor()] = DecodedInstruction.decode(in);
            }
        } catch (EOFException ex) {
            throw new DexException(ex);
        }

        return decoded;
    }

    public DecodedInstruction(mod.agus.jcoderz.dx.io.instructions.InstructionCodec format, int opcode,
                              int index, mod.agus.jcoderz.dx.io.IndexType indexType, int target, long literal) {
        if (format == null) {
            throw new NullPointerException("format == null");
        }

        if (!Opcodes.isValidShape(opcode)) {
            throw new IllegalArgumentException("invalid opcode");
        }

        this.format = format;
        this.opcode = opcode;
        this.index = index;
        this.indexType = indexType;
        this.target = target;
        this.literal = literal;
    }

    public final InstructionCodec getFormat() {
        return format;
    }

    public final int getOpcode() {
        return opcode;
    }

    public final short getOpcodeUnit() {
        return (short) opcode;
    }

    public final int getIndex() {
        return index;
    }

    public final short getIndexUnit() {
        return (short) index;
    }

    public final IndexType getIndexType() {
        return indexType;
    }

    public final int getTarget() {
        return target;
    }

    public final int getTarget(int baseAddress) {
        return target - baseAddress;
    }

    public final short getTargetUnit(int baseAddress) {
        int relativeTarget = getTarget(baseAddress);

        if (relativeTarget != (short) relativeTarget) {
            throw new DexException("Target out of range: "
                    + mod.agus.jcoderz.dx.util.Hex.s4(relativeTarget));
        }

        return (short) relativeTarget;
    }

    public final int getTargetByte(int baseAddress) {
        int relativeTarget = getTarget(baseAddress);

        if (relativeTarget != (byte) relativeTarget) {
            throw new DexException("Target out of range: "
                    + mod.agus.jcoderz.dx.util.Hex.s4(relativeTarget));
        }

        return relativeTarget & 0xff;
    }

    public final long getLiteral() {
        return literal;
    }

    public final int getLiteralInt() {
        if (literal != (int) literal) {
            throw new DexException("Literal out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(literal));
        }

        return (int) literal;
    }

    public final short getLiteralUnit() {
        if (literal != (short) literal) {
            throw new DexException("Literal out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(literal));
        }

        return (short) literal;
    }

    public final int getLiteralByte() {
        if (literal != (byte) literal) {
            throw new DexException("Literal out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(literal));
        }

        return (int) literal & 0xff;
    }

    public final int getLiteralNibble() {
        if ((literal < -8) || (literal > 7)) {
            throw new DexException("Literal out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(literal));
        }

        return (int) literal & 0xf;
    }

    public abstract int getRegisterCount();

    public int getA() {
        return 0;
    }

    public int getB() {
        return 0;
    }

    public int getC() {
        return 0;
    }

    public int getD() {
        return 0;
    }

    public int getE() {
        return 0;
    }

    public final short getRegisterCountUnit() {
        int registerCount = getRegisterCount();

        if ((registerCount & ~0xffff) != 0) {
            throw new DexException("Register count out of range: "
                    + mod.agus.jcoderz.dx.util.Hex.u8(registerCount));
        }

        return (short) registerCount;
    }

    public final short getAUnit() {
        int a = getA();

        if ((a & ~0xffff) != 0) {
            throw new DexException("Register A out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(a));
        }

        return (short) a;
    }

    public final short getAByte() {
        int a = getA();

        if ((a & ~0xff) != 0) {
            throw new DexException("Register A out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(a));
        }

        return (short) a;
    }

    public final short getANibble() {
        int a = getA();

        if ((a & ~0xf) != 0) {
            throw new DexException("Register A out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(a));
        }

        return (short) a;
    }

    public final short getBUnit() {
        int b = getB();

        if ((b & ~0xffff) != 0) {
            throw new DexException("Register B out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(b));
        }

        return (short) b;
    }

    public final short getBByte() {
        int b = getB();

        if ((b & ~0xff) != 0) {
            throw new DexException("Register B out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(b));
        }

        return (short) b;
    }

    public final short getBNibble() {
        int b = getB();

        if ((b & ~0xf) != 0) {
            throw new DexException("Register B out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(b));
        }

        return (short) b;
    }

    public final short getCUnit() {
        int c = getC();

        if ((c & ~0xffff) != 0) {
            throw new DexException("Register C out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(c));
        }

        return (short) c;
    }

    public final short getCByte() {
        int c = getC();

        if ((c & ~0xff) != 0) {
            throw new DexException("Register C out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(c));
        }

        return (short) c;
    }

    public final short getCNibble() {
        int c = getC();

        if ((c & ~0xf) != 0) {
            throw new DexException("Register C out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(c));
        }

        return (short) c;
    }

    public final short getDUnit() {
        int d = getD();

        if ((d & ~0xffff) != 0) {
            throw new DexException("Register D out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(d));
        }

        return (short) d;
    }

    public final short getDByte() {
        int d = getD();

        if ((d & ~0xff) != 0) {
            throw new DexException("Register D out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(d));
        }

        return (short) d;
    }

    public final short getDNibble() {
        int d = getD();

        if ((d & ~0xf) != 0) {
            throw new DexException("Register D out of range: " + mod.agus.jcoderz.dx.util.Hex.u8(d));
        }

        return (short) d;
    }

    public final short getENibble() {
        int e = getE();

        if ((e & ~0xf) != 0) {
            throw new DexException("Register E out of range: " + Hex.u8(e));
        }

        return (short) e;
    }

    public final void encode(CodeOutput out) {
        format.encode(this, out);
    }

    public abstract DecodedInstruction withIndex(int newIndex);

    public DecodedInstruction withProtoIndex(int newIndex, int newProtoIndex) {
        throw new IllegalStateException(getClass().toString());
    }

    public short getProtoIndex() {
        throw new IllegalStateException(getClass().toString());
    }
}
