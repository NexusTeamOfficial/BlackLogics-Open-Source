package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;

public class jr extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    private int f308a;
    private int b;
    protected ImageView c;
    protected TextView d;

    public enum a {
        eLayout,
        eWidget
    }

    public jr(Context context) {
        super(context);
        a(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        kp.a(context, this, R.layout.widget_layout);
        this.c = (ImageView) findViewById(R.id.img_widget);
        this.d = (TextView) findViewById(R.id.tv_widget);
        this.d.setTextSize(12.0f);
        this.d.setGravity(3);
        this.d.setPadding((int) kp.a(getContext(), 2.0f), 0, 0, 0);
        setDrawingCacheEnabled(true);
    }

    public void setWidgetImage(int i) {
        this.f308a = i;
        this.c.setImageResource(i);
    }

    public int getWidgetImageResId() {
        return this.f308a;
    }

    public void setWidgetName(String str) {
        this.d.setText(str);
    }

    public String getWidgetName() {
        return this.d.getText().toString();
    }

    /* access modifiers changed from: protected */
    public void setWidgetType(a aVar) {
        setBackgroundResource(R.drawable.icon_bg);
        this.b = aVar.ordinal();
    }

    public int getWidgetType() {
        return this.b;
    }
}
