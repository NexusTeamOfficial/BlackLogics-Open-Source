
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.util.LabeledList;

public final class BasicBlockList extends LabeledList {
    private int regCount;

    public BasicBlockList(int size) {
        super(size);

        regCount = -1;
    }

    private BasicBlockList(BasicBlockList old) {
        super(old);
        regCount = old.regCount;
    }


    public mod.agus.jcoderz.dx.rop.code.BasicBlock get(int n) {
        return (mod.agus.jcoderz.dx.rop.code.BasicBlock) get0(n);
    }

    public void set(int n, mod.agus.jcoderz.dx.rop.code.BasicBlock bb) {
        super.set(n, bb);


        regCount = -1;
    }

    public int getRegCount() {
        if (regCount == -1) {
            RegCountVisitor visitor = new RegCountVisitor();
            forEachInsn(visitor);
            regCount = visitor.getRegCount();
        }

        return regCount;
    }

    public int getInstructionCount() {
        int sz = size();
        int result = 0;

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock one = (mod.agus.jcoderz.dx.rop.code.BasicBlock) getOrNull0(i);
            if (one != null) {
                result += one.getInsns().size();
            }
        }

        return result;
    }

    public int getEffectiveInstructionCount() {
        int sz = size();
        int result = 0;

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock one = (mod.agus.jcoderz.dx.rop.code.BasicBlock) getOrNull0(i);
            if (one != null) {
                mod.agus.jcoderz.dx.rop.code.InsnList insns = one.getInsns();
                int insnsSz = insns.size();

                for (int j = 0; j < insnsSz; j++) {
                    mod.agus.jcoderz.dx.rop.code.Insn insn = insns.get(j);

                    if (insn.getOpcode().getOpcode() != RegOps.MARK_LOCAL) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    public mod.agus.jcoderz.dx.rop.code.BasicBlock labelToBlock(int label) {
        int idx = indexOfLabel(label);

        if (idx < 0) {
            throw new IllegalArgumentException("no such label: "
                    + Hex.u2(label));
        }

        return get(idx);
    }

    public void forEachInsn(mod.agus.jcoderz.dx.rop.code.Insn.Visitor visitor) {
        int sz = size();

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock one = get(i);
            InsnList insns = one.getInsns();
            insns.forEach(visitor);
        }
    }

    public BasicBlockList withRegisterOffset(int delta) {
        int sz = size();
        BasicBlockList result = new BasicBlockList(sz);

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.code.BasicBlock one = (mod.agus.jcoderz.dx.rop.code.BasicBlock) get0(i);
            if (one != null) {
                result.set(i, one.withRegisterOffset(delta));
            }
        }

        if (isImmutable()) {
            result.setImmutable();
        }

        return result;
    }

    public BasicBlockList getMutableCopy() {
        return new BasicBlockList(this);
    }

    public mod.agus.jcoderz.dx.rop.code.BasicBlock preferredSuccessorOf(mod.agus.jcoderz.dx.rop.code.BasicBlock block) {
        int primarySuccessor = block.getPrimarySuccessor();
        IntList successors = block.getSuccessors();
        int succSize = successors.size();

        switch (succSize) {
            case 0: {
                return null;
            }
            case 1: {
                return labelToBlock(successors.get(0));
            }
        }

        if (primarySuccessor != -1) {
            return labelToBlock(primarySuccessor);
        } else {
            return labelToBlock(successors.get(0));
        }
    }

    public boolean catchesEqual(mod.agus.jcoderz.dx.rop.code.BasicBlock block1, BasicBlock block2) {
        TypeList catches1 = block1.getExceptionHandlerTypes();
        TypeList catches2 = block2.getExceptionHandlerTypes();

        if (!StdTypeList.equalContents(catches1, catches2)) {
            return false;
        }

        IntList succ1 = block1.getSuccessors();
        IntList succ2 = block2.getSuccessors();
        int size = succ1.size();

        int primary1 = block1.getPrimarySuccessor();
        int primary2 = block2.getPrimarySuccessor();

        if (((primary1 == -1) || (primary2 == -1))
                && (primary1 != primary2)) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            int label1 = succ1.get(i);
            int label2 = succ2.get(i);

            if (label1 == primary1) {
                if (label2 != primary2) {
                    return false;
                }
                continue;
            }

            if (label1 != label2) {
                return false;
            }
        }

        return true;
    }

    private static class RegCountVisitor
            implements mod.agus.jcoderz.dx.rop.code.Insn.Visitor {
        private int regCount;

        public RegCountVisitor() {
            regCount = 0;
        }

        public int getRegCount() {
            return regCount;
        }

        @Override
        public void visitPlainInsn(PlainInsn insn) {
            visit(insn);
        }

        @Override
        public void visitPlainCstInsn(PlainCstInsn insn) {
            visit(insn);
        }

        @Override
        public void visitSwitchInsn(SwitchInsn insn) {
            visit(insn);
        }

        @Override
        public void visitThrowingCstInsn(ThrowingCstInsn insn) {
            visit(insn);
        }

        @Override
        public void visitThrowingInsn(ThrowingInsn insn) {
            visit(insn);
        }

        @Override
        public void visitFillArrayDataInsn(FillArrayDataInsn insn) {
            visit(insn);
        }

        @Override
        public void visitInvokePolymorphicInsn(InvokePolymorphicInsn insn) {
            visit(insn);
        }

        private void visit(Insn insn) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();

            if (result != null) {
                processReg(result);
            }

            RegisterSpecList sources = insn.getSources();
            int sz = sources.size();

            for (int i = 0; i < sz; i++) {
                processReg(sources.get(i));
            }
        }

        private void processReg(RegisterSpec spec) {
            int reg = spec.getNextReg();

            if (reg > regCount) {
                regCount = reg;
            }
        }
    }
}
