
package mod.agus.jcoderz.dx.util;

import java.util.Arrays;

public final class IntList extends MutabilityControl {
    public static final IntList EMPTY = new IntList(0);

    private int[] values;

    private int size;

    private boolean sorted;

    static {
        EMPTY.setImmutable();
    }

    public static IntList makeImmutable(int value) {
        IntList result = new IntList(1);

        result.add(value);
        result.setImmutable();

        return result;
    }

    public static IntList makeImmutable(int value0, int value1) {
        IntList result = new IntList(2);

        result.add(value0);
        result.add(value1);
        result.setImmutable();

        return result;
    }

    public IntList() {
        this(4);
    }

    public IntList(int initialCapacity) {
        super(true);

        try {
            values = new int[initialCapacity];
        } catch (NegativeArraySizeException ex) {

            throw new IllegalArgumentException("size < 0");
        }

        size = 0;
        sorted = true;
    }

    @Override
    public int hashCode() {
        int result = 0;

        for (int i = 0; i < size; i++) {
            result = (result * 31) + values[i];
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (! (other instanceof IntList)) {
            return false;
        }

        IntList otherList = (IntList) other;

        if (sorted != otherList.sorted) {
            return false;
        }

        if (size != otherList.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (values[i] != otherList.values[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size * 5 + 10);

        sb.append('{');

        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(values[i]);
        }

        sb.append('}');

        return sb.toString();
    }

    public int size() {
        return size;
    }

    public int get(int n) {
        if (n >= size) {
            throw new IndexOutOfBoundsException("n >= size()");
        }

        try {
            return values[n];
        } catch (ArrayIndexOutOfBoundsException ex) {

            throw new IndexOutOfBoundsException("n < 0");
        }
    }

    public void set(int n, int value) {
        throwIfImmutable();

        if (n >= size) {
            throw new IndexOutOfBoundsException("n >= size()");
        }

        try {
            values[n] = value;
            sorted = false;
        } catch (ArrayIndexOutOfBoundsException ex) {

            if (n < 0) {
                throw new IllegalArgumentException("n < 0");
            }
        }
    }

    public void add(int value) {
        throwIfImmutable();

        growIfNeeded();

        values[size++] = value;

        if (sorted && (size > 1)) {
            sorted = (value >= values[size - 2]);
        }
    }

    public void insert(int n, int value) {
        if (n > size) {
            throw new IndexOutOfBoundsException("n > size()");
        }

        growIfNeeded();

        System.arraycopy (values, n, values, n+1, size - n);
        values[n] = value;
        size++;

        sorted = sorted
                && (n == 0 || value > values[n-1])
                && (n == (size - 1) || value < values[n+1]);
    }

    public void removeIndex(int n) {
        if (n >= size) {
            throw new IndexOutOfBoundsException("n >= size()");
        }

        System.arraycopy (values, n + 1, values, n, size - n - 1);
        size--;


    }

    private void growIfNeeded() {
        if (size == values.length) {

            int[] newv = new int[size * 3 / 2 + 10];
            System.arraycopy(values, 0, newv, 0, size);
            values = newv;
        }
    }

    public int top() {
        return get(size - 1);
    }

    public int pop() {
        throwIfImmutable();

        int result;

        result = get(size-1);
        size--;

        return result;
    }

    public void pop(int n) {
        throwIfImmutable();

        size -= n;
    }

    public void shrink(int newSize) {
        if (newSize < 0) {
            throw new IllegalArgumentException("newSize < 0");
        }

        if (newSize > size) {
            throw new IllegalArgumentException("newSize > size");
        }

        throwIfImmutable();

        size = newSize;
    }

    public IntList mutableCopy() {
        int sz = size;
        IntList result = new IntList(sz);

        for (int i = 0; i < sz; i++) {
            result.add(values[i]);
        }

        return result;
    }

    public void sort() {
        throwIfImmutable();

        if (!sorted) {
            Arrays.sort(values, 0, size);
            sorted = true;
        }
    }

    public int indexOf(int value) {
        int ret = binarysearch(value);

        return ret >= 0 ? ret : -1;

    }

    public int binarysearch(int value) {
        int sz = size;

        if (!sorted) {

            for (int i = 0; i < sz; i++) {
                if (values[i] == value) {
                    return i;
                }
            }

            return -sz;
        }


        int min = -1;
        int max = sz;

        while (max > (min + 1)) {
            int guessIdx = min + ((max - min) >> 1);
            int guess = values[guessIdx];

            if (value <= guess) {
                max = guessIdx;
            } else {
                min = guessIdx;
            }
        }

        if ((max != sz)) {
            return (value == values[max]) ? max : (-max - 1);
        } else {
            return -sz - 1;
        }
    }


    public boolean contains(int value) {
        return indexOf(value) >= 0;
    }
}
