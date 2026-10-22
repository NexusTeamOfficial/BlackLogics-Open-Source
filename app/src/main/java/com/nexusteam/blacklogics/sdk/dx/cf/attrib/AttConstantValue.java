
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;

public final class AttConstantValue extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "ConstantValue";

    private final mod.agus.jcoderz.dx.rop.cst.TypedConstant constantValue;

    public AttConstantValue(mod.agus.jcoderz.dx.rop.cst.TypedConstant constantValue) {
        super(ATTRIBUTE_NAME);

        if (!((constantValue instanceof CstString) ||
               (constantValue instanceof CstInteger) ||
               (constantValue instanceof CstLong) ||
               (constantValue instanceof CstFloat) ||
               (constantValue instanceof CstDouble))) {
            if (constantValue == null) {
                throw new NullPointerException("constantValue == null");
            }
            throw new IllegalArgumentException("bad type for constantValue");
        }

        this.constantValue = constantValue;
    }

    @Override
    public int byteLength() {
        return 8;
    }

    public TypedConstant getConstantValue() {
        return constantValue;
    }
}
