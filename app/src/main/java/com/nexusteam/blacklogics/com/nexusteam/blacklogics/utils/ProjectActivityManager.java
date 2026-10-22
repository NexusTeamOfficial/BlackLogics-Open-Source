package com.besome.blacklogics.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.besome.blacklogics.beans.ProjectActivityBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
* ProjectActivityManager
* ----------------------
* - Manages ProjectActivityBean for a specific project
* - Saves to /storage/emulated/0/.blacklogic/data/{projectId}/activities.json
* - Android 11+ requires MANAGE_EXTERNAL_STORAGE
*/
public class ProjectActivityManager {
    private static final Map<String, ProjectActivityManager> instances = new HashMap<>();
    
    private final Map<String, ProjectActivityBean> activityMap = new HashMap<>();
    private final Gson gson = new Gson();
    private final Context context;
    private final String projectId;
    
    public ProjectActivityManager(Context context, String projectId) {
        this.context = context.getApplicationContext();
        this.projectId = projectId;
        load(); // load existing activities on init
    }
    
    public static synchronized ProjectActivityManager getInstance(Context context, String projectId) {
        if (!instances.containsKey(projectId)) {
            instances.put(projectId, new ProjectActivityManager(context, projectId));
        }
        return instances.get(projectId);
    }
    
    

    private File getProjectDir() {
        File baseDir = new File("/storage/emulated/0/"); // /storage/emulated/0/
        return new File(baseDir, ".blacklogics/data/" + projectId);
    }
    
    private File getActivitiesFile() {
        return new File(getProjectDir(), "activities.json");
    }
    

    public void addActivity(ProjectActivityBean bean) {
        if (bean != null && bean.getActivityName() != null) {

            activityMap.put(bean.getActivityName(), bean);
            save();
        }
    }
    
    public ProjectActivityBean getActivity(String activityName) {
        return activityMap.get(activityName);
    }
    
    public void removeActivity(String activityName) {
        if (activityMap.containsKey(activityName)) {
            activityMap.remove(activityName);
            save();
        }
    }
    
    public Map<String, ProjectActivityBean> getAllActivities() {
        return activityMap;
    }
    

    public void save() {
        try {
            File dir = getProjectDir();
            if (!dir.exists()) dir.mkdirs();
            
            File file = getActivitiesFile();
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(activityMap));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void load() {
        try {
            File file = getActivitiesFile();
            if (!file.exists()) return;
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            Type type = new TypeToken<Map<String, ProjectActivityBean>>() {}.getType();
            Map<String, ProjectActivityBean> restored = gson.fromJson(br, type);
            if (restored != null) {
                activityMap.clear();
                activityMap.putAll(restored);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void saveToFile(Context context, String projectId) {
        save();
    }
    
    public void loadFromFile(Context context, String projectId) {
        load();
    }
}
