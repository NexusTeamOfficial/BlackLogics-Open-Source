
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.util.MutabilityException;

public abstract class BaseAnnotations extends BaseAttribute {
    private final mod.agus.jcoderz.dx.rop.annotation.Annotations annotations;

    private final int byteLength;

    public BaseAnnotations(String attributeName, mod.agus.jcoderz.dx.rop.annotation.Annotations annotations,
            int byteLength) {
        super(attributeName);

        try {
            if (annotations.isMutable()) {
                throw new MutabilityException("annotations.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("annotations == null");
        }

        this.annotations = annotations;
        this.byteLength = byteLength;
    }

    @Override
    public final int byteLength() {

        return byteLength + 6;
    }

    public final Annotations getAnnotations() {
        return annotations;
    }
}
