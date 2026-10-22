
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.CstString;

public final class AttSourceFile extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "SourceFile";

    private final mod.agus.jcoderz.dx.rop.cst.CstString sourceFile;

    public AttSourceFile(mod.agus.jcoderz.dx.rop.cst.CstString sourceFile) {
        super(ATTRIBUTE_NAME);

        if (sourceFile == null) {
            throw new NullPointerException("sourceFile == null");
        }

        this.sourceFile = sourceFile;
    }

    @Override
    public int byteLength() {
        return 8;
    }

    public CstString getSourceFile() {
        return sourceFile;
    }
}
