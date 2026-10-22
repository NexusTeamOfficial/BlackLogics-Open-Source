
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class HighRegisterPrefix extends VariableSizeInsn {
    private mod.agus.jcoderz.dx.dex.code.SimpleInsn[] insns;

    public HighRegisterPrefix(mod.agus.jcoderz.dx.rop.code.SourcePosition position,
                              mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers) {
        super(position, registers);

        if (registers.size() == 0) {
            throw new IllegalArgumentException("registers.size() == 0");
        }

        insns = null;
    }

    @Override
    public int codeSize() {
        int result = 0;

        calculateInsnsIfNecessary();

        for (mod.agus.jcoderz.dx.dex.code.SimpleInsn insn : insns) {
            result += insn.codeSize();
        }

        return result;
    }

    @Override
    public void writeTo(AnnotatedOutput out) {
        calculateInsnsIfNecessary();

        for (mod.agus.jcoderz.dx.dex.code.SimpleInsn insn : insns) {
            insn.writeTo(out);
        }
    }

    private void calculateInsnsIfNecessary() {
        if (insns != null) {
            return;
        }

        mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers = getRegisters();
        int sz = registers.size();

        insns = new mod.agus.jcoderz.dx.dex.code.SimpleInsn[sz];

        for (int i = 0, outAt = 0; i < sz; i++) {
          mod.agus.jcoderz.dx.rop.code.RegisterSpec src = registers.get(i);
          insns[i] = moveInsnFor(src, outAt);
          outAt += src.getCategory();
        }
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withRegisters(mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers) {
        return new HighRegisterPrefix(getPosition(), registers);
    }

    @Override
    protected String argString() {
        return null;
    }

    @Override
    protected String listingString0(boolean noteIndices) {
        RegisterSpecList registers = getRegisters();
        int sz = registers.size();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, outAt = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec src = registers.get(i);
            mod.agus.jcoderz.dx.dex.code.SimpleInsn insn = moveInsnFor(src, outAt);

            if (i != 0) {
                sb.append('\n');
            }

            sb.append(insn.listingString0(noteIndices));

            outAt += src.getCategory();
        }

        return sb.toString();
    }

    private static SimpleInsn moveInsnFor(mod.agus.jcoderz.dx.rop.code.RegisterSpec src, int destIndex) {
        return DalvInsn.makeMove(SourcePosition.NO_INFO,
                RegisterSpec.make(destIndex, src.getType()),
                src);
    }
}
