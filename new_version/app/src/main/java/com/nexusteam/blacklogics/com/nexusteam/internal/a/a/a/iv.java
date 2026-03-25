package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class iv extends il {
    public iv(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_google_map);
        setWidgetName("MapView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 18;
        viewBean.layout.paddingLeft = 0;
        viewBean.layout.paddingTop = 0;
        viewBean.layout.paddingRight = 0;
        viewBean.layout.paddingBottom = 0;
        viewBean.layout.width = -1;
        viewBean.layout.height = -1;
        return viewBean;
    }
}
