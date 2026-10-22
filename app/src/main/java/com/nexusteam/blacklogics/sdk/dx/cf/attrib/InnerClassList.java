
package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class InnerClassList extends FixedSizeList {
    public InnerClassList(int count) {
        super(count);
    }

    public Item get(int n) {
        return (Item) get0(n);
    }

    public void set(int n, mod.agus.jcoderz.dx.rop.cst.CstType innerClass, mod.agus.jcoderz.dx.rop.cst.CstType outerClass,
                    mod.agus.jcoderz.dx.rop.cst.CstString innerName, int accessFlags) {
        set0(n, new Item(innerClass, outerClass, innerName, accessFlags));
    }

    public static class Item {
        private final mod.agus.jcoderz.dx.rop.cst.CstType innerClass;

        private final mod.agus.jcoderz.dx.rop.cst.CstType outerClass;

        private final mod.agus.jcoderz.dx.rop.cst.CstString innerName;

        private final int accessFlags;

        public Item(mod.agus.jcoderz.dx.rop.cst.CstType innerClass, mod.agus.jcoderz.dx.rop.cst.CstType outerClass,
                    mod.agus.jcoderz.dx.rop.cst.CstString innerName, int accessFlags) {
            if (innerClass == null) {
                throw new NullPointerException("innerClass == null");
            }

            this.innerClass = innerClass;
            this.outerClass = outerClass;
            this.innerName = innerName;
            this.accessFlags = accessFlags;
        }

        public mod.agus.jcoderz.dx.rop.cst.CstType getInnerClass() {
            return innerClass;
        }

        public CstType getOuterClass() {
            return outerClass;
        }

        public CstString getInnerName() {
            return innerName;
        }

        public int getAccessFlags() {
            return accessFlags;
        }
    }
}
