package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.VideoView;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import android.view.MotionEvent;
import android.view.View;

public class WidgetVideoView extends VideoView implements WidgetContract {
    private String mWidgetId;
    private String mWidgetName;
    private String mVideoPath;
    private boolean isSelected = false;
    private Paint widgetPaint = new Paint();
    private float dragStartX, dragStartY;

    public WidgetVideoView(Context context) {
        this(context, null);
    }

    public WidgetVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //@Override
    public void init() {
      //  super.init();
        setPadding(8, 8, 8, 8);
        setEnabled(false);
        setForeground(getResources().getDrawable(android.R.drawable.ic_media_play));
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
    
    public void setVideoPath() {
        this.mVideoPath = mVideoPath;
    }
    
    public String getVideoPath() {
        return mVideoPath;
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

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
        requestLayout();
    }

    @Override
    public String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("videoview" + i)) {
            i++;
        }
        return "videoview" + i;
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