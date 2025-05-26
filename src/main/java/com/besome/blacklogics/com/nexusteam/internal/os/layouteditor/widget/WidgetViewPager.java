package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.viewpager.widget.ViewPager;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

import android.view.MotionEvent;
import android.view.View;

public class WidgetViewPager extends ViewPager implements WidgetContract {
    private String mWidgetId;
    private String mWidgetName;
    private boolean isSelected = false;
    private Paint widgetPaint = new Paint();
    private Paint placeholderPaint;
    private float dragStartX, dragStartY;

    public WidgetViewPager(Context context) {
        this(context, null);
    }

    public WidgetViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

   // @Override
    public void init() {
       // super.init();
        setPadding(8, 8, 8, 8);
        setEnabled(false);
        placeholderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        placeholderPaint.setColor(Color.GRAY);
        placeholderPaint.setTextSize(24);
        placeholderPaint.setTextAlign(Paint.Align.CENTER);
       // setOnTouchListener(new DragTouchListener());
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getAdapter() == null || getAdapter().getCount() == 0) {
            canvas.drawText("ViewPager", getWidth() / 2f, getHeight() / 2f, placeholderPaint);
        }
        if (isSelected) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
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
        while (WidgetUtil.isWidgetIdExist("viewpager" + i)) {
            i++;
        }
        return "viewpager" + i;
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