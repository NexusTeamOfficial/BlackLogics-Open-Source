package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexusteam.blacklogics.R;

public class er extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    public ImageView f123a;
    public TextView b;
    public ImageView c;
    Context d;
    boolean e;

    public void setTextSize(int i) {
    }

    public er(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.d = context;
        kp.a(context, this, R.layout.var_id_spinner_item);
        this.f123a = (ImageView) findViewById(R.id.icon);
        this.b = (TextView) findViewById(R.id.name);
        this.c = (ImageView) findViewById(R.id.imgv_selected);
    }

    public void setDropDown(boolean z) {
        this.e = z;
    }

    public void a(int i, String str, boolean z) {
        if (z) {
            this.c.setVisibility(0);
        } else {
            this.c.setVisibility(8);
        }
        if (str.charAt(0) == '_') {
            this.b.setText(str.substring(1));
            a(false, -43691, -460768);
        } else {
            this.b.setText(str);
            a(true, -9079435, -1);
        }
        this.f123a.setImageResource(i);
    }

    public void a(boolean z, int i, int i2) {
        if (z) {
            TextView textView = this.b;
            if (!this.e) {
                i = i2;
            }
            textView.setTextColor(i);
            this.b.setTypeface((Typeface) null, 0);
            return;
        }
        TextView textView2 = this.b;
        if (!this.e) {
            i = i2;
        }
        textView2.setTextColor(i);
        this.b.setTypeface((Typeface) null, 3);
    }
}
