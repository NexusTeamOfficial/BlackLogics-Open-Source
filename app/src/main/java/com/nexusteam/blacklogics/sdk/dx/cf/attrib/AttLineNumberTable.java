
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.LineNumberList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public final class AttLineNumberTable extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "LineNumberTable";

    private final mod.agus.jcoderz.dx.cf.code.LineNumberList lineNumbers;

    public AttLineNumberTable(mod.agus.jcoderz.dx.cf.code.LineNumberList lineNumbers) {
        super(ATTRIBUTE_NAME);

        try {
            if (lineNumbers.isMutable()) {
                throw new MutabilityException("lineNumbers.isMutable()");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("lineNumbers == null");
        }

        this.lineNumbers = lineNumbers;
    }

    @Override
    public int byteLength() {
        return 8 + 4 * lineNumbers.size();
    }

    public LineNumberList getLineNumbers() {
        return lineNumbers;
    }
}
