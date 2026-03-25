package com.nexusteam.internal.editor.logic;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogicTopMenu extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    private LinearLayout f1115a;
    private TextView b;
    private LinearLayout c;
    private TextView d;
    private LinearLayout e;
    private TextView f;
    private LinearLayout g;
    private TextView h;
    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private ImageView m;
    private ImageView n;
    private ImageView o;
    private ImageView p;

    public LogicTopMenu(Context context) {
        super(context);
        a(context);
    }

    public LogicTopMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.logic_editor_top_menu);
        this.f1115a = (LinearLayout) findViewById(R.id.layout_delete);
        this.b = (TextView) findViewById(R.id.tv_delete);
        this.c = (LinearLayout) findViewById(R.id.layout_copy);
        this.d = (TextView) findViewById(R.id.tv_copy);
        this.e = (LinearLayout) findViewById(R.id.layout_favorite);
        this.f = (TextView) findViewById(R.id.tv_favorite);
        this.g = (LinearLayout) findViewById(R.id.layout_detail);
        this.h = (TextView) findViewById(R.id.tv_detail);
        this.m = (ImageView) findViewById(R.id.iv_trash);
        this.n = (ImageView) findViewById(R.id.iv_copy);
        this.o = (ImageView) findViewById(R.id.iv_bookmark);
        this.p = (ImageView) findViewById(R.id.iv_detail);
        this.b.setText(kq.a().a(getContext(), R.string.common_word_delete));
        this.d.setText(kq.a().a(getContext(), R.string.common_word_copy));
        this.f.setText(kq.a().a(getContext(), R.string.common_word_collection));
        this.h.setText(kq.a().a(getContext(), R.string.common_word_detail));
    }

    public void a(boolean z) {
        if (z) {
            this.e.setVisibility(0);
            this.c.setVisibility(0);
            this.g.setVisibility(8);
            return;
        }
        this.e.setVisibility(8);
        this.c.setVisibility(8);
        this.g.setVisibility(0);
    }

    public boolean a(float f2, float f3) {
        if (this.f1115a.getVisibility() == 8) {
            return false;
        }
        int[] iArr = new int[2];
        this.f1115a.getLocationOnScreen(iArr);
        if (f2 <= ((float) iArr[0]) || f2 >= ((float) (iArr[0] + this.f1115a.getWidth())) || f3 <= ((float) iArr[1]) || f3 >= ((float) (iArr[1] + this.f1115a.getHeight()))) {
            return false;
        }
        return true;
    }

    public boolean b(float f2, float f3) {
        if (this.c.getVisibility() == 8) {
            return false;
        }
        int[] iArr = new int[2];
        this.c.getLocationOnScreen(iArr);
        if (f2 <= ((float) iArr[0]) || f2 >= ((float) (iArr[0] + this.c.getWidth())) || f3 <= ((float) iArr[1]) || f3 >= ((float) (iArr[1] + this.c.getHeight()))) {
            return false;
        }
        return true;
    }

    public boolean c(float f2, float f3) {
        if (this.e.getVisibility() == 8) {
            return false;
        }
        int[] iArr = new int[2];
        this.e.getLocationOnScreen(iArr);
        if (f2 <= ((float) iArr[0]) || f2 >= ((float) (iArr[0] + this.e.getWidth())) || f3 <= ((float) iArr[1]) || f3 >= ((float) (iArr[1] + this.e.getHeight()))) {
            return false;
        }
        return true;
    }

    public boolean d(float f2, float f3) {
        if (this.g.getVisibility() == 8) {
            return false;
        }
        int[] iArr = new int[2];
        this.g.getLocationOnScreen(iArr);
        if (f2 <= ((float) iArr[0]) || f2 >= ((float) (iArr[0] + this.g.getWidth())) || f3 <= ((float) iArr[1]) || f3 >= ((float) (iArr[1] + this.g.getHeight()))) {
            return false;
        }
        return true;
    }

    public void b(boolean z) {
        this.i = z;
        if (z) {
            this.f1115a.setBackgroundColor(getResources().getColor(R.color.scolor_red_02));
            this.b.setTextColor(-1);
            this.m.setImageResource(R.drawable.ic_trashcan_white_48dp);
            return;
        }
        this.f1115a.setBackgroundColor(-65794);
        this.b.setTextColor(-8553091);
        this.m.setImageResource(R.drawable.icon_delete);
    }

    public void c(boolean z) {
        this.j = z;
        if (z) {
            this.c.setBackgroundColor(getResources().getColor(R.color.scolor_green_normal));
            this.d.setTextColor(-1);
            this.n.setImageResource(R.drawable.copy_48_white);
            return;
        }
        this.c.setBackgroundColor(-65794);
        this.d.setTextColor(-8553091);
        this.n.setImageResource(R.drawable.copy_48_gray);
    }

    public void d(boolean z) {
        this.k = z;
        if (z) {
            this.e.setBackgroundColor(getResources().getColor(R.color.scolor_blue_01));
            this.f.setTextColor(-1);
            this.o.setImageResource(R.drawable.bookmark_48_white);
            return;
        }
        this.e.setBackgroundColor(-65794);
        this.f.setTextColor(-8553091);
        this.o.setImageResource(R.drawable.ic_bookmark_red_48dp);
    }

    public void e(boolean z) {
        this.l = z;
        if (z) {
            this.g.setBackgroundColor(getResources().getColor(R.color.scolor_blue_01));
            this.h.setTextColor(-1);
            this.p.setImageResource(R.drawable.block_96_white);
            return;
        }
        this.g.setBackgroundColor(-65794);
        this.h.setTextColor(-8553091);
        this.p.setImageResource(R.drawable.block_flled_grey);
    }

    public boolean a() {
        return this.i;
    }

    public boolean b() {
        return this.j;
    }

    public boolean c() {
        return this.k;
    }

    public boolean d() {
        return this.l;
    }
}
