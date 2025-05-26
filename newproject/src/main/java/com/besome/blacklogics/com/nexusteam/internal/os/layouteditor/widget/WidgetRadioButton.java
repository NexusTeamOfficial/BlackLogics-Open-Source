package com.nexusteam.internal.os.layouteditor.widget;

import android.graphics.Paint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetRadioButton extends RadioButton implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	
	public WidgetRadioButton(Context context) {
		this(context, null);
	}
	
	public WidgetRadioButton(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.radioButtonStyle);
	}
	
	public WidgetRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		// setEnabled(false);
		setText("RadioButton");
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
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("radiobutton" + i)) {
			i++;
		}
		return "radiobutton" + i;
	}
}
