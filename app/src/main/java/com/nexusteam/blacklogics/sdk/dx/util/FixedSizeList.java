
package mod.agus.jcoderz.dx.util;

import java.util.Arrays;

public class FixedSizeList
        extends MutabilityControl implements ToHuman {
    private Object[] arr;

    public FixedSizeList(int size) {
        super(size != 0);

        try {
            arr = new Object[size];
        } catch (NegativeArraySizeException ex) {

            throw new IllegalArgumentException("size < 0");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {

            return true;
        }

        if ((other == null) || (getClass() != other.getClass())) {

            return false;
        }

        FixedSizeList list = (FixedSizeList) other;
        return Arrays.equals(arr, list.arr);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

    @Override
    public String toString() {
        String name = getClass().getName();

        return toString0(name.substring(name.lastIndexOf('.') + 1) + '{',
                         ", ",
                         "}",
                         false);
    }

    @Override
    public String toHuman() {
        String name = getClass().getName();

        return toString0(name.substring(name.lastIndexOf('.') + 1) + '{',
                         ", ",
                         "}",
                         true);
    }

    public String toString(String prefix, String separator, String suffix) {
        return toString0(prefix, separator, suffix, false);
    }

    public String toHuman(String prefix, String separator, String suffix) {
        return toString0(prefix, separator, suffix, true);
    }

    public final int size() {
        return arr.length;
    }

    public void shrinkToFit() {
        int sz = arr.length;
        int newSz = 0;

        for (int i = 0; i < sz; i++) {
            if (arr[i] != null) {
                newSz++;
            }
        }

        if (sz == newSz) {
            return;
        }

        throwIfImmutable();

        Object[] newa = new Object[newSz];
        int at = 0;

        for (int i = 0; i < sz; i++) {
            Object one = arr[i];
            if (one != null) {
                newa[at] = one;
                at++;
            }
        }

        arr = newa;
        if (newSz == 0) {
            setImmutable();
        }
    }

    protected final Object get0(int n) {
        try {
            Object result = arr[n];

            if (result == null) {
                throw new NullPointerException("unset: " + n);
            }

            return result;
        } catch (ArrayIndexOutOfBoundsException ex) {

            return throwIndex(n);
        }
    }

    protected final Object getOrNull0(int n) {
        return arr[n];
    }

    protected final void set0(int n, Object obj) {
        throwIfImmutable();

        try {
            arr[n] = obj;
        } catch (ArrayIndexOutOfBoundsException ex) {

            throwIndex(n);
        }
    }

    private Object throwIndex(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("n < 0");
        }

        throw new IndexOutOfBoundsException("n >= size()");
    }

    private String toString0(String prefix, String separator, String suffix,
                             boolean human) {
        int len = arr.length;
        StringBuilder sb = new StringBuilder(len * 10 + 10);

        if (prefix != null) {
            sb.append(prefix);
        }

        for (int i = 0; i < len; i++) {
            if ((i != 0) && (separator != null)) {
                sb.append(separator);
            }

            if (human) {
                sb.append(((ToHuman) arr[i]).toHuman());
            } else {
                sb.append(arr[i]);
            }
        }

        if (suffix != null) {
            sb.append(suffix);
        }

        return sb.toString();
    }

}
