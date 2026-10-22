
package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class UniformItemSection extends Section {
    public UniformItemSection(String name, DexFile file, int alignment) {
        super(name, file, alignment);
    }

    @Override
    public final int writeSize() {
        Collection<? extends Item> items = items();
        int sz = items.size();

        if (sz == 0) {
            return 0;
        }


        return sz * items.iterator().next().writeSize();
    }

    public abstract mod.agus.jcoderz.dx.dex.file.IndexedItem get(Constant cst);

    @Override
    protected final void prepare0() {
        DexFile file = getFile();

        orderItems();

        for (Item one : items()) {
            one.addContents(file);
        }
    }

    @Override
    protected final void writeTo0(AnnotatedOutput out) {
        DexFile file = getFile();
        int alignment = getAlignment();

        for (Item one : items()) {
            one.writeTo(file, out);
            out.alignTo(alignment);
        }
    }

    @Override
    public final int getAbsoluteItemOffset(Item item) {
        mod.agus.jcoderz.dx.dex.file.IndexedItem ii = (IndexedItem) item;
        int relativeOffset = ii.getIndex() * ii.writeSize();

        return getAbsoluteOffset(relativeOffset);
    }

    protected abstract void orderItems();
}
