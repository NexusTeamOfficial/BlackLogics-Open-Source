
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;

public final class StdMethod extends StdMember implements Method {
    private final mod.agus.jcoderz.dx.rop.type.Prototype effectiveDescriptor;

    public StdMethod(CstType definingClass, int accessFlags, CstNat nat,
                     AttributeList attributes) {
        super(definingClass, accessFlags, nat, attributes);

        String descStr = getDescriptor().getString();
        effectiveDescriptor =
            mod.agus.jcoderz.dx.rop.type.Prototype.intern(descStr, definingClass.getClassType(),
                                    AccessFlags.isStatic(accessFlags),
                                    nat.isInstanceInit());
    }

    @Override
    public Prototype getEffectiveDescriptor() {
        return effectiveDescriptor;
    }
}
