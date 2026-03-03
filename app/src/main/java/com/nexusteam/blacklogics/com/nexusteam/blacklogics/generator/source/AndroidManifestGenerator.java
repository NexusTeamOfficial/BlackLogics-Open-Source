package com.nexusteam.blacklogics.generator.source;

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

public class AndroidManifestGenerator {
    
    private String packageName;
    private String appName;
    private String appIcon;
    private String themeColor;
    private boolean useAndroidX;
    private List<Map<String, Object>> activities = new ArrayList<>();
    private List<Map<String, Object>> services = new ArrayList<>();
    private List<Map<String, Object>> receivers = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();
    private Map<String, String> attributes = new HashMap<>();
    
    public AndroidManifestGenerator setPackageName(String name) {
        this.packageName = name;
        return this;
    }
    
    public AndroidManifestGenerator setAppName(String name) {
        this.appName = name;
        return this;
    }
    
    public AndroidManifestGenerator setAppIcon(String icon) {
        this.appIcon = icon;
        return this;
    }
    
    public AndroidManifestGenerator setThemeColor(String color) {
        this.themeColor = color;
        return this;
    }
    
    public AndroidManifestGenerator setAndroidX(boolean enable) {
        this.useAndroidX = enable;
        if (enable) {
            addActivity("androidx.activity.ComponentActivity", new HashMap<String, String>());
            addActivity("androidx.appcompat.app.AppCompatActivity", new HashMap<String, String>());
            addService("androidx.lifecycle.LifecycleService", new HashMap<String, String>());
            addPermission("org.apache.http.legacy");
        }
        return this;
    }
    
    public AndroidManifestGenerator addActivity(String name, Map<String, String> attrs) {
        if (!exists(activities, name)) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("attrs", attrs);
            activities.add(map);
        }
        return this;
    }
    
    public AndroidManifestGenerator addService(String name, Map<String, String> attrs) {
        if (!exists(services, name)) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("attrs", attrs);
            services.add(map);
        }
        return this;
    }
    
    public AndroidManifestGenerator addReceiver(String name, Map<String, String> attrs) {
        if (!exists(receivers, name)) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("attrs", attrs);
            receivers.add(map);
        }
        return this;
    }
    
    public AndroidManifestGenerator addPermission(String name) {
        if (!permissions.contains(name)) permissions.add(name);
        return this;
    }
    
    public AndroidManifestGenerator setAttribute(String key, String value) {
        attributes.put(key, value);
        return this;
    }
    
    private boolean exists(List<Map<String, Object>> list, String name) {
        for (Map<String, Object> item : list)
        if (item.get("name").equals(name))
        return true;
        return false;
    }
    
    public String generate() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        xml.append("<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
        xml.append("    package=\"").append(packageName).append("\">\n\n");
        
        for (String p : permissions) xml.append("    <uses-permission android:name=\"").append(p).append("\" />\n");
        xml.append("\n    <application\n");
        xml.append("        android:allowBackup=\"true\"\n");
        xml.append("        android:icon=\"").append(appIcon).append("\"\n");
        xml.append("        android:label=\"").append(appName).append("\"\n");
        xml.append("        android:name=\".BlackApplication\"\n");
        xml.append("        android:theme=\"@style/").append(themeColor).append("\">\n");
        
        for (Map.Entry<String, String> e : attributes.entrySet())
        xml.append("        android:").append(e.getKey()).append("=\"").append(e.getValue()).append("\"\n");
        xml.append("        >\n");
        
        for (Map<String, Object> act : activities) {
            xml.append("        <activity android:name=\"").append(act.get("name")).append("\"");
            Map<String, String> attrs = (Map<String, String>) act.get("attrs");
            if (attrs != null) for (Map.Entry<String, String> a : attrs.entrySet())
            xml.append(" android:").append(a.getKey()).append("=\"").append(a.getValue()).append("\"");
            
            if (act.get("name").equals(".MainActivity")) {
                xml.append(">\n");
                xml.append("            <intent-filter>\n");
                xml.append("                <action android:name=\"android.intent.action.MAIN\" />\n");
                xml.append("                <category android:name=\"android.intent.category.LAUNCHER\" />\n");
                xml.append("            </intent-filter>\n");
                xml.append("        </activity>\n");
            } else {
                xml.append(" />\n");
            }
        }
        
        for (Map<String, Object> srv : services) {
            xml.append("        <service android:name=\"").append(srv.get("name")).append("\"");
            Map<String, String> attrs = (Map<String, String>) srv.get("attrs");
            if (attrs != null) for (Map.Entry<String, String> a : attrs.entrySet())
            xml.append(" android:").append(a.getKey()).append("=\"").append(a.getValue()).append("\"");
            xml.append(" />\n");
        }
        
        for (Map<String, Object> rec : receivers) {
            xml.append("        <receiver android:name=\"").append(rec.get("name")).append("\"");
            Map<String, String> attrs = (Map<String, String>) rec.get("attrs");
            if (attrs != null) for (Map.Entry<String, String> a : attrs.entrySet())
            xml.append(" android:").append(a.getKey()).append("=\"").append(a.getValue()).append("\"");
            xml.append(" />\n");
        }
        
        xml.append("    </application>\n</manifest>");
        return xml.toString();
    }
    
    public AndroidManifestGenerator addLauncherActivity(String name) {
        Map<String, String> launcherAttrs = new HashMap<>();
        launcherAttrs.put("exported", "true");
        launcherAttrs.put("configChanges", "orientation|keyboardHidden|screenSize");
        launcherAttrs.put("windowSoftInputMode", "adjustResize");
        

        Map<String, String> intentFilter = new HashMap<>();
        intentFilter.put("action", "android.intent.action.MAIN");
        intentFilter.put("category", "android.intent.category.LAUNCHER");
        

        return addActivity(name, launcherAttrs)
        .setAttribute("intent-filter", new JSONObject(intentFilter).toString());
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void save(Context context, String sc_id) {
        try {
            File dir = new File("/storage/emulated/0" + "/.blacklogics/data/" + sc_id);
            if (!dir.exists()) dir.mkdirs();
            File file = new File(dir, "logic");
            JSONObject json = file.exists() ? new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8)) : new JSONObject();
            Map<String, Object> data = new HashMap<>();
            if (json.has("manifest_data")) {
                String decoded = new String(Base64.getDecoder().decode(json.getString("manifest_data")), StandardCharsets.UTF_8);
                data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
            }
            Map<String, Object> newManifest = new HashMap<>();
            newManifest.put("package", packageName);
            newManifest.put("appName", appName);
            newManifest.put("appIcon", appIcon);
            newManifest.put("themeColor", themeColor);
            newManifest.put("useAndroidX", useAndroidX);
            newManifest.put("activities", activities);
            newManifest.put("services", services);
            newManifest.put("receivers", receivers);
            newManifest.put("permissions", permissions);
            newManifest.put("attributes", attributes);
            data.put("manifest", newManifest);
            String base64 = Base64.getEncoder().encodeToString(new Gson().toJson(data).getBytes(StandardCharsets.UTF_8));
            json.put("manifest_data", base64);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }
            
            File permFile = new File(dir, "permissions.json");
            String permJson = new Gson().toJson(permissions);
            try (FileOutputStream fos = new FileOutputStream(permFile)) {
                fos.write(permJson.getBytes(StandardCharsets.UTF_8));
            }
            
        } catch (Exception e) {}
    }
    
    public void load(Context context, String sc_id) {
        try {
            File dir = new File("/storage/emulated/0" + "/.blacklogics/data/" + sc_id);
            File file = new File(dir, "logic");
            if (file.exists()) {
                JSONObject json = new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
                if (json.has("manifest_data")) {
                    String decoded = new String(Base64.getDecoder().decode(json.getString("manifest_data")), StandardCharsets.UTF_8);
                    Map<String, Object> data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
                    Map<String, Object> m = (Map<String, Object>) data.get("manifest");
                    if (m != null) {
                        packageName = (String) m.get("package");
                        appName = (String) m.get("appName");
                        appIcon = (String) m.get("appIcon");
                        themeColor = (String) m.get("themeColor");
                        useAndroidX = (boolean) m.get("useAndroidX");
                        activities = (List<Map<String, Object>>) m.get("activities");
                        services = (List<Map<String, Object>>) m.get("services");
                        receivers = (List<Map<String, Object>>) m.get("receivers");
                        attributes = (Map<String, String>) m.get("attributes");
                    }
                }
            }
            
            File permFile = new File(dir, "permission");
            if (permFile.exists()) {
                String permJsonStr = new String(Files.readAllBytes(permFile.toPath()), StandardCharsets.UTF_8);
                List<String> perms = new Gson().fromJson(permJsonStr, new TypeToken<List<String>>(){}.getType());
                if (perms != null) {
                    permissions.clear();
                    permissions.addAll(perms);
                }
            }
            
        } catch (Exception e) {}
    }
    
    public void extractManifest(String sc_id, String outputPath) {
        try {
            load(null, sc_id);
            String xml = generate();
            File out = new File(outputPath + "/AndroidManifest.xml");
            if (!out.getParentFile().exists()) out.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(out)) {
                fos.write(xml.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {}
    }
}
