
package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class FieldIdsSection extends MemberIdsSection {
    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstFieldRef, mod.agus.jcoderz.dx.dex.file.FieldIdItem> fieldIds;

    public FieldIdsSection(DexFile file) {
        super("field_ids", file);

        fieldIds = new TreeMap<mod.agus.jcoderz.dx.rop.cst.CstFieldRef, mod.agus.jcoderz.dx.dex.file.FieldIdItem>();
    }

    @Override
    public Collection<? extends Item> items() {
        return fieldIds.values();
    }

    @Override
    public mod.agus.jcoderz.dx.dex.file.IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        IndexedItem result = fieldIds.get((mod.agus.jcoderz.dx.rop.cst.CstFieldRef) cst);

        if (result == null) {
            throw new IllegalArgumentException("not found");
        }

        return result;
    }

    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        int sz = fieldIds.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (out.annotates()) {
            out.annotate(4, "field_ids_size:  " + mod.agus.jcoderz.dx.util.Hex.u4(sz));
            out.annotate(4, "field_ids_off:   " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    public synchronized mod.agus.jcoderz.dx.dex.file.FieldIdItem intern(mod.agus.jcoderz.dx.rop.cst.CstFieldRef field) {
        if (field == null) {
            throw new NullPointerException("field == null");
        }

        throwIfPrepared();

        mod.agus.jcoderz.dx.dex.file.FieldIdItem result = fieldIds.get(field);

        if (result == null) {
            result = new mod.agus.jcoderz.dx.dex.file.FieldIdItem(field);
            fieldIds.put(field, result);
        }

        return result;
    }

    public int indexOf(CstFieldRef ref) {
        if (ref == null) {
            throw new NullPointerException("ref == null");
        }

        throwIfNotPrepared();

        FieldIdItem item = fieldIds.get(ref);

        if (item == null) {
            throw new IllegalArgumentException("not found");
        }

        return item.getIndex();
    }
}
