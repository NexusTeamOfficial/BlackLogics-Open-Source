
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstNat extends mod.agus.jcoderz.dx.rop.cst.Constant {
    public static final CstNat PRIMITIVE_TYPE_NAT =
        new CstNat(new mod.agus.jcoderz.dx.rop.cst.CstString("TYPE"),
                   new mod.agus.jcoderz.dx.rop.cst.CstString("Ljava/lang/Class;"));

    private final mod.agus.jcoderz.dx.rop.cst.CstString name;

    private final mod.agus.jcoderz.dx.rop.cst.CstString descriptor;

    public CstNat(mod.agus.jcoderz.dx.rop.cst.CstString name, mod.agus.jcoderz.dx.rop.cst.CstString descriptor) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        if (descriptor == null) {
            throw new NullPointerException("descriptor == null");
        }

        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CstNat)) {
            return false;
        }

        CstNat otherNat = (CstNat) other;
        return name.equals(otherNat.name) &&
            descriptor.equals(otherNat.descriptor);
    }

    @Override
    public int hashCode() {
        return (name.hashCode() * 31) ^ descriptor.hashCode();
    }

    @Override
    protected int compareTo0(Constant other) {
        CstNat otherNat = (CstNat) other;
        int cmp = name.compareTo(otherNat.name);

        if (cmp != 0) {
            return cmp;
        }

        return descriptor.compareTo(otherNat.descriptor);
    }

    @Override
    public String toString() {
        return "nat{" + toHuman() + '}';
    }

    @Override
    public String typeName() {
        return "nat";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    public mod.agus.jcoderz.dx.rop.cst.CstString getName() {
        return name;
    }

    public CstString getDescriptor() {
        return descriptor;
    }

    @Override
    public String toHuman() {
        return name.toHuman() + ':' + descriptor.toHuman();
    }

    public Type getFieldType() {
        return Type.intern(descriptor.getString());
    }

    public final boolean isInstanceInit() {
        return name.getString().equals("<init>");
    }

    public final boolean isClassInit() {
        return name.getString().equals("<clinit>");
    }
}
