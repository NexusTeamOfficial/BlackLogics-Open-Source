package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class ir extends il {
    String b = "";

    public ir(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_image_view);
        setWidgetName("ImageView");
    }

    public void setResourceName(String str) {
        this.b = str;
    }

    public String getResourceName() {
        return this.b;
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 6;
        viewBean.image.resName = this.b;
        return viewBean;
    }
}
