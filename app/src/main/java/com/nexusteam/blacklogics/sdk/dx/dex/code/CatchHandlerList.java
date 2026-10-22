
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.FixedSizeList;
import mod.agus.jcoderz.dx.util.Hex;

public final class CatchHandlerList extends FixedSizeList
        implements Comparable<CatchHandlerList> {
    public static final CatchHandlerList EMPTY = new CatchHandlerList(0);

    public CatchHandlerList(int size) {
        super(size);
    }

    public Entry get(int n) {
        return (Entry) get0(n);
    }

    @Override
    public String toHuman() {
        return toHuman("", "");
    }

    public String toHuman(String prefix, String header) {
        StringBuilder sb = new StringBuilder(100);
        int size = size();

        sb.append(prefix);
        sb.append(header);
        sb.append("catch ");

        for (int i = 0; i < size; i++) {
            Entry entry = get(i);

            if (i != 0) {
                sb.append(",\n");
                sb.append(prefix);
                sb.append("  ");
            }

            if ((i == (size - 1)) && catchesAll()) {
                sb.append("<any>");
            } else {
                sb.append(entry.getExceptionType().toHuman());
            }

            sb.append(" -> ");
            sb.append(Hex.u2or4(entry.getHandler()));
        }

        return sb.toString();
    }

    public boolean catchesAll() {
        int size = size();

        if (size == 0) {
            return false;
        }

        Entry last = get(size - 1);
        return last.getExceptionType().equals(mod.agus.jcoderz.dx.rop.cst.CstType.OBJECT);
    }

    public void set(int n, mod.agus.jcoderz.dx.rop.cst.CstType exceptionType, int handler) {
        set0(n, new Entry(exceptionType, handler));
    }

    public void set(int n, Entry entry) {
        set0(n, entry);
    }

    @Override
    public int compareTo(CatchHandlerList other) {
        if (this == other) {

            return 0;
        }

        int thisSize = size();
        int otherSize = other.size();
        int checkSize = Math.min(thisSize, otherSize);

        for (int i = 0; i < checkSize; i++) {
            Entry thisEntry = get(i);
            Entry otherEntry = other.get(i);
            int compare = thisEntry.compareTo(otherEntry);
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

    public static class Entry implements Comparable<Entry> {
        private final mod.agus.jcoderz.dx.rop.cst.CstType exceptionType;

        private final int handler;

        public Entry(mod.agus.jcoderz.dx.rop.cst.CstType exceptionType, int handler) {
            if (handler < 0) {
                throw new IllegalArgumentException("handler < 0");
            }

            if (exceptionType == null) {
                throw new NullPointerException("exceptionType == null");
            }

            this.handler = handler;
            this.exceptionType = exceptionType;
        }

        @Override
        public int hashCode() {
            return (handler * 31) + exceptionType.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Entry) {
                return (compareTo((Entry) other) == 0);
            }

            return false;
        }

        @Override
        public int compareTo(Entry other) {
            if (handler < other.handler) {
                return -1;
            } else if (handler > other.handler) {
                return 1;
            }

            return exceptionType.compareTo(other.exceptionType);
        }

        public CstType getExceptionType() {
            return exceptionType;
        }

        public int getHandler() {
            return handler;
        }
    }
}
