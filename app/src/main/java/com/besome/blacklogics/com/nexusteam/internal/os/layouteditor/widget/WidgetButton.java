package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import com.besome.blacklogics.R;
import com.besome.blacklogics.*;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetButton extends Widget {
    private TextView mTextView;
    private Paint mPaint;
    private Drawable originalBg;
    //private Paint selectionPaint;
    private boolean isSelected = false;
    private float lineSpacingMultiplier = 1.0f;
    private float lineSpacingExtra = 0.0f;

    // Standard constructors
    public WidgetButton(Context context) {
        this(context, null);
    }

    public WidgetButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Initialize TextView that will act as our button
        mTextView = new TextView(context);
        
        // Store original background
        Button defaultButton = new Button(context);
        originalBg = defaultButton.getBackground();
        
        // Apply default button styling
        mTextView.setBackground(originalBg);
        mTextView.setTextColor(defaultButton.getTextColors());
        mTextView.setElevation(4f);
        mTextView.setGravity(17); // CENTER
        mTextView.setTextSize(12);
        /**
        TUDO : SOME CLICK ERRORS SOLVE FOR ADD COMMENT MATHOD
        mTextView.setClickable(true);
        mTextView.setFocusable(true);
        */
        
        // Initialize paint for selection
        mPaint = new Paint();
        mPaint.setColor(0); // Transparent initially
        
        // Add to view hierarchy
        addView(mTextView, new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public TextView getTextView() {
        return mTextView;
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        if (mTextView != null) {
            mTextView.setLayoutParams(params);
        }
    }

    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("button" + i)) {
            i++;
        }
        return "button" + i;
    }

    @Override
    public void setBackgroundColor(int color) {
        if (color == 0) {
            mTextView.setBackground(originalBg);
        } else {
            mTextView.setBackgroundColor(color);
        }
    }

    // ========================
    // TextView/Button Methods Delegation
    // ========================
    
    public void setText(CharSequence text) {
        mTextView.setText(text);
    }

    public CharSequence getText() {
        return mTextView.getText();
    }

    public void setTextSize(float size) {
        mTextView.setTextSize(size);
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }
    
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

   /* public void setOnClickListener(View.OnClickListener listener) {
        mTextView.setOnClickListener(listener);
    }*/

    // ========================
    // Widget Methods
    // ========================
    
    /*
    ==========================
    TUDO : NOT NEED THIS ALL ONE
    ==========================
    **/
    
 /*   @Override
    public void select() {
        super.select();
        isSelected = true;
        mPaint.setColor(getResources().getColor(R.color.widget_selection_color));
        invalidate();
        // Additional button-specific selection handling if needed
    }

    @Override
    public void unselect() {
        super.unselect();
        isSelected = false;
        mPaint.setColor(0);
        invalidate();
        // Additional button-specific unselection handling if needed
    }
    */
}