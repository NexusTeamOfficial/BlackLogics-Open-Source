
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class InsnList
        extends FixedSizeList {
    public InsnList(int size) {
        super(size);
    }

    public Insn get(int n) {
        return (Insn) get0(n);
    }

    public void set(int n, Insn insn) {
        set0(n, insn);
    }

    public Insn getLast() {
        return get(size() - 1);
    }

    public void forEach(Insn.Visitor visitor) {
        int sz = size();

        for (int i = 0; i < sz; i++) {
            get(i).accept(visitor);
        }
    }

    public boolean contentEquals(InsnList b) {
        if (b == null) return false;

        int sz = size();

        if (sz != b.size()) return false;

        for (int i = 0; i < sz; i++) {
            if (!get(i).contentEquals(b.get(i))) {
                return false;
            }
        }

        return true;
    }

    public InsnList withRegisterOffset(int delta) {
        int sz = size();
        InsnList result = new InsnList(sz);

        for (int i = 0; i < sz; i++) {
            Insn one = (Insn) get0(i);
            if (one != null) {
                result.set0(i, one.withRegisterOffset(delta));
            }
        }

        if (isImmutable()) {
            result.setImmutable();
        }

        return result;
    }
}
