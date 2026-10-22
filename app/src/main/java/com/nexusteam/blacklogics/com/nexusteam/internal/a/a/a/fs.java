package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.color.MaterialColors;

public class fs extends RelativeLayout {

    private int f148a;
    private String b;
    private int c;
    private TextView d;
    private View e;
    private int f = 0;

    public int getId() {
        return this.f148a;
    }

    public String getName() {
        return this.b;
    }

    public int getColor() {
        return this.c;
    }

    public fs(Context context, int i, String str, int i2) {
        super(context);
        this.f148a = i;
        this.b = str;
        this.c = i2;
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.palette_selector_item);
        this.d = (TextView) findViewById(R.id.tv_category);
        this.e = findViewById(R.id.bg);
        this.f = (int) kp.a(context, 4.0f);
        this.d.setText(this.b);
        this.e.setBackgroundColor(this.c);
        setSelected(false);
    }

    public void setSelected(boolean z) {
        if (z) {
            this.d.setTextColor(0xFFFFFFFF);
            ViewGroup.LayoutParams layoutParams = this.e.getLayoutParams();
            layoutParams.width = -1;
            this.e.setLayoutParams(layoutParams);
            return;
        }
        this.d.setTextColor(MaterialColors.getColor(this.d, com.google.android.material.R.attr.colorOnSurfaceVariant));
        ViewGroup.LayoutParams layoutParams2 = this.e.getLayoutParams();
        layoutParams2.width = this.f;
        this.e.setLayoutParams(layoutParams2);
    }
}
