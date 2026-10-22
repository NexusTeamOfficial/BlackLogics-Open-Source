
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.cst.CstString;

public class LocalItem implements Comparable<LocalItem> {
    private final mod.agus.jcoderz.dx.rop.cst.CstString name;

    private final mod.agus.jcoderz.dx.rop.cst.CstString signature;

    public static LocalItem make(mod.agus.jcoderz.dx.rop.cst.CstString name, mod.agus.jcoderz.dx.rop.cst.CstString signature) {
        if (name == null && signature == null) {
            return null;
        }

        return new LocalItem (name, signature);
    }

    private LocalItem(mod.agus.jcoderz.dx.rop.cst.CstString name, mod.agus.jcoderz.dx.rop.cst.CstString signature) {
        this.name = name;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LocalItem)) {
            return false;
        }

        LocalItem local = (LocalItem) other;

        return 0 == compareTo(local);
    }

    private static int compareHandlesNulls(mod.agus.jcoderz.dx.rop.cst.CstString a, mod.agus.jcoderz.dx.rop.cst.CstString b) {
        if (a == b) {
            return 0;
        } else if (a == null) {
            return -1;
        } else if (b == null) {
            return 1;
        } else {
            return a.compareTo(b);
        }
    }

    @Override
    public int compareTo(LocalItem local) {
        int ret;

        ret = compareHandlesNulls(name, local.name);

        if (ret != 0) {
            return ret;
        }

        ret = compareHandlesNulls(signature, local.signature);

        return ret;
    }


    @Override
    public int hashCode() {
        return (name == null ? 0 : name.hashCode()) * 31
                + (signature == null ? 0 : signature.hashCode());
    }

    @Override
    public String toString() {
        if (name != null && signature == null) {
            return name.toQuoted();
        } else if (name == null && signature == null) {
            return "";
        }

        return "[" + (name == null ? "" : name.toQuoted())
                + "|" + (signature == null ? "" : signature.toQuoted());
    }

    public mod.agus.jcoderz.dx.rop.cst.CstString getName() {
        return name;
    }

    public CstString getSignature() {
        return signature;
    }
}
