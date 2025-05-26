package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetLinearLayout extends LinearLayout implements WidgetContract {
    private String mWidgetId;
    private String mWidgetName;
    private boolean isSelected = false;
    private Paint widgetPaint = new Paint();
    private Paint borderPaint;
    private float dragStartX, dragStartY;

    public WidgetLinearLayout(Context context) {
        this(context, null);
    }

    public WidgetLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

   // @Override
    public void init() {
       // super.init();
        setMinimumHeight(100);
        setMinimumWidth(100);
        setPadding(8, 8, 8, 8);
        borderPaint = new Paint();
        borderPaint.setColor(Color.LTGRAY);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2);
        setBackgroundColor(Color.TRANSPARENT);
        setOrientation(VERTICAL);
        //setOnTouchListener(new DragTouchListener());
    }

    @Override
    public void setWidgetId(String id) {
        this.mWidgetId = id;
    }

    @Override
    public String getWidgetId() {
        return mWidgetId;
    }

    @Override
    public void setWidgetName(String name) {
        this.mWidgetName = name;
    }

    @Override
    public String getWidgetName() {
        return mWidgetName;
    }

    @Override
    public Paint getWidgetPaint() {
        return widgetPaint;
    }

    @Override
    public void select() {
        isSelected = true;
        widgetPaint.setColor(getResources().getColor(R.color.widget_selection_color));
        invalidate();
    }

    @Override
    public void unselect() {
        isSelected = false;
        widgetPaint.setColor(0);
        invalidate();
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), borderPaint);
        if (isSelected) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
        }
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
        requestLayout();
    }

    public void adjustChildLayout(View child, int width, int height) {
        if (child != null) {
            ViewGroup.LayoutParams params = child.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(width, height);
            } else {
                params.width = width;
                params.height = height;
            }
            child.setLayoutParams(params);
            requestLayout();
        }
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
        requestLayout();
    }

    @Override
    public String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("linearlayout" + i)) {
            i++;
        }
        return "linearlayout" + i;
    }

    private class DragTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dragStartX = event.getRawX() - getX();
                    dragStartY = event.getRawY() - getY();
                    select();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    setPosition(event.getRawX() - dragStartX, event.getRawY() - dragStartY);
                    return true;
                case MotionEvent.ACTION_UP:
                    return true;
            }
            return false;
        }
    }
}