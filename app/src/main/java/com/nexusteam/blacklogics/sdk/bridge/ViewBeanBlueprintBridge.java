package com.nexusteam.blacklogics.sdk.bridge;

import android.util.Log;

import com.nexusteam.blacklogics.sdk.models.WidgetBlueprint;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.beans.WidgetCollectionBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ViewBeanBlueprintBridge {

    private static final String TAG = "ViewBeanBpBridge";

    private ViewBeanBlueprintBridge() {}


    public static WidgetBlueprint viewBeansToBlueprint(String displayName,
            List<ViewBean> beans) {
        if (beans == null || beans.isEmpty()) {
            WidgetBlueprint empty = new WidgetBlueprint();
            empty.setDisplayName(displayName != null ? displayName : "Empty");
            return empty;
        }

        ViewBean root = findRoot(beans);
        if (root == null) root = beans.get(0);

        WidgetBlueprint bp = beanToBlueprint(root, displayName);
        List<ViewBean> children = childrenOf(beans, root.id);
        for (ViewBean child : children) {
            bp.addChildBlueprint(beanSubtreeToBlueprint(child, beans));
        }
        return bp;
    }

    private static WidgetBlueprint beanSubtreeToBlueprint(ViewBean bean,
            List<ViewBean> all) {
        WidgetBlueprint bp = beanToBlueprint(bean, bean.id != null ? bean.id : "child");
        List<ViewBean> children = childrenOf(all, bean.id);
        for (ViewBean child : children) {
            bp.addChildBlueprint(beanSubtreeToBlueprint(child, all));
        }
        return bp;
    }

    private static WidgetBlueprint beanToBlueprint(ViewBean bean, String displayName) {
        WidgetBlueprint bp = new WidgetBlueprint();
        bp.setDisplayName(displayName != null ? displayName
                : (bean.id != null ? bean.id : ViewBean.getViewTypeName(bean.type)));
        bp.setWidgetClass(classNameForType(bean.type));

        if (bean.id != null && bean.id.length() > 0) {
            bp.addAttribute("android:id", "@+id/" + bean.id);
        }

        if (bean.layout != null) {
            bp.addLayoutParam("layout_width", dimToString(bean.layout.width));
            bp.addLayoutParam("layout_height", dimToString(bean.layout.height));
            if (bean.layout.paddingLeft != 0)
                bp.addLayoutParam("padding_left", String.valueOf(bean.layout.paddingLeft));
            if (bean.layout.paddingTop != 0)
                bp.addLayoutParam("padding_top", String.valueOf(bean.layout.paddingTop));
            if (bean.layout.paddingRight != 0)
                bp.addLayoutParam("padding_right", String.valueOf(bean.layout.paddingRight));
            if (bean.layout.paddingBottom != 0)
                bp.addLayoutParam("padding_bottom", String.valueOf(bean.layout.paddingBottom));
            if (bean.layout.marginLeft != 0)
                bp.addLayoutParam("layout_marginLeft", String.valueOf(bean.layout.marginLeft));
            if (bean.layout.marginTop != 0)
                bp.addLayoutParam("layout_marginTop", String.valueOf(bean.layout.marginTop));
            if (bean.layout.marginRight != 0)
                bp.addLayoutParam("layout_marginRight", String.valueOf(bean.layout.marginRight));
            if (bean.layout.marginBottom != 0)
                bp.addLayoutParam("layout_marginBottom", String.valueOf(bean.layout.marginBottom));
        }

        if (bean.text != null) {
            if (bean.text.text != null && bean.text.text.length() > 0) {
                bp.addAttribute("android:text", bean.text.text);
            }
            if (bean.text.textSize > 0) {
                bp.addAttribute("android:textSize", bean.text.textSize + "sp");
            }
        }

        bp.addAttribute("android:enabled", bean.enabled == 1 ? "true" : "false");
        bp.addAttribute("android:clickable", bean.clickable == 1 ? "true" : "false");
        if (bean.alpha != 1.0f) {
            bp.addAttribute("android:alpha", String.valueOf(bean.alpha));
        }

        return bp;
    }


    public static ArrayList<ViewBean> blueprintToViewBeans(WidgetBlueprint root) {
        ArrayList<ViewBean> out = new ArrayList<ViewBean>();
        if (root == null) return out;
        appendBlueprintAsBeans(root, null, -1, 0, out);
        return out;
    }

    private static void appendBlueprintAsBeans(WidgetBlueprint bp, String parentId,
            int parentType, int index, ArrayList<ViewBean> out) {
        ViewBean bean = blueprintToBean(bp);
        bean.parent = parentId;
        bean.parentType = parentType;
        bean.index = index;
        out.add(bean);

        List<WidgetBlueprint> children = bp.getChildBlueprints();
        if (children == null) return;
        for (int i = 0; i < children.size(); i++) {
            WidgetBlueprint child = children.get(i);
            if (child != null) {
                appendBlueprintAsBeans(child, bean.id, bean.type, i, out);
            }
        }
    }

    private static ViewBean blueprintToBean(WidgetBlueprint bp) {
        int type = typeFromClassName(bp.getWidgetClass());
        if (type < 0) type = typeFromClassName(bp.getWidgetType());
        if (type < 0) type = ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW;

        String id = extractId(bp);
        if (id == null || id.isEmpty()) {
            id = "view_" + Integer.toHexString(System.identityHashCode(bp));
        }

        ViewBean bean = new ViewBean(id, type);

        Map<String, String> attrs = bp.getAttributes();
        if (attrs != null) {
            String text = attrs.get("android:text");
            if (text != null && bean.text != null) {
                bean.text.text = text;
            }
            String textSize = attrs.get("android:textSize");
            if (textSize != null && bean.text != null) {
                try {
                    float sz = Float.parseFloat(
                            textSize.replace("sp", "").replace("dp", "").trim());
                    bean.text.textSize = (int) sz;
                } catch (Exception ignored) {}
            }
            String enabled = attrs.get("android:enabled");
            if (enabled != null) bean.enabled = "true".equalsIgnoreCase(enabled) ? 1 : 0;
            String clickable = attrs.get("android:clickable");
            if (clickable != null) bean.clickable = "true".equalsIgnoreCase(clickable) ? 1 : 0;
            String alpha = attrs.get("android:alpha");
            if (alpha != null) {
                try { bean.alpha = Float.parseFloat(alpha); } catch (Exception ignored) {}
            }
        }

        Map<String, String> lp = bp.getLayoutParams();
        if (lp != null && bean.layout != null) {
            bean.layout.width = stringToDim(lp.get("layout_width"));
            bean.layout.height = stringToDim(lp.get("layout_height"));
            bean.layout.paddingLeft = parseIntSafe(lp.get("padding_left"));
            bean.layout.paddingTop = parseIntSafe(lp.get("padding_top"));
            bean.layout.paddingRight = parseIntSafe(lp.get("padding_right"));
            bean.layout.paddingBottom = parseIntSafe(lp.get("padding_bottom"));
            bean.layout.marginLeft = parseIntSafe(lp.get("layout_marginLeft"));
            bean.layout.marginTop = parseIntSafe(lp.get("layout_marginTop"));
            bean.layout.marginRight = parseIntSafe(lp.get("layout_marginRight"));
            bean.layout.marginBottom = parseIntSafe(lp.get("layout_marginBottom"));
        }

        return bean;
    }


    public static WidgetBlueprint collectionToBlueprint(WidgetCollectionBean collection) {
        if (collection == null) {
            return new WidgetBlueprint();
        }
        return viewBeansToBlueprint(collection.getDisplayName(), collection.getWidgets());
    }

    public static WidgetCollectionBean blueprintToCollection(WidgetBlueprint blueprint) {
        if (blueprint == null) {
            return new WidgetCollectionBean("Untitled", new ArrayList<ViewBean>());
        }
        ArrayList<ViewBean> beans = blueprintToViewBeans(blueprint);
        return WidgetCollectionBean.builder()
                .name(blueprint.getDisplayName())
                .description(blueprint.getDescription() != null ? blueprint.getDescription() : "")
                .widgets(beans)
                .tags(blueprint.getTags())
                .favorite(blueprint.isFavorite())
                .thumbnailPath(blueprint.getThumbnailPath())
                .build();
    }


    private static ViewBean findRoot(List<ViewBean> beans) {
        for (ViewBean b : beans) {
            if (b == null) continue;
            if (b.parent == null || b.parent.length() == 0
                    || "root".equals(b.parent) || "parent".equals(b.parent)) {
                return b;
            }
        }
        return null;
    }

    private static List<ViewBean> childrenOf(List<ViewBean> all, String parentId) {
        List<ViewBean> kids = new ArrayList<ViewBean>();
        if (parentId == null) return kids;
        for (ViewBean b : all) {
            if (b != null && parentId.equals(b.parent)) {
                kids.add(b);
            }
        }
        java.util.Collections.sort(kids, new java.util.Comparator<ViewBean>() {
            @Override
            public int compare(ViewBean a, ViewBean b) {
                return Integer.compare(a.index, b.index);
            }
        });
        return kids;
    }

    private static String extractId(WidgetBlueprint bp) {
        Map<String, String> attrs = bp.getAttributes();
        if (attrs == null) return null;
        String raw = attrs.get("android:id");
        if (raw == null) return null;
        int slash = raw.lastIndexOf('/');
        if (slash >= 0 && slash < raw.length() - 1) {
            return raw.substring(slash + 1);
        }
        return raw;
    }

    private static String dimToString(int dim) {
        if (dim == -1) return "match_parent";
        if (dim == -2) return "wrap_content";
        return dim + "px";
    }

    private static int stringToDim(String s) {
        if (s == null) return -2;
        if ("match_parent".equals(s) || "fill_parent".equals(s)) return -1;
        if ("wrap_content".equals(s)) return -2;
        try {
            return Integer.parseInt(s.replace("px", "").replace("dp", "").trim());
        } catch (Exception e) {
            return -2;
        }
    }

    private static int parseIntSafe(String v) {
        if (v == null) return 0;
        try {
            return Integer.parseInt(v.replace("px", "").replace("dp", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static String classNameForType(int type) {
        String simple = ViewBean.getViewTypeName(type);
        if (simple == null || simple.isEmpty()) {
            return "android.widget.TextView";
        }
        switch (type) {
            case ViewBean.VIEW_TYPE_LAYOUT_LINEAR:
                return "android.widget.LinearLayout";
            case ViewBean.VIEW_TYPE_LAYOUT_RELATIVE:
                return "android.widget.RelativeLayout";
            case ViewBean.VIEW_TYPE_LAYOUT_HSCROLLVIEW:
                return "android.widget.HorizontalScrollView";
            case ViewBean.VIEW_TYPE_LAYOUT_VSCROLLVIEW:
                return "android.widget.ScrollView";
            case ViewBean.VIEW_TYPE_WIDGET_CONSTRAINTLAYOUT:
                return "androidx.constraintlayout.widget.ConstraintLayout";
            case ViewBean.VIEW_TYPE_WIDGET_COORDINATORLAYOUT:
                return "androidx.coordinatorlayout.widget.CoordinatorLayout";
            case ViewBean.VIEW_TYPE_WIDGET_NESTEDSCROLLVIEW:
                return "androidx.core.widget.NestedScrollView";
            case ViewBean.VIEW_TYPE_WIDGET_RECYCLERVIEW:
                return "androidx.recyclerview.widget.RecyclerView";
            case ViewBean.VIEW_TYPE_WIDGET_CARDVIEW:
                return "androidx.cardview.widget.CardView";
            case ViewBean.VIEW_TYPE_WIDGET_MATERIALBUTTON:
                return "com.google.android.material.button.MaterialButton";
            case ViewBean.VIEW_TYPE_WIDGET_MATERIALCARDVIEW:
                return "com.google.android.material.card.MaterialCardView";
            case ViewBean.VIEW_TYPE_WIDGET_TEXTINPUTLAYOUT:
                return "com.google.android.material.textfield.TextInputLayout";
            case ViewBean.VIEW_TYPE_WIDGET_TEXTINPUTEDITTEXT:
                return "com.google.android.material.textfield.TextInputEditText";
            case ViewBean.VIEW_TYPE_WIDGET_FAB:
                return "com.google.android.material.floatingactionbutton.FloatingActionButton";
            case ViewBean.VIEW_TYPE_WIDGET_SWITCHMATERIAL:
                return "com.google.android.material.switchmaterial.SwitchMaterial";
            case ViewBean.VIEW_TYPE_WIDGET_SLIDER:
                return "com.google.android.material.slider.Slider";
            case ViewBean.VIEW_TYPE_WIDGET_CHIP:
                return "com.google.android.material.chip.Chip";
            case ViewBean.VIEW_TYPE_WIDGET_CHIPGROUP:
                return "com.google.android.material.chip.ChipGroup";
            case ViewBean.VIEW_TYPE_WIDGET_BOTTOMNAVIGATIONVIEW:
                return "com.google.android.material.bottomnavigation.BottomNavigationView";
            case ViewBean.VIEW_TYPE_WIDGET_TABLAYOUT:
                return "com.google.android.material.tabs.TabLayout";
            case ViewBean.VIEW_TYPE_WIDGET_VIEWPAGER2:
                return "androidx.viewpager2.widget.ViewPager2";
            case ViewBean.VIEW_TYPE_WIDGET_VIEWPAGER:
                return "androidx.viewpager.widget.ViewPager";
            case ViewBean.VIEW_TYPE_WIDGET_LOTTIE:
                return "com.airbnb.lottie.LottieAnimationView";
            case ViewBean.VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW:
                return "de.hdodenhof.circleimageview.CircleImageView";
            case ViewBean.VIEW_TYPE_WIDGET_NAVIGATIONVIEW:
                return "com.google.android.material.navigation.NavigationView";
            default:
                return "android.widget." + simple;
        }
    }

    private static int typeFromClassName(String className) {
        if (className == null || className.isEmpty()) return -1;
        String simple = className;
        int dot = className.lastIndexOf('.');
        if (dot >= 0 && dot < className.length() - 1) {
            simple = className.substring(dot + 1);
        }
        int t = ViewBean.getViewTypeByTypeNameExtended(simple);
        if (t >= 0) return t;
        return ViewBean.getViewTypeByTypeName(simple);
    }
}
