package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class il extends jr {

    /* renamed from: a  reason: collision with root package name */
    protected String f298a;

    public ViewBean getBean() {
        return null;
    }

    public il(Context context) {
        super(context);
        setBackgroundResource(R.drawable.icon_bg);
    }

    public void setText(String str) {
        this.d.setTextSize(2, 11.0f);
        setWidgetName(str);
    }

    public void setName(String str) {
        this.f298a = str;
    }

    public String getName() {
        return this.f298a;
    }
}
