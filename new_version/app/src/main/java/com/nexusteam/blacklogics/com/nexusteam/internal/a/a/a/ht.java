package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatButton;
import com.nexusteam.internal.beans.ViewBean;

public class ht extends AppCompatButton implements hq {

    /* renamed from: a  reason: collision with root package name */
    public Paint f280a;
    private ViewBean b;
    private boolean c;
    private boolean d;
    private float e;
    private Drawable f;

    public ht(Context context) {
        super(context);
        a(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        this.e = kp.a(context, 1.0f);
        this.f280a = new Paint(1);
        this.f280a.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        setFocusable(false);
        this.f = getBackground();
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

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding((int) (((float) i) * this.e), (int) (((float) i2) * this.e), (int) (((float) i3) * this.e), (int) (((float) i4) * this.e));
    }

    public void setBackgroundColor(int i) {
        if (i == 16777215) {
            setBackground(this.f);
        } else {
            super.setBackgroundColor(i);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.c) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f280a);
        }
        super.onDraw(canvas);
    }
}
