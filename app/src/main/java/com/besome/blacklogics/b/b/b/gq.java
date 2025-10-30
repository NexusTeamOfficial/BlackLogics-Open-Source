package b.b.b;

import android.content.Context;
import android.os.Environment;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class gq {
    
    private Map<String, Object> gradleConfig = new HashMap<>();
    
    public gq() {
        setDefaultConfig();
    }
    
    private void setDefaultConfig() {
        // Build gradle default config
        Map<String, Object> buildGradle = new HashMap<>();
        buildGradle.put("minSdk", 21);
        buildGradle.put("targetSdk", 34);
        buildGradle.put("compileSdk", 34);
        buildGradle.put("versionCode", 1);
        buildGradle.put("versionName", "1.0");
        buildGradle.put("applicationId", "com.myapp.project");
        
        // Dependencies
        List<String> dependencies = Arrays.asList(
            "implementation 'androidx.appcompat:appcompat:1.6.1'",
            "implementation 'com.google.android.material:material:1.9.0'",
            "implementation 'androidx.constraintlayout:constraintlayout:2.1.4'"
        );
        
        gradleConfig.put("buildGradle", buildGradle);
        gradleConfig.put("dependencies", dependencies);
        gradleConfig.put("plugins", Arrays.asList("com.android.application"));
    }
    
    public gq setMinSdk(int minSdk) {
        ((Map<String, Object>) gradleConfig.get("buildGradle")).put("minSdk", minSdk);
        return this;
    }
    
    public gq setTargetSdk(int targetSdk) {
        ((Map<String, Object>) gradleConfig.get("buildGradle")).put("targetSdk", targetSdk);
        return this;
    }
    
    public gq setCompileSdk(int compileSdk) {
        ((Map<String, Object>) gradleConfig.get("buildGradle")).put("compileSdk", compileSdk);
        return this;
    }
    
    public gq setVersionCode(int versionCode) {
        ((Map<String, Object>) gradleConfig.get("buildGradle")).put("versionCode", versionCode);
        return this;
    }
    
    public gq setVersionName(String versionName) {
        ((Map<String, Object>) gradleConfig.get("buildGradle")).put("versionName", versionName);
        return this;
    }
    
    public gq setApplicationId(String applicationId) {
        ((Map<String, Object>) gradleConfig.get("buildGradle")).put("applicationId", applicationId);
        return this;
    }
    
    public gq addDependency(String dependency) {
        List<String> dependencies = (List<String>) gradleConfig.get("dependencies");
        if (!dependencies.contains(dependency)) {
            dependencies.add(dependency);
        }
        return this;
    }
    
    public gq addPlugin(String plugin) {
        List<String> plugins = (List<String>) gradleConfig.get("plugins");
        if (!plugins.contains(plugin)) {
            plugins.add(plugin);
        }
        return this;
    }
    
    public String generateBuildGradle() {
        Map<String, Object> buildConfig = (Map<String, Object>) gradleConfig.get("buildGradle");
        List<String> plugins = (List<String>) gradleConfig.get("plugins");
        List<String> dependencies = (List<String>) gradleConfig.get("dependencies");
        
        StringBuilder gradle = new StringBuilder();
        
        // Plugins
        for (String plugin : plugins) {
            gradle.append("plugins {\n");
            gradle.append("    id '").append(plugin).append("'\n");
            gradle.append("}\n\n");
        }
        
        // Android configuration
        gradle.append("android {\n");
        gradle.append("    compileSdk ").append(buildConfig.get("compileSdk")).append("\n\n");
        
        gradle.append("    defaultConfig {\n");
        gradle.append("        applicationId \"").append(buildConfig.get("applicationId")).append("\"\n");
        gradle.append("        minSdk ").append(buildConfig.get("minSdk")).append("\n");
        gradle.append("        targetSdk ").append(buildConfig.get("targetSdk")).append("\n");
        gradle.append("        versionCode ").append(buildConfig.get("versionCode")).append("\n");
        gradle.append("        versionName \"").append(buildConfig.get("versionName")).append("\"\n");
        gradle.append("    }\n\n");
        
        gradle.append("    buildTypes {\n");
        gradle.append("        release {\n");
        gradle.append("            minifyEnabled false\n");
        gradle.append("            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'\n");
        gradle.append("        }\n");
        gradle.append("    }\n");
        gradle.append("    compileOptions {\n");
        gradle.append("        sourceCompatibility JavaVersion.VERSION_1_8\n");
        gradle.append("        targetCompatibility JavaVersion.VERSION_1_8\n");
        gradle.append("    }\n");
        gradle.append("}\n\n");
        
        // Dependencies
        gradle.append("dependencies {\n");
        for (String dependency : dependencies) {
            gradle.append("    ").append(dependency).append("\n");
        }
        gradle.append("}\n");
        
        return gradle.toString();
    }
    
    public String generateSettingsGradle() {
        return "include ':app'";
    }
    
    public void save(Context context, String sc_id) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + sc_id);
            if (!dir.exists()) dir.mkdirs();
            File file = new File(dir, "logic");
            JSONObject json = file.exists() ? new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8)) : new JSONObject();
            Map<String, Object> data = new HashMap<>();
            if (json.has("gradle_data")) {
                String decoded = new String(Base64.getDecoder().decode(json.getString("gradle_data")), StandardCharsets.UTF_8);
                data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
            }
            data.put("gradle", gradleConfig);
            String base64 = Base64.getEncoder().encodeToString(new Gson().toJson(data).getBytes(StandardCharsets.UTF_8));
            json.put("gradle_data", base64);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void load(Context context, String sc_id) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/.blacklogics/data/" + sc_id);
            File file = new File(dir, "logic");
            if (file.exists()) {
                JSONObject json = new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
                if (json.has("gradle_data")) {
                    String decoded = new String(Base64.getDecoder().decode(json.getString("gradle_data")), StandardCharsets.UTF_8);
                    Map<String, Object> data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
                    gradleConfig = (Map<String, Object>) data.get("gradle");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void extractGradleFiles(String sc_id, String outputPath) {
        try {
            load(null, sc_id);
            
            // Generate build.gradle
            File buildGradle = new File(outputPath + "/build.gradle");
            if (!buildGradle.getParentFile().exists()) buildGradle.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(buildGradle)) {
                fos.write(generateBuildGradle().getBytes(StandardCharsets.UTF_8));
            }
            
            // Generate settings.gradle
            File settingsGradle = new File(outputPath + "/settings.gradle");
            try (FileOutputStream fos = new FileOutputStream(settingsGradle)) {
                fos.write(generateSettingsGradle().getBytes(StandardCharsets.UTF_8));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}