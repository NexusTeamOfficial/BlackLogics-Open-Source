package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetTextView extends TextView implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	private CharSequence originalText;
	private float lineSpacingMultiplier = 1.0f;
	private float lineSpacingExtra = 0.0f;
	
	public WidgetTextView(Context context) {
		this(context, null);
	}
	
	public WidgetTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	// @Override
	public void init() {
		//super.init();
		setTextSize(10);
		setPadding(8, 8, 8, 8);
		setSingleLine(false);
		setEllipsize(TextUtils.TruncateAt.END);
		// setEnabled(false);
		originalText = getText();
	}
	@Override
	public boolean performClick() {
		// Do nothing, just return true to consume the click
		return true;
	}
	
	public TextView getTextView() {
		return this;
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
		if (isSelected) {
			canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
		}
	}
	
	public void setLineSpacingMultiplier(float multiplier) {
		this.lineSpacingMultiplier = multiplier;
		setLineSpacing(lineSpacingExtra, lineSpacingMultiplier);
	}
	
	public float getLineSpacingMultiplier() {
		return lineSpacingMultiplier;
	}
	
	public void setLineSpacingExtra(float spacing) {
		this.lineSpacingExtra = spacing;
		setLineSpacing(lineSpacingExtra, lineSpacingMultiplier);
	}
	
	public float getLineSpacingExtra() {
		return lineSpacingExtra;
	}
	
	public void setLines(int line) {
		setMaxLines(line);
	}
	
	public int getLines() {
		return getMaxLines();
	}
	
	public void resetText() {
		setText(originalText);
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("textview" + i)) {
			i++;
		}
		return "textview" + i;
	}
}
