package com.nexusteam.blacklogics.bridge;

import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.beans.LayoutBean;
import com.nexusteam.internal.beans.TextBean;
import com.nexusteam.internal.beans.ImageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ViewBeanToXmlConverter {

    private ViewBeanToXmlConverter() {}

    public static String convert(ArrayList<ViewBean> beans) {
        if (beans == null || beans.isEmpty()) {
            return defaultRoot();
        }

        Map<String, ArrayList<ViewBean>> children = new HashMap<>();
        ViewBean root = null;
        for (ViewBean b : beans) {
            if (b.parent == null || b.parent.isEmpty() || "root".equalsIgnoreCase(b.parent)) {
                root = b;
            } else {
                if (!children.containsKey(b.parent)) {
                    children.put(b.parent, new ArrayList<ViewBean>());
                }
                children.get(b.parent).add(b);
            }
        }
        if (root == null) root = beans.get(0);

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        writeView(sb, root, children, 0, true);
        return sb.toString();
    }

    private static void writeView(StringBuilder sb, ViewBean bean,
                                  Map<String, ArrayList<ViewBean>> children,
                                  int indent, boolean isRoot) {
        String pad = repeat("    ", indent);
        String tag = typeToTag(bean.type);
        sb.append(pad).append("<").append(tag);
        if (isRoot) {
            sb.append("\n").append(pad).append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"");
            sb.append("\n").append(pad).append("    xmlns:app=\"http://schemas.android.com/apk/res-auto\"");
            sb.append("\n").append(pad).append("    xmlns:tools=\"http://schemas.android.com/tools\"");
        }

        if (bean.id != null && !bean.id.isEmpty()) {
            attr(sb, pad, "android:id", "@+id/" + bean.id);
        }

        LayoutBean lay = bean.layout;
        if (lay != null) {
            attr(sb, pad, "android:layout_width", sizeToStr(lay.width));
            attr(sb, pad, "android:layout_height", sizeToStr(lay.height));
            if (lay.paddingLeft != 0 || lay.paddingTop != 0 || lay.paddingRight != 0 || lay.paddingBottom != 0) {
                if (lay.paddingLeft == lay.paddingTop && lay.paddingTop == lay.paddingRight && lay.paddingRight == lay.paddingBottom) {
                    attr(sb, pad, "android:padding", lay.paddingLeft + "dp");
                } else {
                    if (lay.paddingLeft != 0) attr(sb, pad, "android:paddingLeft", lay.paddingLeft + "dp");
                    if (lay.paddingTop != 0) attr(sb, pad, "android:paddingTop", lay.paddingTop + "dp");
                    if (lay.paddingRight != 0) attr(sb, pad, "android:paddingRight", lay.paddingRight + "dp");
                    if (lay.paddingBottom != 0) attr(sb, pad, "android:paddingBottom", lay.paddingBottom + "dp");
                }
            }
            if (lay.orientation == 0) attr(sb, pad, "android:orientation", "vertical");
            else if (lay.orientation == 1) attr(sb, pad, "android:orientation", "horizontal");
            if (lay.backgroundColor != 0xffffff) {
                attr(sb, pad, "android:background", String.format("#%08X", lay.backgroundColor));
            }
            if (lay.gravity != 0) {
            }
        } else {
            attr(sb, pad, "android:layout_width", "match_parent");
            attr(sb, pad, "android:layout_height", "wrap_content");
        }

        if (bean.text != null) {
            TextBean t = bean.text;
            if (t.text != null && !t.text.isEmpty()) attr(sb, pad, "android:text", t.text);
            if (t.textSize > 0) attr(sb, pad, "android:textSize", t.textSize + "sp");
            if (t.textColor != 0) attr(sb, pad, "android:textColor", String.format("#%08X", t.textColor));
        }

        if (bean.image != null && bean.image.resName != null && !bean.image.resName.isEmpty()) {
            attr(sb, pad, "android:src", "@drawable/" + bean.image.resName);
        }

        if (bean.enabled == 0) attr(sb, pad, "android:enabled", "false");
        if (bean.clickable == 0) attr(sb, pad, "android:clickable", "false");
        if (bean.alpha > 0 && bean.alpha < 1f) attr(sb, pad, "android:alpha", String.valueOf(bean.alpha));

        ArrayList<ViewBean> kids = children.get(bean.id);
        boolean hasKids = kids != null && !kids.isEmpty();

        if (!hasKids) {
            sb.append(" />\n");
        } else {
            sb.append(">\n");
            for (ViewBean child : kids) {
                writeView(sb, child, children, indent + 1, false);
            }
            sb.append(pad).append("</").append(tag).append(">\n");
        }
    }

    private static void attr(StringBuilder sb, String pad, String name, String value) {
        sb.append("\n").append(pad).append("    ").append(name).append("=\"").append(escape(value)).append("\"");
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String sizeToStr(int size) {
        if (size == LayoutBean.LAYOUT_MATCH_PARENT || size == -1) return "match_parent";
        if (size == LayoutBean.LAYOUT_WRAP_CONTENT || size == -2) return "wrap_content";
        return size + "dp";
    }

    private static String typeToTag(int type) {
        switch (type) {
            case 0: return "LinearLayout";
            case 2: return "RelativeLayout";
            case 3: return "Button";
            case 4: return "TextView";
            case 5: return "EditText";
            case 6: return "ImageView";
            case 7: return "WebView";
            case 8: return "ProgressBar";
            case 9: return "ListView";
            case 10: return "Spinner";
            case 11: return "CheckBox";
            case 12: return "ScrollView";
            case 13: return "Switch";
            case 14: return "SeekBar";
            case 15: return "CalendarView";
            case 16: return "FloatingActionButton";
            case 17: return "AdView";
            case 18: return "MapView";
            case 19: return "RadioButton";
            case 20: return "RadioGroup";
            case 29: return "com.google.android.material.button.MaterialButton";
            case 30: return "com.google.android.material.card.MaterialCardView";
            case 39: return "androidx.recyclerview.widget.RecyclerView";
            case 42: return "androidx.cardview.widget.CardView";
            case 43: return "androidx.constraintlayout.widget.ConstraintLayout";
            default: return "LinearLayout";
        }
    }

    private static String defaultRoot() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
                + "    android:layout_width=\"match_parent\"\n"
                + "    android:layout_height=\"match_parent\"\n"
                + "    android:orientation=\"vertical\" />\n";
    }

    private static String repeat(String s, int n) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++) b.append(s);
        return b.toString();
    }
}
