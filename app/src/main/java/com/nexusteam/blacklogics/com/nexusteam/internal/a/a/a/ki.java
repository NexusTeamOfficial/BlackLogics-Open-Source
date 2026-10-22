package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.ArrayList;

public class ki {

    private static long f330a;

    public static void a(ImageView imageView, int i) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation((float) i);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public static boolean a() {
        if (SystemClock.elapsedRealtime() - f330a < 100) {
            return true;
        }
        f330a = SystemClock.elapsedRealtime();
        return false;
    }

    public static void a(final View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 100);
    }

    public static SpannableStringBuilder a(Context context, String str) {
        kn knVar = new kn(context);
        knVar.a(str);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        while (true) {
            int d = knVar.d();
            if (d != -1) {
                switch (d) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        builder.setSpan(
                            new ForegroundColorSpan(kn.f338a[d]),
                            knVar.c(),
                            knVar.c() + knVar.a(),
                            33
                        );
                        break;
                }
            } else {
                return builder;
            }
        }
    }

    public static SpannableStringBuilder b(Context context, String str) {
        la laVar = new la(context);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        laVar.a(str);
        while (true) {
            int c = laVar.c();
            if (c != -1) {
                if (c == 2 || c == 4 || c == 5) {
                    builder.setSpan(
                        new ForegroundColorSpan(la.f345a[c]),
                        laVar.b(),
                        laVar.b() + laVar.a(),
                        33
                    );
                }
            } else {
                ArrayList<int[]> b = laVar.b(str);
                for (int[] iArr : b) {
                    builder.setSpan(new ForegroundColorSpan(la.f345a[3]), iArr[0], iArr[1], 33);
                }
                return builder;
            }
        }
    }

    public static void a(Context context, EditText editText) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
            .hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void a(View view, Animation.AnimationListener listener) {
        a(view, 1, listener);
    }

    public static void a(final View view, int speed, Animation.AnimationListener listener) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int measuredHeight = view.getMeasuredHeight();

        view.getLayoutParams().height = 1;
        view.setVisibility(View.VISIBLE);

        Animation expandAnim = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height =
                    interpolatedTime == 1.0f ? ViewGroup.LayoutParams.WRAP_CONTENT
                                             : (int) (measuredHeight * interpolatedTime);
                view.requestLayout();
            }
        };

        if (listener != null) expandAnim.setAnimationListener(listener);

        expandAnim.setDuration(
            (long) ((int) (((float) measuredHeight) / view.getContext().getResources()
                .getDisplayMetrics().density) * speed)
        );

        view.startAnimation(expandAnim);
    }

    public static void b(final View view, Animation.AnimationListener listener) {
        final int measuredHeight = view.getMeasuredHeight();

        Animation collapseAnim = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height =
                        measuredHeight - (int) (measuredHeight * interpolatedTime);
                    view.requestLayout();
                }
            }
        };

        if (listener != null) collapseAnim.setAnimationListener(listener);

        collapseAnim.setDuration(
            (long) ((int) (((float) measuredHeight) / view.getContext()
                .getResources().getDisplayMetrics().density))
        );

        view.startAnimation(collapseAnim);
    }
}
