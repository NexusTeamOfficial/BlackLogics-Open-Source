
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.cf.attrib.AttConstantValue;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;

public final class StdField extends StdMember implements Field {
    public StdField(CstType definingClass, int accessFlags, CstNat nat,
                    mod.agus.jcoderz.dx.cf.iface.AttributeList attributes) {
        super(definingClass, accessFlags, nat, attributes);
    }

    @Override
    public TypedConstant getConstantValue() {
        AttributeList attribs = getAttributes();
        AttConstantValue cval = (AttConstantValue)
            attribs.findFirst(AttConstantValue.ATTRIBUTE_NAME);

        if (cval == null) {
            return null;
        }

        return cval.getConstantValue();
    }
}
