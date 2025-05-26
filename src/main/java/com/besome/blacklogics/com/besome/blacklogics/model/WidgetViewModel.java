package com.besome.blacklogics.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WidgetViewModel extends ViewModel {
    public MutableLiveData<Boolean> refreshTrigger = new MutableLiveData<>();

    public void triggerRefresh() {
        refreshTrigger.setValue(true);
    }

    public LiveData<Boolean> getRefreshTrigger() {
        return refreshTrigger;
    }
}