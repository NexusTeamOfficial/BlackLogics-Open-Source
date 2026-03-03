package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;

public class ik extends il {
    public ik(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_admob);
        setWidgetName("AdView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 17;
        viewBean.layout.paddingLeft = 0;
        viewBean.layout.paddingTop = 0;
        viewBean.layout.paddingRight = 0;
        viewBean.layout.paddingBottom = 0;
        viewBean.layout.width = -1;
        viewBean.layout.height = -2;
        viewBean.adSize = "SMART_BANNER";
        viewBean.adUnitId = "debug : ca-app-pub-3940256099942544/6300978111";
        return viewBean;
    }
}
