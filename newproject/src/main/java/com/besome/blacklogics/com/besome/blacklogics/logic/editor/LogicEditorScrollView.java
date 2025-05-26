package com.besome.blacklogics.logic.editor;

import com.besome.blacklogics.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class LogicEditorScrollView extends FrameLayout {
    private boolean isDragged = false;
    private boolean isScrollEnabled = true;
    private float lastPosX = -1.0f;
    private float lastPosY = -1.0f;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;
    private int touchSlop = 0;

    public LogicEditorScrollView(Context context) {
        super(context);
        init(context);
    }

    public LogicEditorScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void addView(View view) {
        if (getChildCount() > 1) {
            throw new IllegalStateException("BothDirectionScrollView should have child View only one");
        }
        super.addView(view);
    }

    public boolean getScrollEnabled() {
        return this.isScrollEnabled;
    }

    protected boolean isPossibleScroll() {
        if (getChildCount() <= 0 || !this.isScrollEnabled) {
            return false;
        }
        View childAt = getChildAt(0);
        return getWidth() < (childAt.getWidth() + getPaddingLeft()) + getPaddingRight() || getHeight() < (childAt.getHeight() + getPaddingTop()) + getPaddingBottom();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!isPossibleScroll()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 2 && this.isDragged) {
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.offsetX = x;
                this.offsetY = y;
                this.isDragged = false;
                break;
            case 1:
                this.isDragged = false;
                break;
            case 2:
                action = (int) Math.abs(this.offsetY - y);
                if (((int) Math.abs(this.offsetX - x)) > this.touchSlop || action > this.touchSlop) {
                    this.isDragged = true;
                    break;
                }
        }
        return this.isDragged;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i = 0;
        if (!isPossibleScroll()) {
            return false;
        }
        View childAt = getChildAt(0);
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.lastPosX = x;
                this.lastPosY = y;
                break;
            case 1:
                this.lastPosX = -1.0f;
                this.lastPosY = -1.0f;
                break;
            case 2:
                if (this.lastPosX < 0.0f) {
                    this.lastPosX = x;
                }
                if (this.lastPosY < 0.0f) {
                    this.lastPosY = y;
                }
                action = (int) (this.lastPosX - x);
                int i2 = (int) (this.lastPosY - y);
                this.lastPosX = x;
                this.lastPosY = y;
                if (action <= 0) {
                    if (getScrollX() <= 0) {
                        action = 0;
                    }
                    action = Math.max(0 - getScrollX(), action);
                } else {
                    int right = ((childAt.getRight() - getScrollX()) - getWidth()) - getPaddingRight();
                    action = right > 0 ? Math.min(right, action) : 0;
                }
                if (i2 <= 0) {
                    if (getScrollY() > 0) {
                        i = i2;
                    }
                    i = Math.max(0 - getScrollY(), i);
                } else {
                    int bottom = ((childAt.getBottom() - getScrollY()) - getHeight()) - getPaddingBottom();
                    if (bottom > 0) {
                        i = Math.min(bottom, i2);
                    }
                }
                if (!(action == 0 && i == 0)) {
                    scrollBy(action, i);
                    break;
                }
        }
        return true;
    }

    public void setScrollEnabled(boolean z) {
        this.isScrollEnabled = z;
    }
}
