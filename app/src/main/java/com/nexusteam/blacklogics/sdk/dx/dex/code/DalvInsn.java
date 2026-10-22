
package mod.agus.jcoderz.dx.dex.code;

import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.TwoColumnOutput;

public abstract class DalvInsn {
    private int address;

    private final mod.agus.jcoderz.dx.dex.code.Dop opcode;

    private final mod.agus.jcoderz.dx.rop.code.SourcePosition position;

    private final mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers;

    public static SimpleInsn makeMove(mod.agus.jcoderz.dx.rop.code.SourcePosition position,
                                      mod.agus.jcoderz.dx.rop.code.RegisterSpec dest, mod.agus.jcoderz.dx.rop.code.RegisterSpec src) {
        boolean category1 = dest.getCategory() == 1;
        boolean reference = dest.getType().isReference();
        int destReg = dest.getReg();
        int srcReg = src.getReg();
        mod.agus.jcoderz.dx.dex.code.Dop opcode;

        if ((srcReg | destReg) < 16) {
            opcode = reference ? Dops.MOVE_OBJECT :
                (category1 ? Dops.MOVE : Dops.MOVE_WIDE);
        } else if (destReg < 256) {
            opcode = reference ? Dops.MOVE_OBJECT_FROM16 :
                (category1 ? Dops.MOVE_FROM16 : Dops.MOVE_WIDE_FROM16);
        } else {
            opcode = reference ? Dops.MOVE_OBJECT_16 :
                (category1 ? Dops.MOVE_16 : Dops.MOVE_WIDE_16);
        }

        return new SimpleInsn(opcode, position,
                              mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(dest, src));
    }

    public DalvInsn(mod.agus.jcoderz.dx.dex.code.Dop opcode, mod.agus.jcoderz.dx.rop.code.SourcePosition position,
                    mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers) {
        if (opcode == null) {
            throw new NullPointerException("opcode == null");
        }

        if (position == null) {
            throw new NullPointerException("position == null");
        }

        if (registers == null) {
            throw new NullPointerException("registers == null");
        }

        this.address = -1;
        this.opcode = opcode;
        this.position = position;
        this.registers = registers;
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append(identifierString());
        sb.append(' ');
        sb.append(position);

        sb.append(": ");
        sb.append(opcode.getName());

        boolean needComma = false;
        if (registers.size() != 0) {
            sb.append(registers.toHuman(" ", ", ", null));
            needComma = true;
        }

        String extra = argString();
        if (extra != null) {
            if (needComma) {
                sb.append(',');
            }
            sb.append(' ');
            sb.append(extra);
        }

        return sb.toString();
    }

    public final boolean hasAddress() {
        return (address >= 0);
    }

    public final int getAddress() {
        if (address < 0) {
            throw new RuntimeException("address not yet known");
        }

        return address;
    }

    public final mod.agus.jcoderz.dx.dex.code.Dop getOpcode() {
        return opcode;
    }

    public final SourcePosition getPosition() {
        return position;
    }

    public final mod.agus.jcoderz.dx.rop.code.RegisterSpecList getRegisters() {
        return registers;
    }

    public final boolean hasResult() {
        return opcode.hasResult();
    }

    public final int getMinimumRegisterRequirement(BitSet compatRegs) {
        boolean hasResult = hasResult();
        int regSz = registers.size();
        int resultRequirement = 0;
        int sourceRequirement = 0;

        if (hasResult && !compatRegs.get(0)) {
            resultRequirement = registers.get(0).getCategory();
        }

        for (int i = hasResult ? 1 : 0; i < regSz; i++) {
            if (!compatRegs.get(i)) {
                sourceRequirement += registers.get(i).getCategory();
            }
        }

        return Math.max(sourceRequirement, resultRequirement);
    }

    public DalvInsn getLowRegVersion() {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs =
            registers.withExpandedRegisters(0, hasResult(), null);
        return withRegisters(regs);
    }

    public DalvInsn expandedPrefix(BitSet compatRegs) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = registers;
        boolean firstBit = compatRegs.get(0);

        if (hasResult()) compatRegs.set(0);

        regs = regs.subset(compatRegs);

        if (hasResult()) compatRegs.set(0, firstBit);

        if (regs.size() == 0) return null;

        return new HighRegisterPrefix(position, regs);
    }

    public DalvInsn expandedSuffix(BitSet compatRegs) {
        if (hasResult() && !compatRegs.get(0)) {
            RegisterSpec r = registers.get(0);
            return makeMove(position, r, r.withReg(0));
        } else {
            return null;
        }
    }

    public DalvInsn expandedVersion(BitSet compatRegs) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs =
            registers.withExpandedRegisters(0, hasResult(), compatRegs);
        return withRegisters(regs);
    }

    public final String identifierString() {
        if (address != -1) {
            return String.format("%04x", address);
        }

        return Hex.u4(System.identityHashCode(this));
    }

    public final String listingString(String prefix, int width,
            boolean noteIndices) {
        String insnPerSe = listingString0(noteIndices);

        if (insnPerSe == null) {
            return null;
        }

        String addr = prefix + identifierString() + ": ";
        int w1 = addr.length();
        int w2 = (width == 0) ? insnPerSe.length() : (width - w1);

        return TwoColumnOutput.toString(addr, w1, "", insnPerSe, w2);
    }

    public final void setAddress(int address) {
        if (address < 0) {
            throw new IllegalArgumentException("address < 0");
        }

        this.address = address;
    }

    public final int getNextAddress() {
        return getAddress() + codeSize();
    }

    public DalvInsn withMapper(RegisterMapper mapper) {
      return withRegisters(mapper.map(getRegisters()));
    }

    public abstract int codeSize();

    public abstract void writeTo(AnnotatedOutput out);

    public abstract DalvInsn withOpcode(Dop opcode);

    public abstract DalvInsn withRegisterOffset(int delta);

    public abstract DalvInsn withRegisters(RegisterSpecList registers);

    protected abstract String argString();

    protected abstract String listingString0(boolean noteIndices);

    public String cstString() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public String cstComment() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
