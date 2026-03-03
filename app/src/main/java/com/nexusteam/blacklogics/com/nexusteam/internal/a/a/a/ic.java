package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import com.nexusteam.internal.beans.ViewBean;

public class ic extends AppCompatTextView implements hq {

    /* renamed from: a  reason: collision with root package name */
    public Paint f290a;
    private ViewBean b;
    private boolean c;
    private boolean d;
    private float e;

    public ic(Context context) {
        super(context);
        a(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        setGravity(17);
        setTypeface((Typeface) null, 1);
        setText("MapView");
        this.e = kp.a(context, 1.0f);
        this.f290a = new Paint(1);
        this.f290a.setColor(-1785080368);
        setDrawingCacheEnabled(true);
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

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.c) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f290a);
        }
        super.onDraw(canvas);
    }
}
