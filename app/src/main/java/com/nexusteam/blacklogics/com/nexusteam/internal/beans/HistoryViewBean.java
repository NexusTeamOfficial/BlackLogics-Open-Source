package com.nexusteam.internal.beans;

import com.nexusteam.internal.jo;
import java.util.ArrayList;
import java.util.Iterator;

public class HistoryViewBean extends jo {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_MOVE = 3;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_UPDATE = 1;
    private int actionType;
    private ArrayList<ViewBean> addedData;
    private ViewBean currentUpdateData;
    private ViewBean moveData;
    private ViewBean prevUpdateData;
    private ArrayList<ViewBean> removedData;

    public void actionUpdate(ViewBean viewBean, ViewBean viewBean2) {
        this.actionType = 1;
        this.prevUpdateData = new ViewBean();
        this.prevUpdateData.copy(viewBean);
        this.currentUpdateData = new ViewBean();
        this.currentUpdateData.copy(viewBean2);
    }

    public void actionAdd(ArrayList<ViewBean> arrayList) {
        this.actionType = 0;
        this.addedData = new ArrayList<>();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = new ViewBean();
            viewBean.copy(it.next());
            this.addedData.add(viewBean);
        }
    }

    public void actionRemove(ArrayList<ViewBean> arrayList) {
        this.actionType = 2;
        this.removedData = new ArrayList<>();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = new ViewBean();
            viewBean.copy(it.next());
            this.removedData.add(viewBean);
        }
    }

    public void actionMove(ViewBean viewBean) {
        this.actionType = 3;
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

    public void copy(HistoryViewBean historyViewBean) {
        this.actionType = historyViewBean.actionType;
        if (historyViewBean.prevUpdateData != null) {
            this.prevUpdateData = new ViewBean();
            this.prevUpdateData.copy(historyViewBean.prevUpdateData);
        }
        if (historyViewBean.currentUpdateData != null) {
            this.currentUpdateData = new ViewBean();
            this.currentUpdateData.copy(historyViewBean.currentUpdateData);
        }
        if (historyViewBean.moveData != null) {
            this.moveData = new ViewBean();
            this.moveData.copy(historyViewBean.moveData);
        }
        if (historyViewBean.addedData != null) {
            this.addedData = new ArrayList<>();
            Iterator<ViewBean> it = historyViewBean.addedData.iterator();
            while (it.hasNext()) {
                ViewBean viewBean = new ViewBean();
                viewBean.copy(it.next());
                this.addedData.add(viewBean);
            }
        }
        if (historyViewBean.removedData != null) {
            this.removedData = new ArrayList<>();
            Iterator<ViewBean> it2 = historyViewBean.removedData.iterator();
            while (it2.hasNext()) {
                ViewBean viewBean2 = new ViewBean();
                viewBean2.copy(it2.next());
                this.removedData.add(viewBean2);
            }
        }
    }

    public HistoryViewBean clone() {
        HistoryViewBean historyViewBean = new HistoryViewBean();
        historyViewBean.copy(this);
        return historyViewBean;
    }
}
