package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class hs extends LinearLayout implements hq {
    
    /* renamed from: a  reason: collision with root package name */
    public Paint f279a;
    private ViewBean b;
    private boolean c;
    private boolean d;
    private float e;
    private ImageView f;
    private int adSize;
    int drawableRes;
    
    public hs(Context context) {
        super(context);
        a(context);
    }
    
    /* access modifiers changed from: protected */
    public void a(Context context) {
        this.e = kp.a(context, 1.0f);
        this.f279a = new Paint(1);
        this.f279a.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        this.f = new ImageView(getContext());
        this.f.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.f.setImageResource(R.drawable.admob_banner);
        this.f.setScaleType(ImageView.ScaleType.FIT_XY);
        this.f.setPadding(0, 0, 0, 0);
        addView(this.f);
        setGravity(17);
    }
    
    public void setAdSize(String size) {
        if (size == null) return;
        
        if (size.equalsIgnoreCase("BANNER")) {
            drawableRes = R.drawable.admob_banner;
        } else if (size.equalsIgnoreCase("LARGE_BANNER")) {
            drawableRes = R.drawable.admob_large_banner;
        } else if (size.equalsIgnoreCase("MEDIUM_RECTANGLE") || size.equalsIgnoreCase("MEDIUM_BANNER")) {
            drawableRes = R.drawable.admob_medium_banner;
        } else {
            drawableRes = R.drawable.admob_banner; // default
        }
        
        this.adSize = drawableRes; // store int drawable ID
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
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f279a);
        }
        super.onDraw(canvas);
    }
}
