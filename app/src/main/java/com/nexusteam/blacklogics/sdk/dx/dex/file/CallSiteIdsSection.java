package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstCallSite;
import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;

public final class CallSiteIdsSection extends UniformItemSection {

    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef, CallSiteIdItem> callSiteIds = new TreeMap<>();

    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstCallSite, CallSiteItem> callSites = new TreeMap<>();

    public CallSiteIdsSection(DexFile dexFile) {
        super("call_site_ids", dexFile, 4);
    }

    @Override
    public IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();

        IndexedItem result = callSiteIds.get((mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef) cst);
        if (result == null) {
            throw new IllegalArgumentException("not found");
        }
        return result;
    }

    @Override
    protected void orderItems() {
        int index = 0;
        for (CallSiteIdItem callSiteId : callSiteIds.values()) {
            callSiteId.setIndex(index++);
        }
    }

    @Override
    public Collection<? extends Item> items() {
        return callSiteIds.values();
    }

    public synchronized void intern(CstCallSiteRef cstRef) {
        if (cstRef == null) {
            throw new NullPointerException("cstRef");
        }

        throwIfPrepared();

        CallSiteIdItem result = callSiteIds.get(cstRef);
        if (result == null) {
            result = new CallSiteIdItem(cstRef);
            callSiteIds.put(cstRef, result);
        }
    }

    void addCallSiteItem(mod.agus.jcoderz.dx.rop.cst.CstCallSite callSite, CallSiteItem callSiteItem) {
        if (callSite == null) {
            throw new NullPointerException("callSite == null");
        }
        if (callSiteItem == null) {
            throw new NullPointerException("callSiteItem == null");
        }
        callSites.put(callSite, callSiteItem);
    }

    CallSiteItem getCallSiteItem(CstCallSite callSite) {
        if (callSite == null) {
            throw new NullPointerException("callSite == null");
        }
        return callSites.get(callSite);
    }
}
