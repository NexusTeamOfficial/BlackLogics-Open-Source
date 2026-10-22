
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public class CstArray extends Constant {
    private final List list;

    public CstArray(List list) {
        if (list == null) {
            throw new NullPointerException("list == null");
        }

        list.throwIfMutable();

        this.list = list;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof CstArray)) {
            return false;
        }

        return list.equals(((CstArray) other).list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    protected int compareTo0(Constant other) {
        return list.compareTo(((CstArray) other).list);
    }

    @Override
    public String toString() {
        return list.toString("array{", ", ", "}");
    }

    @Override
    public String typeName() {
        return "array";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String toHuman() {
        return list.toHuman("{", ", ", "}");
    }

    public List getList() {
        return list;
    }

    public static final class List
            extends FixedSizeList implements Comparable<List> {
        public List(int size) {
            super(size);
        }

        @Override
        public int compareTo(List other) {
            int thisSize = size();
            int otherSize = other.size();
            int compareSize = (thisSize < otherSize) ? thisSize : otherSize;

            for (int i = 0; i < compareSize; i++) {
                Constant thisItem = (Constant) get0(i);
                Constant otherItem = (Constant) other.get0(i);
                int compare = thisItem.compareTo(otherItem);
                if (compare != 0) {
                    return compare;
                }
            }

            if (thisSize < otherSize) {
                return -1;
            } else if (thisSize > otherSize) {
                return 1;
            }

            return 0;
        }

        public Constant get(int n) {
            return (Constant) get0(n);
        }

        public void set(int n, Constant a) {
            set0(n, a);
        }
    }
}
