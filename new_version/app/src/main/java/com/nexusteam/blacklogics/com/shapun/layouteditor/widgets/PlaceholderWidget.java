package com.shapun.layouteditor.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.nexusteam.blacklogics.SketchwareUtil;

public class PlaceholderWidget extends View {
    private final String displayText;    // What to show on screen
    private final String widgetTag;      // Real Android tag for XML (e.g., "VideoView")
    private final Paint placeholderPaint;
    private Drawable placeholderDrawable;
    
    public PlaceholderWidget(Context context, String widgetTag) {
        super(context);
        this.widgetTag = widgetTag;
        this.displayText = widgetTag;    // Can customize this if needed
        
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
    
    // Returns the REAL Android widget tag for XML
    public String getWidgetTag() {
        return widgetTag;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the display text on screen
        canvas.drawText(displayText, getWidth() / 2f, getHeight() / 2f, placeholderPaint);
    }
}