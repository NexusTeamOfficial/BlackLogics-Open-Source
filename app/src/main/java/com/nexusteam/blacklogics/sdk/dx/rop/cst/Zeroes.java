
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class Zeroes {
    private Zeroes() {

    }

    public static Constant zeroFor(Type type) {
        switch (type.getBasicType()) {
            case Type.BT_BOOLEAN: return CstBoolean.VALUE_FALSE;
            case Type.BT_BYTE:    return CstByte.VALUE_0;
            case Type.BT_CHAR:    return CstChar.VALUE_0;
            case Type.BT_DOUBLE:  return CstDouble.VALUE_0;
            case Type.BT_FLOAT:   return CstFloat.VALUE_0;
            case Type.BT_INT:     return CstInteger.VALUE_0;
            case Type.BT_LONG:    return CstLong.VALUE_0;
            case Type.BT_SHORT:   return CstShort.VALUE_0;
            case Type.BT_OBJECT:  return CstKnownNull.THE_ONE;
            default: {
                throw new UnsupportedOperationException("no zero for type: " +
                        type.toHuman());
            }
        }
    }
}
