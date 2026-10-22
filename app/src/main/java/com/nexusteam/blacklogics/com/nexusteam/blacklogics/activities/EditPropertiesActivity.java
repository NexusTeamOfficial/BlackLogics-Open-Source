package com.nexusteam.blacklogics.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Android Studio–style Edit Properties.
 *
 * Attributes are NOT hardcoded. They come from the editor schema list built for
 * the selected View (AttributeStore.buildAttributeListForView) + live AttributeSet
 * values + any custom attrs already applied.
 *
 * Extras:
 *   EXTRA_VIEW_ID, EXTRA_CLASS_NAME, EXTRA_CONVERT_CLASS
 *   EXTRA_ATTR_MAP_JSON     – live name→value from AttributeSet
 *   EXTRA_SCHEMA_LIST_JSON  – ArrayList&lt;HashMap&gt; attribute schemas for this view
 *   EXTRA_CUSTOM_ATTRS      – optional multi-line summary of free-form customs
 */
public class EditPropertiesActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final String EXTRA_VIEW_ID = "view_id";
    public static final String EXTRA_CLASS_NAME = "class_name";
    public static final String EXTRA_CONVERT_CLASS = "convert_class";
    public static final String EXTRA_ATTR_MAP_JSON = "attr_map_json";
    public static final String EXTRA_SCHEMA_LIST_JSON = "schema_list_json";
    public static final String EXTRA_CUSTOM_ATTRS = "custom_attrs";

    public static final String RESULT_ACTION = "action";
    public static final String RESULT_ATTR_NAME = "attr_name";
    public static final String RESULT_ATTR_DISPLAY = "attr_display";
    public static final String RESULT_ATTR_META_JSON = "attr_meta_json";
    public static final String RESULT_ATTR_VALUE = "attr_value";

    /** Live apply without closing activity — registered by ViewEditor while open. */
    public interface LiveApplyListener {
        void onApplyAttribute(String attrName, String value, java.util.HashMap<String, Object> meta);
        void onRequestAction(String action); // id / custom_attrs / convert
        /** Open AttributeDialogs on editor for full type handling (size/color/enum). */
        void onEditAttributeWithDialog(String attrName, java.util.HashMap<String, Object> meta);
    }
    private static java.lang.ref.WeakReference<LiveApplyListener> sLiveListener;
    public static void setLiveApplyListener(LiveApplyListener l) {
        sLiveListener = l != null ? new java.lang.ref.WeakReference<LiveApplyListener>(l) : null;
    }
    private static LiveApplyListener live() {
        return sLiveListener != null ? sLiveListener.get() : null;
    }

    private String viewId;
    private String className;
    private String convertClass;
    private String customAttrsSummary;
    private final Map<String, String> liveValues = new LinkedHashMap<>();
    private final List<HashMap<String, Object>> schemas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_properties);

        Intent in = getIntent();
        viewId = str(in, EXTRA_VIEW_ID, "view");
        className = str(in, EXTRA_CLASS_NAME, "android.view.View");
        convertClass = str(in, EXTRA_CONVERT_CLASS, className);
        customAttrsSummary = in != null ? in.getStringExtra(EXTRA_CUSTOM_ATTRS) : null;

        parseLiveMap(in != null ? in.getStringExtra(EXTRA_ATTR_MAP_JSON) : null);
        parseSchemas(in != null ? in.getStringExtra(EXTRA_SCHEMA_LIST_JSON) : null);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Properties");
        toolbar.setSubtitle(viewId + "  ·  " + simpleClass(className));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { finish(); }
        });

        RecyclerView rv = findViewById(R.id.rv_properties);
        rv.setLayoutManager(new LinearLayoutManager(this));
        currentRows = buildRows();
        rv.setAdapter(new PropertiesAdapter(currentRows, new PropertiesAdapter.Listener() {
            @Override public void onRowClick(PropRow row) { handleClick(row); }
        }));
    }

    @Override
    protected void onDestroy() {
        setLiveApplyListener(null);
        super.onDestroy();
    }

    private static String str(Intent in, String key, String def) {
        if (in == null) return def;
        String v = in.getStringExtra(key);
        return v != null && v.length() > 0 ? v : def;
    }

    private static String simpleClass(String cn) {
        if (cn == null) return "";
        int i = cn.lastIndexOf('.');
        return i >= 0 ? cn.substring(i + 1) : cn;
    }

    private void parseLiveMap(String json) {
        liveValues.clear();
        if (json == null || json.trim().isEmpty()) return;
        try {
            Type t = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> m = new Gson().fromJson(json.trim(), t);
            if (m != null) liveValues.putAll(m);
        } catch (Exception ignored) {}
    }

    private void parseSchemas(String json) {
        schemas.clear();
        if (json == null || json.trim().isEmpty()) return;
        try {
            Type t = new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType();
            ArrayList<HashMap<String, Object>> list = new Gson().fromJson(json.trim(), t);
            if (list != null) schemas.addAll(list);
        } catch (Exception ignored) {}
    }

    private String live(String attrName) {
        if (attrName == null) return "";
        String v = liveValues.get(attrName);
        return v != null ? v : "";
    }

    /**
     * Build rows purely from schema + live AttributeSet (no hard-coded attr list).
     */
    private List<PropRow> buildRows() {
        List<PropRow> rows = new ArrayList<>();

        // —— Always: structural actions ——
        rows.add(PropRow.section("Identity"));
        rows.add(PropRow.action("ID", viewId, "id", R.drawable.ic_prop_id));
        rows.add(PropRow.action(
                "Custom attributes",
                TextUtils.isEmpty(customAttrsSummary)
                        ? (countCustomLive() > 0 ? countCustomLive() + " applied" : "Add free-form XML attrs")
                        : customAttrsSummary,
                "custom_attrs", R.drawable.ic_prop_code));
        rows.add(PropRow.action("Convert", convertClass, "convert", R.drawable.ic_prop_convert));

        // —— Partition schemas: layout_* vs view vs other ——
        List<HashMap<String, Object>> layoutSchemas = new ArrayList<>();
        List<HashMap<String, Object>> viewSchemas = new ArrayList<>();
        Set<String> schemaNames = new HashSet<>();

        for (HashMap<String, Object> meta : schemas) {
            if (meta == null) continue;
            Object an = meta.get("attribute_name");
            if (an == null) continue;
            String name = String.valueOf(an);
            schemaNames.add(name);
            boolean layout = Boolean.TRUE.equals(meta.get("layout_params"))
                    || name.contains("layout_")
                    || name.endsWith(":layout_width")
                    || name.endsWith(":layout_height")
                    || name.endsWith(":layout_margin")
                    || name.contains("layout_margin")
                    || name.contains("layout_gravity")
                    || name.contains("layout_weight")
                    || name.contains("layout_constraint")
                    || name.contains("layout_scrollFlags")
                    || name.contains("layout_collapse")
                    || name.contains("layout_behavior");
            if (layout) layoutSchemas.add(meta);
            else viewSchemas.add(meta);
        }

        if (!layoutSchemas.isEmpty()) {
            rows.add(PropRow.section("Layout properties"));
            for (HashMap<String, Object> meta : layoutSchemas) {
                rows.add(rowFromSchema(meta));
            }
        }

        if (!viewSchemas.isEmpty()) {
            rows.add(PropRow.section(simpleClass(className) + " properties"));
            for (HashMap<String, Object> meta : viewSchemas) {
                rows.add(rowFromSchema(meta));
            }
        }

        // —— Live attrs that are NOT in schema = custom / applied extras ——
        List<Map.Entry<String, String>> customs = new ArrayList<>();
        for (Map.Entry<String, String> e : liveValues.entrySet()) {
            String k = e.getKey();
            if (k == null) continue;
            if ("android:id".equals(k)) continue;
            if (schemaNames.contains(k)) continue;
            customs.add(e);
        }
        if (!customs.isEmpty()) {
            rows.add(PropRow.section("Applied custom attributes"));
            for (Map.Entry<String, String> e : customs) {
                rows.add(PropRow.attr(
                        pretty(e.getKey()),
                        e.getValue() != null ? e.getValue() : "",
                        e.getKey(),
                        R.drawable.ic_prop_attr,
                        null));
            }
        }

        if (layoutSchemas.isEmpty() && viewSchemas.isEmpty() && customs.isEmpty()) {
            rows.add(PropRow.section("No schema attributes"));
            rows.add(PropRow.action(
                    "Add custom attribute",
                    "This view class has no registered schema entries",
                    "custom_attrs", R.drawable.ic_prop_code));
        }

        return rows;
    }

    private int countCustomLive() {
        int n = 0;
        for (String k : liveValues.keySet()) {
            if (k == null || "android:id".equals(k)) continue;
            n++;
        }
        return n;
    }

    private PropRow rowFromSchema(HashMap<String, Object> meta) {
        String attrName = String.valueOf(meta.get("attribute_name"));
        Object display = meta.get("name");
        String title = display != null ? String.valueOf(display) : pretty(attrName);
        String value = live(attrName);
        if (value.isEmpty()) {
            // sensible placeholders by type
            Object type = meta.get("argument_type");
            String t = type != null ? String.valueOf(type) : "";
            if ("size".equals(t)) value = "—";
            else if ("boolean".equals(t)) value = "—";
            else value = "not set";
        }
        int icon = iconFor(attrName, meta);
        return PropRow.attr(title, value, attrName, icon, meta);
    }

    private static int iconFor(String attrName, HashMap<String, Object> meta) {
        if (attrName == null) return R.drawable.ic_prop_attr;
        String n = attrName.toLowerCase(Locale.US);
        if (n.contains("width")) return R.drawable.ic_prop_width;
        if (n.contains("height")) return R.drawable.ic_prop_height;
        if (n.contains("padding")) return R.drawable.ic_prop_padding;
        if (n.contains("margin")) return R.drawable.ic_prop_margin;
        if (n.contains("gravity")) return R.drawable.ic_prop_gravity;
        if (n.contains("weight")) return R.drawable.ic_prop_weight;
        if (n.contains("background") || n.contains("src") || n.contains("tint"))
            return R.drawable.ic_prop_bg;
        if (n.contains("text") || n.contains("hint")) return R.drawable.ic_prop_view;
        return R.drawable.ic_prop_attr;
    }

    private static String pretty(String attr) {
        if (attr == null) return "";
        int c = attr.indexOf(':');
        String name = c >= 0 ? attr.substring(c + 1) : attr;
        name = name.replace('_', ' ');
        if (name.isEmpty()) return attr;
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }


    private PropertiesAdapter propertiesAdapter;
    private List<PropRow> currentRows = new ArrayList<>();

    // onCreate already sets adapter — we need to keep reference. Patch via field assignment in onCreate later.

    private void handleClick(PropRow row) {
        if (row == null || row.type == PropRow.TYPE_SECTION) return;

        if (!"attr".equals(row.action)) {
            LiveApplyListener L = live();
            if (L != null) {
                L.onRequestAction(row.action);
                // stay open for custom_attrs after host opens dialog? close for id/convert is ok
                if ("id".equals(row.action) || "convert".equals(row.action)
                        || "custom_attrs".equals(row.action)) {
                    // apply on host side; stay here except custom can stay
                    android.widget.Toast.makeText(this,
                            "Opened from editor — keep editing properties here",
                            android.widget.Toast.LENGTH_SHORT).show();
                }
            } else {
                Intent data = new Intent();
                data.putExtra(RESULT_ACTION, row.action);
                setResult(RESULT_OK, data);
                finish();
            }
            return;
        }
        promptSetAttribute(row);
    }

    private void promptSetAttribute(final PropRow row) {
        final String attrName = row.attrName;
        final String current = row.subtitle != null
                && !"not set".equals(row.subtitle)
                && !"—".equals(row.subtitle) ? row.subtitle : "";

        String argType = "";
        if (row.meta != null && row.meta.get("argument_type") != null) {
            argType = String.valueOf(row.meta.get("argument_type"));
        }

        // enum / flag from schema xml_arguments
        String[] choices = extractChoices(row.meta);
        if (("enum".equals(argType) || "flag".equals(argType)) && choices != null && choices.length > 0) {
            showChoiceDialog(row, choices, current);
            return;
        }
        // visibility hard-fallback
        if (attrName != null && attrName.endsWith(":visibility")) {
            showChoiceDialog(row, new String[]{"visible", "invisible", "gone"}, current);
            return;
        }

        if ("boolean".equals(argType)) {
            showChoiceDialog(row, new String[]{"true", "false"}, current);
            return;
        }

        if ("size".equals(argType)
                || (attrName != null && (attrName.endsWith("layout_width")
                || attrName.endsWith("layout_height")))) {
            showSizeDialog(row, current);
            return;
        }

        // free-form
        final android.widget.EditText input = new android.widget.EditText(this);
        input.setSingleLine(true);
        input.setText(current);
        input.setHint(attrName);
        int pad = (int) (20 * getResources().getDisplayMetrics().density);
        input.setPadding(pad, pad / 2, pad, pad / 2);
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                .setTitle(row.title != null ? row.title : attrName)
                .setMessage(attrName)
                .setView(input)
                .setPositiveButton("Apply", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface d, int w) {
                        String v = input.getText() != null
                                ? input.getText().toString().trim() : "";
                        applyAttrLive(row, v);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String[] extractChoices(HashMap<String, Object> meta) {
        if (meta == null) return null;
        Object xml = meta.get("xml_arguments");
        if (xml instanceof java.util.List) {
            java.util.List list = (java.util.List) xml;
            if (list.isEmpty()) return null;
            String[] out = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                out[i] = list.get(i) != null ? String.valueOf(list.get(i)) : "";
            }
            return out;
        }
        return null;
    }

    private void showChoiceDialog(final PropRow row, final String[] items, String current) {
        int checked = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i].equalsIgnoreCase(current)) { checked = i; break; }
        }
        final int[] selected = new int[] { checked >= 0 ? checked : 0 };
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                .setTitle(row.title != null ? row.title : row.attrName)
                .setSingleChoiceItems(items, selected[0],
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(android.content.DialogInterface d, int which) {
                                selected[0] = which;
                            }
                        })
                .setPositiveButton("Apply", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface d, int w) {
                        int which = selected[0];
                        if (which < 0 || which >= items.length) which = 0;
                        applyAttrLive(row, items[which]);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showSizeDialog(final PropRow row, String current) {
        final android.widget.EditText custom = new android.widget.EditText(this);
        custom.setHint("e.g. 48dp");
        custom.setSingleLine(true);
        String cur = current != null ? current : "";
        boolean isCustom = cur.length() > 0
                && !"match_parent".equals(cur)
                && !"wrap_content".equals(cur)
                && !"fill_parent".equals(cur);
        if (isCustom) custom.setText(cur);
        int pad = (int) (20 * getResources().getDisplayMetrics().density);
        custom.setPadding(pad, pad / 2, pad, pad / 2);
        final String[] items = new String[] {
                "match_parent", "wrap_content", "fill_parent", "Custom…"
        };
        int checked = "match_parent".equals(cur) ? 0
                : ("wrap_content".equals(cur) ? 1
                : ("fill_parent".equals(cur) ? 2 : 3));
        final int[] selected = new int[] { checked };
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                .setTitle(row.title != null ? row.title : row.attrName)
                .setSingleChoiceItems(items, checked,
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(android.content.DialogInterface d, int which) {
                                selected[0] = which;
                            }
                        })
                .setView(custom)
                .setPositiveButton("Apply", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface d, int w) {
                        int which = selected[0];
                        if (which == 0) applyAttrLive(row, "match_parent");
                        else if (which == 1) applyAttrLive(row, "wrap_content");
                        else if (which == 2) applyAttrLive(row, "fill_parent");
                        else {
                            String v = custom.getText() != null
                                    ? custom.getText().toString().trim() : "";
                            if (v.length() == 0) v = "wrap_content";
                            applyAttrLive(row, v);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Apply attribute to editor WITHOUT closing this activity.
     */
    private void applyAttrLive(PropRow row, String value) {
        if (row == null || row.attrName == null) return;
        String v = value != null ? value : "";
        liveValues.put(row.attrName, v);

        LiveApplyListener L = live();
        if (L != null) {
            L.onApplyAttribute(row.attrName, v, row.meta);
        } else {
            // Fallback: single-shot result (first apply only if no listener)
            Intent data = new Intent();
            data.putExtra(RESULT_ACTION, "attr");
            data.putExtra(RESULT_ATTR_NAME, row.attrName);
            data.putExtra(RESULT_ATTR_DISPLAY, row.title);
            data.putExtra(RESULT_ATTR_VALUE, v);
            if (row.meta != null) {
                try {
                    data.putExtra(RESULT_ATTR_META_JSON, new Gson().toJson(row.meta));
                } catch (Exception ignored) {}
            }
            setResult(RESULT_OK, data);
        }

        // Stay open — refresh list values
        refreshRows();
        android.widget.Toast.makeText(this, row.attrName + " = " + v,
                android.widget.Toast.LENGTH_SHORT).show();
    }

    private void refreshRows() {
        currentRows = buildRows();
        RecyclerView rv = findViewById(R.id.rv_properties);
        if (rv != null && rv.getAdapter() instanceof PropertiesAdapter) {
            PropertiesAdapter a = (PropertiesAdapter) rv.getAdapter();
            a.replaceRows(currentRows);
        }
    }

    // -------------------------------------------------------------------------

    static final class PropRow {
        static final int TYPE_SECTION = 0;
        static final int TYPE_ITEM = 1;

        final int type;
        final String title;
        final String subtitle;
        final String action;
        final int iconRes;
        final String attrName;
        final HashMap<String, Object> meta;

        private PropRow(int type, String title, String subtitle, String action,
                        int iconRes, String attrName, HashMap<String, Object> meta) {
            this.type = type;
            this.title = title;
            this.subtitle = subtitle;
            this.action = action;
            this.iconRes = iconRes;
            this.attrName = attrName;
            this.meta = meta;
        }

        static PropRow section(String title) {
            return new PropRow(TYPE_SECTION, title, null, null, 0, null, null);
        }

        static PropRow action(String title, String subtitle, String action, int icon) {
            return new PropRow(TYPE_ITEM, title, subtitle, action, icon, null, null);
        }

        static PropRow attr(String title, String subtitle, String attrName, int icon,
                            HashMap<String, Object> meta) {
            return new PropRow(TYPE_ITEM, title, subtitle, "attr", icon, attrName, meta);
        }
    }

    static final class PropertiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        interface Listener { void onRowClick(PropRow row); }

        private final List<PropRow> rows = new ArrayList<PropRow>();
        private final Listener listener;

        PropertiesAdapter(List<PropRow> rows, Listener listener) {
            if (rows != null) this.rows.addAll(rows);
            this.listener = listener;
        }

        void replaceRows(List<PropRow> next) {
            this.rows.clear();
            if (next != null) this.rows.addAll(next);
            notifyDataSetChanged();
        }

        @Override public int getItemViewType(int position) { return rows.get(position).type; }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == PropRow.TYPE_SECTION) {
                View v = android.view.LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_property_section, parent, false);
                return new SectionVH(v);
            }
            View v = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_property_row, parent, false);
            return new ItemVH(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final PropRow row = rows.get(position);
            if (holder instanceof SectionVH) {
                ((SectionVH) holder).title.setText(row.title);
                return;
            }
            ItemVH h = (ItemVH) holder;
            h.title.setText(row.title);
            h.subtitle.setText(row.subtitle != null ? row.subtitle : "");
            try { h.icon.setImageResource(row.iconRes); } catch (Exception e) {
                h.icon.setImageDrawable(null);
            }

            String sub = row.subtitle;
            if (sub != null && sub.startsWith("#") && sub.length() >= 7) {
                try {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setShape(GradientDrawable.RECTANGLE);
                    float r = 6f * holder.itemView.getResources().getDisplayMetrics().density;
                    gd.setCornerRadius(r);
                    gd.setColor(Color.parseColor(sub.trim()));
                    gd.setStroke((int) (1 * holder.itemView.getResources().getDisplayMetrics().density), 0x33000000);
                    h.swatch.setBackground(gd);
                    h.swatch.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    h.swatch.setVisibility(View.GONE);
                }
            } else {
                h.swatch.setVisibility(View.GONE);
            }

            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (listener != null) listener.onRowClick(row);
                }
            });
        }

        @Override public int getItemCount() { return rows.size(); }

        static class SectionVH extends RecyclerView.ViewHolder {
            final TextView title;
            SectionVH(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_section);
            }
        }

        static class ItemVH extends RecyclerView.ViewHolder {
            final ImageView icon;
            final TextView title;
            final TextView subtitle;
            final View swatch;
            ItemVH(View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.img_icon);
                title = itemView.findViewById(R.id.tv_title);
                subtitle = itemView.findViewById(R.id.tv_subtitle);
                swatch = itemView.findViewById(R.id.preview_swatch);
            }
        }
    }
}
