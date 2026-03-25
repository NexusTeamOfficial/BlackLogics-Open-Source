package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;
import com.nexusteam.internal.beans.ViewBean;

public class ia extends LinearLayout implements hq, hr {

    /* renamed from: a  reason: collision with root package name */
    public Paint f288a;
    private ViewBean b = null;
    private boolean c = false;
    private boolean d = false;
    private int e = 0;

    public ia(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        setOrientation(0);
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) kp.a(context, 32.0f));
        setMinimumHeight((int) kp.a(context, 32.0f));
        this.f288a = new Paint(1);
        this.f288a.setStrokeWidth(kp.a(getContext(), 2.0f));
    }

    public void setLayoutGravity(int i) {
        this.e = i;
        super.setGravity(i);
    }

    public int getLayoutGravity() {
        return this.e;
    }

    public ViewBean getBean() {
        return this.b;
    }

    public void setBean(ViewBean viewBean) {
        this.b = viewBean;
    }

    public void setSelection(boolean z) {
        this.c = z;
        invalidate();
    }

    public boolean getSelection() {
        return this.c;
    }

    public void setFixed(boolean z) {
        this.d = z;
    }

    public boolean getFixed() {
        return this.d;
    }

    public void addView(View view, int i) {
        int childCount = getChildCount();
        if (i > childCount) {
            super.addView(view);
            return;
        }
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            } else if (getChildAt(i3).getVisibility() == 8) {
                i2 = i3;
                break;
            } else {
                i3++;
            }
        }
        if (i2 < 0 || i < i2) {
            super.addView(view, i);
        } else {
            super.addView(view, i + 1);
        }
    }

    public void a() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof hq) {
                ((hq) childAt).getBean().index = i;
                i++;
            }
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding((int) kp.a(getContext(), (float) i), (int) kp.a(getContext(), (float) i2), (int) kp.a(getContext(), (float) i3), (int) kp.a(getContext(), (float) i4));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!this.d) {
            if (this.c) {
                this.f288a.setColor(-1785080368);
                canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f288a);
            }
            this.f288a.setColor(1610612736);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = (float) measuredWidth;
            canvas.drawLine(0.0f, 0.0f, f, 0.0f, this.f288a);
            float f2 = (float) measuredHeight;
            canvas.drawLine(0.0f, 0.0f, 0.0f, f2, this.f288a);
            Canvas canvas2 = canvas;
            float f3 = f;
            float f4 = f2;
            canvas2.drawLine(f, 0.0f, f3, f4, this.f288a);
            canvas2.drawLine(0.0f, f2, f3, f4, this.f288a);
        }
        super.onDraw(canvas);
    }

    public void setChildScrollEnabled(boolean z) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof hr) {
                ((hr) childAt).setChildScrollEnabled(z);
            }
            if (childAt instanceof hy) {
                ((hy) childAt).setScrollEnabled(z);
            }
            if (childAt instanceof ii) {
                ((ii) childAt).setScrollEnabled(z);
            }
        }
    }
}
