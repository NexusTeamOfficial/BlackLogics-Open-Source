
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.SizeOf;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class StringIdItem
        extends IndexedItem implements Comparable {
    private final CstString value;

    private StringDataItem data;

    public StringIdItem(CstString value) {
        if (value == null) {
            throw new NullPointerException("value == null");
        }

        this.value = value;
        this.data = null;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StringIdItem)) {
            return false;
        }

        StringIdItem otherString = (StringIdItem) other;
        return value.equals(otherString.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Object other) {
        StringIdItem otherString = (StringIdItem) other;
        return value.compareTo(otherString.value);
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_STRING_ID_ITEM;
    }

    @Override
    public int writeSize() {
        return SizeOf.STRING_ID_ITEM;
    }

    @Override
    public void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file) {
        if (data == null) {

            MixedItemSection stringData = file.getStringData();
            data = new StringDataItem(value);
            stringData.add(data);
        }
    }

    @Override
    public void writeTo(DexFile file, AnnotatedOutput out) {
        int dataOff = data.getAbsoluteOffset();

        if (out.annotates()) {
            out.annotate(0, indexString() + ' ' + value.toQuoted(100));
            out.annotate(4, "  string_data_off: " + Hex.u4(dataOff));
        }

        out.writeInt(dataOff);
    }

    public CstString getValue() {
        return value;
    }

    public StringDataItem getData() {
        return data;
    }
}
