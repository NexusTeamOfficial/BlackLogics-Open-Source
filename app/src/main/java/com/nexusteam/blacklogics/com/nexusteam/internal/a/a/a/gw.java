package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;

public class gw extends RelativeLayout {

    /* renamed from: a  reason: collision with root package name */
    private ImageView f243a;
    private TextView b;

    public gw(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.property_subheader);
        this.b = (TextView) findViewById(R.id.tv_name);
        this.f243a = (ImageView) findViewById(R.id.img_add);
    }

    public void setHeaderName(String str) {
        this.b.setText(str);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.f243a.setVisibility(0);
        this.f243a.setOnClickListener(onClickListener);
    }
}
