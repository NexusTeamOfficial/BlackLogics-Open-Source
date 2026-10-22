package com.nexusteam.blacklogics.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.nexusteam.blacklogics.model.ProjectTemplate;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProjectCreateAssistant {

    private static final String PREFS = "create_project_pro_prefs";

    public static String suggestPackage(String appName) {
        if (appName == null || appName.trim().length() == 0) {
            return "com.my.newproject";
        }
        String cleaned = appName.trim().toLowerCase(Locale.US)
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "");
        if (cleaned.length() == 0) cleaned = "app";
        if (Character.isDigit(cleaned.charAt(0))) {
            cleaned = "app" + cleaned;
        }
        return "com.my." + cleaned;
    }

    public static boolean isValidPackage(String pkg) {
        if (pkg == null) return false;
        return pkg.matches("^([a-zA-Z_][a-zA-Z0-9_]*\\.)+[a-zA-Z_][a-zA-Z0-9_]*$");
    }

    public static boolean isPackageTaken(String pkg) {
        if (pkg == null) return false;
        try {
            File root = new File(TheBlockLogicsUtil.mysc);
            if (!root.exists()) return false;
            File[] files = root.listFiles();
            if (files == null) return false;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String projectPathPreview(String scId) {
        return TheBlockLogicsUtil.mysc + scId;
    }

    public static double luminance(int color) {
        double r = Color.red(color) / 255.0;
        double g = Color.green(color) / 255.0;
        double b = Color.blue(color) / 255.0;
        r = (r <= 0.03928) ? r / 12.92 : Math.pow((r + 0.055) / 1.055, 2.4);
        g = (g <= 0.03928) ? g / 12.92 : Math.pow((g + 0.055) / 1.055, 2.4);
        b = (b <= 0.03928) ? b / 12.92 : Math.pow((b + 0.055) / 1.055, 2.4);
        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }

    public static boolean isLowContrastOnWhite(int color) {
        double L1 = luminance(color);
        double L2 = 1.0;
        double lighter = Math.max(L1, L2);
        double darker = Math.min(L1, L2);
        double ratio = (lighter + 0.05) / (darker + 0.05);
        return ratio < 3.0;
    }

    public static String includesFor(ProjectTemplate t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("• MainActivity + main.xml\n");
        if (t.hasToolbar()) sb.append("• Toolbar / AppBar chrome in editor\n");
        if (t.hasDrawer()) sb.append("• Navigation drawer + header layout\n");
        if (t.hasFab()) sb.append("• Floating Action Button host\n");
        if (!t.hasStatusBar()) sb.append("• Fullscreen (no status bar)\n");
        if (t.getType() == ProjectTemplate.TYPE_LOGIN) {
            sb.append("• Login form (email + password)\n");
        }
        if (t.getType() == ProjectTemplate.TYPE_TABS) {
            sb.append("• TabLayout + ViewPager scaffold\n");
        }
        if (t.getType() == ProjectTemplate.TYPE_BOTTOM_NAV
                || (t.hasDrawer() && t.hasFab())) {
            sb.append("• Bottom nav / advanced chrome layout\n");
        }
        sb.append("• colors.xml · styles.xml · strings.xml");
        return sb.toString();
    }

    public static void saveLastTheme(Context c, String name, int primary, int dark, int accent, int hi, int normal) {
        SharedPreferences sp = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sp.edit()
                .putString("theme_name", name)
                .putInt("c_primary", primary)
                .putInt("c_dark", dark)
                .putInt("c_accent", accent)
                .putInt("c_hi", hi)
                .putInt("c_normal", normal)
                .apply();
    }

    public static int[] loadLastTheme(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (!sp.contains("c_primary")) return null;
        return new int[]{
                sp.getInt("c_primary", 0),
                sp.getInt("c_dark", 0),
                sp.getInt("c_accent", 0),
                sp.getInt("c_hi", 0),
                sp.getInt("c_normal", 0)
        };
    }

    public static void saveLastTemplateType(Context c, int type) {
        c.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putInt("last_template", type).apply();
    }

    public static int loadLastTemplateType(Context c, int fallback) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getInt("last_template", fallback);
    }

    public static void saveSdkPrefs(Context c, int minSdk, int targetSdk, boolean androidx) {
        c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
                .putInt("min_sdk", minSdk)
                .putInt("target_sdk", targetSdk)
                .putBoolean("androidx", androidx)
                .apply();
    }

    public static int loadMinSdk(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt("min_sdk", 26);
    }

    public static int loadTargetSdk(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt("target_sdk", 36);
    }

    public static boolean loadAndroidX(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean("androidx", true);
    }

    public static List<Integer> minSdkOptions() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(21);
        list.add(24);
        list.add(26);
        list.add(28);
        list.add(31);
        list.add(34);
        return list;
    }

    public static List<Integer> targetSdkOptions() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(33);
        list.add(34);
        list.add(35);
        list.add(36);
        return list;
    }

    public static int avatarColor(String name) {
        if (name == null || name.length() == 0) return Color.parseColor("#3F51B5");
        int h = name.hashCode();
        float hue = (h % 360 + 360) % 360;
        return Color.HSVToColor(new float[]{hue, 0.55f, 0.75f});
    }

    public static String firstLetter(String name) {
        if (name == null || name.trim().length() == 0) return "A";
        return name.trim().substring(0, 1).toUpperCase(Locale.US);
    }
}
