
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.DexFormat;
import mod.agus.jcoderz.dex.DexIndexOverflowException;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

import java.util.Collection;
import java.util.TreeMap;

public final class TypeIdsSection extends UniformItemSection {
    private final TreeMap<mod.agus.jcoderz.dx.rop.type.Type, mod.agus.jcoderz.dx.dex.file.TypeIdItem> typeIds;

    public TypeIdsSection(DexFile file) {
        super("type_ids", file, 4);

        typeIds = new TreeMap<mod.agus.jcoderz.dx.rop.type.Type, mod.agus.jcoderz.dx.dex.file.TypeIdItem>();
    }

    @Override
    public Collection<? extends Item> items() {
        return typeIds.values();
    }

    @Override
    public mod.agus.jcoderz.dx.dex.file.IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        mod.agus.jcoderz.dx.rop.type.Type type = ((mod.agus.jcoderz.dx.rop.cst.CstType) cst).getClassType();
        IndexedItem result = typeIds.get(type);

        if (result == null) {
            throw new IllegalArgumentException("not found: " + cst);
        }

        return result;
    }

    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        int sz = typeIds.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (sz > DexFormat.MAX_TYPE_IDX + 1) {
            throw new DexIndexOverflowException(
                    String.format("Too many type identifiers to fit in one dex file: %1$d; max is %2$d.%n"
                                    + "You may try using multi-dex. If multi-dex is enabled then the list of "
                                    + "classes for the main dex list is too large.",
                            items().size(), DexFormat.MAX_MEMBER_IDX + 1));
        }

        if (out.annotates()) {
            out.annotate(4, "type_ids_size:   " + mod.agus.jcoderz.dx.util.Hex.u4(sz));
            out.annotate(4, "type_ids_off:    " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    public synchronized mod.agus.jcoderz.dx.dex.file.TypeIdItem intern(mod.agus.jcoderz.dx.rop.type.Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfPrepared();

        mod.agus.jcoderz.dx.dex.file.TypeIdItem result = typeIds.get(type);

        if (result == null) {
            result = new mod.agus.jcoderz.dx.dex.file.TypeIdItem(new mod.agus.jcoderz.dx.rop.cst.CstType(type));
            typeIds.put(type, result);
        }

        return result;
    }

    public synchronized mod.agus.jcoderz.dx.dex.file.TypeIdItem intern(mod.agus.jcoderz.dx.rop.cst.CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfPrepared();

        mod.agus.jcoderz.dx.rop.type.Type typePerSe = type.getClassType();
        mod.agus.jcoderz.dx.dex.file.TypeIdItem result = typeIds.get(typePerSe);

        if (result == null) {
            result = new mod.agus.jcoderz.dx.dex.file.TypeIdItem(type);
            typeIds.put(typePerSe, result);
        }

        return result;
    }

    public int indexOf(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfNotPrepared();

        mod.agus.jcoderz.dx.dex.file.TypeIdItem item = typeIds.get(type);

        if (item == null) {
            throw new IllegalArgumentException("not found: " + type);
        }

        return item.getIndex();
    }

    public int indexOf(CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        return indexOf(type.getClassType());
    }

    @Override
    protected void orderItems() {
        int idx = 0;

        for (Object i : items()) {
            ((TypeIdItem) i).setIndex(idx);
            idx++;
        }
    }
}
