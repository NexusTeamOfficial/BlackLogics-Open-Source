package com.nexusteam.blacklogics.generator.source.model;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ActivityTypeRegistry {
    
    private static ActivityTypeRegistry instance;
    private Map<String, ActivityConfig> activityConfigs = new LinkedHashMap<>();
    private Gson gson;
    
    private ActivityTypeRegistry() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .create();
    }
    
    public static ActivityTypeRegistry getInstance() {
        if (instance == null) {
            instance = new ActivityTypeRegistry();
        }
        return instance;
    }
    

    public void save(Context context, String sc_id) {
        FileOutputStream fos = null;
        try {
            File dir = new File("/storage/emulated/0" + "/.blacklogics/data/" + sc_id);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            File file = new File(dir, "activity_types");
            JSONObject json = new JSONObject();
            

            Map<String, Map<String, Object>> configMap = new HashMap<>();
            for (Map.Entry<String, ActivityConfig> entry : activityConfigs.entrySet()) {
                configMap.put(entry.getKey(), entry.getValue().toMap());
            }
            

            String jsonStr = gson.toJson(configMap);
            String base64 = Base64.encodeToString(jsonStr.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
            
            json.put("activity_type_data", base64);
            json.put("version", "1.0");
            json.put("saved_at", System.currentTimeMillis());
            
            fos = new FileOutputStream(file);
            fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    public void load(Context context, String sc_id) {
        FileInputStream fis = null;
        try {
            File dir = new File("/storage/emulated/0" + "/.blacklogics/data/" + sc_id);
            File file = new File(dir, "activity_types");
            
            if (!file.exists()) return;
            

            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            String content = new String(data, StandardCharsets.UTF_8);
            
            JSONObject json = new JSONObject(content);
            
            if (!json.has("activity_type_data")) return;
            
            String base64 = json.getString("activity_type_data");
            String decoded = new String(Base64.decode(base64, Base64.DEFAULT), StandardCharsets.UTF_8);
            
            Type type = new TypeToken<Map<String, Map<String, Object>>>(){}.getType();
            Map<String, Map<String, Object>> configMap = gson.fromJson(decoded, type);
            
            activityConfigs.clear();
            
            for (Map.Entry<String, Map<String, Object>> entry : configMap.entrySet()) {
                ActivityConfig config = ActivityConfig.fromMap(entry.getValue());
                activityConfigs.put(entry.getKey(), config);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    public void registerActivity(String name, ActivityType type, String layoutName) {
        activityConfigs.put(name, new ActivityConfig(name, type, layoutName));
    }
    
    public void registerActivity(ActivityConfig config) {
        activityConfigs.put(config.getActivityName(), config);
    }
    
    public ActivityConfig getActivityConfig(String name) {
        return activityConfigs.get(name);
    }
    
    public boolean hasActivity(String name) {
        return activityConfigs.containsKey(name);
    }
    
    public void removeActivity(String name) {
        activityConfigs.remove(name);
    }
    
    public List<ActivityConfig> getAllActivities() {
        return new ArrayList<>(activityConfigs.values());
    }
    
    public List<String> getAllActivityNames() {
        return new ArrayList<>(activityConfigs.keySet());
    }
    
    public List<ActivityConfig> getActivitiesByType(ActivityType type) {
        List<ActivityConfig> result = new ArrayList<>();
        for (ActivityConfig config : activityConfigs.values()) {
            if (config.getType() == type) {
                result.add(config);
            }
        }
        return result;
    }
    
    public ActivityConfig getLauncherActivity() {
        for (ActivityConfig config : activityConfigs.values()) {
            if (config.isLauncher()) {
                return config;
            }
        }
        return null;
    }
    
    public void clear() {
        activityConfigs.clear();
    }
    
    public int size() {
        return activityConfigs.size();
    }
    

    public String exportToJson() {
        Map<String, Map<String, Object>> configMap = new HashMap<>();
        for (Map.Entry<String, ActivityConfig> entry : activityConfigs.entrySet()) {
            configMap.put(entry.getKey(), entry.getValue().toMap());
        }
        return gson.toJson(configMap);
    }
    
    public void importFromJson(String json) {
        Type type = new TypeToken<Map<String, Map<String, Object>>>(){}.getType();
        Map<String, Map<String, Object>> configMap = gson.fromJson(json, type);
        
        activityConfigs.clear();
        for (Map.Entry<String, Map<String, Object>> entry : configMap.entrySet()) {
            ActivityConfig config = ActivityConfig.fromMap(entry.getValue());
            activityConfigs.put(entry.getKey(), config);
        }
    }
}