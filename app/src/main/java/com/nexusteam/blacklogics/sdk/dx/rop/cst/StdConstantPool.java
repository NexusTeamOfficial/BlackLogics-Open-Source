
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.MutabilityControl;

public final class StdConstantPool
        extends MutabilityControl implements ConstantPool {
    private final Constant[] entries;

    public StdConstantPool(int size) {
        super(size > 1);

        if (size < 1) {
            throw new IllegalArgumentException("size < 1");
        }

        entries = new Constant[size];
    }

    @Override
    public int size() {
        return entries.length;
    }

    @Override
    public Constant getOrNull(int n) {
        try {
            return entries[n];
        } catch (IndexOutOfBoundsException ex) {

            return throwInvalid(n);
        }
    }

    @Override
    public Constant get0Ok(int n) {
        if (n == 0) {
            return null;
        }

        return get(n);
    }

    @Override
    public Constant get(int n) {
        try {
            Constant result = entries[n];

            if (result == null) {
                throwInvalid(n);
            }

            return result;
        } catch (IndexOutOfBoundsException ex) {

            return throwInvalid(n);
        }
    }

    @Override
    public Constant[] getEntries() {
        return entries;
    }

    public void set(int n, Constant cst) {
        throwIfImmutable();

        boolean cat2 = (cst != null) && cst.isCategory2();

        if (n < 1) {
            throw new IllegalArgumentException("n < 1");
        }

        if (cat2) {

            if (n == (entries.length - 1)) {
                throw new IllegalArgumentException("(n == size - 1) && " +
                                                   "cst.isCategory2()");
            }
            entries[n + 1] = null;
        }

        if ((cst != null) && (entries[n] == null)) {
            Constant prev = entries[n - 1];
            if ((prev != null) && prev.isCategory2()) {
                entries[n - 1] = null;
            }
        }

        entries[n] = cst;
    }

    private static Constant throwInvalid(int idx) {
        throw new ExceptionWithContext("invalid constant pool index " +
                                       Hex.u2(idx));
    }
}
