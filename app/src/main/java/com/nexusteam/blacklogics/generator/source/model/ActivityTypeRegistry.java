package com.nexusteam.blacklogics.generator.source.model;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.model.DataModel;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Stores per-screen ActivityConfig (Activity / Fragment / DialogFragment / …).
 *
 * Key lookup is fuzzy: UI registers under ProjectFileBean.fileName (e.g. "frag")
 * while the generator looks up activity.getJavaName() (e.g. "FragActivity").
 * Both must resolve to the same config.
 */
public class ActivityTypeRegistry {

    private static final String TAG = "ActivityTypeRegistry";

    private static final String ACT_SUFFIX  = DataModel.ADD_ACTIVITY;   // "Activity"
    private static final String FRAG_SUFFIX = DataModel.ADD_FRAGMENT;   // "Fragment"

    private static ActivityTypeRegistry instance;
    private final Map<String, ActivityConfig> activityConfigs = new LinkedHashMap<>();
    private final Gson gson;

    private ActivityTypeRegistry() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .create();
    }

    public static ActivityTypeRegistry getInstance() {
        if (instance == null) instance = new ActivityTypeRegistry();
        return instance;
    }

    // =====================================================================
    // Canonical multi-key helpers
    // =====================================================================

    public static Set<String> canonicalKeys(String name) {
        Set<String> keys = new LinkedHashSet<>();
        if (name == null || name.trim().isEmpty()) return keys;

        String n = name.trim();
        keys.add(n);

        if (n.endsWith(".java")) { n = n.substring(0, n.length() - 5); keys.add(n); }
        if (n.endsWith(".xml"))  { n = n.substring(0, n.length() - 4); keys.add(n); }

        keys.add(n.toLowerCase());

        if (!n.endsWith(ACT_SUFFIX) && !n.endsWith(FRAG_SUFFIX)) {
            String pascal = toPascalCase(n);
            if (!pascal.isEmpty()) {
                keys.add(pascal);
                keys.add(pascal + ACT_SUFFIX);
                keys.add(pascal + FRAG_SUFFIX);
                keys.add(pascal + ACT_SUFFIX + ".java");
                keys.add(pascal + FRAG_SUFFIX + ".java");
            }
        }

        if (n.endsWith(ACT_SUFFIX) && n.length() > ACT_SUFFIX.length()) {
            String base = n.substring(0, n.length() - ACT_SUFFIX.length());
            keys.add(base);
            keys.add(base.toLowerCase());
            keys.add(toSnakeOrLower(base));
        }
        if (n.endsWith(FRAG_SUFFIX) && n.length() > FRAG_SUFFIX.length()) {
            String base = n.substring(0, n.length() - FRAG_SUFFIX.length());
            keys.add(base);
            keys.add(base.toLowerCase());
            keys.add(toSnakeOrLower(base));
        }

        keys.add(n + ".java");
        return keys;
    }

    private static String toPascalCase(String s) {
        if (s == null || s.isEmpty()) return "";
        if (Character.isUpperCase(s.charAt(0)) && !s.contains("_") && !s.contains("-")) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        boolean up = true;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '_' || c == '-' || c == ' ') { up = true; continue; }
            if (up) { sb.append(Character.toUpperCase(c)); up = false; }
            else    { sb.append(Character.toLowerCase(c)); }
        }
        return sb.toString();
    }

    private static String toSnakeOrLower(String s) {
        if (s == null || s.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c) && i > 0) sb.append('_');
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    private static Set<ActivityConfig> identitySet() {
        return java.util.Collections.newSetFromMap(
                new IdentityHashMap<ActivityConfig, Boolean>());
    }

    private void indexConfig(String primaryKey, ActivityConfig config) {
        if (config == null) return;
        Set<String> keys = canonicalKeys(primaryKey);
        if (config.getActivityName() != null) keys.addAll(canonicalKeys(config.getActivityName()));
        if (config.getLayoutName()   != null) keys.addAll(canonicalKeys(config.getLayoutName()));
        for (String k : keys) activityConfigs.put(k, config);
    }

    // =====================================================================
    // Persistence
    // =====================================================================

    public void save(Context context, String sc_id) {
        if (sc_id == null || sc_id.trim().isEmpty()) {
            Log.e(TAG, "save: invalid sc_id='" + sc_id + "', skipping write");
            return;
        }
        final String sc = sc_id.trim();
        FileOutputStream fos = null;
        try {
            File dir = new File("/storage/emulated/0/.blacklogics/data/" + sc);
            if (!dir.exists() && !dir.mkdirs()) {
                Log.e(TAG, "save: cannot create dir " + dir.getAbsolutePath());
                return;
            }

            File file = new File(dir, "activity_types");
            JSONObject json = new JSONObject();

            Map<String, Map<String, Object>> configMap = new LinkedHashMap<>();
            Set<ActivityConfig> seen = identitySet();
            for (Map.Entry<String, ActivityConfig> entry : activityConfigs.entrySet()) {
                ActivityConfig cfg = entry.getValue();
                if (cfg == null || seen.contains(cfg)) continue;
                seen.add(cfg);
                String primary = cfg.getActivityName() != null
                        ? cfg.getActivityName()
                        : entry.getKey();
                configMap.put(primary, cfg.toMap());
            }

            String jsonStr = gson.toJson(configMap);
            String base64 = Base64.encodeToString(
                    jsonStr.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);

            json.put("activity_type_data", base64);
            json.put("version", "1.2");
            json.put("saved_at", System.currentTimeMillis());

            fos = new FileOutputStream(file);
            fos.write(json.toString().getBytes(StandardCharsets.UTF_8));
            Log.d(TAG, "save sc_id=" + sc + " entries=" + configMap.size()
                    + " file=" + file.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "save failed", e);
        } finally {
            if (fos != null) try { fos.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void load(Context context, String sc_id) {
        if (sc_id == null || sc_id.trim().isEmpty()) {
            Log.e(TAG, "load: invalid sc_id='" + sc_id + "', skipping read");
            return;
        }
        final String sc = sc_id.trim();
        FileInputStream fis = null;
        try {
            File dir = new File("/storage/emulated/0/.blacklogics/data/" + sc);
            File file = new File(dir, "activity_types");
            if (!file.exists()) return;

            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            String content = new String(data, StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(content);
            if (!json.has("activity_type_data")) return;

            String base64 = json.getString("activity_type_data");
            String decoded = new String(
                    Base64.decode(base64, Base64.DEFAULT), StandardCharsets.UTF_8);

            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> configMap = gson.fromJson(decoded, type);

            activityConfigs.clear();
            if (configMap != null) {
                for (Map.Entry<String, Map<String, Object>> entry : configMap.entrySet()) {
                    ActivityConfig config = ActivityConfig.fromMap(entry.getValue());
                    indexConfig(entry.getKey(), config);
                }
            }
            Log.d(TAG, "load sc_id=" + sc + " mapSize=" + activityConfigs.size());
        } catch (Exception e) {
            Log.e(TAG, "load failed", e);
        } finally {
            if (fis != null) try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    // =====================================================================
    // API
    // =====================================================================

    /**
     * Idempotent: if a config already exists under any alias of {@code name},
     * updates its type/layout in place (preserves launcher, dialogConfig, etc.).
     */
    public void registerActivity(String name, ActivityType type, String layoutName) {
        if (name == null) return;

        ActivityConfig existing = getActivityConfig(name);
        if (existing != null) {
            if (type != null) existing.setType(type);
            if (layoutName != null && !layoutName.isEmpty()) {
                existing.setLayoutName(layoutName);
            }
            // Ensure new alias set points to the same object.
            indexConfig(name, existing);
            Log.d(TAG, "register(update) name=" + name + " type=" + existing.getType()
                    + " layout=" + existing.getLayoutName());
            return;
        }

        ActivityConfig config = new ActivityConfig(name, type, layoutName);
        indexConfig(name, config);
        Log.d(TAG, "register(new) name=" + name + " type=" + type + " layout=" + layoutName);
    }

    public void registerActivity(ActivityConfig config) {
        if (config == null) return;
        String name = config.getActivityName();
        if (name == null) name = "unknown";
        indexConfig(name, config);
    }

    public ActivityConfig getActivityConfig(String name) {
        if (name == null) return null;
        ActivityConfig c = activityConfigs.get(name);
        if (c != null) return c;
        for (String k : canonicalKeys(name)) {
            c = activityConfigs.get(k);
            if (c != null) {
                Log.d(TAG, "getActivityConfig alias hit name=" + name + " via=" + k
                        + " type=" + c.getType());
                return c;
            }
        }
        Log.d(TAG, "getActivityConfig MISS name=" + name + " knownKeys=" + activityConfigs.keySet());
        return null;
    }

    public boolean hasActivity(String name) { return getActivityConfig(name) != null; }

    public void removeActivity(String name) {
        if (name == null) return;
        ActivityConfig cfg = getActivityConfig(name);
        if (cfg == null) { activityConfigs.remove(name); return; }
        List<String> toRemove = new ArrayList<>();
        for (Map.Entry<String, ActivityConfig> e : activityConfigs.entrySet()) {
            if (e.getValue() == cfg) toRemove.add(e.getKey());
        }
        for (String k : toRemove) activityConfigs.remove(k);
    }

    public List<ActivityConfig> getAllActivities() {
        Set<ActivityConfig> seen = identitySet();
        List<ActivityConfig> out = new ArrayList<>();
        for (ActivityConfig c : activityConfigs.values()) {
            if (c != null && seen.add(c)) out.add(c);
        }
        return out;
    }

    public List<String> getAllActivityNames() {
        List<String> names = new ArrayList<>();
        for (ActivityConfig c : getAllActivities()) {
            if (c.getActivityName() != null) names.add(c.getActivityName());
        }
        return names;
    }

    public List<ActivityConfig> getActivitiesByType(ActivityType type) {
        List<ActivityConfig> result = new ArrayList<>();
        for (ActivityConfig config : getAllActivities()) {
            if (config.getType() == type) result.add(config);
        }
        return result;
    }

    public ActivityConfig getLauncherActivity() {
        for (ActivityConfig config : getAllActivities()) {
            if (config.isLauncher()) return config;
        }
        return null;
    }

    public void clear() { activityConfigs.clear(); }
    public int size()   { return getAllActivities().size(); }

    public String exportToJson() {
        Map<String, Map<String, Object>> configMap = new LinkedHashMap<>();
        for (ActivityConfig c : getAllActivities()) {
            String primary = c.getActivityName() != null ? c.getActivityName() : "unknown";
            configMap.put(primary, c.toMap());
        }
        return gson.toJson(configMap);
    }

    public void importFromJson(String json) {
        Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
        Map<String, Map<String, Object>> configMap = gson.fromJson(json, type);
        activityConfigs.clear();
        if (configMap != null) {
            for (Map.Entry<String, Map<String, Object>> entry : configMap.entrySet()) {
                ActivityConfig config = ActivityConfig.fromMap(entry.getValue());
                indexConfig(entry.getKey(), config);
            }
        }
    }
}