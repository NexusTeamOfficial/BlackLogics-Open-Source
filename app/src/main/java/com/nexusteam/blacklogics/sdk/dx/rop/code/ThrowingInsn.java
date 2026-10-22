
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public final class ThrowingInsn
        extends mod.agus.jcoderz.dx.rop.code.Insn {
    private final TypeList catches;

    public static String toCatchString(TypeList catches) {
        StringBuilder sb = new StringBuilder(100);

        sb.append("catch");

        int sz = catches.size();
        for (int i = 0; i < sz; i++) {
            sb.append(" ");
            sb.append(catches.getType(i).toHuman());
        }

        return sb.toString();
    }

    public ThrowingInsn(mod.agus.jcoderz.dx.rop.code.Rop opcode, SourcePosition position,
                        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources,
                        TypeList catches) {
        super(opcode, position, null, sources);

        if (opcode.getBranchingness() != Rop.BRANCH_THROW) {
            throw new IllegalArgumentException("opcode with invalid branchingness: " + opcode.getBranchingness());
        }

        if (catches == null) {
            throw new NullPointerException("catches == null");
        }

        this.catches = catches;
    }

    @Override
    public String getInlineString() {
        return toCatchString(catches);
    }

    @Override
    public TypeList getCatches() {
        return catches;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitThrowingInsn(this);
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withAddedCatch(Type type) {
        return new ThrowingInsn(getOpcode(), getPosition(),
                                getSources(), catches.withAddedType(type));
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withRegisterOffset(int delta) {
        return new ThrowingInsn(getOpcode(), getPosition(),
                                getSources().withOffset(delta),
                                catches);
    }

    @Override
    public Insn withNewRegisters(RegisterSpec result,
                                 RegisterSpecList sources) {

        return new ThrowingInsn(getOpcode(), getPosition(),
                                sources,
                                catches);
    }
}
