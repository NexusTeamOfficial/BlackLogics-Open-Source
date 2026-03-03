package com.nexusteam.internal.lib.ui;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    /* renamed from: a  reason: collision with root package name */
    private boolean f1595a = true;
    private boolean b = true;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.b || !this.f1595a) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.b || !this.f1595a) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void a() {
        this.f1595a = true;
    }

    public void b() {
        this.f1595a = false;
    }

    public void setUseScroll(boolean z) {
        this.b = z;
    }
}
