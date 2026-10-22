
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstKnownNull extends CstLiteralBits {
    public static final CstKnownNull THE_ONE = new CstKnownNull();

    private CstKnownNull() {

    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof CstKnownNull);
    }

    @Override
    public int hashCode() {
        return 0x4466757a;
    }

    @Override
    protected int compareTo0(Constant other) {
        return 0;
    }

    @Override
    public String toString() {
        return "known-null";
    }

    @Override
    public Type getType() {
        return Type.KNOWN_NULL;
    }

    @Override
    public String typeName() {
        return "known-null";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String toHuman() {
        return "null";
    }

    @Override
    public boolean fitsInInt() {

        return true;
    }

    @Override
    public int getIntBits() {
        return 0;
    }

    @Override
    public long getLongBits() {
        return 0;
    }
}
