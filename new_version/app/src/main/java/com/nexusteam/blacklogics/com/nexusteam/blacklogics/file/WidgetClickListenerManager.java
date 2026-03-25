package com.besome.blacklogics.file;

import android.util.ArrayMap;
import android.util.Base64;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.besome.blacklogics.*;
import com.nexusteam.blacklogics.*;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
public class WidgetClickListenerManager {

    private final Map<String, List<WidgetClickListener>> listenerMap;
    private final Gson gson;
    private static WidgetClickListenerManager instance;


    private WidgetClickListenerManager() {
        listenerMap = new ArrayMap<>();
        gson = new Gson();
    }


    public static synchronized WidgetClickListenerManager getInstance() {
        if (instance == null) {
            instance = new WidgetClickListenerManager();
        }
        return instance;
    }


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


    public void addClickListener(String activityName, String widgetId, String logic) {
        List<WidgetClickListener> listeners = listenerMap.get(activityName);
        if (listeners == null) {
            listeners = new ArrayList<>();
            listenerMap.put(activityName, listeners);
        }

        for (WidgetClickListener listener : listeners) {
            if (listener.getWidgetId().equals(widgetId)) {
                listeners.remove(listener);
                break;
            }
        }
        listeners.add(new WidgetClickListener(widgetId, logic));

        saveListenersToFile();
    }


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


    public void removeClickListener(String activityName, String widgetId) {
        List<WidgetClickListener> listeners = listenerMap.get(activityName);
        if (listeners != null) {
       
        }
    }


    public List<WidgetClickListener> getClickListeners(String activityName) {
        return null;
    }


    public String generateClickListenerCode(String activityName) {
        StringBuilder code = new StringBuilder();
        List<WidgetClickListener> listeners = getClickListeners(activityName);

        for (WidgetClickListener listener : listeners) {
            String widgetId = listener.getWidgetId();
            String logic = listener.getLogic();


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


    private void saveListenersToFile() {
        try {
            String json = gson.toJson(listenerMap);
            String encodedJson = Base64.encodeToString(json.getBytes(), Base64.DEFAULT);
            String savePath = ViewEditorFragmentActivity.projectPath + "/listeners/project_listeners.json";
            FileUtil.writeFile(savePath, encodedJson);
        } catch (Exception e) {

        }
    }


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

        }
    }


    public void clearListeners(String activityName) {
        listenerMap.remove(activityName);
        saveListenersToFile(); // Update single JSON file
    }


    public void clearAllListeners() {
        listenerMap.clear();

        String filePath = ViewEditorFragmentActivity.projectPath + "/listeners/project_listeners.json";
        FileUtil.deleteFile(filePath);
    }
}