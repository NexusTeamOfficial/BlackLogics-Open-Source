
package com.nexusteam.blacklogics.model;

import android.graphics.Color;

import com.google.gson.Gson;
import com.nexusteam.blacklogics.utils.FileUtil;
import com.nexusteam.blacklogics.utils.ResourceUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProjectFile {

    private String projectId;
    private Map<String, Object> projectData;
    private final Gson gson = new Gson();
    private final String projectFilePath;

    public ProjectFile(String projectId) {
        this.projectId = projectId;
        this.projectFilePath = getProjectFilePath();
        loadProjectData();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
        loadProjectData();
    }

    public int getColor(String colorKey) {
        if (projectData != null && projectData.containsKey(colorKey)) {
            Object colorValue = projectData.get(colorKey);
            if (colorValue instanceof String) {
                try {
                    return Color.parseColor((String) colorValue);
                } catch (Exception e) {
                    return getDefaultColor(colorKey);
                }
            }
        }
        return getDefaultColor(colorKey);
    }

    public String getString(String key, String defaultValue) {
        if (projectData != null && projectData.containsKey(key)) {
            Object value = projectData.get(key);
            if (value instanceof String) {
                return (String) value;
            }
        }
        return defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        if (projectData != null && projectData.containsKey(key)) {
            Object value = projectData.get(key);
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof String) {
                try {
                    return Integer.parseInt((String) value);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (projectData != null && projectData.containsKey(key)) {
            Object value = projectData.get(key);
            if (value instanceof Boolean) {
                return (Boolean) value;
            } else if (value instanceof String) {
                return Boolean.parseBoolean((String) value);
            }
        }
        return defaultValue;
    }

    private void loadProjectData() {
        try {
            if (FileUtil.isExistFile(projectFilePath)) {
                String jsonContent = FileUtil.readFile(projectFilePath);
                if (jsonContent != null && !jsonContent.trim().isEmpty()) {
                    projectData = gson.fromJson(jsonContent, HashMap.class);
                }
            }
        } catch (Exception e) {
            projectData = new HashMap<>();
        }
        
        if (projectData == null) {
            projectData = new HashMap<>();
        }
    }

    public void saveProjectData() {
        try {
            String jsonContent = gson.toJson(projectData);
            FileUtil.writeFile(projectFilePath, jsonContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValue(String key, Object value) {
        if (projectData != null) {
            projectData.put(key, value);
            saveProjectData();
        }
    }

    private String getProjectFilePath() {
        return new File(FileUtil.getExternalStorageDir(), 
                       ".blacklogics/data/" + projectId + "/project.json").getAbsolutePath();
    }

    private int getDefaultColor(String colorKey) {
        switch (colorKey) {
            case "color_primary_dark":
                return Color.parseColor("#ff0084c2");

            case "color_control_highlight":
                return Color.parseColor("#20008dcd");

            case "color_control_normal":
                return Color.parseColor("#ff57beee");

            case "color_primary":
            default:
                return Color.parseColor("#ff008dcd");
        }
    }

    public Map<String, Object> getProjectData() {
        return projectData;
    }

    public void setProjectData(Map<String, Object> projectData) {
        this.projectData = projectData;
        saveProjectData();
    }
}