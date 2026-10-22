
package mod.agus.jcoderz.dx.rop.annotation;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;

public final class NameValuePair implements Comparable<NameValuePair> {
    private final mod.agus.jcoderz.dx.rop.cst.CstString name;

    private final mod.agus.jcoderz.dx.rop.cst.Constant value;

    public NameValuePair(mod.agus.jcoderz.dx.rop.cst.CstString name, mod.agus.jcoderz.dx.rop.cst.Constant value) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        if (value == null) {
            throw new NullPointerException("value == null");
        }

        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name.toHuman() + ":" + value;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 + value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof NameValuePair)) {
            return false;
        }

        NameValuePair otherPair = (NameValuePair) other;

        return name.equals(otherPair.name)
            && value.equals(otherPair.value);
    }

    @Override
    public int compareTo(NameValuePair other) {
        int result = name.compareTo(other.name);

        if (result != 0) {
            return result;
        }

        return value.compareTo(other.value);
    }

    public CstString getName() {
        return name;
    }

    public Constant getValue() {
        return value;
    }
}
