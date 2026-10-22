package com.nexusteam.blacklogics.sdk.layouteditor;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.nexusteam.blacklogics.activities.EditPropertiesActivity;
import com.nexusteam.blacklogics.sdk.AttributeSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Opens Edit Properties with the SAME attribute schemas the bottom sheet uses
 * (AttributeStore.buildAttributeListForView) + live values from AttributeSet.
 */
public final class EditPropertiesLauncher {

    public static final int REQ_EDIT_PROPERTIES = 4401;

    private EditPropertiesLauncher() {}

    public static void open(
            Activity activity,
            View target,
            IdManager idManager,
            AttributeStore store) {
        if (activity == null || target == null) return;

        String id = null;
        try {
            if (idManager != null) id = idManager.getId(target);
        } catch (Exception ignored) {}
        if (id == null || id.isEmpty()) id = "view";

        String className = target.getClass().getName();

        // Live values
        Map<String, String> map = new HashMap<>();
        if (store != null) {
            AttributeSet set = store.get(target);
            if (set != null) {
                for (Attribute a : set.getAttributes()) {
                    if (a == null || a.getName() == null) continue;
                    map.put(a.getName(), a.getValue() != null ? a.getValue() : "");
                }
            }
        }

        // Schema list — exactly what bottom sheet attribute chips use
        ArrayList<HashMap<String, Object>> schemaList = new ArrayList<>();
        if (store != null) {
            try {
                store.buildAttributeListForView(target, schemaList);
            } catch (Exception ignored) {}
        }

        // Custom summary: live attrs not present in schema names
        java.util.HashSet<String> schemaNames = new java.util.HashSet<>();
        for (HashMap<String, Object> m : schemaList) {
            if (m != null && m.get("attribute_name") != null) {
                schemaNames.add(String.valueOf(m.get("attribute_name")));
            }
        }
        StringBuilder custom = new StringBuilder();
        for (Map.Entry<String, String> e : map.entrySet()) {
            String k = e.getKey();
            if (k == null || "android:id".equals(k)) continue;
            if (schemaNames.contains(k)) continue;
            if (custom.length() > 0) custom.append('\n');
            custom.append(k).append("=\"").append(e.getValue()).append('"');
        }

        Gson gson = new Gson();
        Intent intent = new Intent(activity, EditPropertiesActivity.class);
        intent.putExtra(EditPropertiesActivity.EXTRA_VIEW_ID, id);
        intent.putExtra(EditPropertiesActivity.EXTRA_CLASS_NAME, className);
        intent.putExtra(EditPropertiesActivity.EXTRA_CONVERT_CLASS, className);
        intent.putExtra(EditPropertiesActivity.EXTRA_ATTR_MAP_JSON, gson.toJson(map));
        intent.putExtra(EditPropertiesActivity.EXTRA_SCHEMA_LIST_JSON, gson.toJson(schemaList));
        if (custom.length() > 0) {
            intent.putExtra(EditPropertiesActivity.EXTRA_CUSTOM_ATTRS, custom.toString());
        }
        activity.startActivityForResult(intent, REQ_EDIT_PROPERTIES);
    }


    public interface AttrApplyCallback {
        void applyAttribute(View view, String attrName, String value, HashMap<String, Object> meta);
        void showMessage(String msg);
    }

    public static boolean handleResult(
            int requestCode,
            int resultCode,
            Intent data,
            View target,
            AttributeDialogs dialogs,
            AttrApplyCallback applyCallback,
            Runnable onIdRequested,
            Runnable onCustomAttrs,
            Runnable onConvert) {
        if (requestCode != REQ_EDIT_PROPERTIES) return false;
        if (resultCode != Activity.RESULT_OK || data == null) return true;

        String action = data.getStringExtra(EditPropertiesActivity.RESULT_ACTION);
        if (action == null) return true;

        switch (action) {
            case "id":
                if (onIdRequested != null) onIdRequested.run();
                break;
            case "custom_attrs":
                if (onCustomAttrs != null) onCustomAttrs.run();
                else if (dialogs != null && target != null) dialogs.showCustomAttributesDialog(target);
                break;
            case "convert":
                if (onConvert != null) onConvert.run();
                else if (dialogs != null && target != null) {
                    HashMap<String, Object> meta = new HashMap<>();
                    meta.put("name", "Convert");
                    meta.put("attribute_name", "app:convertedWidget");
                    meta.put("argument_type", "String");
                    dialogs.showAttributeInputDialog(target, meta);
                }
                break;
            case "attr": {
                String attrName = data.getStringExtra(EditPropertiesActivity.RESULT_ATTR_NAME);
                if (target == null || attrName == null) break;
                String attrValue = data.getStringExtra(EditPropertiesActivity.RESULT_ATTR_VALUE);

                HashMap<String, Object> meta = null;
                String metaJson = data.getStringExtra(EditPropertiesActivity.RESULT_ATTR_META_JSON);
                if (metaJson != null) {
                    try {
                        meta = new Gson().fromJson(metaJson,
                                new com.google.gson.reflect.TypeToken<HashMap<String, Object>>() {}.getType());
                    } catch (Exception ignored) {}
                }
                if (meta == null) {
                    meta = new HashMap<>();
                    meta.put("attribute_name", attrName);
                    meta.put("name", data.getStringExtra(EditPropertiesActivity.RESULT_ATTR_DISPLAY));
                }

                if (attrValue != null && applyCallback != null) {
                    applyCallback.applyAttribute(target, attrName, attrValue, meta);
                    if (applyCallback != null) {
                        applyCallback.showMessage("Set " + attrName + " = " + attrValue);
                    }
                } else if (dialogs != null) {
                    if (attrName.contains("padding") && !attrName.contains("paddingLeft")) {
                        dialogs.showPaddingEditorDialog(target);
                    } else if (attrName.contains("layout_margin") && !attrName.contains("goneMargin")) {
                        dialogs.showMarginEditorDialog(target);
                    } else {
                        dialogs.showAttributeInputDialog(target, meta);
                    }
                }
                break;
            }
        }
        return true;
    }

    /** Backward-compatible overload (no AttrApplyCallback) */
    public static boolean handleResult(
            int requestCode,
            int resultCode,
            Intent data,
            View target,
            AttributeDialogs dialogs,
            Runnable onIdRequested,
            Runnable onCustomAttrs,
            Runnable onConvert,
            Runnable onCustomView) {
        return handleResult(requestCode, resultCode, data, target, dialogs,
                (AttrApplyCallback) null, onIdRequested, onCustomAttrs, onConvert);
    }
}
