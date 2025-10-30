package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import com.besome.blacklogics.R;

public class PaletteSelectorItem extends RelativeLayout {
    private View bg;
    private int mColor;
    private int mId;
    private String mName;
    private TextView tvCategory;
    private int widthNonSelected = 0;

    public PaletteSelectorItem(Context context, int i, String str, int i2) {
        super(context);
        this.mId = i;
        this.mName = str;
        this.mColor = i2;
        init(context);
    }

    private void init(Context context) {
        LayoutUtil.inflate(context, this, R.layout.palette_selector_item);
        this.tvCategory = (TextView) findViewById(R.id.tv_category);
        this.bg = findViewById(R.id.bg);
        this.widthNonSelected = (int) LayoutUtil.getDip(context, 4.0f);
        this.tvCategory.setText(this.mName);
        this.bg.setBackgroundColor(this.mColor);
        setSelected(false);
    }

    public int getColor() {
        return this.mColor;
    }

    public int getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public void setSelected(boolean var1) {
        if(var1) {
            this.tvCategory.setTextColor(-1);
            ViewGroup.LayoutParams var3 = this.bg.getLayoutParams();
            var3.width = -1;
            this.bg.setLayoutParams(var3);
        } else {
            this.tvCategory.setTextColor(-11513776);
            ViewGroup
           .LayoutParams var2 = this.bg.getLayoutParams();
            var2.width = this.widthNonSelected;
            this.bg.setLayoutParams(var2);
        }
    }
}
