
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public abstract class StdMember implements Member {
    private final mod.agus.jcoderz.dx.rop.cst.CstType definingClass;

    private final int accessFlags;

    private final mod.agus.jcoderz.dx.rop.cst.CstNat nat;

    private final mod.agus.jcoderz.dx.cf.iface.AttributeList attributes;

    public StdMember(mod.agus.jcoderz.dx.rop.cst.CstType definingClass, int accessFlags, mod.agus.jcoderz.dx.rop.cst.CstNat nat,
                     mod.agus.jcoderz.dx.cf.iface.AttributeList attributes) {
        if (definingClass == null) {
            throw new NullPointerException("definingClass == null");
        }

        if (nat == null) {
            throw new NullPointerException("nat == null");
        }

        if (attributes == null) {
            throw new NullPointerException("attributes == null");
        }

        this.definingClass = definingClass;
        this.accessFlags = accessFlags;
        this.nat = nat;
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append(getClass().getName());
        sb.append('{');
        sb.append(nat.toHuman());
        sb.append('}');

        return sb.toString();
    }

    @Override
    public final CstType getDefiningClass() {
        return definingClass;
    }

    @Override
    public final int getAccessFlags() {
        return accessFlags;
    }

    @Override
    public final CstNat getNat() {
        return nat;
    }

    @Override
    public final mod.agus.jcoderz.dx.rop.cst.CstString getName() {
        return nat.getName();
    }

    @Override
    public final CstString getDescriptor() {
        return nat.getDescriptor();
    }

    @Override
    public final AttributeList getAttributes() {
        return attributes;
    }
}
