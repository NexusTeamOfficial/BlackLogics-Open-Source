package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstCallSite;
import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class CallSiteIdItem extends IndexedItem implements Comparable {

    private static final int ITEM_SIZE = 4;

    final mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef invokeDynamicRef;

    mod.agus.jcoderz.dx.dex.file.CallSiteItem data;

    public CallSiteIdItem(CstCallSiteRef invokeDynamicRef) {
        this.invokeDynamicRef = invokeDynamicRef;
        this.data = null;
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_CALL_SITE_ID_ITEM;
    }

    @Override
    public int writeSize() {
        return ITEM_SIZE;
    }

    @Override
    public void addContents(DexFile file) {
        CstCallSite callSite = invokeDynamicRef.getCallSite();
        CallSiteIdsSection callSiteIds = file.getCallSiteIds();
        mod.agus.jcoderz.dx.dex.file.CallSiteItem callSiteItem = callSiteIds.getCallSiteItem(callSite);
        if (callSiteItem == null) {
            MixedItemSection byteData = file.getByteData();
            callSiteItem = new CallSiteItem(callSite);
            byteData.add(callSiteItem);
            callSiteIds.addCallSiteItem(callSite, callSiteItem);
        }
        this.data = callSiteItem;
    }

    @Override
    public void writeTo(DexFile file, AnnotatedOutput out) {
        int offset = data.getAbsoluteOffset();
        if (out.annotates()) {
            out.annotate(0, indexString() + ' ' + invokeDynamicRef.toString());
            out.annotate(4, "call_site_off: " + Hex.u4(offset));
        }
        out.writeInt(offset);
    }

    @Override
    public int compareTo(Object o) {
        CallSiteIdItem other = (CallSiteIdItem) o;
        return invokeDynamicRef.compareTo(other.invokeDynamicRef);
    }
}
