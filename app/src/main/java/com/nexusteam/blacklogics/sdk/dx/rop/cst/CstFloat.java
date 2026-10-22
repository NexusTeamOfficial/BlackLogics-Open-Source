
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstFloat
        extends CstLiteral32 {
    public static final CstFloat VALUE_0 = make(Float.floatToIntBits(0.0f));

    public static final CstFloat VALUE_1 = make(Float.floatToIntBits(1.0f));

    public static final CstFloat VALUE_2 = make(Float.floatToIntBits(2.0f));

    public static CstFloat make(int bits) {
        return new CstFloat(bits);
    }

    private CstFloat(int bits) {
        super(bits);
    }

    @Override
    public String toString() {
        int bits = getIntBits();
        return "float{0x" + Hex.u4(bits) + " / " +
            Float.intBitsToFloat(bits) + '}';
    }

    @Override
    public Type getType() {
        return Type.FLOAT;
    }

    @Override
    public String typeName() {
        return "float";
    }

    @Override
    public String toHuman() {
        return Float.toString(Float.intBitsToFloat(getIntBits()));
    }

    public float getValue() {
        return Float.intBitsToFloat(getIntBits());
    }
}
