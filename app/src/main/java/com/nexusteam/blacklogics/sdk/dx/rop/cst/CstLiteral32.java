
package mod.agus.jcoderz.dx.rop.cst;

public abstract class CstLiteral32
        extends CstLiteralBits {
    private final int bits;

     CstLiteral32(int bits) {
        this.bits = bits;
    }

    @Override
    public final boolean equals(Object other) {
        return (other != null) &&
            (getClass() == other.getClass()) &&
            bits == ((CstLiteral32) other).bits;
    }

    @Override
    public final int hashCode() {
        return bits;
    }

    @Override
    protected int compareTo0(Constant other) {
        int otherBits = ((CstLiteral32) other).bits;

        if (bits < otherBits) {
            return -1;
        } else if (bits > otherBits) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public final boolean isCategory2() {
        return false;
    }

    @Override
    public final boolean fitsInInt() {
        return true;
    }

    @Override
    public final int getIntBits() {
        return bits;
    }

    @Override
    public final long getLongBits() {
        return (long) bits;
    }
}
