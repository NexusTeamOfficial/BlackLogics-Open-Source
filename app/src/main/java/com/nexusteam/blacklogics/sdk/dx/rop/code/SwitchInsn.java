
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public final class SwitchInsn
        extends Insn {
    private final IntList cases;

    public SwitchInsn(Rop opcode, SourcePosition position, RegisterSpec result,
                      RegisterSpecList sources, IntList cases) {
        super(opcode, position, result, sources);

        if (opcode.getBranchingness() != Rop.BRANCH_SWITCH) {
            throw new IllegalArgumentException("bogus branchingness");
        }

        if (cases == null) {
            throw new NullPointerException("cases == null");
        }

        this.cases = cases;
    }

    @Override
    public String getInlineString() {
        return cases.toString();
    }

    @Override
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitSwitchInsn(this);
    }

    @Override
    public Insn withAddedCatch(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public Insn withRegisterOffset(int delta) {
        return new SwitchInsn(getOpcode(), getPosition(),
                              getResult().withOffset(delta),
                              getSources().withOffset(delta),
                              cases);
    }

    @Override
    public boolean contentEquals(Insn b) {
        return false;
    }

    @Override
    public Insn withNewRegisters(RegisterSpec result,
            RegisterSpecList sources) {

        return new SwitchInsn(getOpcode(), getPosition(),
                              result,
                              sources,
                              cases);
    }

    public IntList getCases() {
        return cases;
    }
}
