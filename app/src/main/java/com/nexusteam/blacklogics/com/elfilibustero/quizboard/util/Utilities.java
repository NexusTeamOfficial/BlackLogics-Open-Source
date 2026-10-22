package com.elfilibustero.quizboard.util;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Utilities {


    public static long elapsed;


    private static final int DIMENSION_TYPE_PIXELS = 1;

    public static float applyDimens(Context context, float dpValue) {
        return TypedValue.applyDimension(DIMENSION_TYPE_PIXELS, dpValue, context.getResources().getDisplayMetrics());
    }

    public static void setImageColorMatrix(View view, int saturationLevel) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation((float) saturationLevel);
            imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        }
    }

    public static boolean isElapsedRealtime() {
        if (SystemClock.elapsedRealtime() - elapsed < 100L) {
            return true;
        } else {
            elapsed = SystemClock.elapsedRealtime();
            return false;
        }
    }

}
