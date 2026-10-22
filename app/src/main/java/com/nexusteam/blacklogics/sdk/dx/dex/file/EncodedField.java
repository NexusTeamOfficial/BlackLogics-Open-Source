
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.Leb128;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

import java.io.PrintWriter;

public final class EncodedField extends EncodedMember
        implements Comparable<EncodedField> {
    private final mod.agus.jcoderz.dx.rop.cst.CstFieldRef field;

    public EncodedField(mod.agus.jcoderz.dx.rop.cst.CstFieldRef field, int accessFlags) {
        super(accessFlags);

        if (field == null) {
            throw new NullPointerException("field == null");
        }


        this.field = field;
    }

    @Override
    public int hashCode() {
        return field.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof EncodedField)) {
            return false;
        }

        return compareTo((EncodedField) other) == 0;
    }

    @Override
    public int compareTo(EncodedField other) {
        return field.compareTo(other.field);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append(getClass().getName());
        sb.append('{');
        sb.append(mod.agus.jcoderz.dx.util.Hex.u2(getAccessFlags()));
        sb.append(' ');
        sb.append(field);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void addContents(DexFile file) {
        FieldIdsSection fieldIds = file.getFieldIds();
        fieldIds.intern(field);
    }

    @Override
    public CstString getName() {
        return field.getNat().getName();
    }

    @Override
    public String toHuman() {
        return field.toHuman();
    }

    @Override
    public void debugPrint(PrintWriter out, boolean verbose) {

        out.println(toString());
    }

    public CstFieldRef getRef() {
        return field;
    }

    @Override
    public int encode(DexFile file, AnnotatedOutput out,
            int lastIndex, int dumpSeq) {
        int fieldIdx = file.getFieldIds().indexOf(field);
        int diff = fieldIdx - lastIndex;
        int accessFlags = getAccessFlags();

        if (out.annotates()) {
            out.annotate(0, String.format("  [%x] %s", dumpSeq,
                            field.toHuman()));
            out.annotate(Leb128.unsignedLeb128Size(diff),
                    "    field_idx:    " + Hex.u4(fieldIdx));
            out.annotate(Leb128.unsignedLeb128Size(accessFlags),
                    "    access_flags: " +
                    AccessFlags.fieldString(accessFlags));
        }

        out.writeUleb128(diff);
        out.writeUleb128(accessFlags);

        return fieldIdx;
    }
}
