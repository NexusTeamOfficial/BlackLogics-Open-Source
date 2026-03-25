package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class iz extends il {
    public iz(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_seek_bar);
        setWidgetName("SeekBar");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 14;
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.layout.width = -1;
        viewBean.text.text = getName();
        return viewBean;
    }
}
