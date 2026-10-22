
package mod.agus.jcoderz.dx.rop.cst;

public abstract class CstLiteralBits
        extends TypedConstant {
    public abstract boolean fitsInInt();

    public abstract int getIntBits();

    public abstract long getLongBits();

    public boolean fitsIn16Bits() {
        if (! fitsInInt()) {
            return false;
        }

        int bits = getIntBits();
        return (short) bits == bits;
    }

    public boolean fitsIn8Bits() {
        if (! fitsInInt()) {
            return false;
        }

        int bits = getIntBits();
        return (byte) bits == bits;
    }
}
