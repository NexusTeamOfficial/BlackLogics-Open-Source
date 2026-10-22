package com.nexusteam.blacklogics.manager;

import android.content.Context;
import android.os.Environment;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.Base64;

public class ProjectConfigManager {
    
    private Map<String, Object> projectData = new HashMap<>();
    
    public ProjectConfigManager() {

        setDefaultData();
    }
    
    private void setDefaultData() {
        projectData.put("package_name", "com.myapp.project");
        projectData.put("project_name", "MyApp");
        projectData.put("sc_name", "main");
        projectData.put("project_id", "app_001");
        projectData.put("min_sdk", 21);
        projectData.put("target_sdk", 34);
        projectData.put("version_code", 1);
        projectData.put("version_name", "1.0");
    }
    
    public ProjectConfigManager setPackageName(String packageName) {
        projectData.put("package_name", packageName);
        return this;
    }
    
    public ProjectConfigManager setProjectName(String projectName) {
        projectData.put("project_name", projectName);
        return this;
    }
    
    public ProjectConfigManager setScName(String scName) {
        projectData.put("sc_name", scName);
        return this;
    }
    
    public ProjectConfigManager setProjectId(String projectId) {
        projectData.put("project_id", projectId);
        return this;
    }
    
    public ProjectConfigManager setMinSdk(int minSdk) {
        projectData.put("min_sdk", minSdk);
        return this;
    }
    
    public ProjectConfigManager setTargetSdk(int targetSdk) {
        projectData.put("target_sdk", targetSdk);
        return this;
    }
    
    public ProjectConfigManager setVersionCode(int versionCode) {
        projectData.put("version_code", versionCode);
        return this;
    }
    
    public ProjectConfigManager setVersionName(String versionName) {
        projectData.put("version_name", versionName);
        return this;
    }
    
    public void save(Context context) {
    try {
        String sc_id = (String) projectData.get("project_id");
        File dir = new File("/storage/emulated/0/.blacklogics/data/" + sc_id);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, "logic");

        JSONObject json = new JSONObject();
        if (file.exists()) {
            try {
                String existing = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                if (existing != null && !existing.trim().isEmpty()) {
                    json = new JSONObject(existing);
                }
            } catch (Exception ignored) {}
        }

        String encodedData = Base64.getEncoder().encodeToString(
            new JSONObject(projectData).toString().getBytes(StandardCharsets.UTF_8)
        );
        json.put("project_data", encodedData);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  public void load(Context context, String projectId) {
    try {
        File dir = new File("/storage/emulated/0/.blacklogics/data/" + projectId);
        File file = new File(dir, "logic");

        if (file.exists()) {
            JSONObject json = new JSONObject(
                new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8)
            );

            if (json.has("project_data")) {
                String decoded = new String(
                    Base64.getDecoder().decode(json.getString("project_data")),
                    StandardCharsets.UTF_8
                );
                JSONObject dataJson = new JSONObject(decoded);

                projectData.clear();
                Iterator<String> keys = dataJson.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    projectData.put(key, dataJson.get(key));
                }
            }
            else if (json.has("manifest_data")) {
                try {
                    String decoded = new String(
                        Base64.getDecoder().decode(json.getString("manifest_data")),
                        StandardCharsets.UTF_8
                    );
                    JSONObject root = new JSONObject(decoded);
                    if (root.has("manifest")) {
                        JSONObject manifest = root.getJSONObject("manifest");
                        if (manifest.has("package")) {
                            projectData.put("package_name", manifest.getString("package"));
                        }
                    }
                } catch (Exception ignored) {}
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    

    public String getPackageName() {
        return (String) projectData.get("package_name");
    }
    
    public String getProjectName() {
        return (String) projectData.get("project_name");
    }
    
    public String getScName() {
        return (String) projectData.get("sc_name");
    }
    
    public String getProjectId() {
        return (String) projectData.get("project_id");
    }
    
    public int getMinSdk() {
        return (int) projectData.get("min_sdk");
    }
    
    public int getTargetSdk() {
        return (int) projectData.get("target_sdk");
    }
    
    public int getVersionCode() {
        return (int) projectData.get("version_code");
    }
    
    public String getVersionName() {
        return (String) projectData.get("version_name");
    }
    









}