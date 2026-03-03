package com.nexusteam.blacklogics.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.apk.builder.ApplicationLoader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {

    public static final Type TYPE_MAP = new TypeToken<Map<String, Object>>() {}.getType();
    public static final Type TYPE_MAP_LIST = new TypeToken<List<Map<String, Object>>>() {}.getType();
    public static final Type TYPE_STRING_LIST = new TypeToken<List<String>>() {}.getType();
    public static final Type TYPE_STRING_MAP = new TypeToken<Map<String, String>>() {}.getType();
    public static final Type TYPE_STRING = new TypeToken<ArrayList<String>>() {}.getType(); // Added missing type

    private Helper() {

    }

    public static void fixFileProvider() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure").invoke(null);
            } catch (Exception e) {
                Log.e("Helper", "Error fixing file URI exposure: " + e.getMessage(), e);
            }
        }
    }

    public static void setViewsVisibility(boolean hide, View... views) {
        for (View view : views) {
            view.setVisibility(hide ? View.GONE : View.VISIBLE);
        }
    }

    public static String getResString(int resId) {
        try {
            Context context = ApplicationLoader.getContext();
            return context.getResources().getString(resId);
        } catch (Exception e) {
            Log.e("Helper", "Error getting resource string", e);
            return "";
        }
    }

    public static View.OnClickListener getBackPressedClickListener(final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity != null) {
                    activity.onBackPressed();
                }
            }
        };
    }

    public static DialogDismissListener getDialogDismissListener(final Dialog dialog) {
        return new DialogDismissListener(dialog);
    }

    public static DialogDismissListener getDialogOnDismissKeyboardHider() {
        return new DialogDismissListener();
    }

    public static DialogDismissListener getDialogOnCancelKeyboardHider() {
        return new DialogDismissListener();
    }

    public static void applyRipple(Context context, View view) {
        if (context == null || view == null) {
            return;
        }

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);

        view.setBackgroundResource(typedValue.resourceId);
        view.setClickable(true);
    }

    public static void applyRippleToToolbarView(View view) {
        if (view == null) {
            return;
        }

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.parseColor("#008dcd"));
        background.setCornerRadius(90);

        view.setBackground(new RippleDrawable(
            new ColorStateList(
                new int[][]{new int[]{0}},
                new int[]{Color.parseColor("#64b5f6")}
            ),
            background,
            null
        ));
    }

    public static void applyRippleEffect(View target, int rippleColor, int backgroundColor) {
        if (target == null) {
            return;
        }

        if (!target.isClickable()) {
            target.setClickable(true);
        }

        target.setBackground(new RippleDrawable(
            new ColorStateList(
                new int[][]{new int[]{}},
                new int[]{rippleColor}
            ),
            new ColorDrawable(backgroundColor),
            null
        ));
    }

    public static String trimPath(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        return path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }

    public static void sortPaths(List<String> paths) {
        if (paths == null || paths.isEmpty()) {
            return;
        }

        List<String> directories = new ArrayList<>();
        List<String> files = new ArrayList<>();

        for (String path : paths) {
            if (FileUtil.isDirectory(path)) {
                directories.add(path);
            } else {
                files.add(path);
            }
        }

        Collections.sort(directories, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(files, String.CASE_INSENSITIVE_ORDER);
        
        paths.clear();
        paths.addAll(directories);
        paths.addAll(files);
    }

    public static class DialogDismissListener implements 
            View.OnClickListener, DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {

        private Dialog dialog;
        private boolean hideKeyboard = false;
        private View keyboardView;

        public DialogDismissListener(Dialog dialog) {
            this.dialog = dialog;
        }

        public DialogDismissListener() {

        }

        @Override
        public void onClick(View view) {
            if (dialog != null) {
                dialog.dismiss();
            }
            hideKeyboard();
        }

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            hideKeyboard();
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            hideKeyboard();
        }

        public DialogDismissListener setHideKeyboard(boolean hide, View view) {
            this.hideKeyboard = hide;
            this.keyboardView = view;
            return this;
        }

        private void hideKeyboard() {
            if (hideKeyboard) {
                if (keyboardView == null) {
                    BlackLogicsUtil.hideKeyboard();
                } else {
                    BlackLogicsUtil.hideKeyboard(keyboardView);
                }
            }
        }
    }
}