package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nexusteam.blacklogics.R;
import com.besome.blacklogics.*;

public class Widget extends LinearLayout {
    private String mTexSize;
    private String mWidgetId;
    private String mWidgetName;
    private Paint widgetPaint = new Paint();
    public boolean isSelected = false;


    public Widget(Context context) {
        this(context, null);
    }


    public Widget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public Widget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.widgetPaint.setColor(0);
        setWillNotDraw(false); // Ensure onDraw gets called
        setOrientation(VERTICAL);
    }

    public void setTextSize(String str) {
        this.mTexSize = str;
    }

    public void setWidgetName(String str) {
        this.mWidgetName = str;
    }

    public String getWidgetName() {
        return this.mWidgetName;
    }

    public void setWidgetId(String str) {
        this.mWidgetId = str;
    }

    public String getWidgetId() {
        return this.mWidgetId;
    }

    public Paint getWidgetPaint() {
        return this.widgetPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
        }
    }

    public void select() {
        isSelected = true;
        widgetPaint.setColor(getResources().getColor(R.color.widget_selection_color));
        invalidate();
    }

    public void unselect() {
        isSelected = false;
        widgetPaint.setColor(0);
        invalidate();
    }

    public View getWidget() {
        if (getChildCount() > 0) {
            return getChildAt(0);
        }
        return this;
    }

    @Override
    public void requestLayout() {
        View widget = getWidget();
        if (widget != null && widget.getLayoutParams() != null) {
            ViewGroup.LayoutParams params = getLayoutParams();
            if (params == null) {
                params = new LayoutParams(
                    widget.getLayoutParams().width,
                    widget.getLayoutParams().height
                );
                setLayoutParams(params);
            } else {
                params.width = widget.getLayoutParams().width;
                params.height = widget.getLayoutParams().height;
            }

            if (getParent() instanceof LinearLayout) {
                int orientation = ((LinearLayout) getParent()).getOrientation();
                if (orientation == LinearLayout.HORIZONTAL && params.width == -2) {
                    params.width = -2;
                }
                if (orientation == LinearLayout.VERTICAL && params.height == -2) {
                    params.height = -2;
                }
            }
        }
        super.requestLayout();
    }
}