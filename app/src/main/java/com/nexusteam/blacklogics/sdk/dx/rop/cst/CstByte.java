
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstByte
        extends CstLiteral32 {
    public static final CstByte VALUE_0 = make((byte) 0);

    public static CstByte make(byte value) {
        return new CstByte(value);
    }

    public static CstByte make(int value) {
        byte cast = (byte) value;

        if (cast != value) {
            throw new IllegalArgumentException("bogus byte value: " +
                    value);
        }

        return make(cast);
    }

    private CstByte(byte value) {
        super(value);
    }

    @Override
    public String toString() {
        int value = getIntBits();
        return "byte{0x" + Hex.u1(value) + " / " + value + '}';
    }

    @Override
    public Type getType() {
        return Type.BYTE;
    }

    @Override
    public String typeName() {
        return "byte";
    }

    @Override
    public String toHuman() {
        return Integer.toString(getIntBits());
    }

    public byte getValue() {
        return (byte) getIntBits();
    }
}
