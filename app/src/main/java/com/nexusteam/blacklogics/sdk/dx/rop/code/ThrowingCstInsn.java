
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;

public final class ThrowingCstInsn
        extends CstInsn {
    private final TypeList catches;

    public ThrowingCstInsn(mod.agus.jcoderz.dx.rop.code.Rop opcode, SourcePosition position,
                           mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources,
                           TypeList catches, mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        super(opcode, position, null, sources, cst);

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
        Constant cst = getConstant();
        String constantString = cst.toHuman();
        if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstString) {
            constantString = ((CstString) cst).toQuoted();
        }
        return constantString + " " + ThrowingInsn.toCatchString(catches);
    }

    @Override
    public TypeList getCatches() {
        return catches;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitThrowingCstInsn(this);
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withAddedCatch(Type type) {
        return new ThrowingCstInsn(getOpcode(), getPosition(),
                                   getSources(), catches.withAddedType(type),
                                   getConstant());
    }

    @Override
    public mod.agus.jcoderz.dx.rop.code.Insn withRegisterOffset(int delta) {
        return new ThrowingCstInsn(getOpcode(), getPosition(),
                                   getSources().withOffset(delta),
                                   catches,
                                   getConstant());
    }

    @Override
    public Insn withNewRegisters(RegisterSpec result,
                                 RegisterSpecList sources) {

        return new ThrowingCstInsn(getOpcode(), getPosition(),
                                   sources,
                                   catches,
                                   getConstant());
    }


}
