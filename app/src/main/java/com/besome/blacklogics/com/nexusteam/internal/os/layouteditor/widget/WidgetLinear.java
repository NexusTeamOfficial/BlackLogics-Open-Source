package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.besome.blacklogics.R;
import com.besome.blacklogics.*;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetLinear extends Widget {
    private LinearLayout mLinearLayout;
    private Drawable originalBg;
    private int orientation = LinearLayout.VERTICAL;

    // Standard constructors
    public WidgetLinear(Context context) {
        this(context, null);
    }

    public WidgetLinear(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetLinear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Create the internal LinearLayout
        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        
        // Store original background
        originalBg = mLinearLayout.getBackground();
        
        // Apply the orientation that was set before mLinearLayout was initialized
        mLinearLayout.setOrientation(orientation);
        mLinearLayout.setElevation(4f);
        mLinearLayout.setGravity(17); // CENTER
        
        // Add to view hierarchy
        addView(mLinearLayout);
    }

    @Override
    public void setOrientation(int orientation) {
        this.orientation = orientation;
        if (mLinearLayout != null) {
            mLinearLayout.setOrientation(orientation);
        }
    }
    // ========================
    // Widget Methods
    // ========================
    
    @Override
    public void select() {
        super.select();
        // Additional selection handling if needed
    }

    @Override
    public void unselect() {
        super.unselect();
        // Additional unselection handling if needed
    }

    public static String newWidgetId() {
        int i = 1;
        while (WidgetUtil.isWidgetIdExist("linearlayout" + i)) {
            i++;
        }
        return "linearlayout" + i;
    }

    // ========================
    // LinearLayout Methods Delegation
    // ========================
    
  /*  @Override
    public void setOrientation(int orientation) {
        this.orientation = orientation;
        mLinearLayout.setOrientation(orientation);
    }*/

    public int getOrientation() {
        return mLinearLayout.getOrientation();
    }

    @Override
    public void setGravity(int gravity) {
        mLinearLayout.setGravity(gravity);
    }

    public void setWeightSum(float weightSum) {
        mLinearLayout.setWeightSum(weightSum);
    }

    @Override
    public void addView(View child) {
        mLinearLayout.addView(child);
    }

    public void addView(View child, int index) {
        mLinearLayout.addView(child, index);
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        mLinearLayout.addView(child, params);
    }

    public void addView(View child, int width, int height) {
        mLinearLayout.addView(child, width, height);
    }

    public void addView(View child, float weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.weight = weight;
        mLinearLayout.addView(child, params);
    }

    // ========================
    // Background Methods
    // ========================
    
    @Override
    public void setBackgroundColor(int color) {
        if (color == 0) {
            mLinearLayout.setBackground(originalBg);
        } else {
            mLinearLayout.setBackgroundColor(color);
        }
    }

    @Override
    public void setBackground(Drawable background) {
        mLinearLayout.setBackground(background);
        if (!isSelected) {
            originalBg = background;
        }
    }

    // ========================
    // Layout Methods
    // ========================
    
    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        if (mLinearLayout != null) {
            mLinearLayout.setLayoutParams(params);
        }
    }

    // ========================
    // Helper Methods
    // ========================
    
    public LinearLayout getLinearLayout() {
        return mLinearLayout;
    }

    public void setGravityCenter() {
        mLinearLayout.setGravity(17); // CENTER
    }
}