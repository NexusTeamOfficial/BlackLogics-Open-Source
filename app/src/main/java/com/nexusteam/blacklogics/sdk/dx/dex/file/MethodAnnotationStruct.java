
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.ToHuman;

public final class MethodAnnotationStruct
        implements ToHuman, Comparable<MethodAnnotationStruct> {
    private final CstMethodRef method;

    private mod.agus.jcoderz.dx.dex.file.AnnotationSetItem annotations;

    public MethodAnnotationStruct(CstMethodRef method,
            AnnotationSetItem annotations) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        if (annotations == null) {
            throw new NullPointerException("annotations == null");
        }

        this.method = method;
        this.annotations = annotations;
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof MethodAnnotationStruct)) {
            return false;
        }

        return method.equals(((MethodAnnotationStruct) other).method);
    }

    @Override
    public int compareTo(MethodAnnotationStruct other) {
        return method.compareTo(other.method);
    }

    public void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file) {
        MethodIdsSection methodIds = file.getMethodIds();
        MixedItemSection wordData = file.getWordData();

        methodIds.intern(method);
        annotations = wordData.intern(annotations);
    }

    public void writeTo(DexFile file, AnnotatedOutput out) {
        int methodIdx = file.getMethodIds().indexOf(method);
        int annotationsOff = annotations.getAbsoluteOffset();

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
        return method.toHuman() + ": " + annotations;
    }

    public CstMethodRef getMethod() {
        return method;
    }

    public Annotations getAnnotations() {
        return annotations.getAnnotations();
    }
}
