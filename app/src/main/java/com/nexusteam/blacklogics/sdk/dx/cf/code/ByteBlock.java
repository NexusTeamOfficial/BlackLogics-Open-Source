
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.util.LabeledItem;

public final class ByteBlock implements LabeledItem {
    private final int label;

    private final int start;

    private final int end;

    private final mod.agus.jcoderz.dx.util.IntList successors;

    private final mod.agus.jcoderz.dx.cf.code.ByteCatchList catches;

    public ByteBlock(int label, int start, int end, mod.agus.jcoderz.dx.util.IntList successors,
                     mod.agus.jcoderz.dx.cf.code.ByteCatchList catches) {
        if (label < 0) {
            throw new IllegalArgumentException("label < 0");
        }

        if (start < 0) {
            throw new IllegalArgumentException("start < 0");
        }

        if (end <= start) {
            throw new IllegalArgumentException("end <= start");
        }

        if (successors == null) {
            throw new NullPointerException("targets == null");
        }

        int sz = successors.size();
        for (int i = 0; i < sz; i++) {
            if (successors.get(i) < 0) {
                throw new IllegalArgumentException("successors[" + i +
                                                   "] == " +
                                                   successors.get(i));
            }
        }

        if (catches == null) {
            throw new NullPointerException("catches == null");
        }

        this.label = label;
        this.start = start;
        this.end = end;
        this.successors = successors;
        this.catches = catches;
    }

    @Override
    public String toString() {
        return '{' + mod.agus.jcoderz.dx.util.Hex.u2(label) + ": " + mod.agus.jcoderz.dx.util.Hex.u2(start) + ".." +
            Hex.u2(end) + '}';
    }

    @Override
    public int getLabel() {
        return label;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public IntList getSuccessors() {
        return successors;
    }

    public ByteCatchList getCatches() {
        return catches;
    }
}
