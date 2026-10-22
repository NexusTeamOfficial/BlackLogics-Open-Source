package com.nexusteam.blacklogics.sdk.collection;

import com.nexusteam.internal.beans.ViewBean;
import java.util.ArrayList;

/**
 * Sketchware-style Widget Collection (name + list of ViewBeans).
 */
public class WidgetCollectionBean {
    public String name;
    public ArrayList<ViewBean> widgets;

    public WidgetCollectionBean() {
        this.widgets = new ArrayList<ViewBean>();
    }

    public WidgetCollectionBean(String name, ArrayList<ViewBean> widgets) {
        this.name = name;
        this.widgets = widgets != null ? widgets : new ArrayList<ViewBean>();
    }
}
