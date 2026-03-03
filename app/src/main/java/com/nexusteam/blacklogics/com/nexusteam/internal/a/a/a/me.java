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

public class me extends FrameLayout {

    /* renamed from: a  reason: collision with root package name */
    TextView f376a;
    View b;
    View c;
    TextView d;
    TextView e;
    AnimatorSet f;
    AnimatorSet g;
    AnimatorSet h;
    AnimatorSet i;
    md j;
    md k;
    md l;
    md m;
    private Context n;
    /* access modifiers changed from: private */
    public View o;
    /* access modifiers changed from: private */
    public LinearLayout p;

    public me(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.n = context;
        ViewHelper.a(context, this, R.layout.myproject_buttons);
        this.p = (LinearLayout) findViewById(R.id.project_buttons);
        this.o = findViewById(R.id.confirm_layout);
        this.f376a = (TextView) findViewById(R.id.confirm_title);
        this.f376a.setText(StringResourceManager.a().a(getContext(), (int) R.string.language_message_confirm_delete));
        this.b = findViewById(R.id.confirm_yes);
        this.c = findViewById(R.id.confirm_no);
        this.d = (TextView) findViewById(R.id.confirm_yes_text);
        this.d.setText(StringResourceManager.a().a(getContext(), (int) R.string.common_word_delete));
        this.e = (TextView) findViewById(R.id.confirm_no_text);
        this.e.setText(StringResourceManager.a().a(getContext(), (int) R.string.common_word_cancel));
        this.f376a.setText(StringResourceManager.a().a(context, (int) R.string.myprojects_confirm_project_delete));
        this.d.setText(StringResourceManager.a().a(context, (int) R.string.common_word_delete));
        this.e.setText(StringResourceManager.a().a(context, (int) R.string.common_word_cancel));
        this.o.setVisibility(4);
        this.j = a(0, R.drawable.settings_96, StringResourceManager.a().a(context, (int) R.string.myprojects_list_menu_title_settings));
        this.k = a(1, R.drawable.google_play_48, StringResourceManager.a().a(context, (int) R.string.myprojects_list_menu_title_publish));
        this.l = a(2, R.drawable.ic_export_grey_48dp, StringResourceManager.a().a(context, (int) R.string.myprojects_list_menu_title_sign_export));
        this.m = a(3, R.drawable.ic_delete_grey_48dp, StringResourceManager.a().a(context, (int) R.string.myprojects_list_menu_title_delete));
        this.p.addView(this.j);
        this.p.addView(this.k);
        this.p.addView(this.l);
        this.p.addView(this.m);
        this.f = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        this.g = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        this.h = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        this.i = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        this.b.setOnClickListener(onClickListener);
        this.c.setOnClickListener(onClickListener);
        this.j.setOnClickListener(onClickListener);
        this.k.setOnClickListener(onClickListener);
        this.l.setOnClickListener(onClickListener);
        this.m.setOnClickListener(onClickListener);
    }

    private md a(int i2, int i3, String str) {
        md mdVar = new md(this.n);
        mdVar.f375a = i2;
        mdVar.c.setImageResource(i3);
        mdVar.d.setText(str);
        return mdVar;
    }

    public void a() {
        this.g.setTarget(this.p);
        this.f.setTarget(this.o);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.g).with(this.f);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                me.this.o.setVisibility(0);
                me.this.m.setEnabled(false);
                me.this.c.setEnabled(false);
            }

            public void onAnimationEnd(Animator animator) {
                me.this.m.setEnabled(false);
                me.this.c.setEnabled(true);
                me.this.p.setVisibility(4);
            }
        });
        animatorSet.start();
    }

    public void b() {
        this.h.setTarget(this.p);
        this.i.setTarget(this.o);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.h).with(this.i);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                me.this.p.setVisibility(0);
                me.this.m.setEnabled(false);
                me.this.c.setEnabled(false);
            }

            public void onAnimationEnd(Animator animator) {
                me.this.m.setEnabled(true);
                me.this.c.setEnabled(false);
                me.this.o.setVisibility(4);
            }
        });
        animatorSet.start();
    }
}
