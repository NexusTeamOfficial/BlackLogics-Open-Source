package com.shapun.layouteditor.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.webkit.WebView;

public class PlaceholderWebView extends WebView {

    private Paint textPaint;
    private boolean showPlaceholder = true;

    public PlaceholderWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(dp(14));
        textPaint.setTextAlign(Paint.Align.CENTER);


        setFocusable(false);
        setClickable(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (showPlaceholder) {
            canvas.drawColor(Color.parseColor("#EEEEEE"));

            Paint.FontMetrics fm = textPaint.getFontMetrics();
            float x = getWidth() / 2f;
            float y = (getHeight() / 2f) - (fm.ascent + fm.descent) / 2;

            canvas.drawText("WebView", x, y, textPaint);
        }
    }


    public void loadPreviewUrl(String url) {
        showPlaceholder = false;
        setFocusable(true);
        setClickable(true);
        loadUrl(url);
        invalidate();
    }

    private float dp(int value) {
        return value * getResources().getDisplayMetrics().density;
    }
}
