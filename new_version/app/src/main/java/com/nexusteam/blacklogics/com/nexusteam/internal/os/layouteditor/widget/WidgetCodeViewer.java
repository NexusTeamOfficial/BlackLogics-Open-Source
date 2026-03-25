package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetCodeViewer extends Widget {
    private Paint textPaint;
    private Paint backgroundPaint;
    
    public WidgetCodeViewer(Context context) {
        super(context);
        init();
    }
    
    public WidgetCodeViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public WidgetCodeViewer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(40);
        textPaint.setTypeface(android.graphics.Typeface.MONOSPACE);
        
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.DKGRAY);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        
        int x = 30;
        int y = 80;
        
        drawCodeLine(canvas, "class MyWidget {", x, y, Color.CYAN);
        y += 50;
        drawCodeLine(canvas, "    void render() {", x + 30, y, Color.GREEN);
        y += 50;
        drawCodeLine(canvas, "        draw(text: \"Hello World\");", x + 60, y, Color.YELLOW);
        y += 50;
        drawCodeLine(canvas, "    }", x + 30, y, Color.GREEN);
        y += 50;
        drawCodeLine(canvas, "}", x, y, Color.CYAN);
    }
    
    private void drawCodeLine(Canvas canvas, String text, int x, int y, int color) {
        textPaint.setColor(color);
        canvas.drawText(text, x, y, textPaint);
    }
    
    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("codeviewer" + i)) {
            i++;
        }
        return "codeviewer" + i;
    }
    
  /*  @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        if (backgroundPaint != null) {
            backgroundPaint.setLayoutParams(params);
        }
    }*/
}
