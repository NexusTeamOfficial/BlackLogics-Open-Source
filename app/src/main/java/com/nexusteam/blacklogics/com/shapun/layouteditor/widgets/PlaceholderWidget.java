package com.shapun.layouteditor.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.nexusteam.blacklogics.SketchwareUtil;

public class PlaceholderWidget extends View {
    private final String placeholderText;
    private final Paint placeholderPaint;
    private Drawable placeholderDrawable;
    
    public PlaceholderWidget(Context context, String placeholderText) {
        super(context);
        this.placeholderText = placeholderText;
        

        placeholderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        placeholderPaint.setColor(Color.BLACK);
        placeholderPaint.setTextSize(SketchwareUtil.getDip(context, 14));
        placeholderPaint.setTextAlign(Paint.Align.CENTER);
    }
    
    public void setPlaceholderDrawable(Drawable drawable) {
        this.placeholderDrawable = drawable;
        if (drawable != null) {
            setForeground(drawable);
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(placeholderText, getWidth() / 2f, getHeight() / 2f, placeholderPaint);
    }
}