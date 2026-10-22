
package mod.agus.jcoderz.dx.dex.code.form;

import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.dex.code.MultiCstInsn;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;

public final class Form4rcc extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form4rcc();

    private Form4rcc() {

    }

    @Override
    public String insnArgString(DalvInsn insn) {
        return regRangeString(insn.getRegisters()) + ", " +
            insn.cstString();
    }

    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        if (noteIndices) {
            return insn.cstComment();
        } else {
            return "";
        }
    }

    @Override
    public int codeSize() {
        return 4;
    }

    @Override
    public boolean isCompatible(DalvInsn insn) {
        if (!(insn instanceof MultiCstInsn)) {
            return false;
        }

        MultiCstInsn mci = (MultiCstInsn) insn;
        int methodIdx = mci.getIndex(0);
        int protoIdx = mci.getIndex(1);
        if (!unsignedFitsInShort(methodIdx) || !unsignedFitsInShort(protoIdx)) {
            return false;
        }

        mod.agus.jcoderz.dx.rop.cst.Constant methodRef = mci.getConstant(0);
        if (!(methodRef instanceof CstMethodRef)) {
            return false;
        }

        Constant protoRef = mci.getConstant(1);
        if (!(protoRef instanceof CstProtoRef)) {
            return false;
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpecList regs = mci.getRegisters();
        int sz = regs.size();
        if (sz == 0) {
            return true;
        }

        return (unsignedFitsInByte(regs.getWordCount()) &&
                unsignedFitsInShort(sz) &&
                unsignedFitsInShort(regs.get(0).getReg()) &&
                isRegListSequential(regs));
    }

    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        MultiCstInsn mci = (MultiCstInsn) insn;
        short regB = (short) mci.getIndex(0);
        short regH = (short) mci.getIndex(1);

        RegisterSpecList regs = insn.getRegisters();
        short regC = 0;
        if (regs.size() > 0) {
            regC = (short) regs.get(0).getReg();
        }
        int regA = regs.getWordCount();


        write(out, opcodeUnit(insn,regA), regB, regC, regH);
    }
}
