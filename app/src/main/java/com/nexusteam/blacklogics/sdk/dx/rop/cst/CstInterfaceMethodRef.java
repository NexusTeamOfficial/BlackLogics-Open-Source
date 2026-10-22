
package mod.agus.jcoderz.dx.rop.cst;

public final class CstInterfaceMethodRef
        extends CstBaseMethodRef {
    private mod.agus.jcoderz.dx.rop.cst.CstMethodRef methodRef;

    public CstInterfaceMethodRef(CstType definingClass, CstNat nat) {
        super(definingClass, nat);
        methodRef = null;
    }

    @Override
    public String typeName() {
        return "ifaceMethod";
    }

    public mod.agus.jcoderz.dx.rop.cst.CstMethodRef toMethodRef() {
        if (methodRef == null) {
            methodRef = new CstMethodRef(getDefiningClass(), getNat());
        }

        return methodRef;
    }
}
