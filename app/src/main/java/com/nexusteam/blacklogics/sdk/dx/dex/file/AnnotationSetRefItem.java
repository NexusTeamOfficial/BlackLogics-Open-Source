
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class AnnotationSetRefItem extends OffsettedItem {
    private static final int ALIGNMENT = 4;

    private static final int WRITE_SIZE = 4;

    private AnnotationSetItem annotations;

    public AnnotationSetRefItem(AnnotationSetItem annotations) {
        super(ALIGNMENT, WRITE_SIZE);

        if (annotations == null) {
            throw new NullPointerException("annotations == null");
        }

        this.annotations = annotations;
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_ANNOTATION_SET_REF_ITEM;
    }

    @Override
    public void addContents(DexFile file) {
        MixedItemSection wordData = file.getWordData();

        annotations = wordData.intern(annotations);
    }

    @Override
    public String toHuman() {
        return annotations.toHuman();
    }

    @Override
    protected void writeTo0(DexFile file, AnnotatedOutput out) {
        int annotationsOff = annotations.getAbsoluteOffset();

        if (out.annotates()) {
            out.annotate(4, "  annotations_off: " + Hex.u4(annotationsOff));
        }

        out.writeInt(annotationsOff);
    }
}
