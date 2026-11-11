package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

public class kf {
    public static void a(final ViewGroup viewGroup, int i, Animator.AnimatorListener animatorListener) {
        viewGroup.measure(-1, -2);
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, viewGroup.getMeasuredHeight()});
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                layoutParams.height = intValue;
                viewGroup.setLayoutParams(layoutParams);
            }
        });
        if (animatorListener != null) {
            ofInt.addListener(animatorListener);
        }
        ofInt.setDuration((long) i);
        ofInt.start();
    }

    public static void b(final ViewGroup viewGroup, int i, Animator.AnimatorListener animatorListener) {
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{viewGroup.getHeight(), 0});
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                layoutParams.height = intValue;
                viewGroup.setLayoutParams(layoutParams);
            }
        });
        ofInt.addListener(animatorListener);
        ofInt.setDuration((long) i);
        ofInt.start();
    }

    public static void a(View view, float f, Animator.AnimatorListener animatorListener) {
        view.animate().setListener(animatorListener).rotation(f).start();
    }

    public static void a(View view, float f, int i, int i2, Animator.AnimatorListener animatorListener) {
        view.animate().setListener(animatorListener).rotation(f).setStartDelay((long) i2).setDuration((long) i).start();
    }

    public static void a(View view, int i, int i2, Animator.AnimatorListener animatorListener) {
        view.setAlpha(0.0f);
        view.setTranslationY(140.0f);
        view.animate().setListener(animatorListener).alpha(1.0f).translationY(0.0f).setStartDelay((long) i2).setDuration((long) i).start();
    }

    public static void a(final View view, int i) {
        int parseColor = Color.parseColor("#b2000000");
        ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{0, Integer.valueOf(parseColor)});
        ofObject.setDuration((long) i);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setBackgroundColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        ofObject.start();
    }

    public static void b(View view, int i, int i2, Animator.AnimatorListener animatorListener) {
        view.setTranslationX((float) (-view.getMeasuredWidth()));
        view.animate().translationX(0.0f).setStartDelay((long) i2).setDuration((long) i).setListener(animatorListener).start();
    }

    public static void c(View view, int i, int i2, Animator.AnimatorListener animatorListener) {
        view.setTranslationX((float) view.getMeasuredWidth());
        view.animate().translationX(0.0f).setStartDelay((long) i2).setDuration((long) i).setListener(animatorListener).start();
    }

    public static void d(View view, int i, int i2, Animator.AnimatorListener animatorListener) {
        view.setTranslationY((float) ((-view.getMeasuredHeight()) * 5));
        view.animate().translationY(0.0f).setInterpolator(new BounceInterpolator()).setDuration((long) i).setStartDelay((long) i2).setListener((Animator.AnimatorListener) null).start();
    }
}
