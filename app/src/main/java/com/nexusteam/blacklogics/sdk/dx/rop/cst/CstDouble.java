
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstDouble
        extends CstLiteral64 {
    public static final CstDouble VALUE_0 =
        new CstDouble(Double.doubleToLongBits(0.0));

    public static final CstDouble VALUE_1 =
        new CstDouble(Double.doubleToLongBits(1.0));

    public static CstDouble make(long bits) {
        return new CstDouble(bits);
    }

    private CstDouble(long bits) {
        super(bits);
    }

    @Override
    public String toString() {
        long bits = getLongBits();
        return "double{0x" + Hex.u8(bits) + " / " +
            Double.longBitsToDouble(bits) + '}';
    }

    @Override
    public Type getType() {
        return Type.DOUBLE;
    }

    @Override
    public String typeName() {
        return "double";
    }

    @Override
    public String toHuman() {
        return Double.toString(Double.longBitsToDouble(getLongBits()));
    }

    public double getValue() {
        return Double.longBitsToDouble(getLongBits());
    }
}
