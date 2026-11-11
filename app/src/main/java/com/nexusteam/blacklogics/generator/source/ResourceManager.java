package com.nexusteam.blacklogics.generator.source;

import android.content.Context;
import android.os.Environment;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ResourceManager {
    
    private boolean useAndroidX;
    private boolean hasCustomToolbarChrome = false;
    private Map<String, String> colors;
    private Map<String, String> styles;
    private Map<String, String> dimens;
    private Map<String, String> attributes;
    private Map<String, String> strings;
    
    public ResourceManager() {
        this.colors = new HashMap<String, String>();
        this.styles = new HashMap<String, String>();
        this.dimens = new HashMap<String, String>();
        this.attributes = new HashMap<String, String>();
        this.strings = new HashMap<String, String>();
        
        this.strings.put("app_name", "My App");
        
        setColor("colorPrimary", "#FF3F51B5");
        setColor("colorPrimaryDark", "#FF303F9F");
        setColor("colorAccent", "#FFFF4081");
        setColor("colorBackground", "#FFEEEEEE");
        setColor("colorControlHighlight", "#FFFF9800");
        setColor("colorControlNormal", "#FF2196F3");
        
        setStyle("AppTheme", "@android:style/Theme.Material.Light.DarkActionBar");
        setStyle("AppTheme.NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
        setStyle("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
        setStyle("FullScreen", "AppTheme.NoActionBar");
        setStyle("NoStatusBar", "AppTheme.NoActionBar");
        setStyle("AppTheme.DebugActivity", "AppTheme");
        
        setDimen("app_margin", "8dp");
        setDimen("button_corner", "4dp");
        setDimen("text_size", "16sp");
    }

    public void setHasCustomToolbarChrome(boolean hasCustomToolbarChrome) {
        this.hasCustomToolbarChrome = hasCustomToolbarChrome;
        if (hasCustomToolbarChrome) {
            String noActionBar = this.useAndroidX
                    ? "Theme.MaterialComponents.Light.NoActionBar"
                    : "@android:style/Theme.Material.Light.NoActionBar";
            this.styles.put("AppTheme", noActionBar);
        }
    }

    public boolean getHasCustomToolbarChrome() {
        return this.hasCustomToolbarChrome;
    }
    
    private String normalizeColor(String color) {
        if (color == null) return "#FF000000";
        color = color.trim();
        if (!color.startsWith("#")) {
            color = "#" + color;
        }
        if (color.length() == 7) {
            color = "#FF" + color.substring(1);
        }
        if (color.length() == 4) {
            char r = color.charAt(1);
            char g = color.charAt(2);
            char b = color.charAt(3);
            color = "#FF" + r + r + g + g + b + b;
        }
        return color;
    }
    
    public ResourceManager setAndroidX(boolean enable) {
        this.useAndroidX = enable;
        return this;
    }
    
    public boolean getAndroidX() {
        return useAndroidX;
    }
    
    public ResourceManager setColor(String name, String value) {
        if (this.colors == null) {
            this.colors = new HashMap<String, String>();
        }
        this.colors.put(name, normalizeColor(value));
        return this;
    }
    
    public ResourceManager setColorRaw(String name, String value) {
        if (this.colors == null) {
            this.colors = new HashMap<String, String>();
        }
        this.colors.put(name, value);
        return this;
    }
    
    public ResourceManager setStyle(String name, String parent) {
        if (this.styles == null) {
            this.styles = new HashMap<String, String>();
        }
        this.styles.put(name, parent);
        return this;
    }
    
    public ResourceManager setDimen(String name, String value) {
        if (this.dimens == null) {
            this.dimens = new HashMap<String, String>();
        }
        this.dimens.put(name, value);
        return this;
    }
    
    public ResourceManager setAppName(String name) {
        if (this.strings == null) {
            this.strings = new HashMap<String, String>();
        }
        this.strings.put("app_name", (name == null || name.trim().isEmpty()) ? "My App" : name.trim());
        return this;
    }
    
    public ResourceManager setString(String name, String value) {
        if (this.strings == null) {
            this.strings = new HashMap<String, String>();
        }
        this.strings.put(name, value == null ? "" : value);
        return this;
    }
    
    public String getAppName() {
        if (this.strings == null || !this.strings.containsKey("app_name")) {
            return "My App";
        }
        return this.strings.get("app_name");
    }
    
    public Map<String, String> getStrings() {
        if (this.strings == null) {
            this.strings = new HashMap<String, String>();
        }
        return this.strings;
    }
    
    public ResourceManager setAttribute(String key, String value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<String, String>();
        }
        this.attributes.put(key, value);
        return this;
    }
    
    public Map<String, String> getColors() {
        if (this.colors == null) {
            this.colors = new HashMap<String, String>();
        }
        return this.colors;
    }
    
    public Map<String, String> getStyles() {
        if (this.styles == null) {
            this.styles = new HashMap<String, String>();
        }
        return this.styles;
    }
    
    public Map<String, String> getDimens() {
        if (this.dimens == null) {
            this.dimens = new HashMap<String, String>();
        }
        return this.dimens;
    }
    
    public Map<String, String> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<String, String>();
        }
        return this.attributes;
    }
    
    private void setDefaultColors() {
        this.colors = new HashMap<String, String>();
        this.colors.put("colorPrimary", "#FF3F51B5");
        this.colors.put("colorPrimaryDark", "#FF303F9F");
        this.colors.put("colorAccent", "#FFFF4081");
        this.colors.put("colorControlHighlight", "#FFFF9800");
        this.colors.put("colorControlNormal", "#FF2196F3");
        this.colors.put("colorBackground", "#FFEEEEEE");
    }
    
    public String generateColors() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        
        if (this.colors != null) {
            for (Map.Entry<String, String> color : this.colors.entrySet()) {
                xml.append("    <color name=\"").append(color.getKey())
                .append("\">").append(color.getValue()).append("</color>\n");
            }
        }
        
        xml.append("</resources>");
        return xml.toString();
    }
    
    public String generateStyles() {
        ensureStyleKeys();
        
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        
        String[] order = new String[] {
            "AppTheme",
            "AppTheme.NoActionBar",
            "NoActionBar",
            "FullScreen",
            "NoStatusBar",
            "AppTheme.DebugActivity"
        };
        
        java.util.LinkedHashSet<String> emitted = new java.util.LinkedHashSet<String>();
        for (String name : order) {
            if (this.styles != null && this.styles.containsKey(name)) {
                appendStyleBlock(xml, name);
                emitted.add(name);
            }
        }
        if (this.styles != null) {
            for (String name : this.styles.keySet()) {
                if (!emitted.contains(name)) {
                    appendStyleBlock(xml, name);
                }
            }
        }
        
        if (this.useAndroidX) {
            xml.append("    <style name=\"AppTheme.AppBarOverlay\" parent=\"ThemeOverlay.MaterialComponents.Dark.ActionBar\" />\n");
            xml.append("    <style name=\"AppTheme.PopupOverlay\" parent=\"ThemeOverlay.MaterialComponents.Light\" />\n");
        }
        
        xml.append("</resources>");
        return xml.toString();
    }
    
    private void ensureStyleKeys() {
        if (this.styles == null) {
            this.styles = new HashMap<String, String>();
        }
        if (!this.styles.containsKey("AppTheme")) {
            this.styles.put("AppTheme", this.hasCustomToolbarChrome
                    ? "@android:style/Theme.Material.Light.NoActionBar"
                    : "@android:style/Theme.Material.Light.DarkActionBar");
        }
        if (!this.styles.containsKey("AppTheme.NoActionBar")) {
            this.styles.put("AppTheme.NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
        }
        if (!this.styles.containsKey("NoActionBar")) {
            this.styles.put("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
        }
        if (!this.styles.containsKey("FullScreen")) {
            this.styles.put("FullScreen", "AppTheme.NoActionBar");
        }
        if (!this.styles.containsKey("NoStatusBar")) {
            this.styles.put("NoStatusBar", "AppTheme.NoActionBar");
        }
        if (!this.styles.containsKey("AppTheme.DebugActivity")) {
            this.styles.put("AppTheme.DebugActivity", "AppTheme");
        }
    }
    
    private void appendStyleBlock(StringBuilder xml, String styleName) {
        String parent = resolveParent(styleName);
        boolean noActionBar = isNoActionBarStyle(styleName);
        
        xml.append("    <style name=\"").append(styleName)
           .append("\" parent=\"").append(parent).append("\">\n");
        
        appendColorItems(xml);
        
        if (noActionBar) {
            xml.append("        <item name=\"android:windowActionBar\">false</item>\n");
            xml.append("        <item name=\"android:windowNoTitle\">true</item>\n");
            if (this.useAndroidX) {
                xml.append("        <item name=\"windowActionBar\">false</item>\n");
                xml.append("        <item name=\"windowNoTitle\">true</item>\n");
            }
        }
        
        if ("FullScreen".equals(styleName) || "NoStatusBar".equals(styleName)) {
            xml.append("        <item name=\"android:windowFullscreen\">true</item>\n");
            if ("FullScreen".equals(styleName)) {
                xml.append("        <item name=\"android:windowContentOverlay\">@null</item>\n");
            }
        }
        
        if ("AppTheme.DebugActivity".equals(styleName)) {
            xml.append("        <item name=\"android:windowActionBar\">true</item>\n");
            xml.append("        <item name=\"android:windowNoTitle\">false</item>\n");
        }
        
        appendCustomAttributes(xml);
        
        xml.append("    </style>\n");
    }
    
    private boolean isNoActionBarStyle(String styleName) {
        return "AppTheme.NoActionBar".equals(styleName)
            || "NoActionBar".equals(styleName)
            || "FullScreen".equals(styleName)
            || "NoStatusBar".equals(styleName);
    }
    
    private String resolveParent(String styleName) {
        if (this.useAndroidX) {
            if ("AppTheme".equals(styleName)) {
                return this.hasCustomToolbarChrome
                        ? "Theme.MaterialComponents.Light.NoActionBar"
                        : "Theme.MaterialComponents.Light.NoActionBar";
            }
            if (isNoActionBarStyle(styleName)) {
                return "Theme.MaterialComponents.Light.NoActionBar";
            }
            if ("AppTheme.DebugActivity".equals(styleName)) {
                return "Theme.MaterialComponents.Light.DarkActionBar";
            }
            String stored = this.styles.get(styleName);
            if (stored != null && stored.startsWith("Theme.")) {
                return stored;
            }
            return "Theme.MaterialComponents.Light.NoActionBar";
        }
        if ("AppTheme".equals(styleName)) {
            return this.hasCustomToolbarChrome
                    ? "@android:style/Theme.Material.Light.NoActionBar"
                    : "@android:style/Theme.Material.Light.DarkActionBar";
        }
        if (isNoActionBarStyle(styleName)) {
            return "@android:style/Theme.Material.Light.NoActionBar";
        }
        String stored = this.styles.get(styleName);
        return (stored != null) ? stored : "@android:style/Theme.Material.Light";
    }
    
    private void appendColorItems(StringBuilder xml) {
        if (this.useAndroidX) {
            xml.append("        <item name=\"colorPrimary\">@color/colorPrimary</item>\n");
            xml.append("        <item name=\"colorPrimaryDark\">@color/colorPrimaryDark</item>\n");
            xml.append("        <item name=\"colorAccent\">@color/colorAccent</item>\n");
            xml.append("        <item name=\"colorControlHighlight\">@color/colorControlHighlight</item>\n");
            xml.append("        <item name=\"colorControlNormal\">@color/colorControlNormal</item>\n");
            xml.append("        <item name=\"android:statusBarColor\">@color/colorPrimaryDark</item>\n");
        } else {
            xml.append("        <item name=\"android:colorPrimary\">@color/colorPrimary</item>\n");
            xml.append("        <item name=\"android:colorPrimaryDark\">@color/colorPrimaryDark</item>\n");
            xml.append("        <item name=\"android:colorAccent\">@color/colorAccent</item>\n");
            xml.append("        <item name=\"android:colorControlHighlight\">@color/colorControlHighlight</item>\n");
            xml.append("        <item name=\"android:colorControlNormal\">@color/colorControlNormal</item>\n");
        }
    }
    
    private void appendCustomAttributes(StringBuilder xml) {
        if (this.attributes == null || this.attributes.isEmpty()) return;
        for (Map.Entry<String, String> attr : this.attributes.entrySet()) {
            String key = attr.getKey();
            String value = attr.getValue();
            if (key == null) continue;
            if (this.useAndroidX) {
                if (key.startsWith("android:")) {
                    xml.append("        <item name=\"").append(key).append("\">").append(value).append("</item>\n");
                } else if (key.startsWith("app:")) {
                    xml.append("        <item name=\"").append(key.substring(4)).append("\">").append(value).append("</item>\n");
                } else {
                    xml.append("        <item name=\"").append(key).append("\">").append(value).append("</item>\n");
                }
            } else {
                if (key.startsWith("android:")) {
                    xml.append("        <item name=\"").append(key).append("\">").append(value).append("</item>\n");
                } else if (key.startsWith("app:")) {
                    xml.append("        <item name=\"android:").append(key.substring(4)).append("\">").append(value).append("</item>\n");
                } else {
                    xml.append("        <item name=\"android:").append(key).append("\">").append(value).append("</item>\n");
                }
            }
        }
    }
    
    public String generateDimens() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        if (this.dimens != null) {
            for (Map.Entry<String, String> dimen : this.dimens.entrySet()) {
                xml.append("    <dimen name=\"").append(dimen.getKey())
                .append("\">").append(dimen.getValue()).append("</dimen>\n");
            }
        }
        xml.append("    <dimen name=\"small_margin\">8dp</dimen>\n");
        xml.append("    <dimen name=\"medium_margin\">16dp</dimen>\n");
        xml.append("    <dimen name=\"large_margin\">24dp</dimen>\n");
        xml.append("    <dimen name=\"button_height\">48dp</dimen>\n");
        xml.append("</resources>");
        return xml.toString();
    }
    
    public String generateStrings() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        
        xml.append("    <string name=\"app_name\">")
           .append(escapeXml(getAppName()))
           .append("</string>\n");
        
        if (this.strings != null) {
            for (Map.Entry<String, String> entry : this.strings.entrySet()) {
                if ("app_name".equals(entry.getKey())) {
                    continue;
                }
                xml.append("    <string name=\"").append(entry.getKey())
                   .append("\">").append(escapeXml(entry.getValue())).append("</string>\n");
            }
        }
        
        xml.append("</resources>");
        return xml.toString();
    }
    
    private String escapeXml(String value) {
        if (value == null) return "";
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("'", "\\'")
            .replace("\"", "&quot;");
    }
    
    public String generateAttributes() {
        if (this.attributes == null || this.attributes.isEmpty()) {
            return "";
        }
        
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        xml.append("<resources>\n");
        
        Set<Map.Entry<String, String>> attrSet = this.attributes.entrySet();
        for (Map.Entry<String, String> attr : attrSet) {
            xml.append("    <attr name=\"").append(attr.getKey())
               .append("\" format=\"").append(attr.getValue()).append("\" />\n");
        }
        
        xml.append("</resources>");
        return xml.toString();
    }
    
    public void save(Context context, String sc_id) {
        try {
            File dir = new File("/storage/emulated/0/.blacklogics/data/" + sc_id);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, "logic");
            
            JSONObject json = new JSONObject();
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                if (content != null && !content.trim().isEmpty()) {
                    json = new JSONObject(content);
                }
            }
            
            Map<String, Object> data = new HashMap<String, Object>();
            if (json.has("resource_data")) {
                try {
                    String decoded = new String(Base64.decode(json.getString("resource_data"), Base64.DEFAULT), StandardCharsets.UTF_8);
                    if (decoded != null && !decoded.trim().isEmpty()) {
                        data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
                    }
                } catch (Exception e) {
                    data = new HashMap<String, Object>();
                }
            }
            
            Map<String, Object> newResource = new HashMap<String, Object>();
            newResource.put("useAndroidX", this.useAndroidX);
            
            Map<String, String> colorsToSave = new HashMap<String, String>();
            if (this.colors != null) {
                Set<Map.Entry<String, String>> colorSet = this.colors.entrySet();
                for (Map.Entry<String, String> entry : colorSet) {
                    String colorValue = entry.getValue();
                    if (!colorValue.startsWith("#")) {
                        colorValue = "#" + colorValue;
                    }
                    colorsToSave.put(entry.getKey(), colorValue);
                }
            }
            newResource.put("colors", colorsToSave);
            newResource.put("styles", getStyles());
            newResource.put("dimens", getDimens());
            newResource.put("attributes", getAttributes());
            newResource.put("strings", getStrings());
            
            data.put("resources", newResource);
            
            String jsonStr = new Gson().toJson(data);
            String base64 = Base64.encodeToString(jsonStr.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
            json.put("resource_data", base64);
            
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            } finally {
                fos.close();
            }
            
            android.util.Log.d("ar", "Colors saved: " + colorsToSave.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void saveResourceFile(File dir, String fileName, String content) {
        try {
            File resourceFile = new File(dir, fileName);
            if (!resourceFile.getParentFile().exists()) {
                resourceFile.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(resourceFile);
            try {
                fos.write(content.getBytes(StandardCharsets.UTF_8));
            } finally {
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void load(Context context, String sc_id) {
        try {
            File dir = new File("/storage/emulated/0/.blacklogics/data/" + sc_id);
            File file = new File(dir, "logic");
            if (file.exists()) {
                JSONObject json = new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
                if (json.has("resource_data")) {
                    String decoded = new String(Base64.decode(json.getString("resource_data"), Base64.DEFAULT), StandardCharsets.UTF_8);
                    if (decoded != null && !decoded.trim().isEmpty()) {
                        Map<String, Object> data = new Gson().fromJson(decoded, new TypeToken<Map<String, Object>>(){}.getType());
                        Map<String, Object> r = (Map<String, Object>) data.get("resources");
                        if (r != null) {
                            if (r.containsKey("useAndroidX")) {
                                this.useAndroidX = (Boolean) r.get("useAndroidX");
                            }
                            
                            Map<String, String> loadedColors = (Map<String, String>) r.get("colors");
                            if (loadedColors != null && !loadedColors.isEmpty()) {
                                this.colors = new HashMap<String, String>();
                                Set<Map.Entry<String, String>> colorSet = loadedColors.entrySet();
                                for (Map.Entry<String, String> entry : colorSet) {
                                    String colorValue = entry.getValue();
                                    if (!colorValue.startsWith("#")) {
                                        colorValue = "#" + colorValue;
                                    }
                                    this.colors.put(entry.getKey(), colorValue);
                                }
                                android.util.Log.d("ar", "Colors loaded: " + this.colors.toString());
                            } else {
                                setDefaultColors();
                            }
                            
                            Map<String, String> loadedStyles = (Map<String, String>) r.get("styles");
                            if (loadedStyles != null && !loadedStyles.isEmpty()) {
                                this.styles = loadedStyles;
                            }
                            
                            Map<String, String> loadedDimens = (Map<String, String>) r.get("dimens");
                            if (loadedDimens != null && !loadedDimens.isEmpty()) {
                                this.dimens = loadedDimens;
                            }
                            
                            Map<String, String> loadedAttributes = (Map<String, String>) r.get("attributes");
                            if (loadedAttributes != null && !loadedAttributes.isEmpty()) {
                                this.attributes = loadedAttributes;
                            }
                            
                            Map<String, String> loadedStrings = (Map<String, String>) r.get("strings");
                            if (loadedStrings != null && !loadedStrings.isEmpty()) {
                                this.strings = loadedStrings;
                            }
                        } else {
                            setDefaultColors();
                        }
                    } else {
                        setDefaultColors();
                    }
                } else {
                    setDefaultColors();
                }
            } else {
                setDefaultColors();
            }
        } catch (Exception e) {
            e.printStackTrace();
            setDefaultColors();
        }
    }
    
    public void extractResources(String sc_id, String outputPath) {
        try {
            load(null, sc_id);
            
            if (this.colors == null || this.colors.isEmpty()) {
                setDefaultColors();
                save(null, sc_id);
            }
            
            android.util.Log.d("ar", "Extracting colors: " + (this.colors != null ? this.colors.toString() : "null"));
            android.util.Log.d("ar", "useAndroidX: " + this.useAndroidX);
            
            File valuesDir = new File(outputPath + "/values");
            if (!valuesDir.exists()) {
                valuesDir.mkdirs();
            }
            
            saveResourceFile(valuesDir, "colors.xml", generateColors());
            saveResourceFile(valuesDir, "styles.xml", generateStyles());
            saveResourceFile(valuesDir, "dimens.xml", generateDimens());
            saveResourceFile(valuesDir, "strings.xml", generateStrings());
            
            String attrs = generateAttributes();
            if (!attrs.isEmpty()) {
                saveResourceFile(valuesDir, "attrs.xml", attrs);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}