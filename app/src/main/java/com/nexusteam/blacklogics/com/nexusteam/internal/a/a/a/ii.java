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

public class ii extends FrameLayout implements hq, hr {

    /* renamed from: a  reason: collision with root package name */
    public Paint f296a;
    private ViewBean b = null;
    private boolean c = false;
    private boolean d = false;
    private float e = -1.0f;
    private boolean f = true;
    private final Rect g = new Rect();

    public ii(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) kp.a(context, 32.0f));
        setMinimumHeight((int) kp.a(context, 32.0f));
        this.f296a = new Paint(1);
        this.f296a.setStrokeWidth(kp.a(getContext(), 2.0f));
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

    public void removeView(View view) {
        super.removeView(view);
        setScrollY(0);
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
                this.f296a.setColor(-1785080368);
                canvas.drawRect(new Rect(scrollX, scrollY, scrollX2, scrollY2), this.f296a);
            }
            this.f296a.setColor(-1428881408);
            float f2 = (float) scrollX;
            float f3 = (float) scrollY;
            float f4 = (float) scrollX2;
            Canvas canvas2 = canvas;
            float f5 = f2;
            float f6 = f3;
            canvas2.drawLine(f5, f6, f4, f3, this.f296a);
            float f7 = (float) scrollY2;
            float f8 = f7;
            canvas2.drawLine(f5, f6, f2, f8, this.f296a);
            float f9 = f7;
            canvas.drawLine(f4, f3, f4, f9, this.f296a);
            canvas.drawLine(f2, f9, f4, f8, this.f296a);
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
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.e = y;
                break;
            case 1:
                this.e = -1.0f;
                break;
            case 2:
                if (this.e < 0.0f) {
                    this.e = y;
                }
                int i2 = (int) (this.e - y);
                this.e = y;
                if (i2 <= 0) {
                    if (getScrollY() <= 0) {
                        i2 = 0;
                    }
                    i = Math.max(0 - getScrollY(), i2);
                } else {
                    int bottom = ((childAt.getBottom() - getScrollY()) - getHeight()) + getPaddingRight();
                    i = bottom > 0 ? Math.min(bottom, i2) : 0;
                }
                if (i != 0) {
                    scrollBy(0, i);
                    break;
                }
                break;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void measureChild(View view, int i, int i2) {
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i2) - (getPaddingTop() + getPaddingBottom())), 0));
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i3) - ((((getPaddingTop() + getPaddingBottom()) + marginLayoutParams.topMargin) + marginLayoutParams.bottomMargin) + i4)), 0));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int paddingLeft = getPaddingLeft() + getPaddingRight();
            int measuredHeight = getMeasuredHeight() - (getPaddingTop() + getPaddingBottom());
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(getChildMeasureSpec(i, paddingLeft, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus != null && this != findFocus && a(findFocus, 0, i4)) {
            findFocus.getDrawingRect(this.g);
            offsetDescendantRectToMyCoords(findFocus, this.g);
            a(a(this.g));
        }
    }

    private boolean a(View view, int i, int i2) {
        view.getDrawingRect(this.g);
        offsetDescendantRectToMyCoords(view, this.g);
        return this.g.bottom + i >= getScrollY() && this.g.top - i <= getScrollY() + i2;
    }

    private void a(int i) {
        if (i != 0) {
            scrollBy(0, i);
        }
    }

    /* access modifiers changed from: protected */
    public int a(Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i3 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i3 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i3 && rect.top > scrollY) {
            if (rect.height() > height) {
                i2 = (rect.top - scrollY) + 0;
            } else {
                i2 = (rect.bottom - i3) + 0;
            }
            return Math.min(i2, getChildAt(0).getBottom() - i3);
        } else if (rect.top >= scrollY || rect.bottom >= i3) {
            return 0;
        } else {
            if (rect.height() > height) {
                i = 0 - (i3 - rect.bottom);
            } else {
                i = 0 - (scrollY - rect.top);
            }
            return Math.max(i, -getScrollY());
        }
    }
}
