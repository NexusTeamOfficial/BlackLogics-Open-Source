package com.nexusteam.internal.beans;

import com.nexusteam.internal.jo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Snapshot record for view history. Supports ADD/UPDATE/REMOVE/MOVE plus
 * an attribute-diff list so one UPDATE entry can hold a whole batch of attr changes.
 */
public class ViewHistoryBean extends jo {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_UPDATE = 1;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_MOVE = 3;

    private int actionType;
    private ArrayList<ViewBean> addedData;
    private ViewBean currentUpdateData;
    private ViewBean moveData;
    private ViewBean prevUpdateData;
    private ArrayList<ViewBean> removedData;

    /** Attribute diffs for UPDATE batches: (viewId, attrName, oldValue, newValue). */
    private ArrayList<AttrDiff> attrDiffs;

    public static class AttrDiff {
        public String viewId;
        public String attrName;
        public String oldValue;
        public String newValue;

        public AttrDiff() {}

        public AttrDiff(String viewId, String attrName, String oldValue, String newValue) {
            this.viewId = viewId;
            this.attrName = attrName;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public AttrDiff copy() {
            return new AttrDiff(viewId, attrName, oldValue, newValue);
        }
    }

    public void actionUpdate(ViewBean viewBean, ViewBean viewBean2) {
        this.actionType = ACTION_TYPE_UPDATE;
        this.prevUpdateData = new ViewBean();
        this.prevUpdateData.copy(viewBean);
        this.currentUpdateData = new ViewBean();
        this.currentUpdateData.copy(viewBean2);
    }

    /**
     * Attribute-diff path for UPDATE: one history entry holds a whole batch.
     */
    public void actionUpdateAttrs(List<AttrDiff> diffs) {
        this.actionType = ACTION_TYPE_UPDATE;
        this.attrDiffs = new ArrayList<AttrDiff>();
        if (diffs != null) {
            for (int i = 0; i < diffs.size(); i++) {
                AttrDiff d = diffs.get(i);
                if (d != null) this.attrDiffs.add(d.copy());
            }
        }
    }

    public void actionAdd(ArrayList<ViewBean> arrayList) {
        this.actionType = ACTION_TYPE_ADD;
        this.addedData = new ArrayList<ViewBean>();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = new ViewBean();
            viewBean.copy(it.next());
            this.addedData.add(viewBean);
        }
    }

    public void actionRemove(ArrayList<ViewBean> arrayList) {
        this.actionType = ACTION_TYPE_REMOVE;
        this.removedData = new ArrayList<ViewBean>();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = new ViewBean();
            viewBean.copy(it.next());
            this.removedData.add(viewBean);
        }
    }

    public void actionMove(ViewBean viewBean) {
        this.actionType = ACTION_TYPE_MOVE;
        this.moveData = new ViewBean();
        this.moveData.copy(viewBean);
    }

    public int getActionType() {
        return this.actionType;
    }

    public ViewBean getPrevUpdateData() {
        return this.prevUpdateData;
    }

    public ViewBean getCurrentUpdateData() {
        return this.currentUpdateData;
    }

    public ArrayList<ViewBean> getAddedData() {
        return this.addedData;
    }

    public ArrayList<ViewBean> getRemovedData() {
        return this.removedData;
    }

    public ViewBean getMovedData() {
        return this.moveData;
    }

    public ArrayList<AttrDiff> getAttrDiffs() {
        return this.attrDiffs;
    }

    public void copy(ViewHistoryBean viewHistoryBean) {
        this.actionType = viewHistoryBean.actionType;
        if (viewHistoryBean.prevUpdateData != null) {
            this.prevUpdateData = new ViewBean();
            this.prevUpdateData.copy(viewHistoryBean.prevUpdateData);
        } else {
            this.prevUpdateData = null;
        }
        if (viewHistoryBean.currentUpdateData != null) {
            this.currentUpdateData = new ViewBean();
            this.currentUpdateData.copy(viewHistoryBean.currentUpdateData);
        } else {
            this.currentUpdateData = null;
        }
        if (viewHistoryBean.moveData != null) {
            this.moveData = new ViewBean();
            this.moveData.copy(viewHistoryBean.moveData);
        } else {
            this.moveData = null;
        }
        if (viewHistoryBean.addedData != null) {
            this.addedData = new ArrayList<ViewBean>();
            Iterator<ViewBean> it = viewHistoryBean.addedData.iterator();
            while (it.hasNext()) {
                ViewBean vb = new ViewBean();
                vb.copy(it.next());
                this.addedData.add(vb);
            }
        } else {
            this.addedData = null;
        }
        if (viewHistoryBean.removedData != null) {
            this.removedData = new ArrayList<ViewBean>();
            Iterator<ViewBean> it = viewHistoryBean.removedData.iterator();
            while (it.hasNext()) {
                ViewBean vb = new ViewBean();
                vb.copy(it.next());
                this.removedData.add(vb);
            }
        } else {
            this.removedData = null;
        }
        if (viewHistoryBean.attrDiffs != null) {
            this.attrDiffs = new ArrayList<AttrDiff>();
            for (int i = 0; i < viewHistoryBean.attrDiffs.size(); i++) {
                AttrDiff d = viewHistoryBean.attrDiffs.get(i);
                if (d != null) this.attrDiffs.add(d.copy());
            }
        } else {
            this.attrDiffs = null;
        }
    }

    public ViewHistoryBean clone() {
        ViewHistoryBean c = new ViewHistoryBean();
        c.copy(this);
        return c;
    }
}
