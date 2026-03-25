package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.nexusteam.internal.beans.ViewBean;

public class hy extends FrameLayout implements hq, hr {

    /* renamed from: a  reason: collision with root package name */
    public Paint f285a;
    private ViewBean b = null;
    private boolean c = false;
    private boolean d = false;
    private float e = -1.0f;
    private boolean f = true;
    private int g = 0;
    private final Rect h = new Rect();

    public hy(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) kp.a(context, 32.0f));
        setMinimumHeight((int) kp.a(context, 32.0f));
        this.f285a = new Paint(1);
        this.f285a.setStrokeWidth(kp.a(getContext(), 2.0f));
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
            int scrollX = getScrollX();
            int scrollX2 = getScrollX() + getMeasuredWidth();
            int scrollY = getScrollY();
            int scrollY2 = getScrollY() + getMeasuredHeight();
            if (this.c) {
                this.f285a.setColor(-1785080368);
                canvas.drawRect(new Rect(scrollX, scrollY, scrollX2, scrollY2), this.f285a);
            }
            this.f285a.setColor(-1428881408);
            float f2 = (float) scrollX;
            float f3 = (float) scrollY;
            float f4 = (float) scrollX2;
            Canvas canvas2 = canvas;
            float f5 = f2;
            float f6 = f3;
            canvas2.drawLine(f5, f6, f4, f3, this.f285a);
            float f7 = (float) scrollY2;
            float f8 = f7;
            canvas2.drawLine(f5, f6, f2, f8, this.f285a);
            float f9 = f7;
            canvas.drawLine(f4, f3, f4, f9, this.f285a);
            canvas.drawLine(f2, f9, f4, f8, this.f285a);
        }
        super.onDraw(canvas);
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
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

    public void removeView(View view) {
        super.removeView(view);
        setScrollX(0);
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

    public void setScrollEnabled(boolean z) {
        this.f = z;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i;
        if (!this.f || getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        switch (action) {
            case 0:
                this.e = x;
                break;
            case 1:
                this.e = -1.0f;
                break;
            case 2:
                if (this.e < 0.0f) {
                    this.e = x;
                }
                int i2 = (int) (this.e - x);
                this.e = x;
                if (i2 <= 0) {
                    if (getScrollX() <= 0) {
                        i2 = 0;
                    }
                    i = Math.max(0 - getScrollX(), i2);
                } else {
                    int right = ((childAt.getRight() - getScrollX()) - getWidth()) + getPaddingRight();
                    i = right > 0 ? Math.min(right, i2) : 0;
                }
                if (i != 0) {
                    scrollBy(i, 0);
                    break;
                }
                break;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void measureChild(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i) - (getPaddingLeft() + getPaddingRight())), 0), getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), layoutParams.height));
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i) - ((((getPaddingLeft() + getPaddingRight()) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + i2)), 0), getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
                i4 = getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin;
                i3 = getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                i4 = getPaddingLeft() + getPaddingRight();
                i3 = getPaddingTop() + getPaddingBottom();
            }
            int measuredWidth = getMeasuredWidth() - i4;
            if (childAt.getMeasuredWidth() < measuredWidth) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), getChildMeasureSpec(i2, i3, layoutParams.height));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus != null && this != findFocus && a(findFocus, getRight() - getLeft())) {
            findFocus.getDrawingRect(this.h);
            offsetDescendantRectToMyCoords(findFocus, this.h);
            a(a(this.h));
        }
    }

    private boolean a(View view, int i) {
        view.getDrawingRect(this.h);
        offsetDescendantRectToMyCoords(view, this.h);
        return this.h.right + i >= getScrollX() && this.h.left - i <= getScrollX() + getWidth();
    }

    private void a(int i) {
        if (i != 0) {
            scrollBy(i, 0);
        }
    }

    /* access modifiers changed from: protected */
    public int a(Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int width = getWidth();
        int scrollX = getScrollX();
        int i3 = scrollX + width;
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (rect.left > 0) {
            scrollX += horizontalFadingEdgeLength;
        }
        if (rect.right < getChildAt(0).getWidth()) {
            i3 -= horizontalFadingEdgeLength;
        }
        if (rect.right > i3 && rect.left > scrollX) {
            if (rect.width() > width) {
                i2 = (rect.left - scrollX) + 0;
            } else {
                i2 = (rect.right - i3) + 0;
            }
            return Math.min(i2, getChildAt(0).getRight() - i3);
        } else if (rect.left >= scrollX || rect.right >= i3) {
            return 0;
        } else {
            if (rect.width() > width) {
                i = 0 - (i3 - rect.right);
            } else {
                i = 0 - (scrollX - rect.left);
            }
            return Math.max(i, -getScrollX());
        }
    }
}
