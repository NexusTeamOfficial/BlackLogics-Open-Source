
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public final class AttEnclosingMethod extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "EnclosingMethod";

    private final mod.agus.jcoderz.dx.rop.cst.CstType type;

    private final mod.agus.jcoderz.dx.rop.cst.CstNat method;

    public AttEnclosingMethod(mod.agus.jcoderz.dx.rop.cst.CstType type, mod.agus.jcoderz.dx.rop.cst.CstNat method) {
        super(ATTRIBUTE_NAME);

        if (type == null) {
            throw new NullPointerException("type == null");
        }

        this.type = type;
        this.method = method;
    }

    @Override
    public int byteLength() {
        return 10;
    }

    public CstType getEnclosingClass() {
        return type;
    }

    public CstNat getMethod() {
        return method;
    }
}
