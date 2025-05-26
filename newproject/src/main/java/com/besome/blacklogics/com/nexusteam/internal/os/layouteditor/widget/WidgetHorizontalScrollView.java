package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetHorizontalScrollView extends HorizontalScrollView implements WidgetContract {
    private String mWidgetId;
    private String mWidgetName;
    private boolean isSelected = false;
    private Paint widgetPaint = new Paint();
    private Paint borderPaint;

    public WidgetHorizontalScrollView(Context context) {
        this(context, null);
    }

    public WidgetHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //@Override
    public void init() {
       // super.init();
        setMinimumHeight(100);
        setMinimumWidth(100);
        setPadding(8, 8, 8, 8);
        borderPaint = new Paint();
        borderPaint.setColor(Color.RED);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2);
        setBackgroundColor(Color.TRANSPARENT);
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
    public void addView(View child) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(child);
        requestLayout();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(child, params);
        requestLayout();
    }

    public void adjustChildLayout(View child, int width, int height) {
        if (child != null && child.getParent() == this) {
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

    @Override
    public String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("horizontalscrollview" + i)) {
            i++;
        }
        return "horizontalscrollview" + i;
    }
}