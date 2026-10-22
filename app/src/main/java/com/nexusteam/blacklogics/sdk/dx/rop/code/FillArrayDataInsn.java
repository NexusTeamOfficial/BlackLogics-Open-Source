
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.cst.Constant;

public final class FillArrayDataInsn
        extends Insn {

    private final ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initValues;

    private final mod.agus.jcoderz.dx.rop.cst.Constant arrayType;

    public FillArrayDataInsn(Rop opcode, SourcePosition position,
                             mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources,
                             ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> initValues,
                             mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        super(opcode, position, null, sources);

        if (opcode.getBranchingness() != Rop.BRANCH_NONE) {
            throw new IllegalArgumentException("opcode with invalid branchingness: " + opcode.getBranchingness());
        }

        this.initValues = initValues;
        this.arrayType = cst;
    }


    @Override
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    public ArrayList<mod.agus.jcoderz.dx.rop.cst.Constant> getInitValues() {
        return initValues;
    }

    public Constant getConstant() {
        return arrayType;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitFillArrayDataInsn(this);
    }

    @Override
    public Insn withAddedCatch(Type type) {
        throw new  UnsupportedOperationException("unsupported");
    }

    @Override
    public Insn withRegisterOffset(int delta) {
        return new FillArrayDataInsn(getOpcode(), getPosition(),
                                     getSources().withOffset(delta),
                                     initValues, arrayType);
    }

    @Override
    public Insn withNewRegisters(RegisterSpec result,
            RegisterSpecList sources) {

        return new FillArrayDataInsn(getOpcode(), getPosition(),
                                     sources, initValues, arrayType);
    }
}
