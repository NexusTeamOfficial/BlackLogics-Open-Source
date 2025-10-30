package com.nexusteam.internal.os.layouteditor.util;

import android.content.Context;
import android.os.Environment;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * WidgetStorageManager
 * ---------------------
 * Author: NexusTeam
 *
 * Purpose:
 * Store and manage widget metadata (widgetId, className, activityName, sc_id)
 * for each project in JSON format under:
 * /storage/emulated/0/.blacklogics/data/{sc_id}/views.json
 */
public class WidgetStorageManager {

    private final Context context;
    private final String projectId;    // sc_id
    private final String activityName; // current activity name
    private static final String DIRECTORY_PATH = ".blacklogics/data/";
    private static final String FILE_NAME = "views.json";

    public WidgetStorageManager(Context context, String projectId, String activityName) {
        this.context = context;
        this.projectId = projectId;
        this.activityName = activityName;
    }

    /**
     * Returns the JSON storage file for widgets
     */
    private File getStorageFile() {
        File baseDir = new File(Environment.getExternalStorageDirectory(),
                DIRECTORY_PATH + projectId);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        return new File(baseDir, FILE_NAME);
    }

    /**
     * Reads JSON file and returns JSONArray
     */
    private JSONArray readJsonArray(File file) {
        try {
            if (!file.exists()) return new JSONArray();
            StringBuilder builder = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            return new JSONArray(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    /**
     * Saves widget metadata into views.json
     */
    public void saveWidgetMetadata(View widget, String id) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("widget_id", id);
            jsonObject.put("class", widget.getClass().getSimpleName());
            jsonObject.put("activityName", activityName);
            jsonObject.put("sc_id", projectId);

            File file = getStorageFile();
            JSONArray jsonArray = readJsonArray(file);
            jsonArray.put(jsonObject);

            FileWriter writer = new FileWriter(file);
            writer.write(jsonArray.toString(2));
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all stored widgets metadata
     */
    public JSONArray loadAllWidgets() {
        File file = getStorageFile();
        return readJsonArray(file);
    }

    /**
     * Clears all stored widgets metadata
     */
    public void clearAllWidgets() {
        try {
            File file = getStorageFile();
            if (file.exists()) {
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
