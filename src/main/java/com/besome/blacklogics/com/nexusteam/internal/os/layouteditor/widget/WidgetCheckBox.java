package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetCheckBox extends CheckBox implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	
	public WidgetCheckBox(Context context) {
		this(context, null);
	}
	
	public WidgetCheckBox(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		//  setEnabled(false);
		setText("CheckBox");
		setTextSize(12);
		widgetPaint.setColor(0);
		setWillNotDraw(false);
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
	protected void onDraw(android.graphics.Canvas canvas) {
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
	
	public CheckBox getCheckBoxDisplay() {
		return this;
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("checkbox" + i)) {
			i++;
		}
		return "checkbox" + i;
	}
}
