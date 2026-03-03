package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nexusteam.internal.beans.ViewBean;

public class hx extends FloatingActionButton implements hq {

    /* renamed from: a  reason: collision with root package name */
    private ViewBean f284a;
    private boolean b;
    private boolean c;

    public hx(Context context) {
        super(context);
        a(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        setCompatElevation(0.0f);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.f284a;
    }

    public void setBean(ViewBean viewBean) {
        this.f284a = viewBean;
    }

    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }

    public boolean getSelection() {
        return this.b;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public boolean getFixed() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.b) {
            setBackgroundTintList(ColorStateList.valueOf(-1785080368));
        } else {
            setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_accent)));
        }
        super.onDraw(canvas);
    }
}
