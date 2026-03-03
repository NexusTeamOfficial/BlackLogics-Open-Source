package com.nexusteam.blacklogics.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        iv.setLayoutParams(params);
        iv.setImageResource(iconRes);
        iv.setPadding(35, 35, 35, 35);
        iv.setId(id);

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
                    img.setBackgroundResource(R.drawable.nav_active_bg);
                    img.setColorFilter(Color.WHITE);
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
/*
using hahaha
NavBar nav = (NavBar) findViewById(R.id.customNav);
nav.addNavItem(R.drawable.ic_home, 101);
nav.addNavItem(R.drawable.ic_bookmark, 102);
nav.selectItem(101);

nav.setOnTabSelectedListener(new NavBar.OnTabSelectedListener() {
    @Override
    public void onTabSelected(int id) {

    }
});
*/