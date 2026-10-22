
package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.StdField;
import mod.agus.jcoderz.dx.cf.iface.StdFieldList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;

final  class FieldListParser extends mod.agus.jcoderz.dx.cf.direct.MemberListParser {
    private final mod.agus.jcoderz.dx.cf.iface.StdFieldList fields;

    public FieldListParser(DirectClassFile cf, CstType definer, int offset,
                           AttributeFactory attributeFactory) {
        super(cf, definer, offset, attributeFactory);
        fields = new mod.agus.jcoderz.dx.cf.iface.StdFieldList(getCount());
    }

    public StdFieldList getList() {
        parseIfNecessary();
        return fields;
    }

    @Override
    protected String humanName() {
        return "field";
    }

    @Override
    protected String humanAccessFlags(int accessFlags) {
        return AccessFlags.fieldString(accessFlags);
    }

    @Override
    protected int getAttributeContext() {
        return AttributeFactory.CTX_FIELD;
    }

    @Override
    protected Member set(int n, int accessFlags, CstNat nat,
                         AttributeList attributes) {
        mod.agus.jcoderz.dx.cf.iface.StdField field =
            new StdField(getDefiner(), accessFlags, nat, attributes);

        fields.set(n, field);
        return field;
    }
}
