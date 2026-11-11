package com.nexusteam.blacklogics.design;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reads <base>/<sc_id>/Injection/appcompat/<layoutName> JSON files produced by
 * AppCompatManagerActivity and exposes the attributes per injection type.
 *
 * File format:
 *   [{"type":"toolbar","value":"android:layout_width=\"0dp\""}, ...]
 *
 * Name resolution: the generators pass the JAVA name (e.g. MainActivity), but the
 * manager may have saved the file under the XML name (e.g. activity_main or
 * set_up_activity). load() therefore tries several equivalent spellings so both
 * conventions resolve to the same file.
 */
public final class AppCompatInjectionReader {

    private static final String BASE_PATH = "/storage/emulated/0/.blacklogics/data/";

    /** type -> (attrName -> attrValue)  (later entries win, matching the editor's find()) */
    private final Map<String, Map<String, String>> byType = new LinkedHashMap<>();

    private AppCompatInjectionReader() {}

    // ------------------------------------------------------------------ load

    /**
     * Returns null when no matching injection file exists.
     *
     * Tries, in order:
     *   MainActivity       -> MainActivity, main_activity, activity_main, Main, main
     *   set_up_activity    -> set_up_activity, SetUpActivity
     *   MainActivity.java  -> (same as MainActivity, extension stripped)
     */
    public static AppCompatInjectionReader load(String scId, String layoutName) {
        if (scId == null || layoutName == null || layoutName.trim().isEmpty()) return null;

        String base = layoutName.trim();
        String noExt = base.endsWith(".xml")
                ? base.substring(0, base.length() - 4)
                : base;
        String trimmed = trimActivitySuffix(noExt);   // "Main" (or null)

        String[] candidates = new String[] {
                base,                                   // MainActivity
                noExt,                                  // MainActivity
                pascalToSnake(noExt),                   // main_activity
                trimmed,                                // Main
                pascalToSnake(trimmed),                 // main
                trimmed != null ? "activity_" + pascalToSnake(trimmed) : null, // activity_main
                snakeToPascal(noExt)                    // SetUpActivity (for set_up_activity)
        };

        for (String cand : candidates) {
            if (cand == null) continue;
            cand = cand.trim();
            if (cand.isEmpty()) continue;
            File f = new File(BASE_PATH + scId + "/Injection/appcompat/" + cand);
            if (!f.isFile()) continue;
            try {
                String raw = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
                return parse(raw);
            } catch (Exception e) {
                // try next candidate
            }
        }
        return null;
    }

    /** Same as load() but reads from an explicit folder path. */
    public static AppCompatInjectionReader loadFromDir(String dir, String layoutName) {
        if (dir == null || layoutName == null || layoutName.trim().isEmpty()) return null;

        String base = layoutName.trim();
        String noExt = base.endsWith(".xml")
                ? base.substring(0, base.length() - 4)
                : base;
        String trimmed = trimActivitySuffix(noExt);

        String[] candidates = new String[] {
                base, noExt, pascalToSnake(noExt), trimmed, pascalToSnake(trimmed),
                trimmed != null ? "activity_" + pascalToSnake(trimmed) : null,
                snakeToPascal(noExt)
        };

        for (String cand : candidates) {
            if (cand == null) continue;
            cand = cand.trim();
            if (cand.isEmpty()) continue;
            File f = new File(dir, cand);
            if (!f.isFile()) continue;
            try {
                String raw = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
                return parse(raw);
            } catch (Exception e) {
                // try next
            }
        }
        return null;
    }

    // ------------------------------------------------------------------ parse

    private static AppCompatInjectionReader parse(String json) throws Exception {
        AppCompatInjectionReader r = new AppCompatInjectionReader();
        String s = json == null ? "" : json.trim();
        if (s.isEmpty()) return r;
        JSONArray arr = new JSONArray(s);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            String type = o.getString("type");
            String raw = o.getString("value");
            int eq = raw.indexOf('=');
            String name, val;
            if (eq < 0) {
                name = raw.trim();
                val = "";
            } else {
                name = raw.substring(0, eq).trim();
                val = raw.substring(eq + 1).trim();
                if (val.length() >= 2 && val.startsWith("\"") && val.endsWith("\"")) {
                    val = val.substring(1, val.length() - 1);
                }
                val = val.replace("&quot;", "\"");
            }
            Map<String, String> m = r.byType.get(type);
            if (m == null) {
                m = new LinkedHashMap<>();
                r.byType.put(type, m);
            }
            m.put(name, val);
        }
        return r;
    }

    // ------------------------------------------------------------------ accessors

    public boolean isEmpty() { return byType.isEmpty(); }

    public boolean has(String type) {
        Map<String, String> m = byType.get(type);
        return m != null && !m.isEmpty();
    }

    public Map<String, String> attrs(String type) {
        Map<String, String> m = byType.get(type);
        return m == null ? new LinkedHashMap<String, String>() : m;
    }

    public String get(String type, String attrName) {
        Map<String, String> m = byType.get(type);
        if (m == null) return null;
        String v = m.get(attrName);
        return (v == null || v.isEmpty()) ? null : v;
    }

    public static String asXmlAttrs(Map<String, String> attrs, String indent) {
        if (attrs == null || attrs.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : attrs.entrySet()) {
            if (e.getKey().startsWith("xmlns:")) continue;
            if (e.getValue() == null || e.getValue().isEmpty()) continue;
            sb.append('\n').append(indent)
              .append(e.getKey()).append("=\"").append(escapeAttr(e.getValue())).append('"');
        }
        return sb.toString();
    }

    private static String escapeAttr(String v) {
        return v.replace("&", "&amp;").replace("\"", "&quot;")
                .replace("<", "&lt;").replace(">", "&gt;");
    }

    // ------------------------------------------------------------------ name conversion

    /** MainActivity -> main_activity ; SetUpActivity -> set_up_activity */
    private static String pascalToSnake(String s) {
        if (s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0 && s.charAt(i - 1) != '_'
                        && !Character.isUpperCase(s.charAt(i - 1))) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /** set_up_activity -> SetUpActivity ; main_activity -> MainActivity */
    private static String snakeToPascal(String s) {
        if (s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder();
        boolean upperNext = true;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '_' || c == '-' || c == ' ' || c == '.') {
                upperNext = true;
            } else if (upperNext) {
                sb.append(Character.toUpperCase(c));
                upperNext = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /** MainActivity -> Main ; main_activity -> main ; else null */
    private static String trimActivitySuffix(String s) {
        if (s == null) return null;
        if (s.endsWith("Activity") && s.length() > "Activity".length()) {
            return s.substring(0, s.length() - "Activity".length());
        }
        if (s.endsWith("_activity") && s.length() > "_activity".length()) {
            return s.substring(0, s.length() - "_activity".length());
        }
        return null;
    }
}