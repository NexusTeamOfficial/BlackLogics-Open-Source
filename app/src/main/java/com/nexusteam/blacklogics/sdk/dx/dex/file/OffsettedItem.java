
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class OffsettedItem extends Item
        implements Comparable<OffsettedItem> {
    private final int alignment;

    private int writeSize;

    private Section addedTo;

    private int offset;

    public static int getAbsoluteOffsetOr0(OffsettedItem item) {
        if (item == null) {
            return 0;
        }

        return item.getAbsoluteOffset();
    }

    public OffsettedItem(int alignment, int writeSize) {
        Section.validateAlignment(alignment);

        if (writeSize < -1) {
            throw new IllegalArgumentException("writeSize < -1");
        }

        this.alignment = alignment;
        this.writeSize = writeSize;
        this.addedTo = null;
        this.offset = -1;
    }

    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        OffsettedItem otherItem = (OffsettedItem) other;
        ItemType thisType = itemType();
        ItemType otherType = otherItem.itemType();

        if (thisType != otherType) {
            return false;
        }

        return (compareTo0(otherItem) == 0);
    }

    @Override
    public final int compareTo(OffsettedItem other) {
        if (this == other) {
            return 0;
        }

        ItemType thisType = itemType();
        ItemType otherType = other.itemType();

        if (thisType != otherType) {
            return thisType.compareTo(otherType);
        }

        return compareTo0(other);
    }

    public final void setWriteSize(int writeSize) {
        if (writeSize < 0) {
            throw new IllegalArgumentException("writeSize < 0");
        }

        if (this.writeSize >= 0) {
            throw new UnsupportedOperationException("writeSize already set");
        }

        this.writeSize = writeSize;
    }

    @Override
    public final int writeSize() {
        if (writeSize < 0) {
            throw new UnsupportedOperationException("writeSize is unknown");
        }

        return writeSize;
    }

    @Override
    public final void writeTo(DexFile file, mod.agus.jcoderz.dx.util.AnnotatedOutput out) {
        out.alignTo(alignment);

        try {
            if (writeSize < 0) {
                throw new UnsupportedOperationException(
                        "writeSize is unknown");
            }
            out.assertCursor(getAbsoluteOffset());
        } catch (RuntimeException ex) {
            throw ExceptionWithContext.withContext(ex,
                    "...while writing " + this);
        }

        writeTo0(file, out);
    }

    public final int getRelativeOffset() {
        if (offset < 0) {
            throw new RuntimeException("offset not yet known");
        }

        return offset;
    }

    public final int getAbsoluteOffset() {
        if (offset < 0) {
            throw new RuntimeException("offset not yet known");
        }

        return addedTo.getAbsoluteOffset(offset);
    }

    public final int place(Section addedTo, int offset) {
        if (addedTo == null) {
            throw new NullPointerException("addedTo == null");
        }

        if (offset < 0) {
            throw new IllegalArgumentException("offset < 0");
        }

        if (this.addedTo != null) {
            throw new RuntimeException("already written");
        }

        int mask = alignment - 1;
        offset = (offset + mask) & ~mask;

        this.addedTo = addedTo;
        this.offset = offset;

        place0(addedTo, offset);

        return offset;
    }

    public final int getAlignment() {
        return alignment;
    }

    public final String offsetString() {
        return '[' + Integer.toHexString(getAbsoluteOffset()) + ']';
    }

    public abstract String toHuman();

    protected int compareTo0(OffsettedItem other) {
        throw new UnsupportedOperationException("unsupported");
    }

    protected void place0(Section addedTo, int offset) {

    }

    protected abstract void writeTo0(DexFile file, AnnotatedOutput out);
}
