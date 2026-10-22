
package mod.agus.jcoderz.dx.dex.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class ClassDefsSection extends UniformItemSection {
    private final TreeMap<mod.agus.jcoderz.dx.rop.type.Type, ClassDefItem> classDefs;

    private ArrayList<ClassDefItem> orderedDefs;

    public ClassDefsSection(DexFile file) {
        super("class_defs", file, 4);

        classDefs = new TreeMap<mod.agus.jcoderz.dx.rop.type.Type, ClassDefItem>();
        orderedDefs = null;
    }

    @Override
    public Collection<? extends Item> items() {
        if (orderedDefs != null) {
            return orderedDefs;
        }

        return classDefs.values();
    }

    @Override
    public IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        mod.agus.jcoderz.dx.rop.type.Type type = ((mod.agus.jcoderz.dx.rop.cst.CstType) cst).getClassType();
        IndexedItem result = classDefs.get(type);

        if (result == null) {
            throw new IllegalArgumentException("not found");
        }

        return result;
    }

    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        int sz = classDefs.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (out.annotates()) {
            out.annotate(4, "class_defs_size: " + mod.agus.jcoderz.dx.util.Hex.u4(sz));
            out.annotate(4, "class_defs_off:  " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    public void add(ClassDefItem clazz) {
        mod.agus.jcoderz.dx.rop.type.Type type;

        try {
            type = clazz.getThisClass().getClassType();
        } catch (NullPointerException ex) {

            throw new NullPointerException("clazz == null");
        }

        throwIfPrepared();

        if (classDefs.get(type) != null) {
            throw new IllegalArgumentException("already added: " + type);
        }

        classDefs.put(type, clazz);
    }

    @Override
    protected void orderItems() {
        int sz = classDefs.size();
        int idx = 0;

        orderedDefs = new ArrayList<ClassDefItem>(sz);

        for (mod.agus.jcoderz.dx.rop.type.Type type : classDefs.keySet()) {
            idx = orderItems0(type, idx, sz - idx);
        }
    }

    private int orderItems0(mod.agus.jcoderz.dx.rop.type.Type type, int idx, int maxDepth) {
        ClassDefItem c = classDefs.get(type);

        if ((c == null) || (c.hasIndex())) {
            return idx;
        }

        if (maxDepth < 0) {
            throw new RuntimeException("class circularity with " + type);
        }

        maxDepth--;

        CstType superclassCst = c.getSuperclass();
        if (superclassCst != null) {
            Type superclass = superclassCst.getClassType();
            idx = orderItems0(superclass, idx, maxDepth);
        }

        TypeList interfaces = c.getInterfaces();
        int sz = interfaces.size();
        for (int i = 0; i < sz; i++) {
            idx = orderItems0(interfaces.getType(i), idx, maxDepth);
        }

        c.setIndex(idx);
        orderedDefs.add(c);
        return idx + 1;
    }
}
