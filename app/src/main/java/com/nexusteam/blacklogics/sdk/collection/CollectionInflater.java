package com.nexusteam.blacklogics.sdk.collection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.layouteditor.Attribute;
import com.nexusteam.blacklogics.sdk.layouteditor.AttributeStore;
import com.nexusteam.blacklogics.sdk.layouteditor.IdManager;
import com.nexusteam.blacklogics.sdk.layouteditor.ViewBeanRegistry;
import com.nexusteam.internal.beans.LayoutBean;
import com.nexusteam.internal.beans.ViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inflates Sketchware-style ArrayList&lt;ViewBean&gt; into live editor Views.
 * Applies attributes into AttributeStore + IdManager + ViewBeanRegistry.
 */
public final class CollectionInflater {

    private static final String TAG = "CollectionInflater";

    public interface Host {
        Context getContext();
        AttributeStore getAttributeStore();
        IdManager getIdManager();
        ViewBeanRegistry getBeanRegistry();
        void applyAttribute(View view, Attribute attribute);
        void applyDefaultViewSetup(View view);
        void ensureDefaultLayoutParams(View view);
        void applyContainerStroke(View view);
    }

    private CollectionInflater() {}

    /**
     * Inflate full hierarchy under destination.
     * Returns the root created view (first root bean).
     */
    public static View inflate(
            Host host,
            ArrayList<ViewBean> beans,
            ViewGroup destination,
            int insertIndex) {

        if (host == null || beans == null || beans.isEmpty() || destination == null) {
            return null;
        }

        // Build parent -> children map
        Map<String, List<ViewBean>> childrenMap = new HashMap<>();
        ViewBean rootBean = null;
        for (ViewBean b : beans) {
            if (b == null) continue;
            String p = b.parent;
            if (p == null || p.isEmpty() || "root".equals(p) || "parent".equals(p)) {
                if (rootBean == null) rootBean = b;
            } else {
                List<ViewBean> list = childrenMap.get(p);
                if (list == null) {
                    list = new ArrayList<>();
                    childrenMap.put(p, list);
                }
                list.add(b);
            }
        }
        if (rootBean == null) rootBean = beans.get(0);

        View rootView = createViewRecursive(host, rootBean, childrenMap, destination);
        if (rootView == null) return null;

        if (insertIndex < 0 || insertIndex > destination.getChildCount()) {
            destination.addView(rootView);
        } else {
            destination.addView(rootView, insertIndex);
        }
        return rootView;
    }

    private static View createViewRecursive(
            Host host,
            ViewBean bean,
            Map<String, List<ViewBean>> childrenMap,
            ViewGroup logicalParent) {

        Context ctx = host.getContext();
        View view = createViewForType(ctx, bean);
        if (view == null) return null;

        // Keep original id for childrenMap lookup BEFORE collision rename.
        // childrenMap is keyed by the ids stored at save time; if we rename due to
        // collision on a non-empty canvas, lookup by new id would miss children.
        final String originalId = bean.id;

        String id = bean.id;
        if (id == null || id.isEmpty() || id.startsWith("_")) {
            id = host.getIdManager().generateNewId(view);
        } else {
            if (host.getIdManager().isIdUsed(id)) {
                id = host.getIdManager().generateNewId(view);
            }
        }
        try {
            host.getIdManager().addNewId(view, id);
        } catch (Exception e) {
            try {
                id = host.getIdManager().generateNewId(view);
                host.getIdManager().addNewId(view, id);
            } catch (Exception ignored) {}
        }
        bean.id = id;

        // Layout params
        ViewGroup.LayoutParams lp = buildLayoutParams(logicalParent, bean);
        view.setLayoutParams(lp);

        host.applyDefaultViewSetup(view);
        host.ensureDefaultLayoutParams(view);

        // AttributeStore
        AttributeSet set = host.getAttributeStore().getOrCreate(view);
        applyBeanToAttributeSet(bean, set);

        // Apply each attribute to live view
        for (Attribute a : set.getAttributes()) {
            try {
                host.applyAttribute(view, a);
            } catch (Exception e) {
                Log.w(TAG, "applyAttribute failed: " + a.getName(), e);
            }
        }

        // Registry under the live (possibly renamed) id
        if (host.getBeanRegistry() != null) {
            host.getBeanRegistry().putBean(id, bean);
        }

        if (view instanceof ViewGroup) {
            host.applyContainerStroke(view);
            // Lookup by ORIGINAL id (save-time key), not the collision-renamed id
            List<ViewBean> kids = null;
            if (originalId != null && !originalId.isEmpty()) {
                kids = childrenMap.get(originalId);
            }
            if (kids == null) {
                kids = childrenMap.get(bean.id);
            }
            if (kids != null) {
                ViewGroup vg = (ViewGroup) view;
                for (ViewBean child : kids) {
                    View childView = createViewRecursive(host, child, childrenMap, vg);
                    if (childView != null) {
                        vg.addView(childView);
                    }
                }
            }
        }

        return view;
    }

    private static void applyBeanToAttributeSet(ViewBean bean, AttributeSet set) {
        if (bean == null || set == null) return;

        // From free-form attrs map
        try {
            Map<String, String> attrs = bean.getAttrs();
            if (attrs != null) {
                for (Map.Entry<String, String> e : attrs.entrySet()) {
                    if (e.getKey() != null) {
                        set.add(new Attribute(e.getKey(), e.getValue() != null ? e.getValue() : ""));
                    }
                }
            }
        } catch (Exception ignored) {}

        // Layout size
        if (bean.layout != null) {
            set.add(new Attribute("android:layout_width", sizeToString(bean.layout.width)));
            set.add(new Attribute("android:layout_height", sizeToString(bean.layout.height)));
            if (bean.layout.paddingLeft != 0 || bean.layout.paddingTop != 0
                    || bean.layout.paddingRight != 0 || bean.layout.paddingBottom != 0) {
                if (bean.layout.paddingLeft == bean.layout.paddingTop
                        && bean.layout.paddingTop == bean.layout.paddingRight
                        && bean.layout.paddingRight == bean.layout.paddingBottom) {
                    set.add(new Attribute("android:padding", bean.layout.paddingLeft + "dp"));
                } else {
                    set.add(new Attribute("android:paddingLeft", bean.layout.paddingLeft + "dp"));
                    set.add(new Attribute("android:paddingTop", bean.layout.paddingTop + "dp"));
                    set.add(new Attribute("android:paddingRight", bean.layout.paddingRight + "dp"));
                    set.add(new Attribute("android:paddingBottom", bean.layout.paddingBottom + "dp"));
                }
            }
            if (bean.layout.marginLeft != 0)
                set.add(new Attribute("android:layout_marginLeft", bean.layout.marginLeft + "dp"));
            if (bean.layout.marginTop != 0)
                set.add(new Attribute("android:layout_marginTop", bean.layout.marginTop + "dp"));
            if (bean.layout.marginRight != 0)
                set.add(new Attribute("android:layout_marginRight", bean.layout.marginRight + "dp"));
            if (bean.layout.marginBottom != 0)
                set.add(new Attribute("android:layout_marginBottom", bean.layout.marginBottom + "dp"));
            if (bean.layout.orientation == 0)
                set.add(new Attribute("android:orientation", "horizontal"));
            else if (bean.layout.orientation == 1)
                set.add(new Attribute("android:orientation", "vertical"));
        }

        // Text
        try {
            if (bean.text != null) {
                if (bean.text.text != null && bean.text.text.length() > 0) {
                    if (bean.type == 5) {
                        set.add(new Attribute("android:hint", bean.text.text));
                    } else {
                        set.add(new Attribute("android:text", bean.text.text));
                    }
                }
                if (bean.text.textSize > 0) {
                    set.add(new Attribute("android:textSize", bean.text.textSize + "sp"));
                }
            }
        } catch (Exception ignored) {}

        if (bean.enabled == 0) {
            set.add(new Attribute("android:enabled", "false"));
        }
    }

    private static String sizeToString(int v) {
        if (v == -1) return "match_parent";
        if (v == -2) return "wrap_content";
        if (v < 0) return "wrap_content";
        return v + "dp";
    }

    private static ViewGroup.LayoutParams buildLayoutParams(ViewGroup parent, ViewBean bean) {
        int w = ViewGroup.LayoutParams.WRAP_CONTENT;
        int h = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (bean != null && bean.layout != null) {
            w = bean.layout.width == -1 ? ViewGroup.LayoutParams.MATCH_PARENT
                    : (bean.layout.width == -2 ? ViewGroup.LayoutParams.WRAP_CONTENT : bean.layout.width);
            h = bean.layout.height == -1 ? ViewGroup.LayoutParams.MATCH_PARENT
                    : (bean.layout.height == -2 ? ViewGroup.LayoutParams.WRAP_CONTENT : bean.layout.height);
        }
        if (parent instanceof LinearLayout) {
            return new LinearLayout.LayoutParams(w, h);
        }
        if (parent instanceof RelativeLayout) {
            return new RelativeLayout.LayoutParams(w, h);
        }
        if (parent instanceof FrameLayout) {
            return new FrameLayout.LayoutParams(w, h);
        }
        return new ViewGroup.LayoutParams(w, h);
    }

    private static View createViewForType(Context ctx, ViewBean bean) {
        int type = bean != null ? bean.type : 0;
        switch (type) {
            case 0: // LinearLayout
                LinearLayout ll = new LinearLayout(ctx);
                ll.setOrientation(LinearLayout.VERTICAL);
                return ll;
            case 1: // RelativeLayout
                return new RelativeLayout(ctx);
            case 2: // HorizontalScrollView
                return new HorizontalScrollView(ctx);
            case 3: // Button
                return new Button(ctx);
            case 4: // TextView
                return new TextView(ctx);
            case 5: // EditText
                return new EditText(ctx);
            case 6: // ImageView
                return new ImageView(ctx);
            case 8: // ProgressBar
                return new ProgressBar(ctx);
            case 11: // CheckBox
                return new CheckBox(ctx);
            case 12: // ScrollView
                return new ScrollView(ctx);
            case 13: // Switch
                return new Switch(ctx);
            case 14: // SeekBar
                return new SeekBar(ctx);
            case 19: // RadioButton
                return new RadioButton(ctx);
            default:
                // Placeholder box for heavy / unknown widgets
                return createPlaceholder(ctx, typeName(type));
        }
    }

    private static View createPlaceholder(Context ctx, String label) {
        TextView tv = new TextView(ctx);
        tv.setText(label);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setTextColor(Color.parseColor("#1565C0"));
        int pad = (int) (8 * ctx.getResources().getDisplayMetrics().density);
        tv.setPadding(pad, pad, pad, pad);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#E3F2FD"));
        bg.setStroke((int) (1 * ctx.getResources().getDisplayMetrics().density), Color.parseColor("#90CAF9"));
        bg.setCornerRadius(8);
        tv.setBackground(bg);
        tv.setMinWidth((int) (80 * ctx.getResources().getDisplayMetrics().density));
        tv.setMinHeight((int) (40 * ctx.getResources().getDisplayMetrics().density));
        return tv;
    }

    private static String typeName(int type) {
        switch (type) {
            case 7: return "WebView";
            case 9: return "ListView";
            case 10: return "Spinner";
            case 15: return "CalendarView";
            case 16: return "FAB";
            case 17: return "AdView";
            case 18: return "MapView";
            case 20: return "RadioGroup";
            case 25: return "GridView";
            default: return "Widget(" + type + ")";
        }
    }
}
