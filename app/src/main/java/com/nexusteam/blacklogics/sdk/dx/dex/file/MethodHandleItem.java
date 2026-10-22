package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class MethodHandleItem extends IndexedItem {

    private final int ITEM_SIZE = 8;

    private final CstMethodHandle methodHandle;

    public MethodHandleItem(CstMethodHandle methodHandle) {
        this.methodHandle = methodHandle;
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_METHOD_HANDLE_ITEM;
    }

    @Override
    public int writeSize() {
        return ITEM_SIZE;
    }

    @Override
    public void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file) {
        MethodHandlesSection methodHandles = file.getMethodHandles();
        methodHandles.intern(methodHandle);
    }

    @Override
    public void writeTo(mod.agus.jcoderz.dx.dex.file.DexFile file, AnnotatedOutput out) {
        int targetIndex = getTargetIndex(file);
        int mhType = methodHandle.getMethodHandleType();
        if (out.annotates()) {
            out.annotate(0, indexString() + ' ' + methodHandle.toString());
            String typeComment = " // " + CstMethodHandle.getMethodHandleTypeName(mhType);
            out.annotate(2, "type:     " + Hex.u2(mhType) + typeComment);
            out.annotate(2, "reserved: " + Hex.u2(0));
            String targetComment = " // " +  methodHandle.getRef().toString();
            if (methodHandle.isAccessor()) {
                out.annotate(2, "fieldId:  " + Hex.u2(targetIndex) + targetComment);
            } else {
                out.annotate(2, "methodId: " + Hex.u2(targetIndex) + targetComment);
            }
            out.annotate(2, "reserved: " + Hex.u2(0));
        }
        out.writeShort(mhType);
        out.writeShort(0);
        out.writeShort(getTargetIndex(file));
        out.writeShort(0);
    }

    private int getTargetIndex(DexFile file) {
        Constant ref = methodHandle.getRef();
        if (methodHandle.isAccessor()) {
            FieldIdsSection fieldIds = file.getFieldIds();
            return fieldIds.indexOf((CstFieldRef) ref);
        } else if (methodHandle.isInvocation()) {
            if (ref instanceof CstInterfaceMethodRef) {
                ref = ((CstInterfaceMethodRef)ref).toMethodRef();
            }
            MethodIdsSection methodIds = file.getMethodIds();
            return methodIds.indexOf((CstBaseMethodRef) ref);
        } else {
            throw new IllegalStateException("Unhandled invocation type");
        }
    }
}
