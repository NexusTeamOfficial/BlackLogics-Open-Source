package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Switch;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetSwitch extends Switch implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	
	public WidgetSwitch(Context context) {
		this(context, null);
	}
	
	public WidgetSwitch(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	//@Override
	public void init() {
		//super.init();
		setText("Switch");
		setTextSize(12);
		setPadding(8, 8, 8, 8);
		// setEnabled(false);
	}
	
	@Override
	public boolean performClick() {
		// Do nothing, just return true to consume the click
		return true;
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
	
	public void setCheckedDisplay(boolean checked) {
		setChecked(checked);
	}
	
	public boolean isCheckedDisplay() {
		return isChecked();
	}
	
	public Switch getSwitchDisplay() {
		return this;
	}
	
	public float getLineSpacingMultiplier() {
		return 1.0f; // Not applicable
	}
	
	public void setLineSpacingMultiplier(float multiplier) {
		// Switch doesn't support line spacing; ignore
	}
	
	public float getLineSpacingExtra() {
		return 0.0f; // Not applicable
	}
	
	public void setLineSpacingExtra(float spacing) {
		// Switch doesn't support line spacing; ignore
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("switch" + i)) {
			i++;
		}
		return "switch" + i;
	}
}
