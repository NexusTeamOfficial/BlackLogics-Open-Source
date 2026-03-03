package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class ja extends il {
    public ja(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_spinner);
        setWidgetName("Spinner");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.type = 10;
        viewBean.layout.width = -1;
        viewBean.layout.height = -2;
        return viewBean;
    }
}
