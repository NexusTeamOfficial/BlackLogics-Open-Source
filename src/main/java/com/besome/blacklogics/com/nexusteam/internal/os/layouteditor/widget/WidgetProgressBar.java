package com.nexusteam.internal.os.layouteditor.widget;

import android.graphics.Paint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetProgressBar extends ProgressBar implements WidgetContract {
	public enum ProgressType {
		HORIZONTAL,
		CIRCULAR
	}
	
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	private ProgressType progressType = ProgressType.HORIZONTAL;
	
	public WidgetProgressBar(Context context) {
		this(context, null);
	}
	
	public WidgetProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private int getStyleAttr(Context context) {
		return progressType == ProgressType.HORIZONTAL ?
		android.R.attr.progressBarStyleHorizontal : android.R.attr.progressBarStyleSmall;
	}
	
	private void init(Context context) {
		//setEnabled(false);
		setMax(100);
		setProgress(0);
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
	
	public void setProgressDisplay(int progress) {
		setProgress(Math.max(0, Math.min(progress, getMax())));
	}
	
	public int getProgressDisplay() {
		return getProgress();
	}
	
	public void setMaxProgress(int max) {
		setMax(Math.max(1, max));
	}
	
	public int getMaxProgress() {
		return getMax();
	}
	
	public void setProgressType(ProgressType type) {
		this.progressType = type;
		setIndeterminate(type == ProgressType.CIRCULAR);
		requestLayout();
	}
	
	public ProgressType getProgressType() {
		return progressType;
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("progressbar" + i)) {
			i++;
		}
		return "progressbar" + i;
	}
}
