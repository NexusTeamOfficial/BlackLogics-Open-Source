package com.nexusteam.blacklogics.util;

import android.util.Log;
import com.nexusteam.blacklogics.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * SK Pro style: load string resource keys from project strings.xml.
 * Paths mirror Sketchware / BlackLogics resource layouts.
 */
public final class ProjectStringResources {

    private static final String TAG = "ProjectStringResources";

    private ProjectStringResources() {}

    /** Ordered unique keys from the first strings.xml found for scId. */
    public static ArrayList<String> loadKeys(String scId) {
        ArrayList<String> keys = new ArrayList<String>();
        LinkedHashSet<String> seen = new LinkedHashSet<String>();
        if (scId == null || scId.length() == 0) return keys;

        String[] paths = candidatePaths(scId);
        for (int i = 0; i < paths.length; i++) {
            String xml = readFile(paths[i]);
            if (xml == null || xml.length() == 0) continue;
            parseKeys(xml, seen);
            if (!seen.isEmpty()) {
                Log.d(TAG, "loaded " + seen.size() + " keys from " + paths[i]);
                break;
            }
        }
        keys.addAll(seen);
        return keys;
    }

    /** First existing strings.xml path, or preferred create path. */
    public static String resolveStringsXmlPath(String scId) {
        if (scId == null) return null;
        String[] paths = candidatePaths(scId);
        for (int i = 0; i < paths.length; i++) {
            if (new File(paths[i]).exists()) return paths[i];
        }
        return paths.length > 0 ? paths[0] : null;
    }

    /** Ensure parent dirs + minimal strings.xml exist; returns path. */
    public static String ensureStringsXml(String scId) {
        String path = resolveStringsXmlPath(scId);
        if (path == null) return null;
        File f = new File(path);
        if (f.exists()) return path;
        try {
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            String seed = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                    + "<resources>\n"
                    + "    <string name=\"app_name\">MyApp</string>\n"
                    + "</resources>\n";
            try {
                FileUtil.writeFile(path, seed);
            } catch (Throwable t) {
                java.io.FileWriter fw = new java.io.FileWriter(f);
                fw.write(seed);
                fw.close();
            }
        } catch (Throwable t) {
            Log.e(TAG, "ensureStringsXml failed", t);
        }
        return path;
    }

    public static String[] candidatePaths(String scId) {
        String base = FileUtil.getExternalStorageDir();
        return new String[] {
                base + "/.blacklogics/data/" + scId + "/files/resource/values/strings.xml",
                base + "/.blacklogics/data/" + scId + "/files/resources/values/strings.xml",
                base + "/.blacklogics/data/" + scId + "/resource/values/strings.xml",
                base + "/.blacklogics/data/" + scId + "/resources/values/strings.xml",
                base + "/.blacklogics/mysc/" + scId + "/files/resource/values/strings.xml",
                base + "/.blacklogics/data/" + scId + "/files/resource/values/strings.xml"
        };
    }

    public static void parseKeys(String xml, LinkedHashSet<String> out) {
        if (xml == null || out == null) return;
        int idx = 0;
        while (idx < xml.length()) {
            int p = xml.indexOf("name=\"", idx);
            if (p < 0) break;
            int startQuote = xml.indexOf('"', p);
            if (startQuote < 0) break;
            int endQuote = xml.indexOf('"', startQuote + 1);
            if (endQuote < 0) break;
            int tag = xml.lastIndexOf('<', p);
            if (tag >= 0) {
                String head = xml.substring(tag, Math.min(p + 1, xml.length())).toLowerCase();
                if (head.contains("<string")) {
                    String k = xml.substring(startQuote + 1, endQuote);
                    if (k.length() > 0) out.add(k);
                }
            }
            idx = endQuote + 1;
        }
    }

    private static String readFile(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) return null;
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            br.close();
            return sb.toString();
        } catch (Throwable t) {
            return null;
        }
    }
}
