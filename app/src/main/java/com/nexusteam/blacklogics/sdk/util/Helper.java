package com.nexusteam.sdk.util;

import static com.apk.builder.ApplicationLoader.getContext;

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

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.color.MaterialColors;
import com.google.gson.reflect.TypeToken;

import mod.SketchwareUtil;
import com.nexusteam.sdk.lib.FileUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Helper {

    public static Type TYPE_MAP = new TypeToken<HashMap<String, Object>>() {}.getType();
    public static Type TYPE_MAP_LIST =
        new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType();
    public static Type TYPE_STRING = new TypeToken<ArrayList<String>>() {}.getType();
    public static Type TYPE_STRING_MAP = new TypeToken<HashMap<String, String>>() {}.getType();

    private Helper() {}

    public static void fixFileprovider() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure").invoke(null);
            } catch (Exception e) {
                Log.e("Helper",
                    "An error occurred while trying to fix death on file URI exposure: "
                        + e.getMessage(),
                    e);
            }
        }
    }

    public static void setViewsVisibility(boolean hide, View... views) {
        for (View view : views) {
            view.setVisibility(hide ? View.GONE : View.VISIBLE);
        }
    }

    public static String getResString(int resId) {
        return "";
    }

    public static View.OnClickListener getBackPressedClickListener(final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
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
        if (context == null || view == null) return;

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless, typedValue, true);

        view.setBackgroundResource(typedValue.resourceId);
        view.setClickable(true);
    }

    public static void applyRippleToToolbarView(@NonNull View view) {
        int rippleColor = withAlpha(
            MaterialColors.getColor(view, com.google.android.material.R.attr.colorPrimary),
            0.12f
        );
        int backgroundColor = MaterialColors.getColor(
            view, com.google.android.material.R.attr.colorSurfaceContainerHigh
        );

        applyRippleEffect(view, rippleColor, backgroundColor);
    }

    @Deprecated
    public static void applyRippleToToolbarViewLegacy(@NonNull View view) {
        if (view == null) return;

        GradientDrawable content = new GradientDrawable();
        content.setColor(Color.parseColor("#904A47"));
        content.setCornerRadius(90);

        view.setBackground(new RippleDrawable(
            new ColorStateList(
                new int[][] { new int[] { 0 } },
                new int[] { Color.parseColor("#64b5f6") }
            ),
            content,
            null
        ));
    }

    public static void applyRippleEffect(
        @Nullable final View target, @ColorInt final int rippleColor, @ColorInt int standardColor) {
        if (target == null) return;

        if (!target.isClickable()) {
            target.setClickable(true);
        }

        target.setBackground(new RippleDrawable(
            new ColorStateList(new int[][] { new int[] {} }, new int[] { rippleColor }),
            new ColorDrawable(standardColor),
            null
        ));
    }

    public static void applyRippleEffect(@NonNull View target) {
        int rippleColor = withAlpha(
            MaterialColors.getColor(target, com.google.android.material.R.attr.colorPrimary),
            0.12f
        );
        int backgroundColor = MaterialColors.getColor(
            target, com.google.android.material.R.attr.colorSurfaceContainerHigh
        );
        applyRippleEffect(target, rippleColor, backgroundColor);
    }

    public static String trimPath(String path) {
        return path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }

    public static void sortPaths(ArrayList<String> paths) {
        ArrayList<String> directories = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();

        for (String str : paths) {
            if (FileUtil.isDirectory(str)) {
                directories.add(str);
            } else {
                files.add(str);
            }
        }

        Collections.sort(directories, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(files, String.CASE_INSENSITIVE_ORDER);
        paths.clear();
        paths.addAll(directories);
        paths.addAll(files);
    }


    @ColorInt
    private static int withAlpha(@ColorInt int color, float alpha) {
        int a = Math.round(alpha * 255f);
        return (color & 0x00FFFFFF) | (a << 24);
    }

    public static class DialogDismissListener implements View.OnClickListener,
                                                         DialogInterface.OnDismissListener,
                                                         DialogInterface.OnCancelListener {

        private Dialog dialog;
        private boolean hideKeyboard = false;
        private View hideKeyboardView;

        private DialogDismissListener(Dialog dialog) {
            this.dialog = dialog;
        }

        private DialogDismissListener() {}

        @Override
        public void onClick(View v) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            hideKeyboard();
        }

        public DialogDismissListener setHideKeyboard(boolean hide, View visibleView) {
            hideKeyboard = hide;
            hideKeyboardView = visibleView;
            return this;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            hideKeyboard();
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            hideKeyboard();
        }

        private void hideKeyboard() {
            if (hideKeyboard) {
                if (hideKeyboardView == null) {
                    SketchwareUtil.hideKeyboard();
                } else {
                    SketchwareUtil.hideKeyboard(hideKeyboardView);
                }
            }
        }
    }
}