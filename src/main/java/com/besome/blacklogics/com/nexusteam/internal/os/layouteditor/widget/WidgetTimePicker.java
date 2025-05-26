package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TimePicker;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetTimePicker extends TimePicker implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	
	public WidgetTimePicker(Context context) {
		this(context, null);
	}
	
	public WidgetTimePicker(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	//@Override
	public void init() {
		// super.init();
		setPadding(8, 8, 8, 8);
		// setEnabled(false);
		setIs24HourView(false);
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
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("timepicker" + i)) {
			i++;
		}
		return "timepicker" + i;
	}
}
