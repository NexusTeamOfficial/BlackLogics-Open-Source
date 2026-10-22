
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import java.util.Collection;
import java.util.TreeMap;

public final class MethodIdsSection extends MemberIdsSection {
    private final TreeMap<CstBaseMethodRef, MethodIdItem> methodIds;

    public MethodIdsSection(DexFile file) {
        super("method_ids", file);

        methodIds = new TreeMap<CstBaseMethodRef, MethodIdItem>();
    }

    @Override
    public Collection<? extends Item> items() {
        return methodIds.values();
    }

    @Override
    public mod.agus.jcoderz.dx.dex.file.IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        IndexedItem result = methodIds.get((CstBaseMethodRef) cst);

        if (result == null) {
            throw new IllegalArgumentException("not found");
        }

        return result;
    }

    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        int sz = methodIds.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (out.annotates()) {
            out.annotate(4, "method_ids_size: " + Hex.u4(sz));
            out.annotate(4, "method_ids_off:  " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    public synchronized MethodIdItem intern(CstBaseMethodRef method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        throwIfPrepared();

        MethodIdItem result = methodIds.get(method);

        if (result == null) {
            result = new MethodIdItem(method);
            methodIds.put(method, result);
        }

        return result;
    }

    public int indexOf(CstBaseMethodRef ref) {
        if (ref == null) {
            throw new NullPointerException("ref == null");
        }

        throwIfNotPrepared();

        MethodIdItem item = methodIds.get(ref);

        if (item == null) {
            throw new IllegalArgumentException("not found");
        }

        return item.getIndex();
    }
}
