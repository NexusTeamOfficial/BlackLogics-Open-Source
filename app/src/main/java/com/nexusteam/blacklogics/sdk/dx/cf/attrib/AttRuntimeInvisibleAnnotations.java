
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;

public final class AttRuntimeInvisibleAnnotations extends BaseAnnotations {
    public static final String ATTRIBUTE_NAME = "RuntimeInvisibleAnnotations";

    public AttRuntimeInvisibleAnnotations(Annotations annotations,
                                          int byteLength) {
        super(ATTRIBUTE_NAME, annotations, byteLength);
    }
}
