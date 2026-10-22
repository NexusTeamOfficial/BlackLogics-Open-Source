
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstChar
        extends CstLiteral32 {
    public static final CstChar VALUE_0 = make((char) 0);

    public static CstChar make(char value) {
        return new CstChar(value);
    }

    public static CstChar make(int value) {
        char cast = (char) value;

        if (cast != value) {
            throw new IllegalArgumentException("bogus char value: " +
                    value);
        }

        return make(cast);
    }

    private CstChar(char value) {
        super(value);
    }

    @Override
    public String toString() {
        int value = getIntBits();
        return "char{0x" + Hex.u2(value) + " / " + value + '}';
    }

    @Override
    public Type getType() {
        return Type.CHAR;
    }

    @Override
    public String typeName() {
        return "char";
    }

    @Override
    public String toHuman() {
        return Integer.toString(getIntBits());
    }

    public char getValue() {
        return (char) getIntBits();
    }
}
