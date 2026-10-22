package com.nexusteam.blacklogics.sdk.layouteditor;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public final class PanelAnimations {

    private PanelAnimations() {}

    public static void animatePanelOpen(View panel) {
        if (panel == null) return;
        panel.setVisibility(View.VISIBLE);
        panel.setTranslationY(80f);
        panel.setAlpha(0f);

        ObjectAnimator slide = ObjectAnimator.ofFloat(panel, "translationY", 80f, 0f);
        ObjectAnimator fade  = ObjectAnimator.ofFloat(panel, "alpha", 0f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(slide, fade);
        set.setDuration(220);
        set.setInterpolator(new DecelerateInterpolator(1.5f));
        set.start();
    }

    public static void animatePanelClose(final View panel, final Runnable onEnd) {
        if (panel == null) return;

        ObjectAnimator slide = ObjectAnimator.ofFloat(panel, "translationY", 0f, 60f);
        ObjectAnimator fade  = ObjectAnimator.ofFloat(panel, "alpha", 1f, 0f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(slide, fade);
        set.setDuration(180);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                panel.setVisibility(View.GONE);
                panel.setTranslationY(0f);
                panel.setAlpha(1f);
                if (onEnd != null) onEnd.run();
            }
        });
        set.start();
    }

    public static void crossFadeTabs(final View attributesTab, final View eventTab,
                                     boolean showAttributes) {
        if (attributesTab == null || eventTab == null) return;

        final View hide = showAttributes ? eventTab : attributesTab;
        final View show = showAttributes ? attributesTab : eventTab;

        hide.animate()
            .alpha(0f)
            .setDuration(120)
            .withEndAction(new Runnable() {
                @Override
                public void run() {
                    hide.setVisibility(View.GONE);
                    show.setAlpha(0f);
                    show.setVisibility(View.VISIBLE);
                    show.animate().alpha(1f).setDuration(160).start();
                }
            })
            .start();
    }

    public static void selectionPop(final View selectedView) {
        if (selectedView == null) return;
        selectedView.animate()
            .scaleX(1.04f).scaleY(1.04f)
            .setDuration(110)
            .setInterpolator(new OvershootInterpolator(1.2f))
            .withEndAction(new Runnable() {
                @Override
                public void run() {
                    selectedView.animate()
                        .scaleX(1f).scaleY(1f)
                        .setDuration(90)
                        .start();
                }
            })
            .start();
    }

    public static void setupListAnimator(RecyclerView rv) {
        if (rv == null) return;
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(180);
        animator.setRemoveDuration(150);
        animator.setChangeDuration(120);
        animator.setMoveDuration(160);
        animator.setSupportsChangeAnimations(false);
        rv.setItemAnimator(animator);
    }
}
