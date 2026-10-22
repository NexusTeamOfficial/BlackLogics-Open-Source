
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class PositionList extends FixedSizeList {
    public static final PositionList EMPTY = new PositionList(0);

    public static final int NONE = 1;

    public static final int LINES = 2;

    public static final int IMPORTANT = 3;

    public static PositionList make(DalvInsnList insns, int howMuch) {
        switch (howMuch) {
            case NONE: {
                return EMPTY;
            }
            case LINES:
            case IMPORTANT: {

                break;
            }
            default: {
                throw new IllegalArgumentException("bogus howMuch");
            }
        }

        mod.agus.jcoderz.dx.rop.code.SourcePosition noInfo = mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO;
        mod.agus.jcoderz.dx.rop.code.SourcePosition cur = noInfo;
        int sz = insns.size();
        PositionList.Entry[] arr = new PositionList.Entry[sz];
        boolean lastWasTarget = false;
        int at = 0;

        for (int i = 0; i < sz; i++) {
            DalvInsn insn = insns.get(i);

            if (insn instanceof CodeAddress) {
                lastWasTarget = true;;
                continue;
            }

            mod.agus.jcoderz.dx.rop.code.SourcePosition pos = insn.getPosition();

            if (pos.equals(noInfo) || pos.sameLine(cur)) {
                continue;
            }

            if ((howMuch == IMPORTANT) && !lastWasTarget) {
                continue;
            }

            cur = pos;
            arr[at] = new PositionList.Entry(insn.getAddress(), pos);
            at++;

            lastWasTarget = false;
        }

        PositionList result = new PositionList(at);
        for (int i = 0; i < at; i++) {
            result.set(i, arr[i]);
        }

        result.setImmutable();
        return result;
    }

    public PositionList(int size) {
        super(size);
    }

    public Entry get(int n) {
        return (Entry) get0(n);
    }

    public void set(int n, Entry entry) {
        set0(n, entry);
    }

    public static class Entry {
        private final int address;

        private final mod.agus.jcoderz.dx.rop.code.SourcePosition position;

        public Entry (int address, mod.agus.jcoderz.dx.rop.code.SourcePosition position) {
            if (address < 0) {
                throw new IllegalArgumentException("address < 0");
            }

            if (position == null) {
                throw new NullPointerException("position == null");
            }

            this.address = address;
            this.position = position;
        }

        public int getAddress() {
            return address;
        }

        public SourcePosition getPosition() {
            return position;
        }
    }
}
