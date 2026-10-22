
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.util.ToHuman;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;

public abstract class SsaInsn implements ToHuman, Cloneable {
    private final mod.agus.jcoderz.dx.ssa.SsaBasicBlock block;

    private mod.agus.jcoderz.dx.rop.code.RegisterSpec result;

    protected SsaInsn(mod.agus.jcoderz.dx.rop.code.RegisterSpec result, mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
        if (block == null) {
            throw new NullPointerException("block == null");
        }

        this.block = block;
        this.result = result;
    }

    public static SsaInsn makeFromRop(mod.agus.jcoderz.dx.rop.code.Insn insn, mod.agus.jcoderz.dx.ssa.SsaBasicBlock block) {
        return new NormalSsaInsn(insn, block);
    }

    @Override
    public SsaInsn clone() {
        try {
            return (SsaInsn)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException ("unexpected", ex);
        }
    }

    public mod.agus.jcoderz.dx.rop.code.RegisterSpec getResult() {
        return result;
    }

    protected void setResult(mod.agus.jcoderz.dx.rop.code.RegisterSpec result) {
        if (result == null) {
            throw new NullPointerException("result == null");
        }

        this.result = result;
    }

    abstract public RegisterSpecList getSources();

    public SsaBasicBlock getBlock() {
        return block;
    }

    public boolean isResultReg(int reg) {
        return result != null && result.getReg() == reg;
    }


    public void changeResultReg(int reg) {
        if (result != null) {
            result = result.withReg(reg);
        }
    }

    public final void setResultLocal(mod.agus.jcoderz.dx.rop.code.LocalItem local) {
        LocalItem oldItem = result.getLocalItem();

        if (local != oldItem && (local == null
                || !local.equals(result.getLocalItem()))) {
            result = mod.agus.jcoderz.dx.rop.code.RegisterSpec.makeLocalOptional(
                    result.getReg(), result.getType(), local);
        }
    }

    public final void mapRegisters(mod.agus.jcoderz.dx.ssa.RegisterMapper mapper) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpec oldResult = result;

        result = mapper.map(result);
        block.getParent().updateOneDefinition(this, oldResult);
        mapSourceRegisters(mapper);
    }

    abstract public void mapSourceRegisters(RegisterMapper mapper);

    abstract public Rop getOpcode();

    abstract public mod.agus.jcoderz.dx.rop.code.Insn getOriginalRopInsn();

    public RegisterSpec getLocalAssignment() {
        if (result != null && result.getLocalItem() != null) {
            return result;
        }

        return null;
    }

    public boolean isRegASource(int reg) {
        return null != getSources().specForRegister(reg);
    }

    public abstract Insn toRopInsn();

    public abstract boolean isPhiOrMove();

    public abstract boolean hasSideEffect();

    public boolean isNormalMoveInsn() {
        return false;
    }

    public boolean isMoveException() {
        return false;
    }

    abstract public boolean canThrow();

    public abstract void accept(Visitor v);

    public static interface Visitor {
        public void visitMoveInsn(NormalSsaInsn insn);

        public void visitPhiInsn(PhiInsn insn);

        public void visitNonMoveInsn(NormalSsaInsn insn);
    }
}
