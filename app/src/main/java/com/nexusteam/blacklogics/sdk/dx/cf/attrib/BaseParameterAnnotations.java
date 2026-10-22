
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public abstract class BaseParameterAnnotations extends BaseAttribute {
    private final mod.agus.jcoderz.dx.rop.annotation.AnnotationsList parameterAnnotations;

    private final int byteLength;

    public BaseParameterAnnotations(String attributeName,
                                    mod.agus.jcoderz.dx.rop.annotation.AnnotationsList parameterAnnotations, int byteLength) {
        super(attributeName);

        try {
            if (parameterAnnotations.isMutable()) {
                throw new MutabilityException(
                        "parameterAnnotations.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("parameterAnnotations == null");
        }

        this.parameterAnnotations = parameterAnnotations;
        this.byteLength = byteLength;
    }

    @Override
    public final int byteLength() {

        return byteLength + 6;
    }

    public final AnnotationsList getParameterAnnotations() {
        return parameterAnnotations;
    }
}
