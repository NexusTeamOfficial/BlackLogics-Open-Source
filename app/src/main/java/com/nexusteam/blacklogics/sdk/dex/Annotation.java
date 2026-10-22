
package mod.agus.jcoderz.dex;

import static mod.agus.jcoderz.dex.EncodedValueReader.ENCODED_ANNOTATION;

public final class Annotation implements Comparable<Annotation> {
    private final Dex dex;
    private final byte visibility;
    private final EncodedValue encodedAnnotation;

    public Annotation(Dex dex, byte visibility, EncodedValue encodedAnnotation) {
        this.dex = dex;
        this.visibility = visibility;
        this.encodedAnnotation = encodedAnnotation;
    }

    public byte getVisibility() {
        return visibility;
    }

    public mod.agus.jcoderz.dex.EncodedValueReader getReader() {
        return new mod.agus.jcoderz.dex.EncodedValueReader(encodedAnnotation, ENCODED_ANNOTATION);
    }

    public int getTypeIndex() {
        EncodedValueReader reader = getReader();
        reader.readAnnotation();
        return reader.getAnnotationType();
    }

    public void writeTo(Dex.Section out) {
        out.writeByte(visibility);
        encodedAnnotation.writeTo(out);
    }

    @Override
    public int compareTo(Annotation other) {
        return encodedAnnotation.compareTo(other.encodedAnnotation);
    }

    @Override
    public String toString() {
        return dex == null
                ? visibility + " " + getTypeIndex()
                : visibility + " " + dex.typeNames().get(getTypeIndex());
    }
}
