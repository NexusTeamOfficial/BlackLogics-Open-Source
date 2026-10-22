
package mod.agus.jcoderz.dx.dex.file;

public abstract class IndexedItem extends Item {
    private int index;

    public IndexedItem() {
        index = -1;
    }

    public final boolean hasIndex() {
        return (index >= 0);
    }

    public final int getIndex() {
        if (index < 0) {
            throw new RuntimeException("index not yet set");
        }

        return index;
    }

    public final void setIndex(int index) {
        if (this.index != -1) {
            throw new RuntimeException("index already set");
        }

        this.index = index;
    }

    public final String indexString() {
        return '[' + Integer.toHexString(index) + ']';
    }
}
