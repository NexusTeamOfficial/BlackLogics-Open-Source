
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.util.MutabilityException;

public final class AttInnerClasses extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "InnerClasses";

    private final InnerClassList innerClasses;

    public AttInnerClasses(InnerClassList innerClasses) {
        super(ATTRIBUTE_NAME);

        try {
            if (innerClasses.isMutable()) {
                throw new MutabilityException("innerClasses.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("innerClasses == null");
        }

        this.innerClasses = innerClasses;
    }

    @Override
    public int byteLength() {
        return 8 + innerClasses.size() * 8;
    }

    public InnerClassList getInnerClasses() {
        return innerClasses;
    }
}
