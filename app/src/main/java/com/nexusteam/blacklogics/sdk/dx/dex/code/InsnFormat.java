
package mod.agus.jcoderz.dx.dex.code;

import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstLiteral64;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public abstract class InsnFormat {
    public static final boolean ALLOW_EXTENDED_OPCODES = true;

    public final String listingString(mod.agus.jcoderz.dx.dex.code.DalvInsn insn, boolean noteIndices) {
        String op = insn.getOpcode().getName();
        String arg = insnArgString(insn);
        String comment = insnCommentString(insn, noteIndices);
        StringBuilder sb = new StringBuilder(100);

        sb.append(op);

        if (arg.length() != 0) {
            sb.append(' ');
            sb.append(arg);
        }

        if (comment.length() != 0) {
            sb.append(" // ");
            sb.append(comment);
        }

        return sb.toString();
    }

    public abstract String insnArgString(mod.agus.jcoderz.dx.dex.code.DalvInsn insn);

    public abstract String insnCommentString(mod.agus.jcoderz.dx.dex.code.DalvInsn insn,
                                             boolean noteIndices);

    public abstract int codeSize();

    public abstract boolean isCompatible(mod.agus.jcoderz.dx.dex.code.DalvInsn insn);

    public BitSet compatibleRegs(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        return new BitSet();
    }

    public boolean branchFits(mod.agus.jcoderz.dx.dex.code.TargetInsn insn) {
        return false;
    }

    public abstract void writeTo(mod.agus.jcoderz.dx.util.AnnotatedOutput out, mod.agus.jcoderz.dx.dex.code.DalvInsn insn);

    protected static String regListString(mod.agus.jcoderz.dx.rop.code.RegisterSpecList list) {
        int sz = list.size();
        StringBuilder sb = new StringBuilder(sz * 5 + 2);

        sb.append('{');

        for (int i = 0; i < sz; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(list.get(i).regString());
        }

        sb.append('}');

        return sb.toString();
    }

    protected static String regRangeString(mod.agus.jcoderz.dx.rop.code.RegisterSpecList list) {
        int size = list.size();
        StringBuilder sb = new StringBuilder(30);

        sb.append("{");

        switch (size) {
            case 0: {

                break;
            }
            case 1: {
                sb.append(list.get(0).regString());
                break;
            }
            default: {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec lastReg = list.get(size - 1);
                if (lastReg.getCategory() == 2) {
                    lastReg = lastReg.withOffset(1);
                }

                sb.append(list.get(0).regString());
                sb.append("..");
                sb.append(lastReg.regString());
            }
        }

        sb.append("}");

        return sb.toString();
    }

    protected static String literalBitsString(mod.agus.jcoderz.dx.rop.cst.CstLiteralBits value) {
        StringBuilder sb = new StringBuilder(100);

        sb.append('#');

        if (value instanceof CstKnownNull) {
            sb.append("null");
        } else {
            sb.append(value.typeName());
            sb.append(' ');
            sb.append(value.toHuman());
        }

        return sb.toString();
    }

    protected static String literalBitsComment(CstLiteralBits value,
                                               int width) {
        StringBuilder sb = new StringBuilder(20);

        sb.append("#");

        long bits;

        if (value instanceof mod.agus.jcoderz.dx.rop.cst.CstLiteral64) {
            bits = ((CstLiteral64) value).getLongBits();
        } else {
            bits = value.getIntBits();
        }

        switch (width) {
            case 4:  sb.append(mod.agus.jcoderz.dx.util.Hex.uNibble((int) bits)); break;
            case 8:  sb.append(mod.agus.jcoderz.dx.util.Hex.u1((int) bits));      break;
            case 16: sb.append(mod.agus.jcoderz.dx.util.Hex.u2((int) bits));      break;
            case 32: sb.append(mod.agus.jcoderz.dx.util.Hex.u4((int) bits));      break;
            case 64: sb.append(mod.agus.jcoderz.dx.util.Hex.u8(bits));            break;
            default: {
                throw new RuntimeException("shouldn't happen");
            }
        }

        return sb.toString();
    }

    protected static String branchString(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        mod.agus.jcoderz.dx.dex.code.TargetInsn ti = (mod.agus.jcoderz.dx.dex.code.TargetInsn) insn;
        int address = ti.getTargetAddress();

        return (address == (char) address) ? mod.agus.jcoderz.dx.util.Hex.u2(address) : mod.agus.jcoderz.dx.util.Hex.u4(address);
    }

    protected static String branchComment(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        mod.agus.jcoderz.dx.dex.code.TargetInsn ti = (TargetInsn) insn;
        int offset = ti.getTargetOffset();

        return (offset == (short) offset) ? mod.agus.jcoderz.dx.util.Hex.s2(offset) : Hex.s4(offset);
    }

    protected static boolean signedFitsInNibble(int value) {
        return (value >= -8) && (value <= 7);
    }

    protected static boolean unsignedFitsInNibble(int value) {
        return value == (value & 0xf);
    }

    protected static boolean signedFitsInByte(int value) {
        return (byte) value == value;
    }

    protected static boolean unsignedFitsInByte(int value) {
        return value == (value & 0xff);
    }

    protected static boolean signedFitsInShort(int value) {
        return (short) value == value;
    }

    protected static boolean unsignedFitsInShort(int value) {
        return value == (value & 0xffff);
    }

    protected static boolean isRegListSequential(RegisterSpecList list) {
        int sz = list.size();

        if (sz < 2) {
            return true;
        }

        int first = list.get(0).getReg();
        int next = first;

        for (int i = 0; i < sz; i++) {
            RegisterSpec one = list.get(i);
            if (one.getReg() != next) {
                return false;
            }
            next += one.getCategory();
        }

        return true;
    }

    protected static int argIndex(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        int arg = ((CstInteger) ((CstInsn) insn).getConstant()).getValue();

        if (arg < 0) {
            throw new IllegalArgumentException("bogus insn");
        }

        return arg;
    }

    protected static short opcodeUnit(mod.agus.jcoderz.dx.dex.code.DalvInsn insn, int arg) {
        if ((arg & 0xff) != arg) {
            throw new IllegalArgumentException("arg out of range 0..255");
        }

        int opcode = insn.getOpcode().getOpcode();

        if ((opcode & 0xff) != opcode) {
            throw new IllegalArgumentException("opcode out of range 0..255");
        }

        return (short) (opcode | (arg << 8));
    }

    protected static short opcodeUnit(DalvInsn insn) {
        int opcode = insn.getOpcode().getOpcode();

        if ((opcode < 0x100) || (opcode > 0xffff)) {
            throw new IllegalArgumentException("opcode out of range 0..65535");
        }

        return (short) opcode;
    }

    protected static short codeUnit(int low, int high) {
        if ((low & 0xff) != low) {
            throw new IllegalArgumentException("low out of range 0..255");
        }

        if ((high & 0xff) != high) {
            throw new IllegalArgumentException("high out of range 0..255");
        }

        return (short) (low | (high << 8));
    }

    protected static short codeUnit(int n0, int n1, int n2, int n3) {
        if ((n0 & 0xf) != n0) {
            throw new IllegalArgumentException("n0 out of range 0..15");
        }

        if ((n1 & 0xf) != n1) {
            throw new IllegalArgumentException("n1 out of range 0..15");
        }

        if ((n2 & 0xf) != n2) {
            throw new IllegalArgumentException("n2 out of range 0..15");
        }

        if ((n3 & 0xf) != n3) {
            throw new IllegalArgumentException("n3 out of range 0..15");
        }

        return (short) (n0 | (n1 << 4) | (n2 << 8) | (n3 << 12));
    }

    protected static int makeByte(int low, int high) {
        if ((low & 0xf) != low) {
            throw new IllegalArgumentException("low out of range 0..15");
        }

        if ((high & 0xf) != high) {
            throw new IllegalArgumentException("high out of range 0..15");
        }

        return low | (high << 4);
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0) {
        out.writeShort(c0);
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0, short c1) {
        out.writeShort(c0);
        out.writeShort(c1);
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0, short c1,
                                short c2) {
        out.writeShort(c0);
        out.writeShort(c1);
        out.writeShort(c2);
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0, short c1,
                                short c2, short c3) {
        out.writeShort(c0);
        out.writeShort(c1);
        out.writeShort(c2);
        out.writeShort(c3);
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0, short c1,
                                short c2, short c3, short c4) {
        out.writeShort(c0);
        out.writeShort(c1);
        out.writeShort(c2);
        out.writeShort(c3);
        out.writeShort(c4);
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0, int c1c2) {
        write(out, c0, (short) c1c2, (short) (c1c2 >> 16));
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0, int c1c2,
                                short c3) {
        write(out, c0, (short) c1c2, (short) (c1c2 >> 16), c3);
    }

    protected static void write(mod.agus.jcoderz.dx.util.AnnotatedOutput out, short c0, int c1c2,
                                short c3, short c4) {
        write(out, c0, (short) c1c2, (short) (c1c2 >> 16), c3, c4);
    }

    protected static void write(AnnotatedOutput out, short c0, long c1c2c3c4) {
        write(out, c0, (short) c1c2c3c4, (short) (c1c2c3c4 >> 16),
                (short) (c1c2c3c4 >> 32), (short) (c1c2c3c4 >> 48));
    }
}
