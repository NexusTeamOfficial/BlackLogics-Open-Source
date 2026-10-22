
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.CstString;

public final class AttSourceDebugExtension extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "SourceDebugExtension";

    private final mod.agus.jcoderz.dx.rop.cst.CstString smapString;

    public AttSourceDebugExtension(mod.agus.jcoderz.dx.rop.cst.CstString smapString) {
        super(ATTRIBUTE_NAME);

        if (smapString == null) {
            throw new NullPointerException("smapString == null");
        }

        this.smapString = smapString;
    }

    @Override
    public int byteLength() {


        return 6 + smapString.getUtf8Size();
    }

    public CstString getSmapString() {
        return smapString;
    }
}
