package com.nexusteam.blacklogics.utils;

import android.os.AsyncTask;

import com.nexusteam.blacklogics.interfaces.AsyncTaskListener;
import com.nexusteam.blacklogics.custom.LoadingDialog;

import java.lang.ref.WeakReference;

public abstract class LoadingAsyncTask<T>
        extends AsyncTask<Void, Void, T> {

    private WeakReference<LoadingDialog> loadingRef;
    private WeakReference<AsyncTaskListener<T>> listenerRef;
    private Exception error;

    public LoadingAsyncTask(
            LoadingDialog loadingDialog,
            AsyncTaskListener<T> listener
    ) {
        this.loadingRef = new WeakReference<>(loadingDialog);
        this.listenerRef = new WeakReference<>(listener);
    }

    @Override
    protected void onPreExecute() {
        LoadingDialog loading = loadingRef.get();
        AsyncTaskListener<T> listener = listenerRef.get();

        if (loading != null) loading.show();
        if (listener != null) listener.onTaskStart();
    }

    @Override
    protected void onPostExecute(T result) {
        LoadingDialog loading = loadingRef.get();
        AsyncTaskListener<T> listener = listenerRef.get();

        if (loading != null) loading.hide();

        if (listener != null) {
            if (error != null) {
                listener.onTaskError(error);
            } else {
                listener.onTaskSuccess(result);
            }
        }
        clear();
    }

    @Override
    protected void onCancelled() {
        clear();
    }

    protected void setError(Exception e) {
        this.error = e;
    }

    private void clear() {
        if (loadingRef != null) loadingRef.clear();
        if (listenerRef != null) listenerRef.clear();
    }
}
