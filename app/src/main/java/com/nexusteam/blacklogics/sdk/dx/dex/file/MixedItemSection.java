
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public final class MixedItemSection extends Section {
    static enum SortType {
        NONE,

        TYPE,

        INSTANCE;
    };

    private static final Comparator<mod.agus.jcoderz.dx.dex.file.OffsettedItem> TYPE_SORTER =
        new Comparator<mod.agus.jcoderz.dx.dex.file.OffsettedItem>() {
        @Override
        public int compare(mod.agus.jcoderz.dx.dex.file.OffsettedItem item1, mod.agus.jcoderz.dx.dex.file.OffsettedItem item2) {
            ItemType type1 = item1.itemType();
            ItemType type2 = item2.itemType();
            return type1.compareTo(type2);
        }
    };

    private final ArrayList<mod.agus.jcoderz.dx.dex.file.OffsettedItem> items;

    private final HashMap<mod.agus.jcoderz.dx.dex.file.OffsettedItem, mod.agus.jcoderz.dx.dex.file.OffsettedItem> interns;

    private final SortType sort;

    private int writeSize;

    public MixedItemSection(String name, mod.agus.jcoderz.dx.dex.file.DexFile file, int alignment,
                            SortType sort) {
        super(name, file, alignment);

        this.items = new ArrayList<mod.agus.jcoderz.dx.dex.file.OffsettedItem>(100);
        this.interns = new HashMap<mod.agus.jcoderz.dx.dex.file.OffsettedItem, mod.agus.jcoderz.dx.dex.file.OffsettedItem>(100);
        this.sort = sort;
        this.writeSize = -1;
    }

    @Override
    public Collection<? extends Item> items() {
        return items;
    }

    @Override
    public int writeSize() {
        throwIfNotPrepared();
        return writeSize;
    }

    @Override
    public int getAbsoluteItemOffset(Item item) {
        mod.agus.jcoderz.dx.dex.file.OffsettedItem oi = (mod.agus.jcoderz.dx.dex.file.OffsettedItem) item;
        return oi.getAbsoluteOffset();
    }

    public int size() {
        return items.size();
    }

    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        if (writeSize == -1) {
            throw new RuntimeException("write size not yet set");
        }

        int sz = writeSize;
        int offset = (sz == 0) ? 0 : getFileOffset();
        String name = getName();

        if (name == null) {
            name = "<unnamed>";
        }

        int spaceCount = 15 - name.length();
        char[] spaceArr = new char[spaceCount];
        Arrays.fill(spaceArr, ' ');
        String spaces = new String(spaceArr);

        if (out.annotates()) {
            out.annotate(4, name + "_size:" + spaces + Hex.u4(sz));
            out.annotate(4, name + "_off: " + spaces + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    public void add(mod.agus.jcoderz.dx.dex.file.OffsettedItem item) {
        throwIfPrepared();

        try {
            if (item.getAlignment() > getAlignment()) {
                throw new IllegalArgumentException(
                        "incompatible item alignment");
            }
        } catch (NullPointerException ex) {

            throw new NullPointerException("item == null");
        }

        items.add(item);
    }

    public synchronized <T extends mod.agus.jcoderz.dx.dex.file.OffsettedItem> T intern(T item) {
        throwIfPrepared();

        mod.agus.jcoderz.dx.dex.file.OffsettedItem result = interns.get(item);

        if (result != null) {
            return (T) result;
        }

        add(item);
        interns.put(item, item);
        return item;
    }

    public <T extends mod.agus.jcoderz.dx.dex.file.OffsettedItem> T get(T item) {
        throwIfNotPrepared();

        mod.agus.jcoderz.dx.dex.file.OffsettedItem result = interns.get(item);

        if (result != null) {
            return (T) result;
        }

        throw new NoSuchElementException(item.toString());
    }

    public void writeIndexAnnotation(AnnotatedOutput out, ItemType itemType,
            String intro) {
        throwIfNotPrepared();

        TreeMap<String, mod.agus.jcoderz.dx.dex.file.OffsettedItem> index =
            new TreeMap<String, mod.agus.jcoderz.dx.dex.file.OffsettedItem>();

        for (mod.agus.jcoderz.dx.dex.file.OffsettedItem item : items) {
            if (item.itemType() == itemType) {
                String label = item.toHuman();
                index.put(label, item);
            }
        }

        if (index.size() == 0) {
            return;
        }

        out.annotate(0, intro);

        for (Map.Entry<String, mod.agus.jcoderz.dx.dex.file.OffsettedItem> entry : index.entrySet()) {
            String label = entry.getKey();
            mod.agus.jcoderz.dx.dex.file.OffsettedItem item = entry.getValue();
            out.annotate(0, item.offsetString() + ' ' + label + '\n');
        }
    }

    @Override
    protected void prepare0() {
        mod.agus.jcoderz.dx.dex.file.DexFile file = getFile();


        int i = 0;
        for (;;) {
            int sz = items.size();
            if (i >= sz) {
                break;
            }

            for (; i < sz; i++) {
                mod.agus.jcoderz.dx.dex.file.OffsettedItem one = items.get(i);
                one.addContents(file);
            }
        }
    }

    public void placeItems() {
        throwIfNotPrepared();

        switch (sort) {
            case INSTANCE: {
                Collections.sort(items);
                break;
            }
            case TYPE: {
                Collections.sort(items, TYPE_SORTER);
                break;
            }
        }

        int sz = items.size();
        int outAt = 0;
        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.dex.file.OffsettedItem one = items.get(i);
            try {
                int placedAt = one.place(this, outAt);

                if (placedAt < outAt) {
                    throw new RuntimeException("bogus place() result for " +
                            one);
                }

                outAt = placedAt + one.writeSize();
            } catch (RuntimeException ex) {
                throw ExceptionWithContext.withContext(ex,
                        "...while placing " + one);
            }
        }

        writeSize = outAt;
    }

    @Override
    protected void writeTo0(AnnotatedOutput out) {
        boolean annotates = out.annotates();
        boolean first = true;
        DexFile file = getFile();
        int at = 0;

        for (OffsettedItem one : items) {
            if (annotates) {
                if (first) {
                    first = false;
                } else {
                    out.annotate(0, "\n");
                }
            }

            int alignMask = one.getAlignment() - 1;
            int writeAt = (at + alignMask) & ~alignMask;

            if (at != writeAt) {
                out.writeZeroes(writeAt - at);
                at = writeAt;
            }

            one.writeTo(file, out);
            at += one.writeSize();
        }

        if (at != writeSize) {
            throw new RuntimeException("output size mismatch");
        }
    }
}
