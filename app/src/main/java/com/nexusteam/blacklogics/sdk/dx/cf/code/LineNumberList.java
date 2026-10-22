
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class LineNumberList extends FixedSizeList {
    public static final LineNumberList EMPTY = new LineNumberList(0);

    public static LineNumberList concat(LineNumberList list1,
                                        LineNumberList list2) {
        if (list1 == EMPTY) {

            return list2;
        }

        int sz1 = list1.size();
        int sz2 = list2.size();
        LineNumberList result = new LineNumberList(sz1 + sz2);

        for (int i = 0; i < sz1; i++) {
            result.set(i, list1.get(i));
        }

        for (int i = 0; i < sz2; i++) {
            result.set(sz1 + i, list2.get(i));
        }

        return result;
    }

    public LineNumberList(int count) {
        super(count);
    }

    public Item get(int n) {
        return (Item) get0(n);
    }

    public void set(int n, Item item) {
        if (item == null) {
            throw new NullPointerException("item == null");
        }

        set0(n, item);
    }

    public void set(int n, int startPc, int lineNumber) {
        set0(n, new Item(startPc, lineNumber));
    }

    public int pcToLine(int pc) {
        int sz = size();
        int bestPc = -1;
        int bestLine = -1;

        for (int i = 0; i < sz; i++) {
            Item one = get(i);
            int onePc = one.getStartPc();
            if ((onePc <= pc) && (onePc > bestPc)) {
                bestPc = onePc;
                bestLine = one.getLineNumber();
                if (bestPc == pc) {

                    break;
                }
            }
        }

        return bestLine;
    }

    public static class Item {
        private final int startPc;

        private final int lineNumber;

        public Item(int startPc, int lineNumber) {
            if (startPc < 0) {
                throw new IllegalArgumentException("startPc < 0");
            }

            if (lineNumber < 0) {
                throw new IllegalArgumentException("lineNumber < 0");
            }

            this.startPc = startPc;
            this.lineNumber = lineNumber;
        }

        public int getStartPc() {
            return startPc;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }
}
