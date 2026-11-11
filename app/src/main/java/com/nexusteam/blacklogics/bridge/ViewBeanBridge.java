package com.nexusteam.blacklogics.bridge;

import android.util.Log;

import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.editor.layout.storage.LayoutStorage;
import com.nexusteam.blacklogics.editor.layout.LayoutLoader;

import java.util.ArrayList;

public final class ViewBeanBridge {

    private static final String TAG = "ViewBeanBridge";

    private ViewBeanBridge() {}

    public static void saveToLayoutStore(String scId, String xmlName, ArrayList<ViewBean> beans) {
        if (scId == null || xmlName == null || beans == null) return;
        try {
            String layoutKey = xmlName;
            if (layoutKey.endsWith(".xml")) {
                layoutKey = layoutKey.substring(0, layoutKey.length() - 4);
            }

            String xml = ViewBeanToXmlConverter.convert(beans);
            try {
                LayoutStorage.class.getMethod("save", String.class, String.class, String.class)
                        .invoke(null, scId, layoutKey, xml);
            } catch (NoSuchMethodException e) {
                Log.w(TAG, "LayoutStorage.save(scId,key,xml) not found — implement adapter");
            }

            Log.d(TAG, "Saved " + beans.size() + " widgets → " + layoutKey + ".xml");
        } catch (Throwable t) {
            Log.e(TAG, "saveToLayoutStore failed", t);
        }
    }

    public static ArrayList<ViewBean> loadFromLayoutStore(String scId, String xmlName) {
        try {
            String layoutKey = xmlName;
            if (layoutKey != null && layoutKey.endsWith(".xml")) {
                layoutKey = layoutKey.substring(0, layoutKey.length() - 4);
            }
            String xml = null;
            try {
                xml = (String) LayoutLoader.class
                        .getMethod("getLayoutXml", String.class, String.class)
                        .invoke(null, scId, layoutKey);
            } catch (Throwable ignore) {
            }
            if (xml == null || xml.isEmpty()) return new ArrayList<>();
            return XmlToViewBeanConverter.parse(xml);
        } catch (Throwable t) {
            Log.e(TAG, "loadFromLayoutStore failed", t);
            return new ArrayList<>();
        }
    }
}
