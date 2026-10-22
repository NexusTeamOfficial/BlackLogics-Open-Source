
package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public interface Member extends HasAttribute {
    public CstType getDefiningClass();

    public int getAccessFlags();

    public mod.agus.jcoderz.dx.rop.cst.CstString getName();

    public CstString getDescriptor();

    public CstNat getNat();

    @Override
    public AttributeList getAttributes();
}
