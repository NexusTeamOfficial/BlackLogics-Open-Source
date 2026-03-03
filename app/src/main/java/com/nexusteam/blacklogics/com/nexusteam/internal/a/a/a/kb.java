package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class kb extends RelativeLayout {

    /* renamed from: a  reason: collision with root package name */
    private int f323a = -1;
    private TextView b;

    public kb(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.program_info_item);
        this.b = (TextView) findViewById(R.id.tv_name);
        setClickable(true);
    }

    public void setKey(int i) {
        this.f323a = i;
    }

    public void setName(String str) {
        this.b.setText(str);
    }

    public int getKey() {
        return this.f323a;
    }
}
