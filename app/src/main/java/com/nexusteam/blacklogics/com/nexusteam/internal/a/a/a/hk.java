package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;

class hk extends RelativeLayout {

    /* renamed from: a  reason: collision with root package name */
    Context f265a;
    TextView b;
    ImageView c;

    public hk(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.f265a = context;
        kp.a(context, this, R.layout.color_picker_grid_item);
        this.b = (TextView) findViewById(R.id.tv_color_name);
        this.c = (ImageView) findViewById(R.id.img_selector);
        setPadding(0, 0, 4, 0);
    }
}
