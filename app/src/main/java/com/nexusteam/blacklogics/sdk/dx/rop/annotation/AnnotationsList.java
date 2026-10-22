
package mod.agus.jcoderz.dx.rop.annotation;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class AnnotationsList
        extends FixedSizeList {
    public static final AnnotationsList EMPTY = new AnnotationsList(0);

    public static AnnotationsList combine(AnnotationsList list1,
            AnnotationsList list2) {
        int size = list1.size();

        if (size != list2.size()) {
            throw new IllegalArgumentException("list1.size() != list2.size()");
        }

        AnnotationsList result = new AnnotationsList(size);

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.rop.annotation.Annotations a1 = list1.get(i);
            mod.agus.jcoderz.dx.rop.annotation.Annotations a2 = list2.get(i);
            result.set(i, mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(a1, a2));
        }

        result.setImmutable();
        return result;
    }

    public AnnotationsList(int size) {
        super(size);
    }

    public mod.agus.jcoderz.dx.rop.annotation.Annotations get(int n) {
        return (mod.agus.jcoderz.dx.rop.annotation.Annotations) get0(n);
    }

    public void set(int n, Annotations a) {
        a.throwIfMutable();
        set0(n, a);
    }
}
