package com.nexusteam.internal.os.layouteditor.undo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.nexusteam.blacklogics.sdk.layouteditor.Attribute;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.layouteditor.AttributeStore;
import com.nexusteam.blacklogics.sdk.layouteditor.IdManager;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewBeanRegistry;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.beans.ViewHistoryBean;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Attribute-focused undo/redo manager. Structure changes stay on StableUndoManager.
 * Resolves Views by id via IdManager (no WeakReference). Apply runs on UI thread only.
 */
public class HistoryManager {

    private static final String TAG = "HistoryManager";
    private static final int DEFAULT_MAX = 50;

    public interface OnStackChangedListener {
        void onStackChanged(boolean canUndo, boolean canRedo);
    }

    public interface AttributeApplyBridge {
        /** Apply one attribute to live View + AttributeStore. Must be UI-thread safe. */
        void applyAttributeValue(View view, String attrName, String value);
        AttributeStore getAttributeStore();
        IdManager getIdManager();
        ViewBeanRegistry getBeanRegistry();
    }

    public static class HistoryEntry {
        public String description;
        public ViewHistoryBean historyBean;
        public long sequence;

        public HistoryEntry(String description, ViewHistoryBean bean) {
            this.description = description != null ? description : "Attribute change";
            this.historyBean = bean;
        }
    }

    private static class BoundedDeque {
        private final int maxSize;
        private final ArrayDeque<HistoryEntry> deque;

        BoundedDeque(int maxSize) {
            this.maxSize = maxSize;
            this.deque = new ArrayDeque<HistoryEntry>(maxSize);
        }

        void push(HistoryEntry item) {
            deque.push(item);
            while (deque.size() > maxSize) {
                deque.removeLast();
            }
        }

        HistoryEntry pop() {
            return deque.isEmpty() ? null : deque.pop();
        }

        HistoryEntry peek() {
            return deque.peek();
        }

        boolean isEmpty() {
            return deque.isEmpty();
        }

        int size() {
            return deque.size();
        }

        void clear() {
            deque.clear();
        }

        ArrayList<HistoryEntry> toList() {
            return new ArrayList<HistoryEntry>(deque);
        }
    }

    private static volatile HistoryManager instance;

    public static HistoryManager getInstance() {
        if (instance == null) {
            synchronized (HistoryManager.class) {
                if (instance == null) instance = new HistoryManager();
            }
        }
        return instance;
    }

    private HistoryManager() {}

    private int maxSize = DEFAULT_MAX;
    private final Map<String, BoundedDeque> undoStacks = new HashMap<String, BoundedDeque>();
    private final Map<String, BoundedDeque> redoStacks = new HashMap<String, BoundedDeque>();
    private final Map<String, Long> lastSequence = new HashMap<String, Long>();
    private long globalSequence = 0L;

    private boolean isApplying = false;
    private boolean batchMode = false;
    private String batchActivity;
    private String batchDescription;
    private final List<ViewHistoryBean.AttrDiff> batchDiffs = new ArrayList<ViewHistoryBean.AttrDiff>();

    private OnStackChangedListener listener;
    private AttributeApplyBridge bridge;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public void setListener(OnStackChangedListener l) {
        this.listener = l;
    }

    public void setBridge(AttributeApplyBridge bridge) {
        this.bridge = bridge;
    }

    public void setMaxSize(int size) {
        if (size > 0) this.maxSize = size;
    }

    public long getLastSequence(String activity) {
        Long s = lastSequence.get(activity);
        return s != null ? s.longValue() : 0L;
    }

    private BoundedDeque undoStack(String activity) {
        if (!undoStacks.containsKey(activity)) {
            undoStacks.put(activity, new BoundedDeque(maxSize));
        }
        return undoStacks.get(activity);
    }

    private BoundedDeque redoStack(String activity) {
        if (!redoStacks.containsKey(activity)) {
            redoStacks.put(activity, new BoundedDeque(maxSize));
        }
        return redoStacks.get(activity);
    }

    public void push(HistoryEntry entry) {
        if (isApplying || entry == null) return;
        // activity key must be set by caller via pushForActivity
    }

    public void pushForActivity(String activity, HistoryEntry entry) {
        if (isApplying || entry == null || activity == null) return;
        globalSequence++;
        entry.sequence = globalSequence;
        undoStack(activity).push(entry);
        redoStack(activity).clear();
        lastSequence.put(activity, Long.valueOf(globalSequence));
        notifyListener(activity);
    }

    public void beginBatch() {
        batchMode = true;
        batchDiffs.clear();
        batchDescription = null;
        batchActivity = null;
    }

    public void beginBatch(String activity, String description) {
        batchMode = true;
        batchDiffs.clear();
        batchActivity = activity;
        batchDescription = description;
    }

    public void addBatchDiff(String viewId, String attrName, String oldValue, String newValue) {
        if (!batchMode) return;
        batchDiffs.add(new ViewHistoryBean.AttrDiff(viewId, attrName, oldValue, newValue));
    }

    public void endBatch(String description) {
        if (!batchMode) return;
        batchMode = false;
        String act = batchActivity;
        String desc = description != null ? description : batchDescription;
        if (act == null || batchDiffs.isEmpty()) {
            batchDiffs.clear();
            return;
        }
        ViewHistoryBean hb = new ViewHistoryBean();
        hb.actionUpdateAttrs(new ArrayList<ViewHistoryBean.AttrDiff>(batchDiffs));
        batchDiffs.clear();
        pushForActivity(act, new HistoryEntry(desc != null ? desc : "Batch attribute change", hb));
    }

    public void endBatch() {
        endBatch(batchDescription);
    }

    /**
     * Push a single attribute change (or multiple diffs) for an activity.
     */
    public void pushAttrDiffs(String activity, String description, List<ViewHistoryBean.AttrDiff> diffs) {
        if (isApplying || activity == null || diffs == null || diffs.isEmpty()) return;
        if (batchMode) {
            for (int i = 0; i < diffs.size(); i++) {
                ViewHistoryBean.AttrDiff d = diffs.get(i);
                if (d != null) batchDiffs.add(d.copy());
            }
            if (batchActivity == null) batchActivity = activity;
            if (batchDescription == null) batchDescription = description;
            return;
        }
        ViewHistoryBean hb = new ViewHistoryBean();
        hb.actionUpdateAttrs(diffs);
        pushForActivity(activity, new HistoryEntry(description, hb));
    }

    public boolean canUndo(String activity) {
        return activity != null && !undoStack(activity).isEmpty();
    }

    public boolean canRedo(String activity) {
        return activity != null && !redoStack(activity).isEmpty();
    }

    public boolean undo(String activity) {
        if (activity == null || isApplying) return false;
        HistoryEntry entry = undoStack(activity).pop();
        if (entry == null) return false;
        isApplying = true;
        try {
            applyEntry(entry, true);
            redoStack(activity).push(entry);
            notifyListener(activity);
            return true;
        } finally {
            isApplying = false;
        }
    }

    public boolean redo(String activity) {
        if (activity == null || isApplying) return false;
        HistoryEntry entry = redoStack(activity).pop();
        if (entry == null) return false;
        isApplying = true;
        try {
            applyEntry(entry, false);
            undoStack(activity).push(entry);
            lastSequence.put(activity, Long.valueOf(entry.sequence));
            notifyListener(activity);
            return true;
        } finally {
            isApplying = false;
        }
    }

    public void clear(String activity) {
        if (activity == null) return;
        undoStack(activity).clear();
        redoStack(activity).clear();
        lastSequence.remove(activity);
        notifyListener(activity);
    }

    public void clearAll() {
        undoStacks.clear();
        redoStacks.clear();
        lastSequence.clear();
    }

    public boolean isApplying() {
        return isApplying;
    }

    /**
     * Apply step (UI thread only): update ViewBean attrs AND live View through bridge,
     * keep AttributeStore in sync. Resolve View by IdManager.getView(viewId).
     */
    private void applyEntry(HistoryEntry entry, boolean isUndo) {
        if (entry == null || entry.historyBean == null) return;
        final ViewHistoryBean hb = entry.historyBean;
        final ArrayList<ViewHistoryBean.AttrDiff> diffs = hb.getAttrDiffs();
        if (diffs == null || diffs.isEmpty()) {
            // Full bean update path (prev/current)
            applyBeanUpdate(hb, isUndo);
            return;
        }
        final boolean undoFlag = isUndo;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                applyAttrDiffsOnUi(diffs, undoFlag);
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            mainHandler.post(r);
        }
    }

    private void applyAttrDiffsOnUi(ArrayList<ViewHistoryBean.AttrDiff> diffs, boolean isUndo) {
        if (bridge == null) {
            Log.w(TAG, "No AttributeApplyBridge set; skip apply");
            return;
        }
        IdManager idManager = bridge.getIdManager();
        ViewBeanRegistry registry = bridge.getBeanRegistry();
        for (int i = 0; i < diffs.size(); i++) {
            ViewHistoryBean.AttrDiff d = diffs.get(i);
            if (d == null || d.viewId == null || d.attrName == null) continue;
            String value = isUndo ? d.oldValue : d.newValue;
            View view = idManager != null ? idManager.getView(d.viewId) : null;
            if (view == null) {
                Log.w(TAG, "View not found for id=" + d.viewId + "; skip attr " + d.attrName);
            } else {
                bridge.applyAttributeValue(view, d.attrName, value != null ? value : "");
            }
            if (registry != null) {
                ViewBean bean = registry.getBean(d.viewId);
                if (bean == null) {
                    bean = new ViewBean();
                    bean.id = d.viewId;
                    registry.putBean(d.viewId, bean);
                }
                Map<String, String> attrs = bean.getAttrs();
                if (value == null || value.length() == 0) {
                    attrs.remove(d.attrName);
                } else {
                    attrs.put(d.attrName, value);
                }
            }
        }
    }

    private void applyBeanUpdate(ViewHistoryBean hb, boolean isUndo) {
        ViewBean target = isUndo ? hb.getPrevUpdateData() : hb.getCurrentUpdateData();
        if (target == null || target.id == null || bridge == null) return;
        final ViewBean bean = target;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                IdManager idManager = bridge.getIdManager();
                ViewBeanRegistry registry = bridge.getBeanRegistry();
                View view = idManager != null ? idManager.getView(bean.id) : null;
                if (registry != null) {
                    ViewBean existing = registry.getBean(bean.id);
                    if (existing == null) {
                        registry.putBean(bean.id, bean.clone());
                    } else {
                        existing.copy(bean);
                    }
                }
                if (view != null && bean.getAttrs() != null) {
                    Iterator<Map.Entry<String, String>> it = bean.getAttrs().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> e = it.next();
                        bridge.applyAttributeValue(view, e.getKey(), e.getValue());
                    }
                } else if (view == null) {
                    Log.w(TAG, "View not found for bean id=" + bean.id);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            mainHandler.post(r);
        }
    }

    private void notifyListener(String activity) {
        if (listener != null) {
            listener.onStackChanged(canUndo(activity), canRedo(activity));
        }
    }

    /** Export undo stack for persistence (attribute diffs only). */
    public ArrayList<HistoryEntry> exportUndoStack(String activity) {
        if (activity == null) return new ArrayList<HistoryEntry>();
        return undoStack(activity).toList();
    }

    public ArrayList<HistoryEntry> exportRedoStack(String activity) {
        if (activity == null) return new ArrayList<HistoryEntry>();
        return redoStack(activity).toList();
    }

    /** Import stacks from persistence; corrupt data should pass empty lists. */
    public void importStacks(String activity, List<HistoryEntry> undo, List<HistoryEntry> redo) {
        if (activity == null) return;
        BoundedDeque u = undoStack(activity);
        BoundedDeque r = redoStack(activity);
        u.clear();
        r.clear();
        if (undo != null) {
            // list is top-first from toList (ArrayDeque iteration is head to tail = top to bottom)
            // push in reverse so top stays top
            for (int i = undo.size() - 1; i >= 0; i--) {
                HistoryEntry e = undo.get(i);
                if (e != null) u.push(e);
            }
        }
        if (redo != null) {
            for (int i = redo.size() - 1; i >= 0; i--) {
                HistoryEntry e = redo.get(i);
                if (e != null) r.push(e);
            }
        }
        notifyListener(activity);
    }
}
