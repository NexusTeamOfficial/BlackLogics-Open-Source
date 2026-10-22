
package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;

public final class NormalSsaInsn extends SsaInsn implements Cloneable {
    private mod.agus.jcoderz.dx.rop.code.Insn insn;

    NormalSsaInsn(final mod.agus.jcoderz.dx.rop.code.Insn insn, final SsaBasicBlock block) {
        super(insn.getResult(), block);
        this.insn = insn;
    }

    @Override
    public final void mapSourceRegisters(RegisterMapper mapper) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList oldSources = insn.getSources();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = mapper.map(oldSources);

        if (newSources != oldSources) {
            insn = insn.withNewRegisters(getResult(), newSources);
            getBlock().getParent().onSourcesChanged(this, oldSources);
        }
    }

    public final void changeOneSource(int index, mod.agus.jcoderz.dx.rop.code.RegisterSpec newSpec) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList origSources = insn.getSources();
        int sz = origSources.size();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = new mod.agus.jcoderz.dx.rop.code.RegisterSpecList(sz);

        for (int i = 0; i < sz; i++) {
            newSources.set(i, i == index ? newSpec : origSources.get(i));
        }

        newSources.setImmutable();

        mod.agus.jcoderz.dx.rop.code.RegisterSpec origSpec = origSources.get(index);
        if (origSpec.getReg() != newSpec.getReg()) {
            getBlock().getParent().onSourceChanged(this, origSpec, newSpec);
        }

        insn = insn.withNewRegisters(getResult(), newSources);
    }

    public final void setNewSources (mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList origSources = insn.getSources();

        if (origSources.size() != newSources.size()) {
            throw new RuntimeException("Sources counts don't match");
        }

        insn = insn.withNewRegisters(getResult(), newSources);
    }

    @Override
    public NormalSsaInsn clone() {
        return (NormalSsaInsn) super.clone();
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.RegisterSpecList getSources() {
        return insn.getSources();
    }

    @Override
    public String toHuman() {
        return toRopInsn().toHuman();
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn toRopInsn() {
        return insn.withNewRegisters(getResult(), insn.getSources());
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Rop getOpcode() {
        return insn.getOpcode();
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn getOriginalRopInsn() {
        return insn;
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.RegisterSpec getLocalAssignment() {
        RegisterSpec assignment;

        if (insn.getOpcode().getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MARK_LOCAL) {
            assignment = insn.getSources().get(0);
        } else {
            assignment = getResult();
        }

        if (assignment == null) {
            return null;
        }

        LocalItem local = assignment.getLocalItem();

        if (local == null) {
            return null;
        }

        return assignment;
    }

    public void upgradeToLiteral() {
        RegisterSpecList oldSources = insn.getSources();

        insn = insn.withSourceLiteral();
        getBlock().getParent().onSourcesChanged(this, oldSources);
    }

    @Override
    public boolean isNormalMoveInsn() {
        return insn.getOpcode().getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE;
    }

    @Override
    public boolean isMoveException() {
        return insn.getOpcode().getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_EXCEPTION;
    }

    @Override
    public boolean canThrow() {
        return insn.canThrow();
    }

    @Override
    public void accept(Visitor v) {
        if (isNormalMoveInsn()) {
            v.visitMoveInsn(this);
        } else {
            v.visitNonMoveInsn(this);
        }
    }

    @Override
    public  boolean isPhiOrMove() {
        return isNormalMoveInsn();
    }

    @Override
    public boolean hasSideEffect() {
        mod.agus.jcoderz.dx.rop.code.Rop opcode = getOpcode();

        if (opcode.getBranchingness() != Rop.BRANCH_NONE) {
            return true;
        }

        boolean hasLocalSideEffect
            = Optimizer.getPreserveLocals() && getLocalAssignment() != null;

        switch (opcode.getOpcode()) {
            case mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT:
            case mod.agus.jcoderz.dx.rop.code.RegOps.MOVE:
            case RegOps.CONST:
                return hasLocalSideEffect;
            default:
                return true;
        }
    }
}
