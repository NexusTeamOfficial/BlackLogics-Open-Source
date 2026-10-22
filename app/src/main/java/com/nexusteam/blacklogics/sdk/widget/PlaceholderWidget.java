package com.nexusteam.blacklogics.sdk.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class PlaceholderWidget extends View {

	private String displayText;
	private String widgetTag;
	private final Paint textPaint;
	private final Paint selectionPaint;
	private Drawable placeholderDrawable;
	private boolean selected;

	private static final int PLACEHOLDER_BG = 0xFFE8EAF6;
	private static final int SELECTION_COLOR = 0x552196F3;

	public PlaceholderWidget(Context context) {
		this(context, "View");
	}

	public PlaceholderWidget(Context context, String widgetTag) {
		super(context);
		this.widgetTag = widgetTag != null ? widgetTag : "View";
		this.displayText = simpleName(this.widgetTag);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(0xFF3949AB);
		textPaint.setTextAlign(Paint.Align.CENTER);
		selectionPaint = new Paint();
		selectionPaint.setColor(0);
		setWillNotDraw(false);
		setMinimumWidth(dp(context, 72));
		setMinimumHeight(dp(context, 40));
	}

	public PlaceholderWidget(Context context, AttributeSet attrs) {
		this(context, "View");
	}

	private static String simpleName(String tag) {
		if (tag == null) return "View";
		int i = tag.lastIndexOf('.');
		return i >= 0 ? tag.substring(i + 1) : tag;
	}

	private static int dp(Context c, int v) {
		return (int) (v * c.getResources().getDisplayMetrics().density + 0.5f);
	}

	public void setWidgetTag(String tag) {
		this.widgetTag = tag != null ? tag : "View";
		this.displayText = simpleName(this.widgetTag);
		invalidate();
	}

	public String getWidgetTag() {
		return widgetTag;
	}

	public void setDisplayText(String text) {
		this.displayText = text;
		invalidate();
	}

	public void setPlaceholderDrawable(Drawable drawable) {
		this.placeholderDrawable = drawable;
		invalidate();
	}

	public void select() {
		selected = true;
		selectionPaint.setColor(SELECTION_COLOR);
		invalidate();
	}

	public void unselect() {
		selected = false;
		selectionPaint.setColor(0);
		invalidate();
	}

	public boolean isWidgetSelected() {
		return selected;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(PLACEHOLDER_BG);
		if (placeholderDrawable != null) {
			placeholderDrawable.setBounds(0, 0, getWidth(), getHeight());
			placeholderDrawable.draw(canvas);
		}
		textPaint.setTextSize(dp(getContext(), 12));
		Paint.FontMetrics fm = textPaint.getFontMetrics();
		float x = getWidth() / 2f;
		float y = (getHeight() / 2f) - (fm.ascent + fm.descent) / 2f;
		canvas.drawText(displayText != null ? displayText : "View", x, y, textPaint);
		if (selected) {
			canvas.drawRect(0, 0, getWidth(), getHeight(), selectionPaint);
		}
	}
}
