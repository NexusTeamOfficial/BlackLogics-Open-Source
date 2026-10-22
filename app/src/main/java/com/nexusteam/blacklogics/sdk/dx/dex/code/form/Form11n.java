
package mod.agus.jcoderz.dx.dex.code.form;

import mod.agus.jcoderz.dx.dex.code.CstInsn;
import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;

public final class Form11n extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form11n();

    private Form11n() {

    }

    @Override
    public String insnArgString(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits value = (mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) ((CstInsn) insn).getConstant();

        return regs.get(0).regString() + ", " + literalBitsString(value);
    }

    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits value = (mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) ((CstInsn) insn).getConstant();
        return literalBitsComment(value, 4);
    }

    @Override
    public int codeSize() {
        return 1;
    }

    @Override
    public boolean isCompatible(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();

        if (!((insn instanceof CstInsn) &&
              (regs.size() == 1) &&
              unsignedFitsInNibble(regs.get(0).getReg()))) {
            return false;
        }

        CstInsn ci = (CstInsn) insn;
        Constant cst = ci.getConstant();

        if (!(cst instanceof mod.agus.jcoderz.dx.rop.cst.CstLiteralBits)) {
            return false;
        }

        mod.agus.jcoderz.dx.rop.cst.CstLiteralBits cb = (mod.agus.jcoderz.dx.rop.cst.CstLiteralBits) cst;

        return cb.fitsInInt() && signedFitsInNibble(cb.getIntBits());
    }

    @Override
    public BitSet compatibleRegs(DalvInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = insn.getRegisters();
        BitSet bits = new BitSet(1);

        bits.set(0, unsignedFitsInNibble(regs.get(0).getReg()));
        return bits;
    }

    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        int value =
            ((CstLiteralBits) ((CstInsn) insn).getConstant()).getIntBits();

        write(out,
              opcodeUnit(insn, makeByte(regs.get(0).getReg(), value & 0xf)));
    }
}
