package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RatingBar;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetRatingView extends RatingBar implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	
	public WidgetRatingView(Context context) {
		this(context, null);
	}
	
	public WidgetRatingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	//@Override
	public void init() {
		//    super.init();
		setNumStars(5);
		setRating(0.0f);
		setStepSize(0.5f);
		setPadding(8, 8, 8, 8);
		//  setEnabled(false);
		setIsIndicator(true);
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
	public void setNumStars(int numStars) {
		super.setNumStars(numStars);
	}
	
	@Override
	public void setRating(float rating) {
		super.setRating(rating);
	}
	
	public void setTextSize(float size) {
		// RatingBar doesn't support text size directly; ignore or log
	}
	
	public float getTextSize() {
		return 0; // Not applicable
	}
	
	public void setTextColor(int color) {
		// RatingBar doesn't support text color; ignore or log
	}
	
	public int getCurrentTextColor() {
		return 0; // Not applicable
	}
	
	public Typeface getTypeface() {
		return null; // Not applicable
	}
	
	public void setTypeface(Typeface typeface) {
		// RatingBar doesn't support typeface; ignore
	}
	
	public void setTypeface(Typeface typeface, int style) {
		// RatingBar doesn't support typeface; ignore
	}
	
	public float getLineSpacingMultiplier() {
		return 1.0f; // Not applicable
	}
	
	public void setLineSpacingMultiplier(float multiplier) {
		// RatingBar doesn't support line spacing; ignore
	}
	
	public float getLineSpacingExtra() {
		return 0.0f; // Not applicable
	}
	
	public void setLineSpacingExtra(float spacing) {
		// RatingBar doesn't support line spacing; ignore
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("ratingview" + i)) {
			i++;
		}
		return "ratingview" + i;
	}
}
