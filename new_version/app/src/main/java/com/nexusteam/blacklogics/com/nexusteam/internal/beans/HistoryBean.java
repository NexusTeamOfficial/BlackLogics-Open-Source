package com.nexusteam.internal.beans;

import com.nexusteam.internal.jo;

public class HistoryBean extends jo {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_UPDATE = 1;
    private int actionType;
    private jo currentData;
    private jo prevData;

    public HistoryBean(int i, jo joVar, jo joVar2) {
        this.actionType = i;
        this.prevData = joVar;
        this.currentData = joVar2;
    }

    public int getActionType() {
        return this.actionType;
    }

    public jo getPrevData() {
        return this.prevData;
    }

    public jo getCurrentData() {
        return this.currentData;
    }
}
