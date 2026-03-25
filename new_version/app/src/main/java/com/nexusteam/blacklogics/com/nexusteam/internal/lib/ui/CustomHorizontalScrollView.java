package com.nexusteam.internal.lib.ui;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class CustomHorizontalScrollView extends HorizontalScrollView {

    /* renamed from: a  reason: collision with root package name */
    a f1594a;
    protected boolean b = true;
    protected boolean c = true;

    public interface a {
        void a(int i, int i2, int i3, int i4);
    }

    public CustomHorizontalScrollView(Context context) {
        super(context);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.c || !this.b) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.c || !this.b) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void a() {
        this.b = true;
    }

    public void b() {
        this.b = false;
    }

    public void setUseScroll(boolean z) {
        this.c = z;
    }

    public void setOnScrollChangedListener(a aVar) {
        this.f1594a = aVar;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.f1594a != null) {
            this.f1594a.a(i, i2, i3, i4);
        }
    }
}
