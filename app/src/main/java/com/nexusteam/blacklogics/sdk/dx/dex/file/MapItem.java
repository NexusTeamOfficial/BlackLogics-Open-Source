
package mod.agus.jcoderz.dx.dex.file;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class MapItem extends OffsettedItem {
    private static final int ALIGNMENT = 4;

    private static final int WRITE_SIZE = (4 * 3);

    private final ItemType type;

    private final Section section;

    private final Item firstItem;

    private final Item lastItem;

    private final int itemCount;

    public static void addMap(Section[] sections,
            MixedItemSection mapSection) {
        if (sections == null) {
            throw new NullPointerException("sections == null");
        }

        if (mapSection.items().size() != 0) {
            throw new IllegalArgumentException(
                    "mapSection.items().size() != 0");
        }

        ArrayList<MapItem> items = new ArrayList<MapItem>(50);

        for (Section section : sections) {
            ItemType currentType = null;
            Item firstItem = null;
            Item lastItem = null;
            int count = 0;

            for (Item item : section.items()) {
                ItemType type = item.itemType();
                if (type != currentType) {
                    if (count != 0) {
                        items.add(new MapItem(currentType, section,
                                        firstItem, lastItem, count));
                    }
                    currentType = type;
                    firstItem = item;
                    count = 0;
                }
                lastItem = item;
                count++;
            }

            if (count != 0) {

                items.add(new MapItem(currentType, section,
                                firstItem, lastItem, count));
            } else if (section == mapSection) {

                items.add(new MapItem(mapSection));
            }
        }

        mapSection.add(
                new UniformListItem<MapItem>(ItemType.TYPE_MAP_LIST, items));
    }

    private MapItem(ItemType type, Section section, Item firstItem,
            Item lastItem, int itemCount) {
        super(ALIGNMENT, WRITE_SIZE);

        if (type == null) {
            throw new NullPointerException("type == null");
        }

        if (section == null) {
            throw new NullPointerException("section == null");
        }

        if (firstItem == null) {
            throw new NullPointerException("firstItem == null");
        }

        if (lastItem == null) {
            throw new NullPointerException("lastItem == null");
        }

        if (itemCount <= 0) {
            throw new IllegalArgumentException("itemCount <= 0");
        }

        this.type = type;
        this.section = section;
        this.firstItem = firstItem;
        this.lastItem = lastItem;
        this.itemCount = itemCount;
    }

    private MapItem(Section section) {
        super(ALIGNMENT, WRITE_SIZE);

        if (section == null) {
            throw new NullPointerException("section == null");
        }

        this.type = ItemType.TYPE_MAP_LIST;
        this.section = section;
        this.firstItem = null;
        this.lastItem = null;
        this.itemCount = 1;
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_MAP_ITEM;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append(getClass().getName());
        sb.append('{');
        sb.append(section.toString());
        sb.append(' ');
        sb.append(type.toHuman());
        sb.append('}');

        return sb.toString();
    }

    @Override
    public void addContents(DexFile file) {

    }

    @Override
    public final String toHuman() {
        return toString();
    }

    @Override
    protected void writeTo0(DexFile file, AnnotatedOutput out) {
        int value = type.getMapValue();
        int offset;

        if (firstItem == null) {
            offset = section.getFileOffset();
        } else {
            offset = section.getAbsoluteItemOffset(firstItem);
        }

        if (out.annotates()) {
            out.annotate(0, offsetString() + ' ' + type.getTypeName() +
                    " map");
            out.annotate(2, "  type:   " + mod.agus.jcoderz.dx.util.Hex.u2(value) + " // " +
                    type.toString());
            out.annotate(2, "  unused: 0");
            out.annotate(4, "  size:   " + mod.agus.jcoderz.dx.util.Hex.u4(itemCount));
            out.annotate(4, "  offset: " + Hex.u4(offset));
        }

        out.writeShort(value);
        out.writeShort(0);
        out.writeInt(itemCount);
        out.writeInt(offset);
    }
}
