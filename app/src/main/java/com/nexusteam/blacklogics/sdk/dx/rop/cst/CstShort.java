
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstShort
        extends CstLiteral32 {
    public static final CstShort VALUE_0 = make((short) 0);

    public static CstShort make(short value) {
        return new CstShort(value);
    }

    public static CstShort make(int value) {
        short cast = (short) value;

        if (cast != value) {
            throw new IllegalArgumentException("bogus short value: " +
                    value);
        }

        return make(cast);
    }

    private CstShort(short value) {
        super(value);
    }

    @Override
    public String toString() {
        int value = getIntBits();
        return "short{0x" + Hex.u2(value) + " / " + value + '}';
    }

    @Override
    public Type getType() {
        return Type.SHORT;
    }

    @Override
    public String typeName() {
        return "short";
    }

    @Override
    public String toHuman() {
        return Integer.toString(getIntBits());
    }

    public short getValue() {
        return (short) getIntBits();
    }
}
