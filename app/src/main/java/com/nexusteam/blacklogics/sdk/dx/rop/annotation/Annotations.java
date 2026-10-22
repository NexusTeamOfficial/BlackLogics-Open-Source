
package mod.agus.jcoderz.dx.rop.annotation;

import mod.agus.jcoderz.dx.util.MutabilityControl;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.CstType;

public final class Annotations extends MutabilityControl
        implements Comparable<Annotations> {
    public static final Annotations EMPTY = new Annotations();

    static {
        EMPTY.setImmutable();
    }

    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstType, Annotation> annotations;

    public static Annotations combine(Annotations a1, Annotations a2) {
        Annotations result = new Annotations();

        result.addAll(a1);
        result.addAll(a2);
        result.setImmutable();

        return result;
    }

    public static Annotations combine(Annotations annotations,
            Annotation annotation) {
        Annotations result = new Annotations();

        result.addAll(annotations);
        result.add(annotation);
        result.setImmutable();

        return result;
    }

    public Annotations() {
        annotations = new TreeMap<mod.agus.jcoderz.dx.rop.cst.CstType, Annotation>();
    }

    @Override
    public int hashCode() {
        return annotations.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Annotations)) {
            return false;
        }

        Annotations otherAnnotations = (Annotations) other;

        return annotations.equals(otherAnnotations.annotations);
    }

    @Override
    public int compareTo(Annotations other) {
        Iterator<Annotation> thisIter = annotations.values().iterator();
        Iterator<Annotation> otherIter = other.annotations.values().iterator();

        while (thisIter.hasNext() && otherIter.hasNext()) {
            Annotation thisOne = thisIter.next();
            Annotation otherOne = otherIter.next();

            int result = thisOne.compareTo(otherOne);
            if (result != 0) {
                return result;
            }
        }

        if (thisIter.hasNext()) {
            return 1;
        } else if (otherIter.hasNext()) {
            return -1;
        }

        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        sb.append("annotations{");

        for (Annotation a : annotations.values()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(a.toHuman());
        }

        sb.append("}");
        return sb.toString();
    }

    public int size() {
        return annotations.size();
    }

    public void add(Annotation annotation) {
        throwIfImmutable();

        if (annotation == null) {
            throw new NullPointerException("annotation == null");
        }

        CstType type = annotation.getType();

        if (annotations.containsKey(type)) {
            throw new IllegalArgumentException("duplicate type: " +
                    type.toHuman());
        }

        annotations.put(type, annotation);
    }

    public void addAll(Annotations toAdd) {
        throwIfImmutable();

        if (toAdd == null) {
            throw new NullPointerException("toAdd == null");
        }

        for (Annotation a : toAdd.annotations.values()) {
            add(a);
        }
    }

    public Collection<Annotation> getAnnotations() {
        return Collections.unmodifiableCollection(annotations.values());
    }
}
