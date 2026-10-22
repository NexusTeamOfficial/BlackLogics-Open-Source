
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.ToHuman;

public final class FieldAnnotationStruct
        implements ToHuman, Comparable<FieldAnnotationStruct> {
    private final mod.agus.jcoderz.dx.rop.cst.CstFieldRef field;

    private mod.agus.jcoderz.dx.dex.file.AnnotationSetItem annotations;

    public FieldAnnotationStruct(mod.agus.jcoderz.dx.rop.cst.CstFieldRef field,
                                 AnnotationSetItem annotations) {
        if (field == null) {
            throw new NullPointerException("field == null");
        }

        if (annotations == null) {
            throw new NullPointerException("annotations == null");
        }

        this.field = field;
        this.annotations = annotations;
    }

    @Override
    public int hashCode() {
        return field.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof FieldAnnotationStruct)) {
            return false;
        }

        return field.equals(((FieldAnnotationStruct) other).field);
    }

    @Override
    public int compareTo(FieldAnnotationStruct other) {
        return field.compareTo(other.field);
    }

    public void addContents(DexFile file) {
        FieldIdsSection fieldIds = file.getFieldIds();
        MixedItemSection wordData = file.getWordData();

        fieldIds.intern(field);
        annotations = wordData.intern(annotations);
    }

    public void writeTo(DexFile file, AnnotatedOutput out) {
        int fieldIdx = file.getFieldIds().indexOf(field);
        int annotationsOff = annotations.getAbsoluteOffset();

        if (out.annotates()) {
            out.annotate(0, "    " + field.toHuman());
            out.annotate(4, "      field_idx:       " + mod.agus.jcoderz.dx.util.Hex.u4(fieldIdx));
            out.annotate(4, "      annotations_off: " +
                    Hex.u4(annotationsOff));
        }

        out.writeInt(fieldIdx);
        out.writeInt(annotationsOff);
    }

    @Override
    public String toHuman() {
        return field.toHuman() + ": " + annotations;
    }

    public CstFieldRef getField() {
        return field;
    }

    public Annotations getAnnotations() {
        return annotations.getAnnotations();
    }
}
