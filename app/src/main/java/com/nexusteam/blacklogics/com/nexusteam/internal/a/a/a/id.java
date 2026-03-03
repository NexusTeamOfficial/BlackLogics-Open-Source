package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nexusteam.internal.beans.ViewBean;

public class id extends LinearLayout implements hq {
    
    /* renamed from: a  reason: collision with root package name */
    private ViewBean f291a;
    private boolean b;
    private boolean c;
    private Paint d;
    private float e;
    private ImageView f;
    
    public id(Context context) {
        super(context);
        a(context);
    }
    
    /* access modifiers changed from: protected */
    public void a(Context context) {
        this.e = kp.a(context, 1.0f);
        this.d = new Paint(1);
        this.d.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        this.f = new ImageView(getContext());
        this.f.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.f.setImageResource(R.drawable.progressbar_circle_48dp);
        this.f.setScaleType(ImageView.ScaleType.FIT_XY);
        this.f.setPadding(0, 0, 0, 0);
        addView(this.f);
        setGravity(17);
    }
    
    public void setProgressBarStyle(String style) {
        int type = -1;
        int hash = style.hashCode();
        
        if (hash == -1631599723 && style.equals("?android:progressBarStyle")) {
            type = 0;
        } else if (hash == 583759257 && style.equals("?android:progressBarStyleHorizontal")) {
            type = 1;
        }
        
        float size = 30.0f;
        switch (type) {
            case 0:

            f.setImageResource(R.drawable.progressbar_circle_48dp);
            f.getLayoutParams().width = (int) (e * size);
            f.getLayoutParams().height = (int) (e * size);
            break;
            
            case 1:

            f.setImageResource(R.drawable.progressbar_horizontal_48dp);
            f.getLayoutParams().width = (int) (e * 320.0f);
            f.getLayoutParams().height = (int) (e * size);
            break;
        }
    }
    
    
    public ViewBean getBean() {
        return this.f291a;
    }
    
    public void setBean(ViewBean viewBean) {
        this.f291a = viewBean;
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
    
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding((int) (((float) i) * this.e), (int) (((float) i2) * this.e), (int) (((float) i3) * this.e), (int) (((float) i4) * this.e));
    }
    
    /* access modifiers changed from: protected */
    public synchronized void onDraw(Canvas canvas) {
        if (this.b) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.d);
        }
        super.onDraw(canvas);
    }
}
