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

	// Used for tracking elapsed time
	public static long elapsed;

	// Constants for dimension types
	private static final int DIMENSION_TYPE_PIXELS = 1;

	/**
	 * Converts a DP value to pixels.
	 *
	 * @param context  The context used to get the display metrics.
	 * @param dpValue  The DP value to convert.
	 * @return  The value in pixels.
	 */
	public static float applyDimens(Context context, float dpValue) {
		return TypedValue.applyDimension(DIMENSION_TYPE_PIXELS, dpValue, context.getResources().getDisplayMetrics());
	}

	/**
	 * Applies a color matrix filter to an image view.
	 *
	 * @param view  The view to apply the color matrix filter to.
	 * @param saturationLevel  The saturation level to apply.
	 */
	public static void setImageColorMatrix(View view, int saturationLevel) {
		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			ColorMatrix colorMatrix = new ColorMatrix();
			colorMatrix.setSaturation((float) saturationLevel);
			imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
		}
	}

	/**
	 * Determines if a specified amount of time has elapsed since the last call to this method.
	 * This method is useful for preventing an action from being performed too frequently.
	 *
	 * @return True if the specified amount of time has not elapsed since the last call; false otherwise.
	 */
	public static boolean isElapsedRealtime() {
		if (SystemClock.elapsedRealtime() - elapsed < 100L) {
			return true;
		} else {
			elapsed = SystemClock.elapsedRealtime();
			return false;
		}
	}

}
