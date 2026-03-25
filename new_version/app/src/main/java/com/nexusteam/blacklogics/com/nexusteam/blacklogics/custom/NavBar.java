package com.nexusteam.blacklogics.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nexusteam.blacklogics.R;

public class NavBar extends LinearLayout {

    private int activeColor;
    private int inactiveColor;
    private OnTabSelectedListener listener;
    private int currentId = -1;

    public interface OnTabSelectedListener {
        void onTabSelected(int id);
    }

    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.BOTTOM);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavBarItem);
        activeColor = a.getColor(R.styleable.NavBarItem_active_color, Color.parseColor("#006080"));
        inactiveColor = a.getColor(R.styleable.NavBarItem_inactive_color, Color.LTGRAY);
        a.recycle();
    }

    public void addNavItem(int iconRes, final int id) {
        ImageView iv = new ImageView(getContext());

        // Material style layout
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        iv.setLayoutParams(params);
        iv.setImageResource(iconRes);
        iv.setPadding(24, 24, 24, 24);  // slightly smaller padding for Material feel
        iv.setId(id);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        // Material ripple
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            iv.setForeground(getResources().getDrawable(R.drawable.ripple_material, null));
        } else {
            iv.setBackgroundResource(R.drawable.ripple_material); // fallback for older devices
        }

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(id);
                if (listener != null) {
                    listener.onTabSelected(id);
                }
            }
        });

        addView(iv);
    }

    public void selectItem(int id) {
        if (currentId == id) return;
        currentId = id;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ImageView) {
                ImageView img = (ImageView) child;
                if (img.getId() == id) {
                    img.setBackgroundResource(R.drawable.nav_active_bg); // highlight background
                    img.setColorFilter(activeColor);
                } else {
                    img.setBackgroundColor(Color.TRANSPARENT);
                    img.setColorFilter(inactiveColor);
                }
            }
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.listener = listener;
    }
}