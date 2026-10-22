package com.nexusteam.blacklogics.sdk.collection;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.layouteditor.Attribute;
import com.nexusteam.blacklogics.sdk.layouteditor.AttributeStore;
import com.nexusteam.blacklogics.sdk.layouteditor.IdManager;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewBeanRegistry;
import com.nexusteam.internal.beans.LayoutBean;
import com.nexusteam.internal.beans.TextBean;
import com.nexusteam.internal.beans.ViewBean;

import java.util.ArrayList;
import java.util.Map;

/**
 * Builds Sketchware-style ArrayList&lt;ViewBean&gt; from live editor views.
 * Fully integrates com.nexusteam.internal.beans.ViewBean including:
 * - all children of ViewGroups (recursive)
 * - full AttributeSet → bean.attrs
 * - layout width/height
 * - text / type / parent linkage
 */
public final class CollectionViewBeanHelper {

    private static final String TAG = "CollectionViewBeanHelper";

    private CollectionViewBeanHelper() {}

    /**
     * Collect selected view + ALL its descendants as ViewBeans.
     * Hierarchy preserved via parent / parentType / index fields.
     */
    public static ArrayList<ViewBean> collectFromView(
            View root,
            AttributeStore store,
            IdManager idManager,
            ViewBeanRegistry registry) {

        ArrayList<ViewBean> result = new ArrayList<>();
        if (root == null) return result;

        // Sync registry from live AttributeStore so beans have latest attrs
        if (registry != null && store != null && idManager != null) {
            try {
                registry.syncFromStore(store, idManager);
            } catch (Exception e) {
                Log.w(TAG, "syncFromStore failed", e);
            }
        }

        collectRecursive(root, "root", 0, 0, result, store, idManager, registry);
        Log.d(TAG, "Collected " + result.size() + " ViewBeans (root + children)");
        return result;
    }

    private static void collectRecursive(
            View view,
            String parentId,
            int parentType,
            int indexInParent,
            ArrayList<ViewBean> out,
            AttributeStore store,
            IdManager idManager,
            ViewBeanRegistry registry) {

        if (view == null) return;

        String id = null;
        if (idManager != null) {
            try {
                id = idManager.getId(view);
            } catch (Exception ignored) {}
        }
        if (id == null || id.isEmpty()) {
            id = "view_" + Integer.toHexString(System.identityHashCode(view));
        }

        ViewBean bean = null;
        if (registry != null) {
            ViewBean existing = registry.getBean(id);
            if (existing != null) {
                try {
                    bean = existing.clone();
                } catch (Exception e) {
                    bean = existing;
                }
            }
        }
        if (bean == null) {
            bean = new ViewBean();
        }
        bean.id = id;
        bean.parent = parentId;
        bean.parentType = parentType;
        bean.index = indexInParent;

        // Merge live AttributeSet into bean.attrs (full attributes)
        if (store != null) {
            AttributeSet set = store.get(view);
            if (set != null) {
                try {
                    Map<String, String> attrs = bean.getAttrs();
                    for (Attribute a : set.getAttributes()) {
                        if (a == null || a.getName() == null) continue;
                        String val = a.getValue() != null ? a.getValue() : "";
                        attrs.put(a.getName(), val);
                    }
                    if (bean.layout == null) bean.layout = new LayoutBean();
                    Attribute w = set.getAttribute("android:layout_width");
                    Attribute h = set.getAttribute("android:layout_height");
                    if (w != null && w.getValue() != null) {
                        bean.layout.width = parseSize(w.getValue());
                    }
                    if (h != null && h.getValue() != null) {
                        bean.layout.height = parseSize(h.getValue());
                    }
                } catch (Exception e) {
                    Log.w(TAG, "attr merge failed for " + id, e);
                }
            }
        }

        // Type from class name if still default
        if (bean.type == 0) {
            bean.type = guessType(view);
        }

        // Text content for TextView subclasses
        if (view instanceof TextView) {
            try {
                CharSequence cs = ((TextView) view).getText();
                if (cs != null && cs.length() > 0) {
                    if (bean.text == null) bean.text = new TextBean();
                    bean.text.text = cs.toString();
                }
            } catch (Exception ignored) {}
        }

        // Put back into registry so future syncs see it
        if (registry != null) {
            registry.putBean(id, bean);
        }

        out.add(bean);

        // CRITICAL: always recurse into ViewGroup children
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = vg.getChildAt(i);
                if (child == null) continue;
                Object tag = child.getTag();
                if (tag != null && "editor_chrome".equals(String.valueOf(tag))) continue;
                collectRecursive(child, id, bean.type, i, out, store, idManager, registry);
            }
        }
    }

    private static int parseSize(String v) {
        if (v == null) return -2;
        v = v.trim();
        if ("match_parent".equals(v) || "fill_parent".equals(v)) return -1;
        if ("wrap_content".equals(v)) return -2;
        try {
            if (v.endsWith("dp")) v = v.substring(0, v.length() - 2);
            if (v.endsWith("px")) v = v.substring(0, v.length() - 2);
            return Integer.parseInt(v.trim());
        } catch (Exception e) {
            return -2;
        }
    }

    /** Map live Android view class → Sketchware ViewBean type constants */
    private static int guessType(View view) {
        if (view == null) return ViewBean.VIEW_TYPE_LAYOUT_LINEAR;
        String n = view.getClass().getSimpleName().toLowerCase();
        String full = view.getClass().getName().toLowerCase();

        if (n.contains("constraint")) return ViewBean.VIEW_TYPE_WIDGET_CONSTRAINTLAYOUT;
        if (n.contains("coordinator")) return ViewBean.VIEW_TYPE_WIDGET_COORDINATORLAYOUT;
        if (n.contains("nestedscroll")) return ViewBean.VIEW_TYPE_WIDGET_NESTEDSCROLLVIEW;
        if (n.contains("horizontalscroll") || (n.contains("scroll") && n.contains("horizontal")))
            return ViewBean.VIEW_TYPE_LAYOUT_HSCROLLVIEW;
        if (n.contains("scrollview") || n.equals("scrollview")) return ViewBean.VIEW_TYPE_LAYOUT_VSCROLLVIEW;
        if (n.contains("relativelayout") || n.contains("relative")) return ViewBean.VIEW_TYPE_LAYOUT_RELATIVE;
        if (n.contains("linearlayout") || n.contains("linear")) return ViewBean.VIEW_TYPE_LAYOUT_LINEAR;
        if (n.contains("framelayout") || n.contains("frame")) return ViewBean.VIEW_TYPE_LAYOUT_LINEAR;

        if (n.contains("materialbutton")) return ViewBean.VIEW_TYPE_WIDGET_MATERIALBUTTON;
        if (n.contains("materialcard") || n.contains("materialcardview")) return ViewBean.VIEW_TYPE_WIDGET_MATERIALCARDVIEW;
        if (n.contains("cardview")) return ViewBean.VIEW_TYPE_WIDGET_CARDVIEW;
        if (n.contains("textinputlayout")) return ViewBean.VIEW_TYPE_WIDGET_TEXTINPUTLAYOUT;
        if (n.contains("textinputedit")) return ViewBean.VIEW_TYPE_WIDGET_TEXTINPUTEDITTEXT;
        if (n.contains("bottomnavigation")) return ViewBean.VIEW_TYPE_WIDGET_BOTTOMNAVIGATIONVIEW;
        if (n.contains("navigationview")) return ViewBean.VIEW_TYPE_WIDGET_NAVIGATIONVIEW;
        if (n.contains("tablayout")) return ViewBean.VIEW_TYPE_WIDGET_TABLAYOUT;
        if (n.contains("chipgroup")) return ViewBean.VIEW_TYPE_WIDGET_CHIPGROUP;
        if (n.contains("chip")) return ViewBean.VIEW_TYPE_WIDGET_CHIP;
        if (n.contains("switchmaterial") || (n.contains("switch") && full.contains("material")))
            return ViewBean.VIEW_TYPE_WIDGET_SWITCHMATERIAL;
        if (n.contains("slider")) return ViewBean.VIEW_TYPE_WIDGET_SLIDER;
        if (n.contains("recyclerview")) return ViewBean.VIEW_TYPE_WIDGET_RECYCLERVIEW;
        if (n.contains("viewpager2")) return ViewBean.VIEW_TYPE_WIDGET_VIEWPAGER2;
        if (n.contains("viewpager")) return ViewBean.VIEW_TYPE_WIDGET_VIEWPAGER;
        if (n.contains("lottie")) return ViewBean.VIEW_TYPE_WIDGET_LOTTIE;
        if (n.contains("circleimage")) return ViewBean.VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW;

        if (n.contains("button") && !n.contains("radio") && !n.contains("toggle") && !n.contains("check"))
            return ViewBean.VIEW_TYPE_WIDGET_BUTTON;
        if (n.contains("edittext") || n.contains("edit")) return ViewBean.VIEW_TYPE_WIDGET_EDITTEXT;
        if (n.contains("textview") || n.contains("text")) return ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW;
        if (n.contains("imageview") || n.contains("image")) return ViewBean.VIEW_TYPE_WIDGET_IMAGEVIEW;
        if (n.contains("webview") || n.contains("web")) return ViewBean.VIEW_TYPE_WIDGET_WEBVIEW;
        if (n.contains("progress")) return ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR;
        if (n.contains("listview") || n.contains("list")) return ViewBean.VIEW_TYPE_WIDGET_LISTVIEW;
        if (n.contains("spinner")) return ViewBean.VIEW_TYPE_WIDGET_SPINNER;
        if (n.contains("checkbox") || n.contains("check")) return ViewBean.VIEW_TYPE_WIDGET_CHECKBOX;
        if (n.contains("switch")) return ViewBean.VIEW_TYPE_WIDGET_SWITCH;
        if (n.contains("seekbar") || n.contains("seek")) return ViewBean.VIEW_TYPE_WIDGET_SEEKBAR;
        if (n.contains("calendar")) return ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW;
        if (n.contains("fab") || n.contains("floatingaction")) return ViewBean.VIEW_TYPE_WIDGET_FAB;
        if (n.contains("adview") || n.contains("ad")) return ViewBean.VIEW_TYPE_WIDGET_ADVIEW;
        if (n.contains("map")) return ViewBean.VIEW_TYPE_WIDGET_MAPVIEW;
        if (n.contains("radiobutton")) return ViewBean.VIEW_TYPE_WIDGET_RADIOBUTTON;
        if (n.contains("radiogroup")) return ViewBean.VIEW_TYPE_WIDGET_RADIOGROUP;
        if (n.contains("toggle")) return ViewBean.VIEW_TYPE_WIDGET_TOGGLEBUTTON;
        if (n.contains("rating")) return ViewBean.VIEW_TYPE_WIDGET_RATINGBAR;
        if (n.contains("numberpicker") || n.contains("picker")) return ViewBean.VIEW_TYPE_WIDGET_NUMBERPICKER;
        if (n.contains("videoview") || n.contains("video")) return ViewBean.VIEW_TYPE_WIDGET_VIDEOVIEW;
        if (n.contains("gridview") || n.contains("grid")) return ViewBean.VIEW_TYPE_WIDGET_GRIDVIEW;
        if (n.contains("autocomplete")) return ViewBean.VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW;
        if (n.contains("datepicker")) return ViewBean.VIEW_TYPE_WIDGET_DATEPICKER;
        if (n.contains("timepicker")) return ViewBean.VIEW_TYPE_WIDGET_TIMEPICKER;

        return ViewBean.VIEW_TYPE_LAYOUT_LINEAR;
    }
}
