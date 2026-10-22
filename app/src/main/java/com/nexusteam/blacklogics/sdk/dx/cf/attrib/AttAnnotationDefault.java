
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.Constant;

public final class AttAnnotationDefault extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "AnnotationDefault";

    private final mod.agus.jcoderz.dx.rop.cst.Constant value;

    private final int byteLength;

    public AttAnnotationDefault(mod.agus.jcoderz.dx.rop.cst.Constant value, int byteLength) {
        super(ATTRIBUTE_NAME);

        if (value == null) {
            throw new NullPointerException("value == null");
        }

        this.value = value;
        this.byteLength = byteLength;
    }

    @Override
    public int byteLength() {

        return byteLength + 6;
    }

    public Constant getValue() {
        return value;
    }
}
