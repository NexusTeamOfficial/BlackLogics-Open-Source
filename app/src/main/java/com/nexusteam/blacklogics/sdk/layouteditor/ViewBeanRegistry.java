package com.nexusteam.blacklogics.sdk.layouteditor;

import android.util.Log;
import android.view.View;

import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.internal.beans.ViewBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Per-layout map of view id -> ViewBean. Single place to look up the bean
 * that is the source of truth for attributes during migration.
 */
public class ViewBeanRegistry {

    private static final String TAG = "ViewBeanRegistry";

    private final Map<String, ViewBean> beans = new HashMap<String, ViewBean>();

    public ViewBean getBean(String id) {
        if (id == null) return null;
        return beans.get(id);
    }

    public void putBean(String id, ViewBean bean) {
        if (id == null || bean == null) return;
        beans.put(id, bean);
    }

    public void remove(String id) {
        if (id == null) return;
        beans.remove(id);
    }

    public void clear() {
        beans.clear();
    }

    public int size() {
        return beans.size();
    }

    public Map<String, ViewBean> snapshot() {
        return new HashMap<String, ViewBean>(beans);
    }

    /**
     * Rebuild ViewBean.attrs from AttributeStore for every known view id.
     * Creates a minimal ViewBean if missing.
     */
    public void syncFromStore(AttributeStore store, IdManager idManager) {
        if (store == null || idManager == null) return;
        try {
            java.util.ArrayList<View> views = idManager.getViews();
            for (int i = 0; i < views.size(); i++) {
                View v = views.get(i);
                if (v == null) continue;
                String id = idManager.getId(v);
                if (id == null) continue;
                AttributeSet set = store.get(v);
                ViewBean bean = beans.get(id);
                if (bean == null) {
                    bean = new ViewBean();
                    bean.id = id;
                    beans.put(id, bean);
                }
                Map<String, String> attrs = bean.getAttrs();
                attrs.clear();
                if (set != null) {
                    for (Attribute a : set.getAttributes()) {
                        if (a == null || a.getName() == null) continue;
                        attrs.put(a.getName(), a.getValue() != null ? a.getValue() : "");
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "syncFromStore failed", e);
        }
    }

    /**
     * Push ViewBean.attrs into AttributeStore for every registered bean that has a live View.
     */
    public void syncToStore(AttributeStore store, IdManager idManager) {
        if (store == null || idManager == null) return;
        try {
            Iterator<Map.Entry<String, ViewBean>> it = beans.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, ViewBean> e = it.next();
                String id = e.getKey();
                ViewBean bean = e.getValue();
                if (id == null || bean == null) continue;
                View v = idManager.getView(id);
                if (v == null) continue;
                AttributeSet set = store.getOrCreate(v);
                Map<String, String> attrs = bean.getAttrs();
                Iterator<Map.Entry<String, String>> ait = attrs.entrySet().iterator();
                while (ait.hasNext()) {
                    Map.Entry<String, String> ae = ait.next();
                    set.add(new Attribute(ae.getKey(), ae.getValue() != null ? ae.getValue() : ""));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "syncToStore failed", e);
        }
    }

    /** Ensure a bean exists for this view id and has the given attr written. */
    public void putAttr(String viewId, String attrName, String value) {
        if (viewId == null || attrName == null) return;
        ViewBean bean = beans.get(viewId);
        if (bean == null) {
            bean = new ViewBean();
            bean.id = viewId;
            beans.put(viewId, bean);
        }
        if (value == null || value.length() == 0) {
            bean.getAttrs().remove(attrName);
        } else {
            bean.getAttrs().put(attrName, value);
        }
    }
}
