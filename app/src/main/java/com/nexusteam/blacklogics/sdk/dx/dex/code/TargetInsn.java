
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class TargetInsn extends FixedSizeInsn {
    private CodeAddress target;

    public TargetInsn(mod.agus.jcoderz.dx.dex.code.Dop opcode, SourcePosition position,
                      mod.agus.jcoderz.dx.rop.code.RegisterSpecList registers, CodeAddress target) {
        super(opcode, position, registers);

        if (target == null) {
            throw new NullPointerException("target == null");
        }

        this.target = target;
    }

    @Override
    public mod.agus.jcoderz.dx.dex.code.DalvInsn withOpcode(mod.agus.jcoderz.dx.dex.code.Dop opcode) {
        return new TargetInsn(opcode, getPosition(), getRegisters(), target);
    }

    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new TargetInsn(getOpcode(), getPosition(), registers, target);
    }

    public TargetInsn withNewTargetAndReversed(CodeAddress target) {
        Dop opcode = getOpcode().getOppositeTest();

        return new TargetInsn(opcode, getPosition(), getRegisters(), target);
    }

    public CodeAddress getTarget() {
        return target;
    }

    public int getTargetAddress() {
        return target.getAddress();
    }

    public int getTargetOffset() {
        return target.getAddress() - getAddress();
    }

    public boolean hasTargetOffset() {
        return hasAddress() && target.hasAddress();
    }

    @Override
    protected String argString() {
        if (target == null) {
            return "????";
        }

        return target.identifierString();
    }
}
