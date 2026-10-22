
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstBoolean
        extends CstLiteral32 {
    public static final CstBoolean VALUE_FALSE = new CstBoolean(false);

    public static final CstBoolean VALUE_TRUE = new CstBoolean(true);

    public static CstBoolean make(boolean value) {
        return value ? VALUE_TRUE : VALUE_FALSE;
    }

    public static CstBoolean make(int value) {
        if (value == 0) {
            return VALUE_FALSE;
        } else if (value == 1) {
            return VALUE_TRUE;
        } else {
            throw new IllegalArgumentException("bogus value: " + value);
        }
    }

    private CstBoolean(boolean value) {
        super(value ? 1 : 0);
    }

    @Override
    public String toString() {
        return getValue() ? "boolean{true}" : "boolean{false}";
    }

    @Override
    public Type getType() {
        return Type.BOOLEAN;
    }

    @Override
    public String typeName() {
        return "boolean";
    }

    @Override
    public String toHuman() {
        return getValue() ? "true" : "false";
    }

    public boolean getValue() {
        return (getIntBits() == 0) ? false : true;
    }
}
