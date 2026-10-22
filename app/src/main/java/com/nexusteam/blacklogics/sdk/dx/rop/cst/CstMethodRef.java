
package mod.agus.jcoderz.dx.rop.cst;

public final class CstMethodRef
        extends CstBaseMethodRef {
    public CstMethodRef(CstType definingClass, CstNat nat) {
        super(definingClass, nat);
    }

    @Override
    public String typeName() {
        return "method";
    }
}
