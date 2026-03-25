package com.nexusteam.blacklogics.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.card.MaterialCardView;

public class CustomCardView extends MaterialCardView {
	
	public CustomCardView(Context context) {
		super(context);
		init();
	}
	
	public CustomCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		// default properties set kar rahe hain
		setRadius(dpToPx(16));
		setCardElevation(dpToPx(4));
		setUseCompatPadding(true);
		
		// background tint (theme se lega)
		setCardBackgroundColor(getContext().getTheme()
		.obtainStyledAttributes(new int[]{com.google.android.material.R.attr.colorSurface})
		.getColor(0, 0));
	}
	
	private float dpToPx(float dp) {
		return dp * getResources().getDisplayMetrics().density;
	}
}
