package com.nexusteam.blacklogics.sdk.util;

import com.nexusteam.blacklogics.generator.source.model.ActivityNaming;
import com.nexusteam.blacklogics.manager.ProjectConfigManager;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.manager.GradleConfigManager;
import com.nexusteam.blacklogics.model.DataModel;
import com.apk.builder.FileUtil;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActivityConfig {

    private static final String TAG = "SDKActivityConfig";

    private Map<String, Object> activitiesConfig;
    private AndroidManifestGenerator manifestGenerator;
    private GradleConfigManager gradleConfig;
    private ProjectConfigManager projectConfig;
    private String currentScId;
    private Context currentContext;

    public ActivityConfig() {
        this.activitiesConfig = new HashMap<String, Object>();
        this.manifestGenerator = new AndroidManifestGenerator();
        this.gradleConfig = new GradleConfigManager();
        this.projectConfig = new ProjectConfigManager();
    }

    public void init(Context context, String sc_id) {
        this.currentContext = context;
        this.currentScId = sc_id;
        projectConfig.load(context, sc_id);
        gradleConfig.load(context, sc_id);
        manifestGenerator.load(context, sc_id);
        load(context, sc_id);
    }

    public ActivityConfig setActivityConfig(String activityName,
                                           boolean statusBar,
                                           boolean toolbar,
                                           boolean drawer,
                                           boolean fab,
                                           String type,
                                           String orientation,
                                           String layoutName) {

        Map<String, Object> config = new HashMap<String, Object>();
        config.put("statusBar", Boolean.valueOf(statusBar));
        config.put("toolbar", Boolean.valueOf(toolbar));
        config.put("drawer", Boolean.valueOf(drawer));
        config.put("fab", Boolean.valueOf(fab));
        config.put("type", normalizeType(type));
        config.put("orientation", orientation);
        config.put("layoutName", layoutName);
        config.put("lastModified", Long.valueOf(System.currentTimeMillis()));
        config.put("androidX", Boolean.valueOf(isAndroidXEnabled()));

        setupManifestAttributes(activityName, config);

        activitiesConfig.put(normalizeActivityName(activityName), config);
        return this;
    }

    /** Normalize both enum-style and legacy label types to canonical display names. */
    private static String normalizeType(String type) {
        if (type == null) return "Activity";
        String t = type.trim();
        if (t.isEmpty()) return "Activity";
        if (t.equalsIgnoreCase("Activity")
                || t.equalsIgnoreCase("AppCompatActivity")) return "Activity";
        if (t.equalsIgnoreCase("Fragment")
                || t.equalsIgnoreCase("FRAGMENT_ACTIVITY")) return "Fragment";
        if (t.equalsIgnoreCase("DialogFragment")
                || t.equalsIgnoreCase("DIALOG_FRAGMENT")) return "DialogFragment";
        if (t.equalsIgnoreCase("BottomSheet")
                || t.equalsIgnoreCase("BottomSheetDialogFragment")
                || t.equalsIgnoreCase("BOTTOM_SHEET_DIALOG_FRAGMENT")) return "BottomSheet";
        if (t.equalsIgnoreCase("AppCompatDialogFragment")
                || t.equalsIgnoreCase("APP_COMPAT_DIALOG_FRAGMENT")) return "AppCompatDialogFragment";
        return t; // pass through unknown
    }

    private void setupManifestAttributes(String activityName, Map<String, Object> config) {
        String fullActivityName = getFullActivityName(activityName, (String) config.get("type"));
        Map<String, String> attrs = new HashMap<String, String>();

        String theme = resolveTheme(
                ((Boolean) config.get("toolbar")).booleanValue(),
                ((Boolean) config.get("statusBar")).booleanValue(),
                ((Boolean) config.get("androidX")).booleanValue()
        );
        attrs.put("theme", "@style/" + theme);
        attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
        attrs.put("windowSoftInputMode", "adjustResize");

        String orientation = (String) config.get("orientation");
        if (!"Both".equals(orientation)) {
            if ("Portrait".equals(orientation)) {
                attrs.put("screenOrientation", "portrait");
            } else {
                attrs.put("screenOrientation", "landscape");
            }
        }

        if (isLauncherActivity(activityName)) {
            attrs.put("exported", "true");
            manifestGenerator.addLauncherActivity(fullActivityName);
        } else {
            attrs.put("exported", isExported(activityName) ? "true" : "false");
        }

        String parentActivity = getParentActivity(activityName);
        if (parentActivity != null) {
            attrs.put("parentActivityName", parentActivity);
        }

        manifestGenerator.addActivity(fullActivityName, attrs);

        if (((Boolean) config.get("drawer")).booleanValue()) setupDrawerDependencies();
        if (((Boolean) config.get("fab")).booleanValue())    setupFabDependencies();
    }

    /**
     * Single-source-of-truth fully-qualified name.
     *   "frag" + "Fragment"       -> ".FragActivity"   (not ".FragFragmentActivity")
     *   "main" + "Activity"       -> ".MainActivity"
     *   "FragActivity" + any      -> ".FragActivity"   (idempotent)
     */
    private String getFullActivityName(String activityName, String type) {
        String name = ActivityNaming.stripJava(ActivityNaming.stripXml(activityName));
        if (name == null || name.isEmpty()) name = DataModel.DEFAULT_LAYOUT_NAME;

        // ActivityNaming already guarantees the "Activity" suffix.
        String className = ActivityNaming.toActivityClassName(name);
        return "." + className;
    }

    private String normalizeActivityName(String activityName) {
        if (activityName == null) return null;
        String name = activityName.trim();
        name = ActivityNaming.stripJava(ActivityNaming.stripXml(name));

        // Strip any known activity/fragment suffix so lookup keys are stable.
        String act  = ActivityNaming.activitySuffix();    // "Activity"
        String frag = ActivityNaming.fragmentSuffix();    // "Fragment"

        if (name.length() > act.length() && name.endsWith(act)) {
            name = name.substring(0, name.length() - act.length());
        } else if (name.length() > frag.length() && name.endsWith(frag)) {
            name = name.substring(0, name.length() - frag.length());
        }
        return name;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> resolveActivityConfig(String activityName) {
        if (activityName == null || activitiesConfig == null || activitiesConfig.isEmpty()) {
            return null;
        }
        String norm = normalizeActivityName(activityName);
        Object hit = activitiesConfig.get(norm);
        if (hit instanceof Map) return (Map<String, Object>) hit;

        String raw = activityName.trim();
        hit = activitiesConfig.get(raw);
        if (hit instanceof Map) return (Map<String, Object>) hit;

        String normLower = norm != null ? norm.toLowerCase() : "";
        for (Map.Entry<String, Object> e : activitiesConfig.entrySet()) {
            if (e.getKey() == null) continue;
            String k = e.getKey();
            String kn = normalizeActivityName(k);
            if (norm != null && (norm.equals(k) || norm.equals(kn)
                    || normLower.equals(k.toLowerCase())
                    || (kn != null && normLower.equals(kn.toLowerCase())))) {
                if (e.getValue() instanceof Map) return (Map<String, Object>) e.getValue();
            }
        }
        for (Map.Entry<String, Object> e : activitiesConfig.entrySet()) {
            if (!(e.getValue() instanceof Map)) continue;
            Map<String, Object> cfg = (Map<String, Object>) e.getValue();
            Object ln = cfg.get("layoutName");
            if (ln != null && norm != null
                    && norm.equalsIgnoreCase(String.valueOf(ln).replace(".xml", ""))) {
                return cfg;
            }
        }
        return null;
    }

    private static boolean readBool(Map<String, Object> config, String key, boolean def) {
        if (config == null) return def;
        Object v = config.get(key);
        if (v == null) return def;
        if (v instanceof Boolean) return ((Boolean) v).booleanValue();
        if (v instanceof Number)  return ((Number) v).intValue() != 0;
        String s = String.valueOf(v).trim();
        if ("true".equalsIgnoreCase(s) || "1".equals(s) || "yes".equalsIgnoreCase(s)) return true;
        if ("false".equalsIgnoreCase(s) || "0".equals(s) || "no".equalsIgnoreCase(s)) return false;
        return def;
    }

    private String resolveTheme(boolean toolbar, boolean statusBar, boolean androidx) {
        if (androidx) {
            if (toolbar && statusBar) return "Theme.MaterialComponents.DayNight.NoActionBar";
            if (toolbar)              return "Theme.MaterialComponents.DayNight.NoActionBar.Fullscreen";
            if (statusBar)            return "Theme.MaterialComponents.DayNight.NoActionBar.Transparent";
            return "Theme.MaterialComponents.DayNight.NoActionBar.Fullscreen";
        } else {
            if (toolbar && statusBar) return "AppTheme";
            if (toolbar)              return "AppTheme.NoStatusBar";
            if (statusBar)            return "AppTheme.NoActionBar";
            return "AppTheme.FullScreen";
        }
    }

    private void setupDrawerDependencies() {
        if (isAndroidXEnabled()) {
            gradleConfig.addDependency("implementation 'com.google.android.material:material:1.3.0'");
            manifestGenerator.addActivity(
                    "androidx.drawerlayout.widget.DrawerLayout",
                    new HashMap<String, String>());
        } else {
            gradleConfig.addDependency("implementation 'androidx.drawerlayout:drawerlayout:1.2.0'");
        }
    }

    private void setupFabDependencies() {
        if (isAndroidXEnabled()) {
            gradleConfig.addDependency("implementation 'com.google.android.material:material:1.3.0'");
        }
    }

    private boolean isAndroidXEnabled() {
        try { return manifestGenerator.isUseAndroidX(); }
        catch (Exception e) { return false; }
    }

    public boolean getAndroidXEnabled() { return isAndroidXEnabled(); }

    private boolean isLauncherActivity(String activityName) {
        try {
            Object raw = activitiesConfig.get("main");
            if (raw instanceof Map) {
                Map<String, Object> mainCfg = (Map<String, Object>) raw;
                Object launcher = mainCfg.get("launcher");
                if (launcher != null) {
                    String norm = normalizeActivityName(String.valueOf(launcher));
                    return norm != null && norm.equals(normalizeActivityName(activityName));
                }
            }
        } catch (Exception ignore) {}
        return false;
    }

    private boolean isExported(String activityName) {
        Map<String, Object> config = getActivityConfig(activityName);
        if (config != null && config.get("exported") != null) {
            Object v = config.get("exported");
            if (v instanceof Boolean) return ((Boolean) v).booleanValue();
            return "true".equalsIgnoreCase(String.valueOf(v));
        }
        return false;
    }

    private String getParentActivity(String activityName) {
        Map<String, Object> config = getActivityConfig(activityName);
        if (config != null) return (String) config.get("parentActivity");
        return null;
    }

    /** Merges launcher flag into existing 'main' entry instead of wiping it. */
    @SuppressWarnings("unchecked")
    public void setLauncherActivity(String activityName) {
        Object raw = activitiesConfig.get("main");
        Map<String, Object> mainConfig;
        if (raw instanceof Map) {
            mainConfig = (Map<String, Object>) raw;
        } else {
            mainConfig = new HashMap<String, Object>();
            activitiesConfig.put("main", mainConfig);
        }
        mainConfig.put("launcher", activityName);

        Map<String, Object> activity = getActivityConfig(activityName);
        if (activity != null) {
            String type = normalizeType((String) activity.get("type"));
            String fullName = getFullActivityName(activityName, type);
            manifestGenerator.addLauncherActivity(fullName);
        }
    }

    public void updateActivityTheme(String activityName) {
        Map<String, Object> config = getActivityConfig(activityName);
        if (config != null) {
            String theme = resolveTheme(
                    readBool(config, "toolbar", true),
                    readBool(config, "statusBar", true),
                    isAndroidXEnabled());
            String type = normalizeType((String) config.get("type"));
            String fullName = getFullActivityName(activityName, type);

            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("theme", "@style/" + theme);
            manifestGenerator.addActivity(fullName, attrs);
        }
    }

    public String generateStyles() {
        StringBuilder styles = new StringBuilder();
        styles.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        styles.append("<resources>\n\n");

        boolean androidx = isAndroidXEnabled();
        String parent = androidx
                ? "Theme.MaterialComponents.DayNight"
                : "android:Theme.Material.Light.DarkActionBar";

        styles.append("    <style name=\"AppTheme\" parent=\"" + parent + "\">\n");
        styles.append("        <item name=\"colorPrimary\">@color/colorPrimary</item>\n");
        styles.append("        <item name=\"colorPrimaryDark\">@color/colorPrimaryDark</item>\n");
        styles.append("        <item name=\"colorAccent\">@color/colorAccent</item>\n");
        styles.append("    </style>\n\n");

        String p = androidx ? "" : "android:";
        styles.append("    <style name=\"AppTheme.NoActionBar\" parent=\"AppTheme\">\n");
        styles.append("        <item name=\"" + p + "windowActionBar\">false</item>\n");
        styles.append("        <item name=\"" + p + "windowNoTitle\">true</item>\n");
        styles.append("    </style>\n\n");

        styles.append("    <style name=\"AppTheme.NoStatusBar\" parent=\"AppTheme\">\n");
        styles.append("        <item name=\"android:windowFullscreen\">true</item>\n");
        styles.append("    </style>\n\n");

        styles.append("    <style name=\"AppTheme.FullScreen\" parent=\"AppTheme.NoActionBar\">\n");
        styles.append("        <item name=\"android:windowFullscreen\">true</item>\n");
        styles.append("        <item name=\"android:windowContentOverlay\">@null</item>\n");
        styles.append("    </style>\n\n");

        if (androidx) {
            styles.append("    <style name=\"Theme.MaterialComponents.DayNight.NoActionBar.Fullscreen\" parent=\"Theme.MaterialComponents.DayNight.NoActionBar\">\n");
            styles.append("        <item name=\"android:windowFullscreen\">true</item>\n");
            styles.append("    </style>\n\n");

            styles.append("    <style name=\"Theme.MaterialComponents.DayNight.NoActionBar.Transparent\" parent=\"Theme.MaterialComponents.DayNight.NoActionBar\">\n");
            styles.append("        <item name=\"android:windowIsTranslucent\">true</item>\n");
            styles.append("        <item name=\"android:windowBackground\">@android:color/transparent</item>\n");
            styles.append("    </style>\n");
        }

        styles.append("</resources>");
        return styles.toString();
    }

    public Map<String, Object> getActivityConfig(String activityName) {
        return resolveActivityConfig(activityName);
    }

    public Map<String, Object> getAllActivitiesConfig() { return activitiesConfig; }

    public boolean hasActivity(String activityName) {
        return activitiesConfig.containsKey(normalizeActivityName(activityName));
    }

    public ActivityConfig removeActivity(String activityName) {
        activitiesConfig.remove(normalizeActivityName(activityName));
        return this;
    }

    public String getActivityType(String activityName) {
        Map<String, Object> config = getActivityConfig(activityName);
        if (config != null && config.get("type") != null) {
            return (String) config.get("type");
        }
        return "Activity";
    }

    public String getActivityLayout(String activityName) {
        Map<String, Object> config = getActivityConfig(activityName);
        if (config != null && config.get("layoutName") != null) {
            return (String) config.get("layoutName");
        }
        return null;
    }

    public boolean hasToolbar(String activityName)   { return readBool(getActivityConfig(activityName), "toolbar",   true);  }
    public boolean hasFab(String activityName)       { return readBool(getActivityConfig(activityName), "fab",       false); }
    public boolean hasDrawer(String activityName)    { return readBool(getActivityConfig(activityName), "drawer",    false); }
    public boolean hasStatusBar(String activityName) { return readBool(getActivityConfig(activityName), "statusBar", true);  }

    public String getOrientation(String activityName) {
        Map<String, Object> config = getActivityConfig(activityName);
        if (config != null && config.get("orientation") != null) {
            return (String) config.get("orientation");
        }
        return "Both";
    }

    public Set<String> getActivityNames() { return activitiesConfig.keySet(); }
    public int getActivityCount()         { return activitiesConfig.size(); }

    public void clear() { activitiesConfig.clear(); }

    // =====================================================================
    // Persistence (with strict sc_id guard)
    // =====================================================================

    public void save(Context context, String sc_id) {
        if (sc_id == null || sc_id.trim().isEmpty()) {
            Log.e(TAG, "save: invalid sc_id='" + sc_id + "', skipping");
            return;
        }
        final String sc = sc_id.trim();
        try {
            File dir = new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc);
            if (!dir.exists() && !dir.mkdirs()) {
                Log.e(TAG, "save: cannot create " + dir.getAbsolutePath());
                return;
            }

            File file = new File(dir, "logic");
            JSONObject json;
            if (file.exists()) {
                json = new JSONObject(readFile(file));
            } else {
                json = new JSONObject();
            }

            String configJson = new Gson().toJson(activitiesConfig);
            String base64 = Base64.encodeToString(
                    configJson.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
            json.put("activities_config", base64);

            writeFile(file, json.toString());

            manifestGenerator.save(context, sc);
            gradleConfig.save(context, sc);
            Log.d(TAG, "save sc_id=" + sc + " count=" + activitiesConfig.size());
        } catch (Exception e) {
            Log.e(TAG, "save failed", e);
        }
    }

    public void load(Context context, String sc_id) {
        if (sc_id == null || sc_id.trim().isEmpty()) {
            Log.e(TAG, "load: invalid sc_id='" + sc_id + "', skipping");
            return;
        }
        final String sc = sc_id.trim();
        try {
            File dir = new File(FileUtil.getExternalStorageDir() + "/.blacklogics/data/" + sc);
            File file = new File(dir, "logic");

            if (file.exists()) {
                JSONObject json = new JSONObject(readFile(file));
                if (json.has("activities_config")) {
                    String base64 = json.getString("activities_config");
                    byte[] decoded = Base64.decode(base64, Base64.DEFAULT);
                    String configJson = new String(decoded, StandardCharsets.UTF_8);

                    activitiesConfig = new Gson().fromJson(configJson,
                            new TypeToken<Map<String, Object>>(){}.getType());
                    if (activitiesConfig == null) activitiesConfig = new HashMap<String, Object>();
                }
            }

            manifestGenerator.load(context, sc);
            gradleConfig.load(context, sc);
        } catch (Exception e) {
            Log.e(TAG, "load failed", e);
            activitiesConfig = new HashMap<String, Object>();
        }
    }

    public void extractAll(String sc_id, String outputPath) {
        manifestGenerator.extractManifest(sc_id, outputPath);

        try {
            File valuesDir = new File(outputPath + "/res/values");
            if (!valuesDir.exists()) valuesDir.mkdirs();

            File stylesFile = new File(valuesDir, "styles.xml");
            writeFile(stylesFile, generateStyles());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gradleConfig.extractGradleFiles(sc_id, outputPath);
    }

    private String readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }

    private void writeFile(File file, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    public String exportAsJson() {
        return new Gson().toJson(activitiesConfig);
    }

    public void importFromJson(String json) {
        try {
            activitiesConfig = new Gson().fromJson(json,
                    new TypeToken<Map<String, Object>>(){}.getType());
            if (activitiesConfig == null) activitiesConfig = new HashMap<String, Object>();
        } catch (Exception e) {
            e.printStackTrace();
            activitiesConfig = new HashMap<String, Object>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getActivitiesByType(String type) {
        List<String> result = new ArrayList<String>();
        String want = normalizeType(type);
        for (Map.Entry<String, Object> entry : activitiesConfig.entrySet()) {
            if (!(entry.getValue() instanceof Map)) continue;
            Map<String, Object> config = (Map<String, Object>) entry.getValue();
            if (config != null) {
                String configType = normalizeType((String) config.get("type"));
                if (want.equals(configType)) result.add(entry.getKey());
            }
        }
        return result;
    }

    public boolean isValidActivity(String activityName) {
        Map<String, Object> config = getActivityConfig(activityName);
        if (config == null) return false;
        return config.containsKey("type")
                && config.containsKey("layoutName")
                && config.containsKey("statusBar")
                && config.containsKey("toolbar");
    }
}