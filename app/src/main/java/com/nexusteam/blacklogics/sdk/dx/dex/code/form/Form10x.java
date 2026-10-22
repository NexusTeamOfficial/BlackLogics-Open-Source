
package mod.agus.jcoderz.dx.dex.code.form;

import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.dex.code.SimpleInsn;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Form10x extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form10x();

    private Form10x() {

    }

    @Override
    public String insnArgString(DalvInsn insn) {

        return "";
    }

    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {

        return "";
    }

    @Override
    public int codeSize() {
        return 1;
    }

    @Override
    public boolean isCompatible(DalvInsn insn) {
        return (insn instanceof SimpleInsn) &&
            (insn.getRegisters().size() == 0);
    }

    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        write(out, opcodeUnit(insn, 0));
    }
}
