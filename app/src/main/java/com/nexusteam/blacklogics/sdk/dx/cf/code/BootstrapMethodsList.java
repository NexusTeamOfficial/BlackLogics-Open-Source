package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public class BootstrapMethodsList extends FixedSizeList {
    public static final BootstrapMethodsList EMPTY = new BootstrapMethodsList(0);

    public BootstrapMethodsList(int count) {
        super(count);
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

    public void set(int n, mod.agus.jcoderz.dx.rop.cst.CstType declaringClass, mod.agus.jcoderz.dx.rop.cst.CstMethodHandle bootstrapMethodHandle,
                    BootstrapMethodArgumentsList arguments) {
        set(n, new Item(declaringClass, bootstrapMethodHandle, arguments));
    }

    public static BootstrapMethodsList concat(BootstrapMethodsList list1,
                                              BootstrapMethodsList list2) {
        if (list1 == EMPTY) {
            return list2;
        } else if (list2 == EMPTY) {
            return list1;
        }

        int sz1 = list1.size();
        int sz2 = list2.size();
        BootstrapMethodsList result = new BootstrapMethodsList(sz1 + sz2);

        for (int i = 0; i < sz1; i++) {
            result.set(i, list1.get(i));
        }

        for (int i = 0; i < sz2; i++) {
            result.set(sz1 + i, list2.get(i));
        }

        return result;
    }

    public static class Item {
        private final BootstrapMethodArgumentsList bootstrapMethodArgumentsList;
        private final mod.agus.jcoderz.dx.rop.cst.CstMethodHandle bootstrapMethodHandle;
        private final mod.agus.jcoderz.dx.rop.cst.CstType declaringClass;

        public Item(mod.agus.jcoderz.dx.rop.cst.CstType declaringClass, mod.agus.jcoderz.dx.rop.cst.CstMethodHandle bootstrapMethodHandle,
                    BootstrapMethodArgumentsList bootstrapMethodArguments) {
            if (declaringClass == null) {
                throw new NullPointerException("declaringClass == null");
            }
            if (bootstrapMethodHandle == null) {
                throw new NullPointerException("bootstrapMethodHandle == null");
            }
            if (bootstrapMethodArguments == null) {
                throw new NullPointerException("bootstrapMethodArguments == null");
            }
            this.bootstrapMethodHandle = bootstrapMethodHandle;
            this.bootstrapMethodArgumentsList = bootstrapMethodArguments;
            this.declaringClass = declaringClass;
        }

        public CstMethodHandle getBootstrapMethodHandle() {
            return bootstrapMethodHandle;
        }

        public BootstrapMethodArgumentsList getBootstrapMethodArguments() {
            return bootstrapMethodArgumentsList;
        }

        public CstType getDeclaringClass() {
            return declaringClass;
        }
    }
}
