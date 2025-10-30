package com.besome.blacklogics.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DesignSharedViewModel extends ViewModel {
    private final MutableLiveData<Boolean> saveEvent = new MutableLiveData<>();
    
    public void triggerSave() {
        saveEvent.postValue(true);
    }
    
    public LiveData<Boolean> getSaveEvent() {
        return saveEvent;
    }
}