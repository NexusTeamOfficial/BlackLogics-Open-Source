
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.rop.cst.Constant;

public final class PlainCstInsn
        extends CstInsn {
    public PlainCstInsn(Rop opcode, SourcePosition position,
                        RegisterSpec result, mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources,
                        Constant cst) {
        super(opcode, position, result, sources, cst);

        if (opcode.getBranchingness() != Rop.BRANCH_NONE) {
            throw new IllegalArgumentException("opcode with invalid branchingness: " + opcode.getBranchingness());
        }
    }

    @Override
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPlainCstInsn(this);
    }

    @Override
    public Insn withAddedCatch(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public Insn withRegisterOffset(int delta) {
        return new PlainCstInsn(getOpcode(), getPosition(),
                                getResult().withOffset(delta),
                                getSources().withOffset(delta),
                                getConstant());
    }

    @Override
    public Insn withNewRegisters(RegisterSpec result,
            RegisterSpecList sources) {

        return new PlainCstInsn(getOpcode(), getPosition(),
                                result,
                                sources,
                                getConstant());

    }
}
