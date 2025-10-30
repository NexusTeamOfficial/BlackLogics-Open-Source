package com.nexusteam.blacklogics.util;

import android.util.Base64;
import android.util.Log;

import com.besome.blacklogics.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class WidgetInfoManager {

    private static final String TAG = "WidgetInfoManager";

    /**
     * Save widget info for an activity.
     *
     * @param scId         Project SC_ID (from DesignActivity)
     * @param activityName Activity name (e.g. "MainActivity")
     * @param widgetType   Widget type (e.g. "TextView", "Button")
     * @param widgetId     Widget unique ID
     */
    public static void saveWidgetInfo(String scId, String activityName, String widgetType, String widgetId) {
        try {
            String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + scId;
            String widgetPath = projectPath + "/widget_info/project_widgets.json";

            FileUtil.makeDir(projectPath + "/widget_info/");

            // Read existing widgets
            Map<String, Map<String, String>> widgetMap = new HashMap<>();
            if (FileUtil.isExistFile(widgetPath)) {
                String encodedJson = FileUtil.readFile(widgetPath);
                String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));
                Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
                widgetMap = new Gson().fromJson(decodedJson, mapType);
            }

            // Update widget info
            Map<String, String> activityWidgets = widgetMap.getOrDefault(activityName, new HashMap<>());
            activityWidgets.put(widgetType, widgetId);
            widgetMap.put(activityName, activityWidgets);

            // Save updated widget info
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(widgetMap);
            String encodedJson = Base64.encodeToString(prettyJson.getBytes(), Base64.DEFAULT);

            FileUtil.writeFile(widgetPath, encodedJson);

            Log.d(TAG, "Saved widget: " + activityName + " -> " + widgetType + " -> " + widgetId);

        } catch (Exception e) {
            Log.e(TAG, "Error saving widget info: " + e.getMessage());
        }
    }

    /**
     * Load widget info for an activity.
     *
     * @param scId         Project SC_ID
     * @param activityName Activity name
     * @return Map of widgetType to widgetId
     */
    public static Map<String, String> loadWidgetInfo(String scId, String activityName) {
        try {
            String projectPath = FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + scId;
            String widgetPath = projectPath + "/widget_info/project_widgets.json";

            if (!FileUtil.isExistFile(widgetPath)) {
                return new HashMap<>();
            }

            String encodedJson = FileUtil.readFile(widgetPath);
            String decodedJson = new String(Base64.decode(encodedJson, Base64.DEFAULT));

            Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
            Map<String, Map<String, String>> widgetMap = new Gson().fromJson(decodedJson, mapType);

            return widgetMap.getOrDefault(activityName, new HashMap<>());

        } catch (Exception e) {
            Log.e(TAG, "Error loading widget info: " + e.getMessage());
            return new HashMap<>();
        }
    }

}
