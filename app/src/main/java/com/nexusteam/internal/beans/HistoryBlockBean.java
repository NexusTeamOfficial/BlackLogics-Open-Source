package com.nexusteam.internal.beans;

import com.nexusteam.internal.jo;
import java.util.ArrayList;
import java.util.Iterator;

public class HistoryBlockBean extends jo {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_MOVE = 3;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_UPDATE = 1;
    private int actionType;
    private ArrayList<BlockBean> addedData;
    private ArrayList<BlockBean> afterMove;
    private ArrayList<BlockBean> beforeMove;
    private BlockBean currentOriginalParent;
    private BlockBean currentParentData;
    private BlockBean currentUpdateData;
    private int currentX;
    private int currentY;
    private BlockBean prevOriginalParent;
    private BlockBean prevParentData;
    private BlockBean prevUpdateData;
    private int prevX;
    private int prevY;
    private ArrayList<BlockBean> removedData;

    public void actionUpdate(BlockBean blockBean, BlockBean blockBean2) {
        this.actionType = 1;
        this.prevUpdateData = blockBean.clone();
        this.currentUpdateData = blockBean2.clone();
    }

    public void actionAdd(ArrayList<BlockBean> arrayList, int i, int i2, BlockBean blockBean, BlockBean blockBean2) {
        this.actionType = 0;
        this.currentX = i;
        this.currentY = i2;
        this.addedData = new ArrayList<>();
        this.prevParentData = blockBean;
        this.currentParentData = blockBean2;
        Iterator<BlockBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.addedData.add(it.next().clone());
        }
    }

    public void actionRemove(ArrayList<BlockBean> arrayList, int i, int i2, BlockBean blockBean, BlockBean blockBean2) {
        this.actionType = 2;
        this.currentX = i;
        this.currentY = i2;
        this.prevParentData = blockBean;
        this.currentParentData = blockBean2;
        this.removedData = new ArrayList<>();
        Iterator<BlockBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.removedData.add(it.next().clone());
        }
    }

    public void actionMove(ArrayList<BlockBean> arrayList, ArrayList<BlockBean> arrayList2, int i, int i2, int i3, int i4, BlockBean blockBean, BlockBean blockBean2, BlockBean blockBean3, BlockBean blockBean4) {
        this.actionType = 3;
        this.prevX = i;
        this.prevY = i2;
        this.currentX = i3;
        this.currentY = i4;
        this.prevParentData = blockBean3;
        this.currentParentData = blockBean4;
        this.prevOriginalParent = blockBean;
        this.currentOriginalParent = blockBean2;
        this.beforeMove = arrayList;
        this.afterMove = arrayList2;
    }

    public int getActionType() {
        return this.actionType;
    }

    public BlockBean getPrevUpdateData() {
        return this.prevUpdateData;
    }

    public BlockBean getCurrentUpdateData() {
        return this.currentUpdateData;
    }

    public ArrayList<BlockBean> getAddedData() {
        return this.addedData;
    }

    public ArrayList<BlockBean> getRemovedData() {
        return this.removedData;
    }

    public ArrayList<BlockBean> getBeforeMoveData() {
        return this.beforeMove;
    }

    public ArrayList<BlockBean> getAfterMoveData() {
        return this.afterMove;
    }

    public int getPrevX() {
        return this.prevX;
    }

    public int getPrevY() {
        return this.prevY;
    }

    public int getCurrentX() {
        return this.currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public BlockBean getPrevParentData() {
        return this.prevParentData;
    }

    public BlockBean getCurrentParentData() {
        return this.currentParentData;
    }

    public BlockBean getPrevOriginalParent() {
        return this.prevOriginalParent;
    }

    public BlockBean getCurrentOriginalParent() {
        return this.currentOriginalParent;
    }

    public void copy(HistoryBlockBean historyBlockBean) {
        this.actionType = historyBlockBean.actionType;
        if (historyBlockBean.prevUpdateData != null) {
            this.prevUpdateData = historyBlockBean.prevUpdateData.clone();
        }
        if (historyBlockBean.currentUpdateData != null) {
            this.currentUpdateData = historyBlockBean.currentUpdateData.clone();
        }
        if (historyBlockBean.beforeMove != null) {
            this.beforeMove = new ArrayList<>();
            Iterator<BlockBean> it = historyBlockBean.beforeMove.iterator();
            while (it.hasNext()) {
                this.beforeMove.add(it.next().clone());
            }
        }
        if (historyBlockBean.afterMove != null) {
            this.afterMove = new ArrayList<>();
            Iterator<BlockBean> it2 = historyBlockBean.afterMove.iterator();
            while (it2.hasNext()) {
                this.afterMove.add(it2.next().clone());
            }
        }
        if (historyBlockBean.addedData != null) {
            this.addedData = new ArrayList<>();
            Iterator<BlockBean> it3 = historyBlockBean.addedData.iterator();
            while (it3.hasNext()) {
                this.addedData.add(it3.next().clone());
            }
        }
        if (historyBlockBean.removedData != null) {
            this.removedData = new ArrayList<>();
            Iterator<BlockBean> it4 = historyBlockBean.removedData.iterator();
            while (it4.hasNext()) {
                this.removedData.add(it4.next().clone());
            }
        }
        this.prevX = historyBlockBean.prevX;
        this.prevY = historyBlockBean.prevY;
        this.currentX = historyBlockBean.currentX;
        this.currentY = historyBlockBean.currentY;
        if (historyBlockBean.prevParentData != null) {
            this.prevParentData = historyBlockBean.prevParentData.clone();
        }
        if (historyBlockBean.currentParentData != null) {
            this.currentParentData = historyBlockBean.currentParentData.clone();
        }
        if (historyBlockBean.prevOriginalParent != null) {
            this.prevOriginalParent = historyBlockBean.prevOriginalParent.clone();
        }
        if (historyBlockBean.currentOriginalParent != null) {
            this.currentOriginalParent = historyBlockBean.currentOriginalParent.clone();
        }
    }

    public HistoryBlockBean clone() {
        HistoryBlockBean historyBlockBean = new HistoryBlockBean();
        historyBlockBean.copy(this);
        return historyBlockBean;
    }
}
