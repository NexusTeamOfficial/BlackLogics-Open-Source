
package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastManager {

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    public static void showSimpleToast(Context context, String message) {
        showSimpleToast(context, message, LENGTH_SHORT);
    }

    public static void showSimpleToast(Context context, String message, int duration) {
        if (context == null || message == null) {
            return;
        }
        Toast.makeText(context, message, duration).show();
    }

    public static void showStyledToast(Context context, String message, 
                                       int textColor, int bgColor, int cornerRadius) {
        showStyledToast(context, message, LENGTH_SHORT, textColor, bgColor, cornerRadius);
    }

    public static void showStyledToast(Context context, String message, int duration,
                                       int textColor, int bgColor, int cornerRadius) {
        if (context == null || message == null) {
            return;
        }

        Toast toast = Toast.makeText(context, message, duration);
        View view = toast.getView();
        
        if (view != null) {
            TextView textView = view.findViewById(android.R.id.message);
            if (textView != null) {
                textView.setTextColor(textColor);
                textView.setGravity(Gravity.CENTER);
            }

            GradientDrawable background = new GradientDrawable();
            background.setColor(bgColor);
            background.setCornerRadius(cornerRadius);
            view.setBackground(background);
            view.setPadding(20, 15, 20, 15);
        }

        toast.show();
    }

    public static void showSuccessToast(Context context, String message) {
        showStyledToast(context, message, Color.WHITE, Color.parseColor("#4CAF50"), 8);
    }

    public static void showErrorToast(Context context, String message) {
        showStyledToast(context, message, Color.WHITE, Color.parseColor("#F44336"), 8);
    }

    public static void showWarningToast(Context context, String message) {
        showStyledToast(context, message, Color.BLACK, Color.parseColor("#FFC107"), 8);
    }

    public static void showInfoToast(Context context, String message) {
        showStyledToast(context, message, Color.WHITE, Color.parseColor("#2196F3"), 8);
    }
}