
package mod.agus.jcoderz.dx.dex.file;

import java.util.List;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class UniformListItem<T extends OffsettedItem>
        extends OffsettedItem {
    private static final int HEADER_SIZE = 4;

    private final ItemType itemType;

    private final List<T> items;

    public UniformListItem(ItemType itemType, List<T> items) {
        super(getAlignment(items), writeSize(items));

        if (itemType == null) {
            throw new NullPointerException("itemType == null");
        }

        this.items = items;
        this.itemType = itemType;
    }

    private static int getAlignment(List<? extends OffsettedItem> items) {
        try {

            return Math.max(HEADER_SIZE, items.get(0).getAlignment());
        } catch (IndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("items.size() == 0");
        } catch (NullPointerException ex) {

            throw new NullPointerException("items == null");
        }
    }

    private static int writeSize(List<? extends OffsettedItem> items) {
        OffsettedItem first = items.get(0);
        return (items.size() * first.writeSize()) + getAlignment(items);
    }

    @Override
    public ItemType itemType() {
        return itemType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append(getClass().getName());
        sb.append(items);

        return sb.toString();
    }

    @Override
    public void addContents(DexFile file) {
        for (OffsettedItem i : items) {
            i.addContents(file);
        }
    }

    @Override
    public final String toHuman() {
        StringBuilder sb = new StringBuilder(100);
        boolean first = true;

        sb.append("{");

        for (OffsettedItem i : items) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(i.toHuman());
        }

        sb.append("}");
        return sb.toString();
    }

    public final List<T> getItems() {
        return items;
    }

    @Override
    protected void place0(Section addedTo, int offset) {
        offset += headerSize();

        boolean first = true;
        int theSize = -1;
        int theAlignment = -1;

        for (OffsettedItem i : items) {
            int size = i.writeSize();
            if (first) {
                theSize = size;
                theAlignment = i.getAlignment();
                first = false;
            } else {
                if (size != theSize) {
                    throw new UnsupportedOperationException(
                            "item size mismatch");
                }
                if (i.getAlignment() != theAlignment) {
                    throw new UnsupportedOperationException(
                            "item alignment mismatch");
                }
            }

            offset = i.place(addedTo, offset) + size;
        }
    }

    @Override
    protected void writeTo0(DexFile file, AnnotatedOutput out) {
        int size = items.size();

        if (out.annotates()) {
            out.annotate(0, offsetString() + " " + typeName());
            out.annotate(4, "  size: " + Hex.u4(size));
        }

        out.writeInt(size);

        for (OffsettedItem i : items) {
            i.writeTo(file, out);
        }
    }

    private int headerSize() {
        return getAlignment();
    }
}
