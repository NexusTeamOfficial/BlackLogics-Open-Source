
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.io.OpcodeInfo;
import mod.agus.jcoderz.dx.io.Opcodes;

public final class Dop {
    private final int opcode;

    private final int family;

    private final int nextOpcode;

    private final InsnFormat format;

    private final boolean hasResult;

    public Dop(int opcode, int family, int nextOpcode, InsnFormat format,
            boolean hasResult) {
        if (!mod.agus.jcoderz.dx.io.Opcodes.isValidShape(opcode)) {
            throw new IllegalArgumentException("bogus opcode");
        }

        if (!mod.agus.jcoderz.dx.io.Opcodes.isValidShape(family)) {
            throw new IllegalArgumentException("bogus family");
        }

        if (!mod.agus.jcoderz.dx.io.Opcodes.isValidShape(nextOpcode)) {
            throw new IllegalArgumentException("bogus nextOpcode");
        }

        if (format == null) {
            throw new NullPointerException("format == null");
        }

        this.opcode = opcode;
        this.family = family;
        this.nextOpcode = nextOpcode;
        this.format = format;
        this.hasResult = hasResult;
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getOpcode() {
        return opcode;
    }

    public int getFamily() {
        return family;
    }

    public InsnFormat getFormat() {
        return format;
    }

    public boolean hasResult() {
        return hasResult;
    }

    public String getName() {
        return OpcodeInfo.getName(opcode);
    }

    public int getNextOpcode() {
        return nextOpcode;
    }

    public Dop getOppositeTest() {
        switch (opcode) {
            case mod.agus.jcoderz.dx.io.Opcodes.IF_EQ:  return Dops.IF_NE;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_NE:  return Dops.IF_EQ;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_LT:  return Dops.IF_GE;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_GE:  return Dops.IF_LT;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_GT:  return Dops.IF_LE;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_LE:  return Dops.IF_GT;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_EQZ: return Dops.IF_NEZ;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_NEZ: return Dops.IF_EQZ;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_LTZ: return Dops.IF_GEZ;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_GEZ: return Dops.IF_LTZ;
            case mod.agus.jcoderz.dx.io.Opcodes.IF_GTZ: return Dops.IF_LEZ;
            case Opcodes.IF_LEZ: return Dops.IF_GTZ;
        }

        throw new IllegalArgumentException("bogus opcode: " + this);
    }
}
