
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class Item {
    public Item() {

    }

    public abstract ItemType itemType();

    public final String typeName() {
        return itemType().toHuman();
    }

    public abstract int writeSize();

    public abstract void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file);

    public abstract void writeTo(DexFile file, AnnotatedOutput out);
}
