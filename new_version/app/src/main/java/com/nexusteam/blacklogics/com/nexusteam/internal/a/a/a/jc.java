package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class jc extends il {
    public jc(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_text_view);
        setWidgetName("TextView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 4;
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.text.text = getName();
        return viewBean;
    }
}
