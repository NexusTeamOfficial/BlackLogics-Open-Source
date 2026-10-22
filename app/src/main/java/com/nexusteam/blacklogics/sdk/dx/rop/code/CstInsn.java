
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.cst.Constant;

public abstract class CstInsn
        extends Insn {
    private final mod.agus.jcoderz.dx.rop.cst.Constant cst;

    public CstInsn(Rop opcode, SourcePosition position, RegisterSpec result,
                   RegisterSpecList sources, mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        super(opcode, position, result, sources);

        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        this.cst = cst;
    }

    @Override
    public String getInlineString() {
        return cst.toHuman();
    }

    public Constant getConstant() {
        return cst;
    }

    @Override
    public boolean contentEquals(Insn b) {
        return super.contentEquals(b)
                && cst.equals(((CstInsn)b).getConstant());
    }
}
