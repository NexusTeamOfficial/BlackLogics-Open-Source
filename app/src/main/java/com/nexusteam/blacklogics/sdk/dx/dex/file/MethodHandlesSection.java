package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;

public final class MethodHandlesSection extends UniformItemSection {

    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstMethodHandle, MethodHandleItem> methodHandles = new TreeMap<>();

    public MethodHandlesSection(DexFile dexFile) {
        super("method_handles", dexFile, 8);
    }

    @Override
    public IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();

        IndexedItem result = methodHandles.get((mod.agus.jcoderz.dx.rop.cst.CstMethodHandle) cst);
        if (result == null) {
            throw new IllegalArgumentException("not found");
        }
        return result;
    }

    @Override
    protected void orderItems() {
        int index = 0;
        for (MethodHandleItem item : methodHandles.values()) {
            item.setIndex(index++);
        }
    }

    @Override
    public Collection<? extends Item> items() {
        return methodHandles.values();
    }

    public synchronized void intern(mod.agus.jcoderz.dx.rop.cst.CstMethodHandle methodHandle) {
        if (methodHandle == null) {
            throw new NullPointerException("methodHandle == null");
        }

        throwIfPrepared();

        MethodHandleItem result = methodHandles.get(methodHandle);
        if (result == null) {
            result = new MethodHandleItem(methodHandle);
            methodHandles.put(methodHandle, result);
        }
    }

    int indexOf(CstMethodHandle cstMethodHandle) {
        return methodHandles.get(cstMethodHandle).getIndex();
    }
}
