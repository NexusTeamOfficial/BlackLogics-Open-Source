package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstProtoRef extends TypedConstant {

    private final Prototype prototype;

    public CstProtoRef(Prototype prototype) {
        this.prototype = prototype;
    }

    public static CstProtoRef make(CstString descriptor) {
        Prototype prototype = Prototype.fromDescriptor(descriptor.getString());
        return new CstProtoRef(prototype);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CstProtoRef)) {
            return false;
        }
        CstProtoRef otherCstProtoRef = (CstProtoRef) other;
        return getPrototype().equals(otherCstProtoRef.getPrototype());
    }

    @Override
    public int hashCode() {
        return prototype.hashCode();
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String typeName() {
        return "proto";
    }

    @Override
    protected int compareTo0(Constant other) {
        CstProtoRef otherCstProtoRef = (CstProtoRef) other;
        return prototype.compareTo(otherCstProtoRef.getPrototype());
    }

    @Override
    public String toHuman() {
        return prototype.getDescriptor();
    }

    @Override
    public final String toString() {
        return typeName() + "{" + toHuman() + '}';
    }

    public Prototype getPrototype() {
        return prototype;
    }

    @Override
    public Type getType() {
        return Type.METHOD_TYPE;
    }
}
