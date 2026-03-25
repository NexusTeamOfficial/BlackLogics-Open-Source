package com.nexusteam.blacklogics.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.interfaces.LoadingListener;

public class LoadingDialog {

    private Dialog dialog;
    private LoadingListener listener;

    public LoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.loading, null);

        dialog.setContentView(view);
        dialog.setCancelable(false);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );
            dialog.getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
            );
        }
    }

    public void setLoadingListener(LoadingListener listener) {
        this.listener = listener;
    }

    public void show() {
        if (!dialog.isShowing()) {
            dialog.show();
            if (listener != null) listener.onLoadingShown();
        }
    }

    public void hide() {
        if (dialog.isShowing()) {
            dialog.dismiss();
            if (listener != null) listener.onLoadingHidden();
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }
}
