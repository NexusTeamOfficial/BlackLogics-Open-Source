
package mod.agus.jcoderz.dx.rop.annotation;

import mod.agus.jcoderz.dx.util.MutabilityControl;
import mod.agus.jcoderz.dx.util.ToHuman;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public final class Annotation extends MutabilityControl
        implements Comparable<Annotation>, ToHuman {
    private final mod.agus.jcoderz.dx.rop.cst.CstType type;

    private final mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility visibility;

    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstString, mod.agus.jcoderz.dx.rop.annotation.NameValuePair> elements;

    public Annotation(mod.agus.jcoderz.dx.rop.cst.CstType type, mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility visibility) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        if (visibility == null) {
            throw new NullPointerException("visibility == null");
        }

        this.type = type;
        this.visibility = visibility;
        this.elements = new TreeMap<mod.agus.jcoderz.dx.rop.cst.CstString, mod.agus.jcoderz.dx.rop.annotation.NameValuePair>();
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Annotation)) {
            return false;
        }

        Annotation otherAnnotation = (Annotation) other;

        if (! (type.equals(otherAnnotation.type)
                        && (visibility == otherAnnotation.visibility))) {
            return false;
        }

        return elements.equals(otherAnnotation.elements);
    }

    @Override
    public int hashCode() {
        int hash = type.hashCode();
        hash = (hash * 31) + elements.hashCode();
        hash = (hash * 31) + visibility.hashCode();
        return hash;
    }

    @Override
    public int compareTo(Annotation other) {
        int result = type.compareTo(other.type);

        if (result != 0) {
            return result;
        }

        result = visibility.compareTo(other.visibility);

        if (result != 0) {
            return result;
        }

        Iterator<mod.agus.jcoderz.dx.rop.annotation.NameValuePair> thisIter = elements.values().iterator();
        Iterator<mod.agus.jcoderz.dx.rop.annotation.NameValuePair> otherIter = other.elements.values().iterator();

        while (thisIter.hasNext() && otherIter.hasNext()) {
            mod.agus.jcoderz.dx.rop.annotation.NameValuePair thisOne = thisIter.next();
            mod.agus.jcoderz.dx.rop.annotation.NameValuePair otherOne = otherIter.next();

            result = thisOne.compareTo(otherOne);
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
        return toHuman();
    }

    @Override
    public String toHuman() {
        StringBuilder sb = new StringBuilder();

        sb.append(visibility.toHuman());
        sb.append("-annotation ");
        sb.append(type.toHuman());
        sb.append(" {");

        boolean first = true;
        for (mod.agus.jcoderz.dx.rop.annotation.NameValuePair pair : elements.values()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(pair.getName().toHuman());
            sb.append(": ");
            sb.append(pair.getValue().toHuman());
        }

        sb.append("}");
        return sb.toString();
    }

    public CstType getType() {
        return type;
    }

    public AnnotationVisibility getVisibility() {
        return visibility;
    }

    public void put(mod.agus.jcoderz.dx.rop.annotation.NameValuePair pair) {
        throwIfImmutable();

        if (pair == null) {
            throw new NullPointerException("pair == null");
        }

        elements.put(pair.getName(), pair);
    }

    public void add(mod.agus.jcoderz.dx.rop.annotation.NameValuePair pair) {
        throwIfImmutable();

        if (pair == null) {
            throw new NullPointerException("pair == null");
        }

        CstString name = pair.getName();

        if (elements.get(name) != null) {
            throw new IllegalArgumentException("name already added: " + name);
        }

        elements.put(name, pair);
    }

    public Collection<NameValuePair> getNameValuePairs() {
        return Collections.unmodifiableCollection(elements.values());
    }
}
