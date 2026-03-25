package com.nexusteam.blacklogics;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

public class UiUpdateManager {
    private final Handler handler;
    private Runnable uiUpdateRunnable;
    private final Handler viewLoadHandler;
    private Runnable viewLoadRunnable;
    private final Handler dialogViewLoadHandler;
    private Runnable dialogViewLoadRunnable;
    private final Handler spinnerUpdateHandler;
    private Runnable spinnerUpdateRunnable;
    private boolean isSpinnerUpdating = false;
    private final Handler javaSourceHandler;
    private Runnable javaSourceRunnable;
    private boolean isRunnableActive = false;

    private final ImageView undoIcon;
    private final ImageView redoIcon;
    private final ViewPager customViewPager;

    public UiUpdateManager(ImageView undoIcon, ImageView redoIcon, ViewPager customViewPager) {
        this.undoIcon = undoIcon;
        this.redoIcon = redoIcon;
        this.customViewPager = customViewPager;
        this.handler = new Handler();
        this.viewLoadHandler = new Handler();
        this.dialogViewLoadHandler = new Handler();
        this.spinnerUpdateHandler = new Handler();
        this.javaSourceHandler = new Handler();
    }

    /**
     * Update visibility of undo/redo icons based on current ViewPager tab.
     */
    public void updateUndoRedoIcons() {
        undoIcon.setVisibility(customViewPager.getCurrentItem() == 0 ? View.VISIBLE : View.GONE);
        redoIcon.setVisibility(customViewPager.getCurrentItem() == 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * Clean up handlers and runnables on activity destruction.
     */
    public void cleanup() {
        if (handler != null && uiUpdateRunnable != null) {
            handler.removeCallbacks(uiUpdateRunnable);
        }
        if (viewLoadHandler != null && viewLoadRunnable != null) {
            viewLoadHandler.removeCallbacks(viewLoadRunnable);
        }
        if (dialogViewLoadHandler != null && dialogViewLoadRunnable != null) {
            dialogViewLoadHandler.removeCallbacks(dialogViewLoadRunnable);
        }
        if (spinnerUpdateHandler != null && spinnerUpdateRunnable != null) {
            spinnerUpdateHandler.removeCallbacks(spinnerUpdateRunnable);
        }
        if (javaSourceHandler != null && javaSourceRunnable != null) {
            javaSourceHandler.removeCallbacks(javaSourceRunnable);
        }
    }

    // Getter/setter for runnables (if needed by other classes)
    public void setUiUpdateRunnable(Runnable runnable) {
        this.uiUpdateRunnable = runnable;
    }

    public void setViewLoadRunnable(Runnable runnable) {
        this.viewLoadRunnable = runnable;
    }

    public void setDialogViewLoadRunnable(Runnable runnable) {
        this.dialogViewLoadRunnable = runnable;
    }

    public void setSpinnerUpdateRunnable(Runnable runnable) {
        this.spinnerUpdateRunnable = runnable;
    }

    public void setJavaSourceRunnable(Runnable runnable) {
        this.javaSourceRunnable = runnable;
    }

    public boolean isSpinnerUpdating() {
        return isSpinnerUpdating;
    }

    public void setSpinnerUpdating(boolean updating) {
        this.isSpinnerUpdating = updating;
    }

    public boolean isRunnableActive() {
        return isRunnableActive;
    }

    public void setRunnableActive(boolean active) {
        this.isRunnableActive = active;
    }

    // Access to handlers if needed
    public Handler getHandler() {
        return handler;
    }

    public Handler getViewLoadHandler() {
        return viewLoadHandler;
    }

    public Handler getDialogViewLoadHandler() {
        return dialogViewLoadHandler;
    }

    public Handler getSpinnerUpdateHandler() {
        return spinnerUpdateHandler;
    }

    public Handler getJavaSourceHandler() {
        return javaSourceHandler;
    }
}