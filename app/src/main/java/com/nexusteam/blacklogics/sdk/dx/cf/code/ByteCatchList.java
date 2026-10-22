
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.FixedSizeList;
import mod.agus.jcoderz.dx.util.IntList;

public final class ByteCatchList extends FixedSizeList {
    public static final ByteCatchList EMPTY = new ByteCatchList(0);

    public ByteCatchList(int count) {
        super(count);
    }

    public int byteLength() {
        return 2 + size() * 8;
    }

    public Item get(int n) {
        return (Item) get0(n);
    }

    public void set(int n, Item item) {
        if (item == null) {
            throw new NullPointerException("item == null");
        }

        set0(n, item);
    }

    public void set(int n, int startPc, int endPc, int handlerPc,
            mod.agus.jcoderz.dx.rop.cst.CstType exceptionClass) {
        set0(n, new Item(startPc, endPc, handlerPc, exceptionClass));
    }

    public ByteCatchList listFor(int pc) {
        int sz = size();
        Item[] resultArr = new Item[sz];
        int resultSz = 0;

        for (int i = 0; i < sz; i++) {
            Item one = get(i);
            if (one.covers(pc) && typeNotFound(one, resultArr, resultSz)) {
                resultArr[resultSz] = one;
                resultSz++;
            }
        }

        if (resultSz == 0) {
            return EMPTY;
        }

        ByteCatchList result = new ByteCatchList(resultSz);
        for (int i = 0; i < resultSz; i++) {
            result.set(i, resultArr[i]);
        }

        result.setImmutable();
        return result;
    }

    private static boolean typeNotFound(Item item, Item[] arr, int count) {
        mod.agus.jcoderz.dx.rop.cst.CstType type = item.getExceptionClass();

        for (int i = 0; i < count; i++) {
            mod.agus.jcoderz.dx.rop.cst.CstType one = arr[i].getExceptionClass();
            if ((one == type) || (one == mod.agus.jcoderz.dx.rop.cst.CstType.OBJECT)) {
                return false;
            }
        }

        return true;
    }

    public mod.agus.jcoderz.dx.util.IntList toTargetList(int noException) {
        if (noException < -1) {
            throw new IllegalArgumentException("noException < -1");
        }

        boolean hasDefault = (noException >= 0);
        int sz = size();

        if (sz == 0) {
            if (hasDefault) {
                return mod.agus.jcoderz.dx.util.IntList.makeImmutable(noException);
            }
            return mod.agus.jcoderz.dx.util.IntList.EMPTY;
        }

        mod.agus.jcoderz.dx.util.IntList result = new IntList(sz + (hasDefault ? 1 : 0));

        for (int i = 0; i < sz; i++) {
            result.add(get(i).getHandlerPc());
        }

        if (hasDefault) {
            result.add(noException);
        }

        result.setImmutable();
        return result;
    }

    public TypeList toRopCatchList() {
        int sz = size();
        if (sz == 0) {
            return mod.agus.jcoderz.dx.rop.type.StdTypeList.EMPTY;
        }

        mod.agus.jcoderz.dx.rop.type.StdTypeList result = new StdTypeList(sz);

        for (int i = 0; i < sz; i++) {
            result.set(i, get(i).getExceptionClass().getClassType());
        }

        result.setImmutable();
        return result;
    }

    public static class Item {
        private final int startPc;

        private final int endPc;

        private final int handlerPc;

        private final mod.agus.jcoderz.dx.rop.cst.CstType exceptionClass;

        public Item(int startPc, int endPc, int handlerPc,
                mod.agus.jcoderz.dx.rop.cst.CstType exceptionClass) {
            if (startPc < 0) {
                throw new IllegalArgumentException("startPc < 0");
            }

            if (endPc < startPc) {
                throw new IllegalArgumentException("endPc < startPc");
            }

            if (handlerPc < 0) {
                throw new IllegalArgumentException("handlerPc < 0");
            }

            this.startPc = startPc;
            this.endPc = endPc;
            this.handlerPc = handlerPc;
            this.exceptionClass = exceptionClass;
        }

        public int getStartPc() {
            return startPc;
        }

        public int getEndPc() {
            return endPc;
        }

        public int getHandlerPc() {
            return handlerPc;
        }

        public mod.agus.jcoderz.dx.rop.cst.CstType getExceptionClass() {
            return (exceptionClass != null) ?
                exceptionClass : CstType.OBJECT;
        }

        public boolean covers(int pc) {
            return (pc >= startPc) && (pc < endPc);
        }
    }
}
