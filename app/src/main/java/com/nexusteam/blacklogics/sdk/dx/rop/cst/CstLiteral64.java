
package mod.agus.jcoderz.dx.rop.cst;

public abstract class CstLiteral64
        extends CstLiteralBits {
    private final long bits;

     CstLiteral64(long bits) {
        this.bits = bits;
    }

    @Override
    public final boolean equals(Object other) {
        return (other != null) &&
            (getClass() == other.getClass()) &&
            bits == ((CstLiteral64) other).bits;
    }

    @Override
    public final int hashCode() {
        return (int) bits ^ (int) (bits >> 32);
    }

    @Override
    protected int compareTo0(Constant other) {
        long otherBits = ((CstLiteral64) other).bits;

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
        return true;
    }

    @Override
    public final boolean fitsInInt() {
        return (int) bits == bits;
    }

    @Override
    public final int getIntBits() {
        return (int) bits;
    }

    @Override
    public final long getLongBits() {
        return bits;
    }
}
