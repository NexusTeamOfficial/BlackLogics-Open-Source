package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.besome.blacklogics.R;

public abstract class Widget extends View implements WidgetContract {
    private String mWidgetId;
    private String mWidgetName;
    protected Paint widgetPaint = new Paint();
    protected boolean isSelected = false;

    public Widget(Context context) {
        this(context, null);
    }

    public Widget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Widget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        widgetPaint.setColor(0);
        setWillNotDraw(false);
    }

    @Override
    public void setWidgetName(String str) {
        this.mWidgetName = str;
    }

    @Override
    public String getWidgetName() {
        return this.mWidgetName;
    }

    @Override
    public void setWidgetId(String str) {
        this.mWidgetId = str;
    }

    @Override
    public String getWidgetId() {
        return mWidgetId;
    }

    @Override
    public Paint getWidgetPaint() {
        return this.widgetPaint;
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
        }
    }
}
