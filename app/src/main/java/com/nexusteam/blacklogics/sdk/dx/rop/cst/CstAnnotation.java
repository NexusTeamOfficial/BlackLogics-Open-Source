
package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.annotation.Annotation;

public final class CstAnnotation extends Constant {
    private final Annotation annotation;

    public CstAnnotation(Annotation annotation) {
        if (annotation == null) {
            throw new NullPointerException("annotation == null");
        }

        annotation.throwIfMutable();

        this.annotation = annotation;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof CstAnnotation)) {
            return false;
        }

        return annotation.equals(((CstAnnotation) other).annotation);
    }

    @Override
    public int hashCode() {
        return annotation.hashCode();
    }

    @Override
    protected int compareTo0(Constant other) {
        return annotation.compareTo(((CstAnnotation) other).annotation);
    }

    @Override
    public String toString() {
        return annotation.toString();
    }

    @Override
    public String typeName() {
        return "annotation";
    }

    @Override
    public boolean isCategory2() {
        return false;
    }

    @Override
    public String toHuman() {
        return annotation.toString();
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
