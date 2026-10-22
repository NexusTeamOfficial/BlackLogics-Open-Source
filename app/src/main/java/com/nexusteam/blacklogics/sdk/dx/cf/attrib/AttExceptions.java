
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public final class AttExceptions extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Exceptions";

    private final mod.agus.jcoderz.dx.rop.type.TypeList exceptions;

    public AttExceptions(mod.agus.jcoderz.dx.rop.type.TypeList exceptions) {
        super(ATTRIBUTE_NAME);

        try {
            if (exceptions.isMutable()) {
                throw new MutabilityException("exceptions.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("exceptions == null");
        }

        this.exceptions = exceptions;
    }

    @Override
    public int byteLength() {
        return 8 + exceptions.size() * 2;
    }

    public TypeList getExceptions() {
        return exceptions;
    }
}
