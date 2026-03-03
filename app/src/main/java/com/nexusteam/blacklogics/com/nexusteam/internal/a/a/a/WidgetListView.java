package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;


public class WidgetListView extends ListView implements hq {

    /* renamed from: a  reason: collision with root package name */
    public Paint f289a;
    ArrayList<String> b = new ArrayList<>();
    private ViewBean c;
    private boolean d;
    private boolean e;
    private float f;

    public WidgetListView(Context context) {
        super(context);
        a(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        this.f = ViewHelper.a(context, 1.0f);
        this.f289a = new Paint(1);
        this.f289a.setStrokeWidth(ViewHelper.a(getContext(), 2.0f));
        setDrawingCacheEnabled(true);
        this.b.add("List item 1");
        this.b.add("List item 2");
        this.b.add("List item 3");
        setAdapter(new ArrayAdapter(context, 17367043, this.b));
    }

    public ViewBean getBean() {
        return this.c;
    }

    public void setBean(ViewBean viewBean) {
        this.c = viewBean;
    }

    public void setSelection(boolean z) {
        this.d = z;
        invalidate();
    }

    public boolean getSelection() {
        return this.d;
    }

    public void setFixed(boolean z) {
        this.e = z;
    }

    public boolean getFixed() {
        return this.e;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding((int) (((float) i) * this.f), (int) (((float) i2) * this.f), (int) (((float) i3) * this.f), (int) (((float) i4) * this.f));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.d) {
            this.f289a.setColor(-1785080368);
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f289a);
        } else {
            this.f289a.setColor(1610612736);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f2 = (float) measuredWidth;
            canvas.drawLine(0.0f, 0.0f, f2, 0.0f, this.f289a);
            float f3 = (float) measuredHeight;
            canvas.drawLine(0.0f, 0.0f, 0.0f, f3, this.f289a);
            Canvas canvas2 = canvas;
            float f4 = f2;
            float f5 = f3;
            canvas2.drawLine(f2, 0.0f, f4, f5, this.f289a);
            canvas2.drawLine(0.0f, f3, f4, f5, this.f289a);
        }
        super.onDraw(canvas);
    }
}
