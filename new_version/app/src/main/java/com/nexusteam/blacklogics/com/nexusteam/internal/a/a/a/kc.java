package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class kc extends RelativeLayout {

    /* renamed from: a  reason: collision with root package name */
    private int f324a = -1;
    private TextView b;
    private TextView c;

    public kc(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.program_info_two_line_item);
        this.b = (TextView) findViewById(R.id.tv_name);
        this.c = (TextView) findViewById(R.id.tv_desc);
    }

    public void setKey(int i) {
        this.f324a = i;
    }

    public void setName(String str) {
        this.b.setText(str);
    }

    public void setDesc(String str) {
        this.c.setText(str);
    }

    public int getKey() {
        return this.f324a;
    }
}
