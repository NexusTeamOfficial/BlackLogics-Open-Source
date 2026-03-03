package com.nexusteam.internal;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;

public class fh extends FrameLayout {

    /* renamed from: a  reason: collision with root package name */
    View f136a;
    View b;
    TextView c;
    TextView d;
    public LinearLayout e;
    public View f;
    public LinearLayout g;
    TextView h;
    AnimatorSet i;
    AnimatorSet j;
    AnimatorSet k;
    AnimatorSet l;
    public fj m;
    private Context n;

    public fh(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.n = context;
        kp.a(context, this, R.layout.fr_logic_list_item_buttons);
        this.e = (LinearLayout) findViewById(R.id.project_buttons);
        this.g = (LinearLayout) findViewById(R.id.ll_warning);
        this.f = findViewById(R.id.confirm_layout);
        this.f136a = findViewById(R.id.confirm_yes);
        this.b = findViewById(R.id.confirm_no);
        this.h = (TextView) findViewById(R.id.tv_warning_message);
        this.c = (TextView) findViewById(R.id.confirm_yes_text);
        this.c.setText(kq.a().a(getContext(), (int) R.string.common_word_continue));
        this.d = (TextView) findViewById(R.id.confirm_no_text);
        this.d.setText(kq.a().a(getContext(), (int) R.string.common_word_cancel));
        this.f.setVisibility(4);
        this.g.setVisibility(8);
        this.h.setText(kq.a().a(getContext(), (int) R.string.common_message_confirm));
        this.m = a(0, R.drawable.delete_96, kq.a().a(context, (int) R.string.common_word_delete));
        this.e.addView(this.m);
        this.i = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        this.j = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        this.k = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        this.l = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        this.f136a.setOnClickListener(onClickListener);
        this.b.setOnClickListener(onClickListener);
        this.m.setOnClickListener(onClickListener);
    }

    private fj a(int i2, int i3, String str) {
        fj fjVar = new fj(this.n);
        fjVar.f140a = i2;
        fjVar.c.setImageResource(i3);
        fjVar.d.setText(str);
        return fjVar;
    }

    public void a() {
        this.j.setTarget(this.e);
        this.i.setTarget(this.f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.j).with(this.i);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                fh.this.f.setVisibility(0);
                fh.this.g.setVisibility(0);
                fh.this.m.setEnabled(false);
                fh.this.b.setEnabled(false);
            }

            public void onAnimationEnd(Animator animator) {
                fh.this.m.setEnabled(false);
                fh.this.b.setEnabled(true);
                fh.this.e.setVisibility(4);
            }
        });
        animatorSet.start();
    }

    public void b() {
        this.k.setTarget(this.e);
        this.l.setTarget(this.f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.k).with(this.l);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                fh.this.g.setVisibility(8);
                fh.this.e.setVisibility(0);
                fh.this.m.setEnabled(false);
                fh.this.b.setEnabled(false);
            }

            public void onAnimationEnd(Animator animator) {
                fh.this.m.setEnabled(true);
                fh.this.b.setEnabled(false);
                fh.this.f.setVisibility(4);
            }
        });
        animatorSet.start();
    }
}
