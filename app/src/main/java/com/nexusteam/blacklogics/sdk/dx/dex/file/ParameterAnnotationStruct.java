
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.ToHuman;
import java.util.ArrayList;

public final class ParameterAnnotationStruct
        implements ToHuman, Comparable<ParameterAnnotationStruct> {
    private final CstMethodRef method;

    private final AnnotationsList annotationsList;

    private final mod.agus.jcoderz.dx.dex.file.UniformListItem<mod.agus.jcoderz.dx.dex.file.AnnotationSetRefItem> annotationsItem;

    public ParameterAnnotationStruct(CstMethodRef method,
            AnnotationsList annotationsList, mod.agus.jcoderz.dx.dex.file.DexFile dexFile) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        if (annotationsList == null) {
            throw new NullPointerException("annotationsList == null");
        }

        this.method = method;
        this.annotationsList = annotationsList;


        int size = annotationsList.size();
        ArrayList<mod.agus.jcoderz.dx.dex.file.AnnotationSetRefItem> arrayList = new
            ArrayList<mod.agus.jcoderz.dx.dex.file.AnnotationSetRefItem>(size);

        for (int i = 0; i < size; i++) {
            Annotations annotations = annotationsList.get(i);
            mod.agus.jcoderz.dx.dex.file.AnnotationSetItem item = new AnnotationSetItem(annotations, dexFile);
            arrayList.add(new mod.agus.jcoderz.dx.dex.file.AnnotationSetRefItem(item));
        }

        this.annotationsItem = new UniformListItem<mod.agus.jcoderz.dx.dex.file.AnnotationSetRefItem>(
                ItemType.TYPE_ANNOTATION_SET_REF_LIST, arrayList);
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof ParameterAnnotationStruct)) {
            return false;
        }

        return method.equals(((ParameterAnnotationStruct) other).method);
    }

    @Override
    public int compareTo(ParameterAnnotationStruct other) {
        return method.compareTo(other.method);
    }

    public void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file) {
        MethodIdsSection methodIds = file.getMethodIds();
        MixedItemSection wordData = file.getWordData();

        methodIds.intern(method);
        wordData.add(annotationsItem);
    }

    public void writeTo(DexFile file, AnnotatedOutput out) {
        int methodIdx = file.getMethodIds().indexOf(method);
        int annotationsOff = annotationsItem.getAbsoluteOffset();

        if (out.annotates()) {
            out.annotate(0, "    " + method.toHuman());
            out.annotate(4, "      method_idx:      " + Hex.u4(methodIdx));
            out.annotate(4, "      annotations_off: " +
                    Hex.u4(annotationsOff));
        }

        out.writeInt(methodIdx);
        out.writeInt(annotationsOff);
    }

    @Override
    public String toHuman() {
        StringBuilder sb = new StringBuilder();

        sb.append(method.toHuman());
        sb.append(": ");

        boolean first = true;
        for (AnnotationSetRefItem item : annotationsItem.getItems()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(item.toHuman());
        }

        return sb.toString();
    }

    public CstMethodRef getMethod() {
        return method;
    }

    public AnnotationsList getAnnotationsList() {
        return annotationsList;
    }
}
