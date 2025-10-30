package b.b.b;

import android.content.Context;
import android.os.Environment;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class rs {
    
    private Map<String, Object> settingsData = new HashMap<>();
    private String projectId;
    
    public rs(String projectId) {
        this.projectId = projectId;
        setDefaultSettings();
    }
    
    private void setDefaultSettings() {
        settingsData.put("resource_encrypt", false);
        settingsData.put("obfuscate_code", false);
        settingsData.put("enable_logging", false);
        settingsData.put("compress_resources", false);
        settingsData.put("backup_enabled", false);
        settingsData.put("auto_save", false);
        settingsData.put("target_sdk", 34); // Default targetSdk
        settingsData.put("min_sdk", 21);    // Default minSdk
    }
    
    public rs setResourceEncrypt(boolean enable) {
        settingsData.put("resource_encrypt", enable);
        return this;
    }
    
    public rs setObfuscateCode(boolean enable) {
        settingsData.put("obfuscate_code", enable);
        return this;
    }
    
    public rs setEnableLogging(boolean enable) {
        settingsData.put("enable_logging", enable);
        return this;
    }
    
    public rs setCompressResources(boolean enable) {
        settingsData.put("compress_resources", enable);
        return this;
    }
    
    public rs setBackupEnabled(boolean enable) {
        settingsData.put("backup_enabled", enable);
        return this;
    }
    
    public rs setAutoSave(boolean enable) {
        settingsData.put("auto_save", enable);
        return this;
    }
    
    // New methods for targetSdk and minSdk
    public rs setTargetSdk(int targetSdk) {
        settingsData.put("target_sdk", targetSdk);
        return this;
    }
    
    public rs setMinSdk(int minSdk) {
        settingsData.put("min_sdk", minSdk);
        return this;
    }
    
    public void save(Context context) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + projectId);
            if (!dir.exists()) dir.mkdirs();
            
            File file = new File(dir, "settings");
            
            JSONObject json = new JSONObject();
            String encodedData = Base64.getEncoder().encodeToString(
                new JSONObject(settingsData).toString().getBytes(StandardCharsets.UTF_8)
            );
            json.put("settings_data", encodedData);
            
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void load(Context context) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + projectId);
            File file = new File(dir, "settings");
            
            if (file.exists()) {
                JSONObject json = new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
                if (json.has("settings_data")) {
                    String decoded = new String(Base64.getDecoder().decode(json.getString("settings_data")), StandardCharsets.UTF_8);
                    JSONObject dataJson = new JSONObject(decoded);
                    
                    settingsData.clear();
                    Iterator<String> keys = dataJson.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        settingsData.put(key, dataJson.get(key));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Getter methods
    public boolean isResourceEncrypt() {
        return (boolean) settingsData.get("resource_encrypt");
    }
    
    public boolean isObfuscateCode() {
        return (boolean) settingsData.get("obfuscate_code");
    }
    
    public boolean isEnableLogging() {
        return (boolean) settingsData.get("enable_logging");
    }
    
    public boolean isCompressResources() {
        return (boolean) settingsData.get("compress_resources");
    }
    
    public boolean isBackupEnabled() {
        return (boolean) settingsData.get("backup_enabled");
    }
    
    public boolean isAutoSave() {
        return (boolean) settingsData.get("auto_save");
    }
    
    // New getter methods for SDK versions
    public int getTargetSdk() {
        Object value = settingsData.get("target_sdk");
        if (value instanceof Integer) {
            return (int) value;
        } else if (value instanceof Long) {
            return ((Long) value).intValue();
        }
        return 34; 
    }
    
    public int getMinSdk() {
        Object value = settingsData.get("min_sdk");
        if (value instanceof Integer) {
            return (int) value;
        } else if (value instanceof Long) {
            return ((Long) value).intValue();
        }
        return 21;
    }
    
    // Usage example:
    // rs settings = new rs("app_001");
    // settings.setResourceEncrypt(true)
    //         .setObfuscateCode(false)
    //         .setTargetSdk(34)
    //         .setMinSdk(21)
    //         .save(context);
    //
    // settings.load(context);
    // boolean encrypt = settings.isResourceEncrypt(); // Actual loaded data
    // int targetSdk = settings.getTargetSdk(); // Get stored target SDK
    // int minSdk = settings.getMinSdk(); // Get stored min SDK
}