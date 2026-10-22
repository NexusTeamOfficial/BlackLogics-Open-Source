
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.CstString;

public final class AttSignature extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Signature";

    private final mod.agus.jcoderz.dx.rop.cst.CstString signature;

    public AttSignature(mod.agus.jcoderz.dx.rop.cst.CstString signature) {
        super(ATTRIBUTE_NAME);

        if (signature == null) {
            throw new NullPointerException("signature == null");
        }

        this.signature = signature;
    }

    @Override
    public int byteLength() {
        return 8;
    }

    public CstString getSignature() {
        return signature;
    }
}
