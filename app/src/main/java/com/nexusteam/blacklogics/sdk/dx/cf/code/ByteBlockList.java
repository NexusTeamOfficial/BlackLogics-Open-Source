
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.LabeledList;

public final class ByteBlockList extends LabeledList {

    public ByteBlockList(int size) {
        super(size);
    }

    public mod.agus.jcoderz.dx.cf.code.ByteBlock get(int n) {
        return (mod.agus.jcoderz.dx.cf.code.ByteBlock) get0(n);
    }

    public mod.agus.jcoderz.dx.cf.code.ByteBlock labelToBlock(int label) {
        int idx = indexOfLabel(label);

        if (idx < 0) {
            throw new IllegalArgumentException("no such label: "
                    + Hex.u2(label));
        }

        return get(idx);
    }

    public void set(int n, ByteBlock bb) {
        super.set(n, bb);
    }
}
