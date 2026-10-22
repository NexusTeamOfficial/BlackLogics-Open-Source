
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstEnumRef extends CstMemberRef {
    private mod.agus.jcoderz.dx.rop.cst.CstFieldRef fieldRef;

    public CstEnumRef(CstNat nat) {
        super(new CstType(nat.getFieldType()), nat);

        fieldRef = null;
    }

    @Override
    public String typeName() {
        return "enum";
    }

    @Override
    public Type getType() {
        return getDefiningClass().getClassType();
    }

    public mod.agus.jcoderz.dx.rop.cst.CstFieldRef getFieldRef() {
        if (fieldRef == null) {
            fieldRef = new CstFieldRef(getDefiningClass(), getNat());
        }

        return fieldRef;
    }
}
