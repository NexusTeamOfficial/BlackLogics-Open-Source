package com.besome.blacklogics.file;

import android.util.ArrayMap;
import android.util.Base64;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.besome.blacklogics.*;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WidgetClickListenerManager {
    // Map to store widget click listeners for each activity
    private final Map<String, List<WidgetClickListener>> listenerMap;
    private final Gson gson;
    private static WidgetClickListenerManager instance;

    // Private constructor for singleton pattern
    private WidgetClickListenerManager() {
        listenerMap = new ArrayMap<>();
        gson = new Gson();
    }

    // Singleton instance
    public static synchronized WidgetClickListenerManager getInstance() {
        if (instance == null) {
            instance = new WidgetClickListenerManager();
        }
        return instance;
    }

    // Represents a single click listener
    public static class WidgetClickListener {
        private final String widgetId; // e.g., "textview1"
        private String logic;          // Logic or method name for the click listener

        public WidgetClickListener(String widgetId, String logic) {
            this.widgetId = widgetId;
            this.logic = logic;
        }

        public String getWidgetId() {
            return widgetId;
        }

        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }
    }

    // Add a click listener for a widget in a specific activity and save in real-time
    public void addClickListener(String activityName, String widgetId, String logic) {
        List<WidgetClickListener> listeners = listenerMap.get(activityName);
        if (listeners == null) {
            listeners = new ArrayList<>();
            listenerMap.put(activityName, listeners);
        }
        // Avoid duplicates
        for (WidgetClickListener listener : listeners) {
            if (listener.getWidgetId().equals(widgetId)) {
                listeners.remove(listener);
                break;
            }
        }
        listeners.add(new WidgetClickListener(widgetId, logic));
        // Save to single JSON file in real-time
        saveListenersToFile();
    }

    // Update the logic for an existing click listener and save in real-time
    public void updateClickListenerLogic(String activityName, String widgetId, String newLogic) {
        List<WidgetClickListener> listeners = listenerMap.get(activityName);
        if (listeners != null) {
            for (WidgetClickListener listener : listeners) {
                if (listener.getWidgetId().equals(widgetId)) {
                    listener.setLogic(newLogic);
                    saveListenersToFile();
                    break;
                }
            }
        }
    }

    // Remove a click listener for a widget and save in real-time
    public void removeClickListener(String activityName, String widgetId) {
        List<WidgetClickListener> listeners = listenerMap.get(activityName);
        if (listeners != null) {
            listeners.removeIf(listener -> listener.getWidgetId().equals(widgetId));
            saveListenersToFile();
        }
    }

    // Get all click listeners for an activity
    public List<WidgetClickListener> getClickListeners(String activityName) {
        return listenerMap.getOrDefault(activityName, new ArrayList<>());
    }

    // Generate click listener code for an activity
    public String generateClickListenerCode(String activityName) {
        StringBuilder code = new StringBuilder();
        List<WidgetClickListener> listeners = getClickListeners(activityName);

        for (WidgetClickListener listener : listeners) {
            String widgetId = listener.getWidgetId();
            String logic = listener.getLogic();

            // Generate findViewById and setOnClickListener code
            /*code.append("        ")
                .append(ViewEditorFragmentActivity.useAndroidX ? "androidx.appcompat.widget.AppCompat" : "android.widget.")
                .append("View ").append(widgetId).append(" = findViewById(R.id.").append(widgetId).append(");\n");*/
            code.append("        ").append(widgetId).append(".setOnClickListener(new View.OnClickListener() {\n");
            code.append("            @Override\n");
            code.append("            public void onClick(View v) {\n");
            code.append("                ").append(logic).append(";\n");
            code.append("            }\n");
            code.append("        });\n\n");
        }

        return code.toString();
    }

    // Save all listeners to a single JSON file using Gson and Base64
    private void saveListenersToFile() {
        try {
            String json = gson.toJson(listenerMap);
            String encodedJson = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
            String savePath = ViewEditorFragmentActivity.projectPath + "/listeners/project_listeners.json";
            FileUtil.writeFile(savePath, encodedJson);
        } catch (Exception e) {
           // TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error saving listeners: " + e.toString());
        }
    }

    // Load listeners from the single JSON file using Gson and Base64
    public void loadListenersFromFile() {
        try {
            String filePath = ViewEditorFragmentActivity.projectPath + "/listeners/project_listeners.json";
            if (FileUtil.isExistFile(filePath)) {
                String encodedJson = FileUtil.readFile(filePath);
                String json = new String(Base64.decode(encodedJson, Base64.DEFAULT));
                Type mapType = new TypeToken<Map<String, List<WidgetClickListener>>>(){}.getType();
                Map<String, List<WidgetClickListener>> loadedMap = gson.fromJson(json, mapType);
                listenerMap.clear();
                if (loadedMap != null) {
                    listenerMap.putAll(loadedMap);
                }
            }
        } catch (Exception e) {
            //TheBlockLogicsUtil.showToast(TheBlockLogicsUtil.getContext(), "Error loading listeners: " + e.toString());
        }
    }

    // Clear listeners for an activity
    public void clearListeners(String activityName) {
        listenerMap.remove(activityName);
        saveListenersToFile(); // Update single JSON file
    }

    // Clear all listeners
    public void clearAllListeners() {
        listenerMap.clear();
        // Delete the single JSON file
        String filePath = ViewEditorFragmentActivity.projectPath + "/listeners/project_listeners.json";
        FileUtil.deleteFile(filePath);
    }
}