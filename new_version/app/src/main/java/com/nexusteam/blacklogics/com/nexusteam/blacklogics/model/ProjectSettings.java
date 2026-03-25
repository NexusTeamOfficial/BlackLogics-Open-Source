
package com.nexusteam.blacklogics.model;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.nexusteam.blacklogics.utils.FileUtil;
import com.nexusteam.blacklogics.utils.Helper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProjectSettings {

    public static final String SETTING_MINIMUM_SDK_VERSION = "min_sdk";
    public static final String SETTING_ENABLE_BRIDGELESS_THEMES = "enable_bridgeless_themes";
    public static final String SETTING_APPLICATION_CLASS = "app_class";
    public static final String SETTING_TARGET_SDK_VERSION = "target_sdk";
    public static final String SETTING_DISABLE_OLD_METHODS = "disable_old_methods";
    public static final String SETTING_GENERIC_VALUE_TRUE = "true";
    public static final String SETTING_GENERIC_VALUE_FALSE = "false";
    
    private static final String TAG = "ProjectSettings";
    private final String path;
    public String sc_id;
    private Map<String, String> settingsMap;

    public ProjectSettings(String sc_id) {
        this.sc_id = sc_id;
        this.path = getPath();
        loadSettings();
    }

    public String getPath() {
        return new File("/storage/emulated/0/", 
                       ".blacklogics/data/" + sc_id + "/project_config").getAbsolutePath();
    }

    private void loadSettings() {
        if (FileUtil.isExistFile(path)) {
            try {
                String content = FileUtil.readFile(path).trim();
                if (!content.isEmpty()) {
                    settingsMap = new Gson().fromJson(content, Helper.TYPE_STRING_MAP);
                } else {
                    settingsMap = new HashMap<>();
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to read project settings for project " + sc_id + "!", e);
                settingsMap = new HashMap<>();
                saveSettings();
            }
        } else {
            settingsMap = new HashMap<>();
            saveSettings();
        }
    }

    public int getMinSdkVersion() {
        if (settingsMap.containsKey(SETTING_MINIMUM_SDK_VERSION)) {
            try {
                String value = settingsMap.get(SETTING_MINIMUM_SDK_VERSION);
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Failed to parse minimum SDK version! Defaulting to 21", e);
                return 21;
            }
        } else {
            return 21;
        }
    }

    public int getTargetSdkVersion() {
        if (settingsMap.containsKey(SETTING_TARGET_SDK_VERSION)) {
            try {
                String value = settingsMap.get(SETTING_TARGET_SDK_VERSION);
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Failed to parse target SDK version! Defaulting to 28", e);
                return 28;
            }
        } else {
            return 28;
        }
    }

    public String getApplicationClassName() {
        return getValue(SETTING_APPLICATION_CLASS, ".SketchApplication");
    }

    public boolean areOldMethodsDisabled() {
        return getValue(SETTING_DISABLE_OLD_METHODS, false);
    }

    public boolean areBridgelessThemesEnabled() {
        return getValue(SETTING_ENABLE_BRIDGELESS_THEMES, false);
    }


    public String getValue(String key, String defaultValue) {
        if (settingsMap.containsKey(key)) {
            String value = settingsMap.get(key);
            if (value != null && !value.isEmpty()) {
                return value;
            }
        }
        return defaultValue;
    }


    public boolean getValue(String key, boolean defaultValue) {
        if (settingsMap.containsKey(key)) {
            String value = settingsMap.get(key);
            if (value != null && !value.isEmpty()) {
                return SETTING_GENERIC_VALUE_TRUE.equals(value);
            }
        }
        return defaultValue;
    }


    public int getIntValue(String key, int defaultValue) {
        if (settingsMap.containsKey(key)) {
            try {
                String value = settingsMap.get(key);
                if (value != null && !value.isEmpty()) {
                    return Integer.parseInt(value);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Failed to parse integer value for key: " + key, e);
            }
        }
        return defaultValue;
    }


    public long getLongValue(String key, long defaultValue) {
        if (settingsMap.containsKey(key)) {
            try {
                String value = settingsMap.get(key);
                if (value != null && !value.isEmpty()) {
                    return Long.parseLong(value);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Failed to parse long value for key: " + key, e);
            }
        }
        return defaultValue;
    }

    public void setValue(String key, String value) {
        settingsMap.put(key, value);
        saveSettings();
    }


    public void setValue(String key, boolean value) {
        settingsMap.put(key, value ? SETTING_GENERIC_VALUE_TRUE : SETTING_GENERIC_VALUE_FALSE);
        saveSettings();
    }


    public void setIntValue(String key, int value) {
        settingsMap.put(key, String.valueOf(value));
        saveSettings();
    }


    public void setLongValue(String key, long value) {
        settingsMap.put(key, String.valueOf(value));
        saveSettings();
    }

    public void setValues(View... views) {
        for (View view : views) {
            Object tag = view.getTag();
            if (tag instanceof String) {
                String key = (String) tag;
                String value = extractValueFromView(view);
                settingsMap.put(key, value);
            }
        }
        saveSettings();
    }

    private String extractValueFromView(View view) {
        if (view instanceof EditText) {
            return ((EditText) view).getText().toString();
        } else if (view instanceof CheckBox) {
            return ((CheckBox) view).isChecked() ? SETTING_GENERIC_VALUE_TRUE : SETTING_GENERIC_VALUE_FALSE;
        } else if (view instanceof RadioGroup) {
            return getCheckedRadioButtonValue((RadioGroup) view);
        }
        return "";
    }

    private String getCheckedRadioButtonValue(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.isChecked()) {
                return radioButton.getText().toString();
            }
        }
        return "";
    }

    private void saveSettings() {
        try {
            String jsonContent = new Gson().toJson(settingsMap);
            FileUtil.writeFile(path, jsonContent);
        } catch (Exception e) {
            Log.e(TAG, "Failed to save project settings!", e);
        }
    }

    public Map<String, String> getAllSettings() {
        return new HashMap<>(settingsMap);
    }

    public void setAllSettings(Map<String, String> settings) {
        this.settingsMap = new HashMap<>(settings);
        saveSettings();
    }
}