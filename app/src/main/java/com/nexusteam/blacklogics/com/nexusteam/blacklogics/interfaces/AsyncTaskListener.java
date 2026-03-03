package com.nexusteam.blacklogics.interfaces;

public interface AsyncTaskListener<T> {
    void onTaskStart();
    void onTaskSuccess(T result);
    void onTaskError(Exception e);
}
