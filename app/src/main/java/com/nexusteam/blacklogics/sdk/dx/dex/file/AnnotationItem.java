
package mod.agus.jcoderz.dx.dex.file;

import java.util.Arrays;
import java.util.Comparator;

import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;

public final class AnnotationItem extends mod.agus.jcoderz.dx.dex.file.OffsettedItem {
    private static final int VISIBILITY_BUILD = 0;

    private static final int VISIBILITY_RUNTIME = 1;

    private static final int VISIBILITY_SYSTEM = 2;

    private static final int ALIGNMENT = 1;

    private static final TypeIdSorter TYPE_ID_SORTER = new TypeIdSorter();

    private final mod.agus.jcoderz.dx.rop.annotation.Annotation annotation;

    private TypeIdItem type;

    private byte[] encodedForm;

    private static class TypeIdSorter implements Comparator<AnnotationItem> {
        @Override
        public int compare(AnnotationItem item1, AnnotationItem item2) {
            int index1 = item1.type.getIndex();
            int index2 = item2.type.getIndex();

            if (index1 < index2) {
                return -1;
            } else if (index1 > index2) {
                return 1;
            }

            return 0;
        }
    }

    public static void sortByTypeIdIndex(AnnotationItem[] array) {
        Arrays.sort(array, TYPE_ID_SORTER);
    }

    public AnnotationItem(Annotation annotation, DexFile dexFile) {
        super(ALIGNMENT, -1);

        if (annotation == null) {
            throw new NullPointerException("annotation == null");
        }

        this.annotation = annotation;
        this.type = null;
        this.encodedForm = null;
        addContents(dexFile);
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_ANNOTATION_ITEM;
    }

    @Override
    public int hashCode() {
        return annotation.hashCode();
    }

    @Override
    protected int compareTo0(OffsettedItem other) {
        AnnotationItem otherAnnotation = (AnnotationItem) other;

        return annotation.compareTo(otherAnnotation.annotation);
    }

    @Override
    public String toHuman() {
        return annotation.toHuman();
    }

    @Override
    public void addContents(DexFile file) {
        type = file.getTypeIds().intern(annotation.getType());
        ValueEncoder.addContents(file, annotation);
    }

    @Override
    protected void place0(Section addedTo, int offset) {


        mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput out = new ByteArrayAnnotatedOutput();
        ValueEncoder encoder = new ValueEncoder(addedTo.getFile(), out);

        encoder.writeAnnotation(annotation, false);
        encodedForm = out.toByteArray();


        setWriteSize(encodedForm.length + 1);
    }

    public void annotateTo(mod.agus.jcoderz.dx.util.AnnotatedOutput out, String prefix) {
        out.annotate(0, prefix + "visibility: " +
                annotation.getVisibility().toHuman());
        out.annotate(0, prefix + "type: " + annotation.getType().toHuman());

        for (NameValuePair pair : annotation.getNameValuePairs()) {
            CstString name = pair.getName();
            Constant value = pair.getValue();

            out.annotate(0, prefix + name.toHuman() + ": " +
                    ValueEncoder.constantToHuman(value));
        }
    }

    @Override
    protected void writeTo0(DexFile file, AnnotatedOutput out) {
        boolean annotates = out.annotates();
        AnnotationVisibility visibility = annotation.getVisibility();

        if (annotates) {
            out.annotate(0, offsetString() + " annotation");
            out.annotate(1, "  visibility: VISBILITY_" + visibility);
        }

        switch (visibility) {
            case BUILD:   out.writeByte(VISIBILITY_BUILD); break;
            case RUNTIME: out.writeByte(VISIBILITY_RUNTIME); break;
            case SYSTEM:  out.writeByte(VISIBILITY_SYSTEM); break;
            default: {

                throw new RuntimeException("shouldn't happen");
            }
        }

        if (annotates) {
            ValueEncoder encoder = new ValueEncoder(file, out);
            encoder.writeAnnotation(annotation, true);
        } else {
            out.write(encodedForm);
        }
    }
}
