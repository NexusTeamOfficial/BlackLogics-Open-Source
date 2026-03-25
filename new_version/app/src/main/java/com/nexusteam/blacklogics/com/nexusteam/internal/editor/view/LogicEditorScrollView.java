package com.nexusteam.internal.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class LogicEditorScrollView extends FrameLayout {

    /* renamed from: a  reason: collision with root package name */
    private float f1382a = 0.0f;
    private float b = 0.0f;
    private int c = 0;
    private boolean d = false;
    private boolean e = true;
    private boolean f = true;
    private float g = -1.0f;
    private float h = -1.0f;

    public LogicEditorScrollView(Context context) {
        super(context);
        a(context);
    }

    public LogicEditorScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        this.c = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setScrollEnabled(boolean z) {
        this.e = z;
    }

    public boolean getScrollEnabled() {
        return this.e;
    }

    public void setUseScroll(boolean z) {
        this.f = z;
    }

    public boolean getUseScroll() {
        return this.f;
    }

    public void addView(View view) {
        if (getChildCount() > 1) {
            throw new IllegalStateException("BothDirectionScrollView should have child View only one");
        }
        super.addView(view);
    }

    /* access modifiers changed from: protected */
    public boolean a() {
        if (getChildCount() <= 0 || !this.f || !this.e) {
            return false;
        }
        View childAt = getChildAt(0);
        int width = childAt.getWidth();
        int height = childAt.getHeight();
        if (getWidth() < width + getPaddingLeft() + getPaddingRight() || getHeight() < height + getPaddingTop() + getPaddingBottom()) {
            return true;
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!a()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 2 && this.d) {
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.f1382a = x;
                this.b = y;
                this.d = false;
                break;
            case 1:
                this.d = false;
                break;
            case 2:
                int abs = (int) Math.abs(this.f1382a - x);
                int abs2 = (int) Math.abs(this.b - y);
                if (abs > this.c || abs2 > this.c) {
                    this.d = true;
                    break;
                }
        }
        return this.d;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        int i2 = 0;
        if (!a()) {
            return false;
        }
        View childAt = getChildAt(0);
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.g = x;
                this.h = y;
                return true;
            case 1:
                this.g = -1.0f;
                this.h = -1.0f;
                return true;
            case 2:
                if (this.g < 0.0f) {
                    this.g = x;
                }
                if (this.h < 0.0f) {
                    this.h = y;
                }
                int i3 = (int) (this.g - x);
                int i4 = (int) (this.h - y);
                this.g = x;
                this.h = y;
                if (i3 <= 0) {
                    if (getScrollX() <= 0) {
                        i3 = 0;
                    }
                    i = Math.max(0 - getScrollX(), i3);
                } else {
                    int right = ((childAt.getRight() - getScrollX()) - getWidth()) - getPaddingRight();
                    i = right > 0 ? Math.min(right, i3) : 0;
                }
                if (i4 <= 0) {
                    if (getScrollY() <= 0) {
                        i4 = 0;
                    }
                    i2 = Math.max(0 - getScrollY(), i4);
                } else {
                    int bottom = ((childAt.getBottom() - getScrollY()) - getHeight()) - getPaddingBottom();
                    if (bottom > 0) {
                        i2 = Math.min(bottom, i4);
                    }
                }
                if (i == 0 && i2 == 0) {
                    return true;
                }
                scrollBy(i, i2);
                return true;
            default:
                return true;
        }
    }
}
