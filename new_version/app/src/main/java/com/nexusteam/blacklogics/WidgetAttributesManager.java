package com.nexusteam.blacklogics;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WidgetAttributesManager {
    private static final String TAG = "WidgetAttributesManager";
    private final String jsonFilePath;
    private JSONObject projectJson;  // In-memory JSON root
    private final String projectName;
    private final String projectId;
    private final String packageName;

    // In-memory cache (synced with your widgetCustomAttributes)
    private final Map<String, Map<String, Map<String, String>>> activityWidgetsCache = new HashMap<>();  // activityName -> widgetId -> attrName -> value

    public WidgetAttributesManager(Context context, String projectPath, String projectName, String projectId, String packageName) {
        this.jsonFilePath = projectPath + "/all_widgets_attributes.json";
        this.projectName = projectName;
        this.projectId = projectId;
        this.packageName = packageName;
        loadFromJson();  // Load on init
    }

    /**
     * Load all data from JSON file into memory.
     */
    public void loadFromJson() {
        try {
            if (FileUtil.isExistFile(jsonFilePath)) {
                String jsonString = FileUtil.readFile(jsonFilePath);
                projectJson = new JSONObject(jsonString);
                syncCacheFromJson();
                Log.d(TAG, "Loaded attributes from: " + jsonFilePath);
            } else {
                initEmptyJson();
                Log.d(TAG, "No JSON file found, initialized empty.");
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error loading JSON: " + e.getMessage());
            initEmptyJson();
        }
    }

    /**
     * Save or update an attribute for a widget in a specific activity.
     * Creates activity/widget if not exists.
     */
    public void saveAttribute(String activityName, String widgetId, String attributeName, String value) {
        try {
            JSONArray activities = projectJson.optJSONArray("activities");
            if (activities == null) {
                activities = new JSONArray();
                projectJson.put("activities", activities);
            }

            JSONObject activityObj = findActivityJson(activityName);
            if (activityObj == null) {
                activityObj = createActivityJson(activityName, "layout_" + activityName.toLowerCase(), activityName.equals("MainActivity"));
                activities.put(activityObj);
            }

            JSONObject widgets = activityObj.optJSONObject("widgets");
            if (widgets == null) {
                widgets = new JSONObject();
                activityObj.put("widgets", widgets);
            }

            JSONObject widgetAttrs = widgets.optJSONObject(widgetId);
            if (widgetAttrs == null) {
                widgetAttrs = new JSONObject();
                widgets.put(widgetId, widgetAttrs);
            }

            widgetAttrs.put(attributeName, value);

            // Update cache
            updateCache(activityName, widgetId, attributeName, value);

            saveToJson();  // Persist to file
            Log.d(TAG, "Saved attribute: " + activityName + " > " + widgetId + " > " + attributeName + " = " + value);
        } catch (JSONException e) {
            Log.e(TAG, "Error saving attribute: " + e.getMessage());
        }
    }

    /**
     * Get an attribute value for a widget.
     * Returns defaultValue if not found.
     */
    public String getAttribute(String activityName, String widgetId, String attributeName, String defaultValue) {
        Map<String, Map<String, String>> widgets = activityWidgetsCache.get(activityName);
        if (widgets != null) {
            Map<String, String> attrs = widgets.get(widgetId);
            if (attrs != null && attrs.containsKey(attributeName)) {
                return attrs.get(attributeName);
            }
        }
        return defaultValue;
    }

    /**
     * Delete a widget and its attributes from an activity.
     */
    public void deleteWidget(String activityName, String widgetId) {
        try {
            JSONObject activityObj = findActivityJson(activityName);
            if (activityObj != null) {
                JSONObject widgets = activityObj.optJSONObject("widgets");
                if (widgets != null) {
                    widgets.remove(widgetId);
                }

                // Update cache
                if (activityWidgetsCache.containsKey(activityName)) {
                    activityWidgetsCache.get(activityName).remove(widgetId);
                }

                saveToJson();
                Log.d(TAG, "Deleted widget: " + activityName + " > " + widgetId);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error deleting widget: " + e.getMessage());
        }
    }

    /**
     * Delete an entire activity.
     */
    public void deleteActivity(String activityName) {
        try {
            JSONArray activities = projectJson.optJSONArray("activities");
            if (activities != null) {
                for (int i = 0; i < activities.length(); i++) {
                    JSONObject act = activities.getJSONObject(i);
                    if (act.optString("activity_name").equals(activityName)) {
                        activities.remove(i);
                        break;
                    }
                }
            }

            // Update cache
            activityWidgetsCache.remove(activityName);

            saveToJson();
            Log.d(TAG, "Deleted activity: " + activityName);
        } catch (JSONException e) {
            Log.e(TAG, "Error deleting activity: " + e.getMessage());
        }
    }

    /**
     * Save all in-memory data to JSON file.
     */
    public void saveToJson() {
        try {
            String jsonString = projectJson.toString(4);  // Pretty print
            FileUtil.writeFile(jsonFilePath, jsonString);
            Log.d(TAG, "Saved all attributes to: " + jsonFilePath);
        } catch (JSONException e) {
            Log.e(TAG, "Error saving JSON: " + e.getMessage());
        }
    }

    // Helper: Initialize empty JSON
    private void initEmptyJson() {
        projectJson = new JSONObject();
        try {
            projectJson.put("project_name", projectName);
            projectJson.put("project_id", projectId);
            projectJson.put("package_name", packageName);
            projectJson.put("created_date", new Date().toString());
            projectJson.put("activities", new JSONArray());
        } catch (JSONException ignored) {}
    }

    // Helper: Find activity JSON object by name
    private JSONObject findActivityJson(String activityName) {
        try {
            JSONArray activities = projectJson.optJSONArray("activities");
            if (activities != null) {
                for (int i = 0; i < activities.length(); i++) {
                    JSONObject act = activities.getJSONObject(i);
                    if (act.optString("activity_name").equals(activityName)) {
                        return act;
                    }
                }
            }
        } catch (JSONException ignored) {}
        return null;
    }

    // Helper: Create new activity JSON
    private JSONObject createActivityJson(String activityName, String layoutName, boolean isMain) {
        JSONObject activityJson = new JSONObject();
        try {
            activityJson.put("activity_name", activityName);
            activityJson.put("layout_name", layoutName);
            activityJson.put("is_main_activity", isMain);
            activityJson.put("widgets", new JSONObject());
        } catch (JSONException ignored) {}
        return activityJson;
    }

    // Helper: Sync JSON to cache
    private void syncCacheFromJson() {
        activityWidgetsCache.clear();
        try {
            JSONArray activities = projectJson.optJSONArray("activities");
            if (activities != null) {
                for (int i = 0; i < activities.length(); i++) {
                    JSONObject act = activities.getJSONObject(i);
                    String actName = act.optString("activity_name");
                    JSONObject widgets = act.optJSONObject("widgets");
                    if (widgets != null) {
                        Map<String, Map<String, String>> widgetMap = new HashMap<>();
                        Iterator<String> widgetKeys = widgets.keys();
                        while (widgetKeys.hasNext()) {
                            String widgetId = widgetKeys.next();
                            JSONObject attrsJson = widgets.getJSONObject(widgetId);
                            Map<String, String> attrs = new HashMap<>();
                            Iterator<String> attrKeys = attrsJson.keys();
                            while (attrKeys.hasNext()) {
                                String attrName = attrKeys.next();
                                attrs.put(attrName, attrsJson.optString(attrName));
                            }
                            widgetMap.put(widgetId, attrs);
                        }
                        activityWidgetsCache.put(actName, widgetMap);
                    }
                }
            }
        } catch (JSONException ignored) {}
    }

private void updateCache(String activityName, String widgetId, String attributeName, String value) {

    // Get or create activity map
    Map<String, Map<String, String>> widgetMap = activityWidgetsCache.get(activityName);
    if (widgetMap == null) {
        widgetMap = new HashMap<String, Map<String, String>>();
        activityWidgetsCache.put(activityName, widgetMap);
    }

    // Get or create widget attribute map
    Map<String, String> attributeMap = widgetMap.get(widgetId);
    if (attributeMap == null) {
        attributeMap = new HashMap<String, String>();
        widgetMap.put(widgetId, attributeMap);
    }

    // Put the attribute value
    attributeMap.put(attributeName, value);

    // Sync to global widgetCustomAttributes
   // ViewEditorFragmentActivity.widgetCustomAttributes.put(widgetId, attributeMap);
}

// Expose cache if needed (e.g., for quick access without JSON)
public Map<String, Map<String, String>> getWidgetsForActivity(String activityName) {
    Map<String, Map<String, String>> widgets = activityWidgetsCache.get(activityName);
    return (widgets != null) ? widgets : new HashMap<String, Map<String, String>>();
}

}