package com.nexusteam.internal;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;

public class fi extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    public LinearLayout f139a;
    public LinearLayout b;
    public ImageView c;
    public ImageView d;
    public TextView e;

    public fi(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.fr_logic_list_item_component_event);
        this.f139a = (LinearLayout) findViewById(R.id.container);
        this.d = (ImageView) findViewById(R.id.add_event);
        this.b = (LinearLayout) findViewById(R.id.icon_bg);
        this.c = (ImageView) findViewById(R.id.icon);
        this.e = (TextView) findViewById(R.id.name);
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        this.f139a.setOnClickListener(onClickListener);
    }

    public void a() {
        this.d.setVisibility(0);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        this.c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public void b() {
        this.d.setVisibility(8);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(1.0f);
        this.c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }
}
