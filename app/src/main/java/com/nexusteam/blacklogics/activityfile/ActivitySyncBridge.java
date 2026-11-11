package com.nexusteam.blacklogics.activityfile;

import android.content.Context;
import android.util.Log;

import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.generator.source.model.ActivityConfig;
import com.nexusteam.blacklogics.generator.source.model.ActivityNaming;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import com.nexusteam.blacklogics.manager.ActivityManager;
import com.nexusteam.blacklogics.model.DataModel;
//import com.nexusteam.blacklogics.sdk.util.ActivityConfig;   // NOTE: legacy type — kept as before
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.ly;
import com.nexusteam.internal.ma;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class ActivitySyncBridge {

    private static final String TAG = "ActivitySyncBridge";

    private ActivitySyncBridge() {}

    // =====================================================================
    // Public API
    // =====================================================================

    public static void syncNewOrUpdatedActivity(Context context, String scId,
                                                Complex complex, ProjectFileBean bean) {
        if (!validArgs(context, scId, bean)) return;
        final String sc = scId.trim();

        if (bean.fileType != ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
            saveLy(sc, bean);
            syncCustomView(complex, bean);
            return;
        }

        String layout = resolveLayout(bean);
        String javaName = resolveJavaBase(bean, layout);
        String fullActName = ActivityNaming.toActivityClassName(javaName);

        boolean toolbar = bean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
        boolean fab     = bean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB);
        boolean drawer  = bean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER);
        boolean status  = !bean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN);
        String orientation = orientationString(bean.orientation);

        Log.i(TAG, "START sync " + fullActName + " layout=" + layout);

        saveLy(sc, bean);

        // ---- Determine existing ActivityType FIRST (never overwrite with ACTIVITY) ----
        ActivityType existingType = readExistingType(context, sc, fullActName, javaName, layout);

        // ---- Legacy ActivityConfig (sdk.util) — kept, unrelated to type logic ----
        writeSdkActivityConfig(context, sc, fullActName, status, toolbar, drawer, fab,
                layout, orientation);

        // ---- ActivityTypeRegistry — idempotent update, preserves type ----
        writeTypeRegistry(context, sc, fullActName, existingType, layout);

        // ---- StructureRegistry ----
        writeStructureRegistry(context, sc, fullActName, layout);

        // ---- ActivityManager ----
        writeActivityManager(context, sc, fullActName, layout);

        // ---- Manifest ----
        writeManifest(context, sc, fullActName, toolbar, status);

        // ---- Complex (legacy) ----
        String complexType = typeToComplexString(existingType);
        writeComplex(complex, fullActName, javaName, layout,
                fab, toolbar, drawer, complexType, orientation, status);

        Log.i(TAG, "DONE " + fullActName
                + " type=" + existingType
                + " complexType=" + complexType
                + " fab=" + fab + " drawer=" + drawer);
    }

    public static void removeActivity(Context context, String scId, Complex complex,
                                      ProjectFileBean bean) {
        if (!validArgs(context, scId, bean)) return;
        final String sc = scId.trim();

        String layout = resolveLayout(bean);
        if (layout.isEmpty()) return;

        String javaName = resolveJavaBase(bean, layout);
        String fullActName = ActivityNaming.toActivityClassName(javaName);

        Log.i(TAG, "REMOVE " + fullActName + " / " + layout);

        removeFromLy(sc, bean, layout);

        if (bean.fileType != ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
            if (complex != null) {
                try { complex.deleteCustomViewFile(layout); } catch (Throwable ignore) {}
            }
            Log.i(TAG, "REMOVE custom view done " + layout);
            return;
        }

        removeFromTypeRegistry(context, sc, fullActName, javaName);
        removeFromSdkActivityConfig(context, sc, fullActName, javaName);
        removeFromStructureRegistry(context, sc, fullActName, layout);
        removeFromActivityManager(context, sc, fullActName, layout);
        removeFromManifest(context, sc, fullActName);
        removeFromComplex(complex, layout, fullActName, javaName);

        Log.i(TAG, "REMOVE done " + fullActName);
    }

    public static void syncProjectDefaults(Context context, String scId, Complex complex) {
        if (context == null || scId == null || scId.trim().isEmpty()) return;
        try {
            ProjectFileBean main = new ProjectFileBean(0, "main");
            try { main.options = ProjectFileBean.OPTION_ACTIVITY_TOOLBAR; } catch (Throwable ignore) {}
            syncNewOrUpdatedActivity(context, scId, complex, main);
            if (complex != null) {
                try {
                    complex.setAcName("MainActivity");
                    complex.setXName("main");
                    complex.enableToolBar("MainActivity", false, true);
                } catch (Throwable ignore) {}
            }
        } catch (Throwable t) {
            Log.e(TAG, "syncProjectDefaults", t);
        }
    }

    // =====================================================================
    // Guard / naming helpers
    // =====================================================================

    private static boolean validArgs(Context context, String scId, ProjectFileBean bean) {
        if (context == null || bean == null || scId == null || scId.trim().isEmpty()) {
            Log.e(TAG, "invalid args scId='" + scId + "' bean=" + bean);
            return false;
        }
        return true;
    }

    private static String resolveLayout(ProjectFileBean bean) {
        String name = bean.fileName != null ? bean.fileName : bean.getXmlName();
        return ActivityNaming.stripXml(name);
    }

    private static String resolveJavaBase(ProjectFileBean bean, String layout) {
        String javaName = bean.getJavaName();
        if (javaName == null || javaName.trim().isEmpty()) {
            javaName = ActivityNaming.toPascalCase(layout);
        }
        return ActivityNaming.stripJava(javaName);
    }

    // =====================================================================
    // ActivityType read/write (source of truth for type)
    // =====================================================================

    private static ActivityType readExistingType(Context context, String sc,
                                                 String fullActName,
                                                 String javaName, String layout) {
        try {
            ActivityTypeRegistry reg = ActivityTypeRegistry.getInstance();
            reg.load(context, sc);

            ActivityConfig cfg = reg.getActivityConfig(fullActName);
            if (cfg == null) cfg = reg.getActivityConfig(javaName);
            if (cfg == null) cfg = reg.getActivityConfig(layout);
            if (cfg != null && cfg.getType() != null) {
                Log.i(TAG, "readExistingType hit name=" + fullActName + " type=" + cfg.getType());
                return cfg.getType();
            }
        } catch (Throwable t) {
            Log.e(TAG, "readExistingType failed", t);
        }
        return ActivityType.ACTIVITY;
    }

    private static void writeTypeRegistry(Context context, String sc,
                                          String fullActName, ActivityType type,
                                          String layout) {
        try {
            ActivityTypeRegistry reg = ActivityTypeRegistry.getInstance();
            reg.load(context, sc);

            // registerActivity is idempotent — merges into existing config.
            reg.registerActivity(fullActName, type, layout);
            reg.save(context, sc);

            Log.i(TAG, "TypeRegistry OK type=" + type + " name=" + fullActName);
        } catch (Throwable t) {
            Log.e(TAG, "TypeRegistry FAIL", t);
        }
    }

    private static void removeFromTypeRegistry(Context context, String sc,
                                               String fullActName, String javaName) {
        try {
            ActivityTypeRegistry reg = ActivityTypeRegistry.getInstance();
            reg.load(context, sc);
            reg.removeActivity(fullActName);
            reg.removeActivity(javaName);
            reg.save(context, sc);
            Log.i(TAG, "remove TypeRegistry OK");
        } catch (Throwable t) {
            Log.e(TAG, "remove TypeRegistry", t);
        }
    }

    /** Map ActivityType → string expected by Complex/generators. */
    private static String typeToComplexString(ActivityType t) {
        if (t == null) return "Activity";
        switch (t) {
            case FRAGMENT_ACTIVITY:            return "Fragment";
            case DIALOG_FRAGMENT:              return "DialogFragment";
            case APP_COMPAT_DIALOG_FRAGMENT:   return "AppCompatDialogFragment";
            case BOTTOM_SHEET_DIALOG_FRAGMENT: return "BottomSheetDialogFragment";
            case LIST_FRAGMENT:                return "ListFragment";
            case PREFERENCE_FRAGMENT:          return "PreferenceFragment";
            case APP_COMPAT_ACTIVITY:          return "AppCompatActivity";
            case ACTIVITY:                     return "Activity";
            default:                           return "Activity";
        }
    }

    // =====================================================================
    // Legacy SDK ActivityConfig (unchanged behaviour)
    // =====================================================================

    private static void writeSdkActivityConfig(Context context, String sc, String fullActName,
                                               boolean status, boolean toolbar,
                                               boolean drawer, boolean fab,
                                               String layout, String orientation) {
        try {
            com.nexusteam.blacklogics.sdk.util.ActivityConfig activityConfig =
                    new com.nexusteam.blacklogics.sdk.util.ActivityConfig();
            activityConfig.init(context, sc);
            activityConfig.setActivityConfig(
                    fullActName, status, toolbar, drawer, fab, "Activity", orientation, layout);
            try { activityConfig.updateActivityTheme(fullActName); } catch (Throwable ignore) {}
            activityConfig.save(context, sc);
            Log.i(TAG, "SDK ActivityConfig OK");
        } catch (Throwable t) {
            Log.e(TAG, "SDK ActivityConfig FAIL", t);
        }
    }

    private static void removeFromSdkActivityConfig(Context context, String sc,
                                                    String fullActName, String javaName) {
        try {
            com.nexusteam.blacklogics.sdk.util.ActivityConfig activityConfig =
                    new com.nexusteam.blacklogics.sdk.util.ActivityConfig();
            activityConfig.init(context, sc);
            activityConfig.removeActivity(fullActName);
            try { activityConfig.removeActivity(javaName); } catch (Throwable ignore) {}
            activityConfig.save(context, sc);
            Log.i(TAG, "remove SDK ActivityConfig OK");
        } catch (Throwable t) {
            Log.e(TAG, "remove SDK ActivityConfig", t);
        }
    }

    // =====================================================================
    // StructureRegistry
    // =====================================================================

    private static void writeStructureRegistry(Context context, String sc,
                                               String fullActName, String layout) {
        try {
            ActivityStructureRegistry registry = new ActivityStructureRegistry();
            registry.load(context, sc);
            registry.addActivity(fullActName, layout);
            registry.save(context, sc);
            Log.i(TAG, "StructureRegistry OK");
        } catch (Throwable t) {
            Log.e(TAG, "StructureRegistry FAIL", t);
        }
    }

    private static void removeFromStructureRegistry(Context context, String sc,
                                                    String fullActName, String layout) {
        try {
            ActivityStructureRegistry registry = new ActivityStructureRegistry();
            registry.load(context, sc);
            registry.removeActivity(fullActName);
            registry.removeActivity(layout);
            registry.save(context, sc);
            Log.i(TAG, "remove StructureRegistry OK");
        } catch (Throwable t) {
            Log.e(TAG, "remove StructureRegistry", t);
        }
    }

    // =====================================================================
    // ActivityManager
    // =====================================================================

    private static void writeActivityManager(Context context, String sc,
                                             String fullActName, String layout) {
        try {
            ActivityManager activityManager = new ActivityManager();
            activityManager.load(context, sc);
            if (!managerHas(activityManager, fullActName)) {
                activityManager.addActivity(fullActName, layout);
            }
            activityManager.save(context, sc);
            Log.i(TAG, "ActivityManager OK");
        } catch (Throwable t) {
            Log.e(TAG, "ActivityManager FAIL", t);
        }
    }

    private static void removeFromActivityManager(Context context, String sc,
                                                  String fullActName, String layout) {
        try {
            ActivityManager activityManager = new ActivityManager();
            activityManager.load(context, sc);
            activityManager.removeActivity(fullActName);
            activityManager.removeActivity(layout);
            activityManager.save(context, sc);
            Log.i(TAG, "remove ActivityManager OK");
        } catch (Throwable t) {
            Log.e(TAG, "remove ActivityManager", t);
        }
    }

    // =====================================================================
    // Manifest
    // =====================================================================

    private static void writeManifest(Context context, String sc,
                                      String fullActName, boolean toolbar, boolean status) {
        try {
            AndroidManifestGenerator manifest = new AndroidManifestGenerator();
            manifest.load(context, sc);

            Map attrs = createActivityAttributes(toolbar, status);
            String dotted = "." + fullActName;

            try {
                manifest.addActivity(dotted, attrs);
            } catch (Throwable t1) {
                try {
                    manifest.addActivity(fullActName, attrs);
                } catch (Throwable t2) {
                    Log.e(TAG, "addActivity both forms failed", t2);
                }
            }

            try { manifest.setAttribute("supportsRtl", "true"); } catch (Throwable ignore) {}
            manifest.save(context, sc);
            Log.i(TAG, "Manifest.save OK for " + dotted);

            try {
                String projectRoot = resolveProjectRoot(sc);
                if (projectRoot != null) {
                    String mainPath = projectRoot + File.separator + "app" + File.separator
                            + "src" + File.separator + "main";
                    manifest.extractManifest(sc, mainPath);
                    Log.i(TAG, "extractManifest OK → " + mainPath);
                } else {
                    try {
                        java.lang.reflect.Method m = manifest.getClass()
                                .getMethod("extractManifest", String.class);
                        m.invoke(manifest, sc);
                        Log.i(TAG, "extractManifest(scId) OK");
                    } catch (Throwable t) {
                        Log.w(TAG, "extractManifest path unresolved scId=" + sc, t);
                    }
                }
            } catch (Throwable t) {
                Log.e(TAG, "extractManifest FAIL — JSON state saved but XML may be stale", t);
            }
        } catch (Throwable t) {
            Log.e(TAG, "writeManifest FAIL", t);
        }
    }

    private static void removeFromManifest(Context context, String sc, String fullActName) {
        try {
            AndroidManifestGenerator manifest = new AndroidManifestGenerator();
            manifest.load(context, sc);
            manifest.removeActivity("." + fullActName);
            manifest.removeActivity(fullActName);
            manifest.save(context, sc);

            String projectRoot = resolveProjectRoot(sc);
            if (projectRoot != null) {
                String mainPath = projectRoot + File.separator + "app" + File.separator
                        + "src" + File.separator + "main";
                manifest.extractManifest(sc, mainPath);
                Log.i(TAG, "remove extractManifest OK");
            }
            Log.i(TAG, "remove Manifest OK");
        } catch (Throwable t) {
            Log.e(TAG, "remove Manifest", t);
        }
    }

    private static String resolveProjectRoot(String sc) {
        try {
            File f = new File(TheBlockLogicsUtil.mysc + sc);
            if (f.exists()) return f.getAbsolutePath();
        } catch (Throwable ignore) {}
        try {
            File f = new File("/storage/emulated/0/.blacklogics/mysc/" + sc);
            if (f.exists()) return f.getAbsolutePath();
        } catch (Throwable ignore) {}
        try {
            File f = new File("/storage/emulated/0/.sketchware/mysc/" + sc);
            if (f.exists()) return f.getAbsolutePath();
        } catch (Throwable ignore) {}
        return null;
    }

    // =====================================================================
    // Complex (legacy generator bridge)
    // =====================================================================

    private static void writeComplex(Complex complex,
                                     String fullActName, String javaName, String layout,
                                     boolean fab, boolean toolbar, boolean drawer,
                                     String type, String orientation, boolean status) {
        if (complex == null) {
            Log.w(TAG, "Complex is null — generators may miss complex-backed lists");
            return;
        }
        try {
            if (complexAlreadyHas(complex, fullActName, layout)) {
                complex.updateActivity(
                        fullActName, fullActName, layout,
                        fab, toolbar, complex.getAndroidXEnable(), drawer,
                        type, orientation, status);
            } else {
                try { complex.addActivityToManifest(javaName); } catch (Throwable ignore) {}
                try { complex.enableFab(fullActName, fab); } catch (Throwable ignore) {}
                try { complex.enableToolBar(fullActName, complex.getAndroidXEnable(), toolbar); }
                catch (Throwable ignore) {}
                try { complex.addActivity(fullActName, layout, fab, false, drawer); }
                catch (Throwable ignore) {}
                try { complex.setAcName(fullActName); } catch (Throwable ignore) {}
                try { complex.setXName(layout); } catch (Throwable ignore) {}
                try {
                    complex.updateActivity(
                            fullActName, fullActName, layout,
                            fab, toolbar, complex.getAndroidXEnable(), drawer,
                            type, orientation, status);
                } catch (Throwable ignore) {}
            }
            Log.i(TAG, "Complex OK type=" + type + " fab=" + fab + " drawer=" + drawer);
        } catch (Throwable t) {
            Log.e(TAG, "Complex FAIL", t);
        }
    }

    private static void removeFromComplex(Complex complex, String layout,
                                          String fullActName, String javaName) {
        if (complex == null) return;
        try {
            complex.deleteViewFiles(layout, fullActName);
        } catch (Throwable t) {
            try {
                complex.deleteViewFiles(layout, javaName);
            } catch (Throwable t2) {
                Log.e(TAG, "Complex deleteViewFiles", t2);
            }
        }
    }

    // =====================================================================
    // Custom view
    // =====================================================================

    private static void syncCustomView(Complex complex, ProjectFileBean bean) {
        String name = ActivityNaming.stripXml(
                bean.fileName != null ? bean.fileName : bean.getXmlName());
        if (name.isEmpty() || complex == null) return;
        try {
            complex.registerCustomViewInRootLayout(name, "");
        } catch (Throwable t) {
            Log.w(TAG, "registerCustomView", t);
        }
    }

    // =====================================================================
    // ly persistence
    // =====================================================================

    private static void saveLy(String sc, ProjectFileBean bean) {
        try {
            ly ly = ma.b(sc);
            if (ly == null) return;
            ArrayList list = bean.fileType == 0 ? ly.a() : ly.b();
            if (list == null) {
                list = new ArrayList();
                if (bean.fileType == 0) ly.a(list); else ly.b(list);
            }
            String target = normName(bean.fileName != null ? bean.fileName : bean.getXmlName());
            boolean found = false;
            HashSet seen = new HashSet();
            ArrayList cleaned = new ArrayList();
            for (Object o : list) {
                if (!(o instanceof ProjectFileBean)) continue;
                ProjectFileBean p = (ProjectFileBean) o;
                String n = normName(p.fileName != null ? p.fileName : p.getXmlName());
                if (n.isEmpty() || seen.contains(n)) continue;
                seen.add(n);
                if (n.equals(target) && p.fileType == bean.fileType) {
                    found = true;
                    p.options = bean.options;
                    p.orientation = bean.orientation;
                    p.keyboardSetting = bean.keyboardSetting;
                    if (bean.presetName != null) p.presetName = bean.presetName;
                    if (bean.fileName != null) p.fileName = ActivityNaming.stripXml(bean.fileName);
                }
                cleaned.add(p);
            }
            if (!found && target.length() > 0) {
                if (bean.fileName != null && bean.fileName.endsWith(".xml")) {
                    bean.fileName = ActivityNaming.stripXml(bean.fileName);
                }
                cleaned.add(bean);
            }
            if (bean.fileType == 0) ly.a(cleaned); else ly.b(cleaned);
            ly.e();
            try { ly.g(); } catch (Throwable ignore) {}
        } catch (Throwable t) {
            Log.e(TAG, "saveLy", t);
        }
    }

    private static void removeFromLy(String sc, ProjectFileBean bean, String layout) {
        try {
            ly ly = ma.b(sc);
            if (ly == null) return;
            ArrayList list = bean.fileType == 0 ? ly.a() : ly.b();
            if (list == null) return;
            for (int i = list.size() - 1; i >= 0; i--) {
                Object o = list.get(i);
                if (!(o instanceof ProjectFileBean)) continue;
                ProjectFileBean p = (ProjectFileBean) o;
                String n = normName(p.fileName != null ? p.fileName : p.getXmlName());
                if (n.equals(normName(layout))) list.remove(i);
            }
            if (bean.fileType == 0) ly.a(list); else ly.b(list);
            ly.e();
            try { ly.g(); } catch (Throwable ignore) {}
        } catch (Throwable t) {
            Log.e(TAG, "remove ly", t);
        }
    }

    // =====================================================================
    // Misc helpers
    // =====================================================================

    private static boolean complexAlreadyHas(Complex complex, String actName, String layout) {
        try {
            String joined = complex.getAllJavaActivity();
            if (joined != null) {
                String needle = actName.toLowerCase();
                for (String part : joined.split(",")) {
                    if (part == null) continue;
                    String p = part.trim().toLowerCase().replace(".java", "");
                    if (p.equals(needle)) return true;
                }
            }
        } catch (Throwable ignore) {}
        try {
            List all = complex.getAllJavaAndXmlNames();
            if (all != null) {
                String a = actName + ".java";
                String xxml = layout.endsWith(".xml") ? layout : layout + ".xml";
                for (Object o : all) {
                    String s = String.valueOf(o);
                    if (a.equalsIgnoreCase(s) || layout.equalsIgnoreCase(s)
                            || xxml.equalsIgnoreCase(s)) return true;
                }
            }
        } catch (Throwable ignore) {}
        return false;
    }

    private static boolean managerHas(ActivityManager am, String name) {
        try {
            List names = am.getActivityNames();
            if (names == null) return false;
            for (Object n : names) {
                if (name.equals(String.valueOf(n))) return true;
            }
        } catch (Throwable ignore) {}
        return false;
    }

    private static Map createActivityAttributes(boolean toolbar, boolean status) {
        Map attrs = new HashMap();
        attrs.put("exported", "true");
        attrs.put("launchMode", "singleTop");
        attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
        attrs.put("windowSoftInputMode", "adjustResize");
        attrs.put("theme", "@style/" + resolveTheme(toolbar, status));
        return attrs;
    }

    private static String resolveTheme(boolean toolbar, boolean status) {
        if (toolbar && status) return "AppTheme";
        if (toolbar) return "NoStatusBar";
        if (status) return "AppTheme.NoActionBar";
        return "FullScreen";
    }

    private static String orientationString(int orientation) {
        if (orientation == 0) return "Portrait";
        if (orientation == 1) return "Landscape";
        return "Both";
    }

    public static String stripXml(String name) {
        return ActivityNaming.stripXml(name);
    }

    private static String normName(String name) {
        if (name == null) return "";
        name = name.trim().toLowerCase();
        if (name.endsWith(".xml")) name = name.substring(0, name.length() - 4);
        if (name.endsWith(".java")) name = name.substring(0, name.length() - 5);
        return name;
    }

    public static String toJavaName(String layout) {
        return ActivityNaming.toPascalCase(layout);
    }
}