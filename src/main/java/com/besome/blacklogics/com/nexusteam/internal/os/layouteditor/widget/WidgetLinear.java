package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import android.view.MotionEvent;
import android.view.View;

public class WidgetLinear extends LinearLayout implements WidgetContract {
    private String mWidgetId;
    private String mWidgetName;
    private boolean isSelected = false;
    private Paint widgetPaint = new Paint();
    private Drawable originalBg;

    public WidgetLinear(Context context) {
        this(context, null);
    }

    public WidgetLinear(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetLinear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

   // @Override
    public void init() {
        //super.init();
        originalBg = getBackground();
        setOrientation(VERTICAL);
        setElevation(4f);
        setGravity(17); // CENTER
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
        if (isSelected) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        if (color == 0) {
            setBackground(originalBg != null ? originalBg : null);
        } else {
            super.setBackgroundColor(color);
        }
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        if (!isSelected()) {
            originalBg = background;
        }
    }

    public void setGravityCenter() {
        setGravity(17); // CENTER
    }

    @Override
    public String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("linear" + i)) {
            i++;
        }
        return "linear" + i;
    }
}