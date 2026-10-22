
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import java.util.Collection;

public abstract class Section {
    private final String name;

    private final mod.agus.jcoderz.dx.dex.file.DexFile file;

    private final int alignment;

    private int fileOffset;

    private boolean prepared;

    public static void validateAlignment(int alignment) {
        if ((alignment <= 0) ||
            (alignment & (alignment - 1)) != 0) {
            throw new IllegalArgumentException("invalid alignment");
        }
    }

    public Section(String name, mod.agus.jcoderz.dx.dex.file.DexFile file, int alignment) {
        if (file == null) {
            throw new NullPointerException("file == null");
        }

        validateAlignment(alignment);

        this.name = name;
        this.file = file;
        this.alignment = alignment;
        this.fileOffset = -1;
        this.prepared = false;
    }

    public final DexFile getFile() {
        return file;
    }

    public final int getAlignment() {
        return alignment;
    }

    public final int getFileOffset() {
        if (fileOffset < 0) {
            throw new RuntimeException("fileOffset not set");
        }

        return fileOffset;
    }

    public final int setFileOffset(int fileOffset) {
        if (fileOffset < 0) {
            throw new IllegalArgumentException("fileOffset < 0");
        }

        if (this.fileOffset >= 0) {
            throw new RuntimeException("fileOffset already set");
        }

        int mask = alignment - 1;
        fileOffset = (fileOffset + mask) & ~mask;

        this.fileOffset = fileOffset;

        return fileOffset;
    }

    public final void writeTo(AnnotatedOutput out) {
        throwIfNotPrepared();
        align(out);

        int cursor = out.getCursor();

        if (fileOffset < 0) {
            fileOffset = cursor;
        } else if (fileOffset != cursor) {
            throw new RuntimeException("alignment mismatch: for " + this +
                                       ", at " + cursor +
                                       ", but expected " + fileOffset);
        }

        if (out.annotates()) {
            if (name != null) {
                out.annotate(0, "\n" + name + ":");
            } else if (cursor != 0) {
                out.annotate(0, "\n");
            }
        }

        writeTo0(out);
    }

    public final int getAbsoluteOffset(int relative) {
        if (relative < 0) {
            throw new IllegalArgumentException("relative < 0");
        }

        if (fileOffset < 0) {
            throw new RuntimeException("fileOffset not yet set");
        }

        return fileOffset + relative;
    }

    public abstract int getAbsoluteItemOffset(Item item);

    public final void prepare() {
        throwIfPrepared();
        prepare0();
        prepared = true;
    }

    public abstract Collection<? extends Item> items();

    protected abstract void prepare0();

    public abstract int writeSize();

    protected final void throwIfNotPrepared() {
        if (!prepared) {
            throw new RuntimeException("not prepared");
        }
    }

    protected final void throwIfPrepared() {
        if (prepared) {
            throw new RuntimeException("already prepared");
        }
    }

    protected final void align(AnnotatedOutput out) {
        out.alignTo(alignment);
    }

    protected abstract void writeTo0(AnnotatedOutput out);

    protected final String getName() {
        return name;
    }
}
