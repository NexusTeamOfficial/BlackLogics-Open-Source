package com.shapun.layouteditor.utils;

import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.GradientDrawable;
import android.content.res.ColorStateList;
import android.view.View;

public class UiUtils {
    public static void addRipple(View view){
		addRipple(view,0xFFE0E0E0);
	}
	
	public static void addRipple(View view,int color){
		
		ColorStateList clrb = new ColorStateList(new int[][]{new int[]{}}, new int[]{color});
		
		RippleDrawable ripdrb = new RippleDrawable(clrb , null, null);
		view.setBackground(ripdrb);
	}  
	public static GradientDrawable createStrokedBackground(int bgColor,int strokeColor , int borderPx){
		GradientDrawable SketchUi = new GradientDrawable();
		SketchUi.setColor(bgColor);
		SketchUi.setStroke(borderPx,strokeColor);
		return SketchUi;
	}
  
}