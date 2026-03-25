package com.nexusteam.internal;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;

public class fj extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    public int f140a;
    public LinearLayout b;
    public ImageView c;
    public TextView d;
    private Context e;

    public fj(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.e = context;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        setLayoutParams(layoutParams);
        kp.a(context, this, R.layout.fr_logic_list_item_button);
        this.b = (LinearLayout) findViewById(R.id.img_button);
        this.c = (ImageView) findViewById(R.id.icon);
        this.d = (TextView) findViewById(R.id.name);
    }
}
