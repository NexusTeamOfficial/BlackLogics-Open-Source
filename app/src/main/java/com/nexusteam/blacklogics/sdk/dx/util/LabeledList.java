
package mod.agus.jcoderz.dx.util;

import java.util.Arrays;

public class LabeledList extends FixedSizeList {
    private final IntList labelToIndex;

    public LabeledList(int size) {
        super(size);

        labelToIndex = new IntList(size);
    }

    public LabeledList(LabeledList old) {
        super(old.size());
        labelToIndex = old.labelToIndex.mutableCopy();

        int sz = old.size();

        for (int i = 0; i < sz; i++) {
            Object one = old.get0(i);
            if (one != null) {
                set0(i, one);
            }
        }
    }

    public final int getMaxLabel() {
        int sz = labelToIndex.size();


        int i;
        for (i = sz - 1; (i >= 0) && (labelToIndex.get(i) < 0); i--)
             ;

        int newSize = i + 1;

        labelToIndex.shrink(newSize);

        return newSize;
    }

    private void removeLabel(int oldLabel) {
        labelToIndex.set(oldLabel, -1);
    }

    private void addLabelIndex(int label, int index) {
        int origSz = labelToIndex.size();

        for (int i = 0; i <= (label - origSz); i++) {
            labelToIndex.add(-1);
        }

        labelToIndex.set(label, index);
    }

    public final int indexOfLabel(int label) {
        if (label >= labelToIndex.size()) {
            return -1;
        } else {
            return labelToIndex.get(label);
        }
    }

    public final int[] getLabelsInOrder() {
        int sz = size();
        int[] result = new int[sz];

        for (int i = 0; i < sz; i++) {
            LabeledItem li = (LabeledItem) get0(i);
            if (li == null) {
                throw new NullPointerException("null at index " + i);
            }
            result[i] = li.getLabel();
        }

        Arrays.sort(result);
        return result;
    }

    @Override
    public void shrinkToFit() {
        super.shrinkToFit();

        rebuildLabelToIndex();
    }

    private void rebuildLabelToIndex() {
        int szItems = size();

        for (int i = 0; i < szItems; i++) {
            LabeledItem li = (LabeledItem) get0(i);

            if (li != null) {
                labelToIndex.set(li.getLabel(), i);
            }
        }
    }

    protected void set(int n, LabeledItem item) {
        LabeledItem old = (LabeledItem) getOrNull0(n);

        set0(n, item);

        if (old != null) {
            removeLabel(old.getLabel());
        }

        if (item != null) {
            addLabelIndex(item.getLabel(), n);
        }
    }
}
