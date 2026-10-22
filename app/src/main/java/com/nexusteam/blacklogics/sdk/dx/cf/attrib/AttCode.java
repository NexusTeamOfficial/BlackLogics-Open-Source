
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.ByteCatchList;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public final class AttCode extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Code";

    private final int maxStack;

    private final int maxLocals;

    private final mod.agus.jcoderz.dx.cf.code.BytecodeArray code;

    private final mod.agus.jcoderz.dx.cf.code.ByteCatchList catches;

    private final mod.agus.jcoderz.dx.cf.iface.AttributeList attributes;

    public AttCode(int maxStack, int maxLocals, mod.agus.jcoderz.dx.cf.code.BytecodeArray code,
                   mod.agus.jcoderz.dx.cf.code.ByteCatchList catches, mod.agus.jcoderz.dx.cf.iface.AttributeList attributes) {
        super(ATTRIBUTE_NAME);

        if (maxStack < 0) {
            throw new IllegalArgumentException("maxStack < 0");
        }

        if (maxLocals < 0) {
            throw new IllegalArgumentException("maxLocals < 0");
        }

        if (code == null) {
            throw new NullPointerException("code == null");
        }

        try {
            if (catches.isMutable()) {
                throw new mod.agus.jcoderz.dx.util.MutabilityException("catches.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("catches == null");
        }

        try {
            if (attributes.isMutable()) {
                throw new MutabilityException("attributes.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("attributes == null");
        }

        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.catches = catches;
        this.attributes = attributes;
    }

    @Override
    public int byteLength() {
        return 10 + code.byteLength() + catches.byteLength() +
            attributes.byteLength();
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public BytecodeArray getCode() {
        return code;
    }

    public ByteCatchList getCatches() {
        return catches;
    }

    public AttributeList getAttributes() {
        return attributes;
    }
}
