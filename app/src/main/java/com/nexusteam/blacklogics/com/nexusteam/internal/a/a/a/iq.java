package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;

public class iq extends il {
    private String b;
    private ArrayList<ViewBean> e;

    public iq(Context context, String str, ArrayList<ViewBean> arrayList) {
        super(context);
        this.b = str;
        this.e = arrayList;
        a();
    }

    private void a() {
        this.d.setTextSize(2, 11.0f);
        setWidgetName(this.b);
        setWidgetImage(ViewBean.getViewTypeResId(this.e.get(0).type));
    }

    public String getName() {
        return this.b;
    }

    public ArrayList<ViewBean> getData() {
        return this.e;
    }

    public ViewBean getBean() {
        return this.e.get(0);
    }
}
