
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class CatchTable extends FixedSizeList
        implements Comparable<CatchTable> {
    public static final CatchTable EMPTY = new CatchTable(0);

    public CatchTable(int size) {
        super(size);
    }

    public Entry get(int n) {
        return (Entry) get0(n);
    }

    public void set(int n, Entry entry) {
        set0(n, entry);
    }

    @Override
    public int compareTo(CatchTable other) {
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
        private final int start;

        private final int end;

        private final CatchHandlerList handlers;

        public Entry(int start, int end, CatchHandlerList handlers) {
            if (start < 0) {
                throw new IllegalArgumentException("start < 0");
            }

            if (end <= start) {
                throw new IllegalArgumentException("end <= start");
            }

            if (handlers.isMutable()) {
                throw new IllegalArgumentException("handlers.isMutable()");
            }

            this.start = start;
            this.end = end;
            this.handlers = handlers;
        }

        @Override
        public int hashCode() {
            int hash = (start * 31) + end;
            hash = (hash * 31) + handlers.hashCode();
            return hash;
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
            if (start < other.start) {
                return -1;
            } else if (start > other.start) {
                return 1;
            }

            if (end < other.end) {
                return -1;
            } else if (end > other.end) {
                return 1;
            }

            return handlers.compareTo(other.handlers);
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public CatchHandlerList getHandlers() {
            return handlers;
        }
    }
}
