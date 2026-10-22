
package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class ProtoIdsSection extends UniformItemSection {
    private final TreeMap<mod.agus.jcoderz.dx.rop.type.Prototype, ProtoIdItem> protoIds;

    public ProtoIdsSection(DexFile file) {
        super("proto_ids", file, 4);

        protoIds = new TreeMap<mod.agus.jcoderz.dx.rop.type.Prototype, ProtoIdItem>();
    }

    @Override
    public Collection<? extends Item> items() {
        return protoIds.values();
    }

    @Override
    public mod.agus.jcoderz.dx.dex.file.IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        if (!(cst instanceof mod.agus.jcoderz.dx.rop.cst.CstProtoRef)) {
            throw new IllegalArgumentException("cst not instance of CstProtoRef");
        }

        throwIfNotPrepared();
        mod.agus.jcoderz.dx.rop.cst.CstProtoRef protoRef = (CstProtoRef) cst;
        IndexedItem result = protoIds.get(protoRef.getPrototype());
        if (result == null) {
            throw new IllegalArgumentException("not found");
        }

        return result;
    }

    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        int sz = protoIds.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (sz > 65536) {
            throw new UnsupportedOperationException("too many proto ids");
        }

        if (out.annotates()) {
            out.annotate(4, "proto_ids_size:  " + mod.agus.jcoderz.dx.util.Hex.u4(sz));
            out.annotate(4, "proto_ids_off:   " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    public synchronized ProtoIdItem intern(mod.agus.jcoderz.dx.rop.type.Prototype prototype) {
        if (prototype == null) {
            throw new NullPointerException("prototype == null");
        }

        throwIfPrepared();

        ProtoIdItem result = protoIds.get(prototype);

        if (result == null) {
            result = new ProtoIdItem(prototype);
            protoIds.put(prototype, result);
        }

        return result;
    }

    public int indexOf(Prototype prototype) {
        if (prototype == null) {
            throw new NullPointerException("prototype == null");
        }

        throwIfNotPrepared();

        ProtoIdItem item = protoIds.get(prototype);

        if (item == null) {
            throw new IllegalArgumentException("not found");
        }

        return item.getIndex();
    }

    @Override
    protected void orderItems() {
        int idx = 0;

        for (Object i : items()) {
            ((ProtoIdItem) i).setIndex(idx);
            idx++;
        }
    }
}
