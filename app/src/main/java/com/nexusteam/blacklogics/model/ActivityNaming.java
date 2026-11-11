package com.nexusteam.blacklogics.generator.source.model;

import com.nexusteam.blacklogics.model.DataModel;

/**
 * Single source of truth for activity/fragment name resolution.
 *
 * Rules:
 *   - UI passes raw ProjectFileBean.fileName ("frag", "main", "my_screen")
 *   - Generator needs a class name ("FragActivity", "MainActivity", "MyScreenActivity")
 *   - Both must resolve to the SAME canonical key so registry lookup is stable.
 *
 * Do NOT introduce any other suffix source. All code must call these helpers.
 */
public final class ActivityNaming {

    private ActivityNaming() {}

    /** Suffix used for generated activity class names. */
    public static String activitySuffix() {
        return DataModel.ADD_ACTIVITY;   // "Activity"
    }

    /** Suffix used for generated fragment class names. */
    public static String fragmentSuffix() {
        return DataModel.ADD_FRAGMENT;   // "Fragment"
    }

    /** PascalCase version of a layout/fileName: "my_screen" -> "MyScreen". */
    public static String toPascalCase(String name) {
        if (name == null || name.isEmpty()) return "Main";
        String n = stripXml(stripJava(name.trim()));
        if (n.isEmpty()) return "Main";

        StringBuilder sb = new StringBuilder();
        boolean upper = true;
        for (int i = 0; i < n.length(); i++) {
            char c = n.charAt(i);
            if (c == '_' || c == '-' || c == ' ') { upper = true; continue; }
            sb.append(upper ? Character.toUpperCase(c) : c);
            upper = false;
        }
        return sb.length() == 0 ? "Main" : sb.toString();
    }

    /**
     * Full class name as the generator expects it:
     *   "frag"          -> "FragActivity"
     *   "main"          -> "MainActivity"
     *   "FragActivity"  -> "FragActivity"  (unchanged)
     *   "dialog_frag"   -> "DialogFragActivity"
     */
    public static String toActivityClassName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return DataModel.DEFAULT_ACTIVITY;
        }
        String base = stripJava(stripXml(fileName.trim()));
        String suffix = activitySuffix();           // "Activity"

        if (base.length() > suffix.length()
                && base.regionMatches(true, base.length() - suffix.length(),
                                      suffix, 0, suffix.length())) {
            return base;
        }
        return toPascalCase(base) + suffix;
    }

    /** Strip ".java" if present. */
    public static String stripJava(String s) {
        if (s == null) return "";
        if (s.endsWith(".java")) return s.substring(0, s.length() - 5);
        return s;
    }

    /** Strip ".xml" if present. */
    public static String stripXml(String s) {
        if (s == null) return "";
        if (s.endsWith(".xml")) return s.substring(0, s.length() - 4);
        return s;
    }
}