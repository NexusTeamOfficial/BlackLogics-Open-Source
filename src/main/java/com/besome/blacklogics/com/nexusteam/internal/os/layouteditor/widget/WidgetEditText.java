package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.Paint;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetEditText extends EditText implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	private float lineSpacingMultiplier = 1.0f;
	private float lineSpacingExtra = 0.0f;
	private int inputType = InputType.TYPE_CLASS_TEXT;
	
	public WidgetEditText(Context context) {
		this(context, null);
	}
	
	public WidgetEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}
	
	public WidgetEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		//setEnabled(false);
		setKeyListener(null);
		setHint("EditText");
		setTextSize(12);
		setBackgroundResource(android.R.drawable.edit_text);
		setElevation(2f);
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
	
	public EditText getEditText() {
		return this;
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
	
	public void setInputType(int type) {
		this.inputType = type;
	}
	
	public int getInputType() {
		return inputType;
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("edittext" + i)) {
			i++;
		}
		return "edittext" + i;
	}
}
