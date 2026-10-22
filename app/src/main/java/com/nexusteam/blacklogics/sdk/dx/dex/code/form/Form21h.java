
package mod.agus.jcoderz.dx.dex.code.form;

import mod.agus.jcoderz.dx.dex.code.CstInsn;
import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;

public final class Form21h extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form21h();

    private Form21h() {

    }

    @Override
    public String insnArgString(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits value = (mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) ((CstInsn) insn).getConstant();

        return regs.get(0).regString() + ", " + literalBitsString(value);
    }

    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits value = (mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) ((CstInsn) insn).getConstant();

        return
            literalBitsComment(value,
                    (regs.get(0).getCategory() == 1) ? 32 : 64);
    }

    @Override
    public int codeSize() {
        return 2;
    }

    @Override
    public boolean isCompatible(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        if (!((insn instanceof CstInsn) &&
              (regs.size() == 1) &&
              unsignedFitsInByte(regs.get(0).getReg()))) {
            return false;
        }

        CstInsn ci = (CstInsn) insn;
        Constant cst = ci.getConstant();

        if (!(cst instanceof mod.agus.jcoderz.dx.rop.cst.CstLiteralBits)) {
            return false;
        }

        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits cb = (mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) cst;


        if (regs.get(0).getCategory() == 1) {
            int bits = cb.getIntBits();
            return ((bits & 0xffff) == 0);
        } else {
            long bits = cb.getLongBits();
            return ((bits & 0xffffffffffffL) == 0);
        }
    }

    @Override
    public BitSet compatibleRegs(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        BitSet bits = new BitSet(1);

        bits.set(0, unsignedFitsInByte(regs.get(0).getReg()));
        return bits;
    }

    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits cb = (CstLiteralBits) ((CstInsn) insn).getConstant();
        short bits;


        if (regs.get(0).getCategory() == 1) {
            bits = (short) (cb.getIntBits() >>> 16);
        } else {
            bits = (short) (cb.getLongBits() >>> 48);
        }

        write(out, opcodeUnit(insn, regs.get(0).getReg()), bits);
    }
}
