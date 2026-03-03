
package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DisplayUtils {

    public static int dpToPx(Context context, float dp) {
        if (context == null) {
            return 0;
        }
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static int pxToDp(Context context, float px) {
        if (context == null) {
            return 0;
        }
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (px / metrics.density);
    }

    public static int spToPx(Context context, float sp) {
        if (context == null) {
            return 0;
        }
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static float getScreenDensity(Context context) {
        if (context == null) {
            return 1.0f;
        }
        
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }
}