package com.besome.blacklogics.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class CustomViewPager extends FrameLayout {

    private float startX;
    private OnSwipeListener onSwipeListener;

    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                return true;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float deltaX = endX - startX;

                if (Math.abs(deltaX) > 150) {
                    if (deltaX < 0 && onSwipeListener != null) {
                        onSwipeListener.onSwipeLeft();
                    } else if (deltaX > 0 && onSwipeListener != null) {
                        onSwipeListener.onSwipeRight();
                    }
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void setOnSwipeListener(OnSwipeListener listener) {
        this.onSwipeListener = listener;
    }

    public interface OnSwipeListener {
        void onSwipeLeft();
        void onSwipeRight();
    }
}
