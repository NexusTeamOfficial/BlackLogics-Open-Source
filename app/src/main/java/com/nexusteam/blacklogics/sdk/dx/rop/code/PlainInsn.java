
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;

public final class PlainInsn
        extends Insn {
    public PlainInsn(Rop opcode, SourcePosition position,
                     RegisterSpec result, mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources) {
        super(opcode, position, result, sources);

        switch (opcode.getBranchingness()) {
            case Rop.BRANCH_SWITCH:
            case Rop.BRANCH_THROW: {
                throw new IllegalArgumentException("opcode with invalid branchingness: " + opcode.getBranchingness());
            }
        }

        if (result != null && opcode.getBranchingness() != Rop.BRANCH_NONE) {

            throw new IllegalArgumentException
                    ("can't mix branchingness with result");
        }
    }

    public PlainInsn(Rop opcode, SourcePosition position, RegisterSpec result,
                     RegisterSpec source) {
        this(opcode, position, result, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.make(source));
    }

    @Override
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPlainInsn(this);
    }

    @Override
    public Insn withAddedCatch(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override
    public Insn withRegisterOffset(int delta) {
        return new PlainInsn(getOpcode(), getPosition(),
                             getResult().withOffset(delta),
                             getSources().withOffset(delta));
    }

    @Override
    public Insn withSourceLiteral() {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = getSources();
        int szSources = sources.size();

        if (szSources == 0) {
            return this;
        }

        TypeBearer lastType = sources.get(szSources - 1).getTypeBearer();

        if (!lastType.isConstant()) {

            TypeBearer firstType = sources.get(0).getTypeBearer();
            if (szSources == 2 && firstType.isConstant()) {
                mod.agus.jcoderz.dx.rop.cst.Constant cst = (mod.agus.jcoderz.dx.rop.cst.Constant) firstType;
                mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = sources.withoutFirst();
                Rop newRop = Rops.ropFor(getOpcode().getOpcode(), getResult(),
                                             newSources, cst);
                return new mod.agus.jcoderz.dx.rop.code.PlainCstInsn(newRop, getPosition(), getResult(),
                                            newSources, cst);
            }
            return this;
        } else {

            mod.agus.jcoderz.dx.rop.cst.Constant cst = (Constant) lastType;

            mod.agus.jcoderz.dx.rop.code.RegisterSpecList newSources = sources.withoutLast();

            Rop newRop;
            try {

                int opcode = getOpcode().getOpcode();
                if (opcode == RegOps.SUB && cst instanceof mod.agus.jcoderz.dx.rop.cst.CstInteger) {
                    opcode = RegOps.ADD;
                    cst = mod.agus.jcoderz.dx.rop.cst.CstInteger.make(-((CstInteger)cst).getValue());
                }
                newRop = Rops.ropFor(opcode, getResult(), newSources, cst);
            } catch (IllegalArgumentException ex) {

                return this;
            }

            return new PlainCstInsn(newRop, getPosition(),
                    getResult(), newSources, cst);
        }
    }


    @Override
    public Insn withNewRegisters(RegisterSpec result,
            RegisterSpecList sources) {

        return new PlainInsn(getOpcode(), getPosition(),
                             result,
                             sources);

    }
}
