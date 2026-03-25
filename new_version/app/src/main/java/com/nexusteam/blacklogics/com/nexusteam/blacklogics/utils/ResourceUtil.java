
package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.content.res.Resources;

public class ResourceUtil {
    
    public static String getString(Context context, int resId) {
        try {
            return context.getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            return "";
        }
    }
    
    public static String getString(Context context, int resId, Object... formatArgs) {
        try {
            return context.getResources().getString(resId, formatArgs);
        } catch (Resources.NotFoundException e) {
            return "";
        }
    }
    
    public static int getColor(Context context, int resId) {
        try {
            return context.getResources().getColor(resId);
        } catch (Resources.NotFoundException e) {
            return 0;
        }
    }
    
    public static float getDimension(Context context, int resId) {
        try {
            return context.getResources().getDimension(resId);
        } catch (Resources.NotFoundException e) {
            return 0;
        }
    }
    
    public static int getDimensionPixelSize(Context context, int resId) {
        try {
            return context.getResources().getDimensionPixelSize(resId);
        } catch (Resources.NotFoundException e) {
            return 0;
        }
    }
}