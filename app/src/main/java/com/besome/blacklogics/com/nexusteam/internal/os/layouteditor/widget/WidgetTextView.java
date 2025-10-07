package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import java.util.Arrays;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.besome.blacklogics.R;
import com.besome.blacklogics.*;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetTextView extends Widget {
	private TextView mTextView;
	private Paint selectionPaint;
	private boolean isSelected = false;
	private CharSequence originalText;
	private float lineSpacingMultiplier = 1.0f;
	private float lineSpacingExtra = 0.0f;
	
	// Standard constructors
	public WidgetTextView(Context context) {
		this(context, null);
	}
	
	public WidgetTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}
	
	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mTextView = new TextView(context, attrs, defStyleAttr);
		mTextView.setLayoutParams(new ViewGroup.LayoutParams(
		ViewGroup.LayoutParams.WRAP_CONTENT,
		ViewGroup.LayoutParams.WRAP_CONTENT));
		
		mTextView.setTextSize(10);
		mTextView.setPadding(8, 8, 8, 8);
		mTextView.setSingleLine(false);
		mTextView.setEllipsize(TextUtils.TruncateAt.END);
		
		originalText = mTextView.getText();
		selectionPaint = new Paint();
		selectionPaint.setColor(0);
		
		addView(mTextView);
	}
	
	// Add new methods for missing properties
	public float getTextSize() {
		return mTextView.getTextSize();
	}
	
	public int getCurrentTextColor() {
		return mTextView.getCurrentTextColor();
	}
	
	public Typeface getTypeface() {
		return mTextView.getTypeface();
	}
	
	public void setTypeface(Typeface typeface) {
		mTextView.setTypeface(typeface);
	}
	
	public void setTypeface(Typeface typeface, int style) {
		mTextView.setTypeface(typeface, style);
	}
	
	public float getLineSpacingMultiplier() {
		return lineSpacingMultiplier;
	}
	
	public void setLineSpacingMultiplier(float multiplier) {
		this.lineSpacingMultiplier = multiplier;
		mTextView.setLineSpacing(lineSpacingExtra, lineSpacingMultiplier);
	}
	
	public float getLineSpacingExtra() {
		return lineSpacingExtra;
	}
	
	public void setLineSpacingExtra(float spacing) {
		this.lineSpacingExtra = spacing;
		mTextView.setLineSpacing(lineSpacingExtra, lineSpacingMultiplier);
	}
	
	public void setLines(int line) {
		mTextView.setLines(line);
	}
	
	public String getLines() {
		// Get the text from the TextView
		String text = mTextView.getText().toString();
		
		// Split the text into lines based on line breaks
		String[] lines = text.split("\n");
		
		// Now, you can handle the lines as an array or join them into a single string
		return Arrays.toString(lines);
		
	}
	
	// ========================
	// Widget Methods
	// ========================
	
	@Override
	public void select() {
		isSelected = true;
		selectionPaint.setColor(getResources().getColor(R.color.widget_selection_color));
		invalidate();
	}
	
	@Override
	public void unselect() {
		isSelected = false;
		selectionPaint.setColor(0);
		invalidate();
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (isSelected) {
			canvas.drawRect(0, 0, getWidth(), getHeight(), selectionPaint);
		}
	}
	
	public static String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("textview" + i)) {
			i++;
		}
		return "textview" + i;
	}
	
	// ========================
	// TextView Methods Delegation
	// ========================
	
	// Text methods
	public void setText(CharSequence text) {
		mTextView.setText(text);
		if (originalText == null) {
			originalText = text;
		}
	}
	
	public CharSequence getText() {
		return mTextView.getText();
	}
	
	public void setTextSize(float size) {
		mTextView.setTextSize(size);
	}
	
	public void setTextSize(int type, float size) {
		mTextView.setTextSize(type, size);
	}
	
	public void setTextColor(int color) {
		mTextView.setTextColor(color);
	}
	
	// Appearance methods
	public void setAllCaps(boolean allCaps) {
		mTextView.setAllCaps(allCaps);
	}
	
	public void setSingleLine(boolean singleLine) {
		mTextView.setSingleLine(singleLine);
	}
	
	public void setMaxLines(int maxLines) {
		mTextView.setMaxLines(maxLines);
	}
	
	public void setEllipsize(TextUtils.TruncateAt where) {
		mTextView.setEllipsize(where);
	}
	
	// Padding methods
	public void setPadding(int left, int top, int right, int bottom) {
		mTextView.setPadding(left, top, right, bottom);
	}
	
	// Gravity methods
	public void setGravity(int gravity) {
		mTextView.setGravity(gravity);
	}
	
	// Layout methods
	@Override
	public void setLayoutParams(ViewGroup.LayoutParams params) {
		super.setLayoutParams(params);
		if (mTextView != null) {
			mTextView.setLayoutParams(params);
		}
	}
	
	// ========================
	// View Methods Overrides
	// ========================
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		mTextView.setEnabled(enabled);
	}
	
	/*   @Override
	public void setOnClickListener(View.OnClickListener l) {
	mTextView.setOnClickListener(l);
	}
	
	@Override
	public void setOnLongClickListener(View.OnLongClickListener l) {
	mTextView.setOnLongClickListener(l);
	}*/
	
	// ========================
	// Custom Methods
	// ========================
	
	public void resetText() {
		mTextView.setText(originalText);
	}
	
	public TextView getTextView() {
		return mTextView;
	}
	
	public void setTextAppearance(int resId) {
		mTextView.setTextAppearance(getContext(), resId);
	}
}
