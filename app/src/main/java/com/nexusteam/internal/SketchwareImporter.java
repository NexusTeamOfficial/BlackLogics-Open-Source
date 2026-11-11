package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nexusteam.internal.ProjectFileParser;
import com.nexusteam.internal.ProjectModel;
import com.nexusteam.internal.SketchwareViewBridge;
import com.nexusteam.internal.BlockStorage;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;
import com.nexusteam.blacklogics.utils.ActivityBridge;
import com.nexusteam.blacklogics.utils.LogicBridge;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.nexusteam.blacklogics.manager.ActivityManager;

public class SketchwareImporter {
    
    private static final String TAG     = "SketchwareImporter";
    private static final String BL_BASE = "/storage/emulated/0/.blacklogics/";
    private static final String CHARSET = "UTF-8";
    private static final int    BUFFER  = 4096;
    
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    
    public interface Callback {
        void onSuccess(String newScId, ProjectModel model);
        void onError(String reason);
    }
    
    
    public static void importSwb(
    final Context context,
    final File swbFile,
    final String newScId,
    final Callback callback) {
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    final ProjectModel model = importSwbSync(context, swbFile, newScId);
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onSuccess(newScId, model);
                        }
                    });
                } catch (final Exception e) {
                    Log.e(TAG, "Import failed", e);
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onError(e.getMessage() != null
                            ? e.getMessage() : "Unknown error");
                        }
                    });
                }
            }
        }).start();
    }
    
    
    public static ProjectModel importSwbSync(Context context, File swbFile, String newScId) throws Exception {
        if (swbFile == null || !swbFile.exists()) {
            throw new Exception("SWB file not found: " + swbFile);
        }
        
        List<String> importErrors = new ArrayList<String>();
        List<String> activityNames = new ArrayList<String>();
        
        File tempDir = new File(BL_BASE + "temp_import_" + newScId);
        deleteDir(tempDir);
        tempDir.mkdirs();
        
        unzip(swbFile, tempDir);
        Log.d(TAG, "✅ Unzipped → " + tempDir.getAbsolutePath());
        
        Log.d(TAG, "=== TEMP DIRECTORY STRUCTURE ===");
        listFilesRecursively(tempDir, 0);
        
        File projectFile = new File(tempDir, "project");
        if (!projectFile.exists()) {
            projectFile = new File(tempDir, "data/project");
            if (!projectFile.exists()) {
                throw new Exception("'project' file not found in .swb (tried root and data/)");
            }
        }
        
        ProjectModel model = ProjectFileParser.parse(projectFile.getAbsolutePath());
        if (model == null) {
            throw new Exception("Failed to parse Sketchware project file");
        }
        model.setScId(newScId);
        Log.d(TAG, "✅ Parsed project: " + model.getAppName() + " / " + model.getPackageName());
        
        File blDataDir = new File(BL_BASE + "data/" + newScId);
        if (!blDataDir.exists()) {
            blDataDir.mkdirs();
        }
        
        File blMyscDir = new File(BL_BASE + "mysc/list/" + newScId);
        if (!blMyscDir.exists()) {
            blMyscDir.mkdirs();
        }
        
        File swbData = new File(tempDir, "data");
        if (swbData.exists() && swbData.isDirectory()) {
            copyDir(swbData, blDataDir);
            Log.d(TAG, "✅ data/ copied → " + blDataDir.getAbsolutePath());
            
            File[] dataFiles = swbData.listFiles();
            if (dataFiles != null) {
                for (File f : dataFiles) {
                    Log.d(TAG, "  - data/" + f.getName());
                }
            }
        } else {
            Log.w(TAG, "⚠️ No data/ folder found in .swb");
        }
        
        File swbRes = new File(tempDir, "resources");
        String[] subs = new String[]{"icons", "fonts", "sounds"};
        for (int i = 0; i < subs.length; i++) {
            String sub = subs[i];
            File src = new File(swbRes, sub);
            File dest = new File(BL_BASE + "resources/" + sub + "/" + newScId);
            if (src.exists() && src.isDirectory()) {
                dest.mkdirs();
                copyDir(src, dest);
                Log.d(TAG, "✅ resources/" + sub + " copied → " + dest.getAbsolutePath());
            } else {
                Log.d(TAG, "  resources/" + sub + " not found");
            }
        }
        
        writeConfigEnc(model, newScId);
        Log.d(TAG, "✅ config.enc written for sc_id=" + newScId);
        
        BlockStorage.setScId(newScId);
        
        File logicFile = new File(tempDir, "data/logic");
        Log.d(TAG, "🔍 Looking for logic file at: " + logicFile.getAbsolutePath());
        
        if (logicFile.exists()) {
            try {
                int blockCount = LogicBridge.importLogicSync(
                    context,
                    logicFile.getAbsolutePath(),
                    newScId
                );
                Log.d(TAG, "✅ Logic imported: " + blockCount + " blocks");
            } catch (Exception e) {
                String err = "Logic import failed: " + e.getMessage();
                Log.e(TAG, err, e);
                importErrors.add(err);
            }
        } else {
            Log.w(TAG, "⚠️ logic file not found in data/ (normal for empty projects)");
        }
        
        File swbFileFile = new File(tempDir, "data/file");
        Log.d(TAG, "🔍 Looking for 'file' (activities) at: " + swbFileFile.getAbsolutePath());
        
        boolean activitiesImported = false;
        
        if (swbFileFile.exists()) {
            try {
                List<ActivityManager.ViewItem> activities = ActivityBridge.importActivitiesSync(
                    context,
                    swbFileFile.getAbsolutePath(),
                    newScId
                );
                Log.d(TAG, "✅ Activities imported: " + activities.size());
                activitiesImported = true;
                
                for (ActivityManager.ViewItem item : activities) {
                    String actName = item.javaName;
                    if (actName != null && !actName.trim().isEmpty()) {
                        activityNames.add(actName);
                        Log.d(TAG, "📱 Activity found: " + actName);
                    }
                }
                
                ActivityDebugger.debugActivities(context, newScId);
                
            } catch (Exception e) {
                String err = "Activities import failed: " + e.getMessage();
                Log.e(TAG, err, e);
                importErrors.add(err);
                
                try {
                    Log.d(TAG, "🔄 Attempting fallback: create default activity");
                    String defaultAct = createDefaultActivity(context, newScId);
                    if (defaultAct != null) {
                        activityNames.add(defaultAct);
                        activitiesImported = true;
                    }
                } catch (Exception fallbackErr) {
                    Log.e(TAG, "Fallback also failed: " + fallbackErr.getMessage());
                }
            }
        } else {
            Log.w(TAG, "⚠️ 'file' (activities) not found in data/");
            
            try {
                Log.d(TAG, "🔄 Creating default MainActivity");
                String defaultAct = createDefaultActivity(context, newScId);
                if (defaultAct != null) {
                    activityNames.add(defaultAct);
                    activitiesImported = true;
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to create default activity: " + e.getMessage());
                importErrors.add("Could not create default activity");
            }
        }
        
        File viewFile = new File(tempDir, "data/view");
        Log.d(TAG, "🔍 Looking for view file at: " + viewFile.getAbsolutePath());
        
        if (viewFile.exists()) {
            try {
                String outputDir = blDataDir.getAbsolutePath();
                SketchwareViewBridge.convertSync(viewFile.getAbsolutePath(), outputDir);
                model.setLayoutPath(outputDir);
                Log.d(TAG, "✅ Views imported to: " + outputDir);
                
                String viewJson = new String(readFile(viewFile.getAbsolutePath()), CHARSET);
                List<String> layoutNames = parseLayoutNames(viewJson);
                for (String layoutName : layoutNames) {
                    String activityName = layoutNameToActivityName(layoutName);
                    if (!activityNames.contains(activityName)) {
                        activityNames.add(activityName);
                        Log.d(TAG, "📱 Activity from layout: " + activityName);
                    }
                }
                
            } catch (Exception e) {
                String err = "Views import failed: " + e.getMessage();
                Log.e(TAG, err, e);
                importErrors.add(err);
            }
        } else {
            Log.w(TAG, "⚠️ view file not found in data/ (normal for empty projects)");
        }
        
        File libraryFile = new File(tempDir, "data/library");
        if (libraryFile.exists()) {
            try {
                File libDest = new File(blDataDir, "library");
                libDest.mkdirs();
                copyDir(libraryFile, libDest);
                Log.d(TAG, "✅ Library files copied");
            } catch (Exception e) {
                String err = "Library copy failed: " + e.getMessage();
                Log.e(TAG, err, e);
                importErrors.add(err);
            }
        }
        
        File resourceFile = new File(tempDir, "data/resource");
        if (resourceFile.exists()) {
            try {
                File resDest = new File(blDataDir, "resource");
                resDest.mkdirs();
                copyDir(resourceFile, resDest);
                Log.d(TAG, "✅ Resource files copied");
            } catch (Exception e) {
                String err = "Resource copy failed: " + e.getMessage();
                Log.e(TAG, err, e);
                importErrors.add(err);
            }
        }
        
        try {
            registerActivitiesInManifest(context, newScId, model, activityNames);
            Log.d(TAG, "✅ Activities registered in manifest: " + activityNames.size());
        } catch (Exception e) {
            String err = "Manifest registration failed: " + e.getMessage();
            Log.e(TAG, err, e);
            importErrors.add(err);
        }
        
        Log.d(TAG, "=== IMPORT SUMMARY ===");
        Log.d(TAG, "✅ Project: " + model.getAppName());
        Log.d(TAG, "✅ Package: " + model.getPackageName());
        Log.d(TAG, "✅ Activities imported: " + activitiesImported);
        Log.d(TAG, "✅ Activities registered: " + activityNames.size());
        Log.d(TAG, "✅ Errors: " + importErrors.size());
        
        if (!importErrors.isEmpty()) {
            Log.w(TAG, "⚠️ Import completed with " + importErrors.size() + " errors");
            for (String err : importErrors) {
                Log.w(TAG, "  - " + err);
            }
        }
        
        deleteDir(tempDir);
        
        Log.d(TAG, "✅✅✅ Import complete! sc_id=" + newScId);
        return model;
    }
    
    
    private static List<String> parseLayoutNames(String viewJson) {
        List<String> names = new ArrayList<String>();
        if (viewJson == null || viewJson.trim().isEmpty()) {
            return names;
        }
        
        try {
            String[] lines = viewJson.split("\n");
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.startsWith("@")) {
                    String name = trimmed.substring(1).trim();
                    if (name.endsWith(".xml")) {
                        name = name.substring(0, name.length() - 4);
                    }
                    int underscoreIdx = name.indexOf('_');
                    if (underscoreIdx > 0) {
                        name = name.substring(0, underscoreIdx);
                    }
                    if (!name.isEmpty() && !names.contains(name)) {
                        names.add(name);
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to parse layout names: " + e.getMessage());
        }
        
        return names;
    }
    
    
    private static String layoutNameToActivityName(String layoutName) {
        if (layoutName == null || layoutName.trim().isEmpty()) {
            return "MainActivity";
        }
        
        String base = layoutName;
        if (base.endsWith(".xml")) {
            base = base.substring(0, base.length() - 4);
        }
        
        int underscoreIdx = base.indexOf('_');
        if (underscoreIdx > 0) {
            base = base.substring(0, underscoreIdx);
        }
        
        String[] parts = base.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    sb.append(part.substring(1));
                }
            }
        }
        
        String result = sb.toString();
        if (result.isEmpty()) {
            result = "Main";
        }
        
        if (!result.endsWith("Activity")) {
            result = result + "Activity";
        }
        
        return result;
    }
    
    
    private static void registerActivitiesInManifest(
        Context context, 
        String scId, 
        ProjectModel model,
        List<String> activityNames) throws Exception {
        
        if (activityNames == null || activityNames.isEmpty()) {
            Log.w(TAG, "⚠️ No activities to register in manifest");
            return;
        }
        
        AndroidManifestGenerator generator = new AndroidManifestGenerator();
        
        generator.load(context, scId);
        
        generator.setPackageName(model.getPackageName());
        generator.setAppName(model.getAppName());
        
        String iconPath = model.getIconPath();
        if (iconPath != null && !iconPath.isEmpty() && !"default_icon.png".equals(iconPath)) {
            generator.setAppIcon(iconPath);
        } else {
            generator.setAppIcon("@drawable/ic_launcher");
        }
        
        String primaryColor = model.getColorPrimary();
        if (primaryColor != null && !primaryColor.isEmpty()) {
            generator.setThemeColor(primaryColor);
        } else {
            generator.setThemeColor("#3F51B5");
        }
        
        generator.setAndroidX(true);
        
        String firstActivity = null;
        for (String actName : activityNames) {
            if (actName == null || actName.trim().isEmpty()) {
                continue;
            }
            
            String cleanName = actName.trim();
            if (!cleanName.startsWith(".")) {
                cleanName = "." + cleanName;
            }
            
            java.util.Map<String, String> attrs = new java.util.HashMap<String, String>();
            attrs.put("exported", "true");
            attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
            attrs.put("windowSoftInputMode", "adjustResize");
            
            generator.addActivity(cleanName, attrs);
            Log.d(TAG, "✅ Registered activity: " + cleanName);
            
            if (firstActivity == null) {
                firstActivity = cleanName;
            }
        }
        
        if (firstActivity != null) {
            String existingLauncher = generator.getLauncherActivity();
            if (existingLauncher == null || existingLauncher.trim().isEmpty()) {
                String launcherName = firstActivity;
                if (launcherName.startsWith(".")) {
                    launcherName = launcherName.substring(1);
                }
                generator.setLauncherActivity(launcherName);
                Log.d(TAG, "✅ Launcher activity set: " + launcherName);
            }
        }
        
        generator.addPermission("android.permission.INTERNET");
        generator.addPermission("android.permission.ACCESS_NETWORK_STATE");
        generator.addPermission("android.permission.ACCESS_WIFI_STATE");
        generator.addPermission("android.permission.READ_EXTERNAL_STORAGE");
        generator.addPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        
        generator.setAttribute("allowBackup", "true");
        generator.setAttribute("usesCleartextTraffic", "true");
        generator.setAttribute("requestLegacyExternalStorage", "true");
        
        generator.save(context, scId);
        
        Log.d(TAG, "✅ Manifest saved with " + activityNames.size() + " activities");
        
        try {
            String xml = generator.generate();
            File manifestFile = new File("/storage/emulated/0/.blacklogics/data/" + scId + "/AndroidManifest.xml");
            if (!manifestFile.getParentFile().exists()) {
                manifestFile.getParentFile().mkdirs();
            }
            java.io.FileOutputStream fos = new java.io.FileOutputStream(manifestFile);
            fos.write(xml.getBytes(CHARSET));
            fos.close();
            Log.d(TAG, "✅ AndroidManifest.xml generated at: " + manifestFile.getAbsolutePath());
        } catch (Exception e) {
            Log.w(TAG, "Could not generate manifest XML: " + e.getMessage());
        }
    }
    
    
    private static String createDefaultActivity(Context context, String scId) throws Exception {
        ActivityManager manager = new ActivityManager();
        manager.load(context, scId);
        
        String actName = "MainActivity";
        
        if (!manager.exists(actName)) {
            manager.addActivity(actName, "main.xml");
            manager.save(context, scId);
            Log.d(TAG, "✅ Created default " + actName);
        } else {
            Log.d(TAG, "⏭️ " + actName + " already exists");
        }
        
        String layoutPath = BL_BASE + "data/" + scId + "/main.xml";
        File layoutFile = new File(layoutPath);
        if (!layoutFile.exists()) {
            String defaultLayout = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    android:orientation=\"vertical\"\n" +
                "    android:gravity=\"center\"\n" +
                "    android:background=\"#FFFFFF\">\n" +
                "\n" +
                "    <TextView\n" +
                "        android:layout_width=\"wrap_content\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:text=\"MainActivity\"\n" +
                "        android:textSize=\"24sp\"\n" +
                "        android:textColor=\"#000000\"\n" +
                "        android:padding=\"16dp\" />\n" +
                "\n" +
                "</LinearLayout>";
            
            writeFile(layoutFile, defaultLayout.getBytes(CHARSET));
            Log.d(TAG, "✅ Created default main.xml layout");
        }
        
        return actName;
    }
    
    
    private static void listFilesRecursively(File dir, int depth) {
        if (dir == null || !dir.exists()) return;
        File[] files = dir.listFiles();
        if (files == null) return;
        
        for (File f : files) {
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < depth; i++) indent.append("  ");
            
            if (f.isDirectory()) {
                Log.d(TAG, indent.toString() + "📁 " + f.getName() + "/");
                listFilesRecursively(f, depth + 1);
            } else {
                Log.d(TAG, indent.toString() + "📄 " + f.getName() + " (" + f.length() + " bytes)");
            }
        }
    }
    
    
    private static void writeConfigEnc(ProjectModel model, String scId) throws Exception {
        JSONObject json = new JSONObject();
        json.put("projectName",           safeStr(model.getProjectName()));
        json.put("packageName",           safeStr(model.getPackageName()));
        json.put("appName",               safeStr(model.getAppName()));
        json.put("sc_id",                 scId);
        json.put("versionCode",           safeStr(model.getVersionCode()));
        json.put("versionName",           safeStr(model.getVersionName()));
        json.put("colorAccent",           safeStr(model.getColorAccent()));
        json.put("colorPrimary",          safeStr(model.getColorPrimary()));
        json.put("colorPrimaryDark",      safeStr(model.getColorPrimaryDark()));
        json.put("colorPrimaryHighlight", safeStr(model.getColorPrimaryHighlight()));
        json.put("colorPrimaryNormal",    safeStr(model.getColorPrimaryNormal()));
        json.put("iconPath",              safeStr(model.getIconPath()));
        
        File dir = new File(BL_BASE + "mysc/list/" + scId);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        writeFile(new File(dir, "config.enc"), json.toString().getBytes(CHARSET));
    }
    
    
    private static void unzip(File zipFile, File destDir) throws Exception {
        String destPath = destDir.getCanonicalPath();
        ZipInputStream zis = null;
        
        try {
            zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry entry;
            
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                
                File outFile = new File(destDir, entryName);
                String outPath = outFile.getCanonicalPath();
                if (!outPath.startsWith(destPath + File.separator)
                && !outPath.equals(destPath)) {
                    Log.w(TAG, "Skipping unsafe zip entry: " + entryName);
                    zis.closeEntry();
                    continue;
                }
                
                if (entry.isDirectory()) {
                    outFile.mkdirs();
                } else {
                    File parent = outFile.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(outFile);
                        byte[] buf = new byte[BUFFER];
                        int len;
                        while ((len = zis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                    } finally {
                        if (fos != null) {
                            try { fos.close(); } catch (Exception ignored) {}
                        }
                    }
                }
                zis.closeEntry();
            }
        } finally {
            if (zis != null) {
                try { zis.close(); } catch (Exception ignored) {}
            }
        }
    }
    
    
    private static void copyDir(File src, File dest) throws Exception {
        if (!src.exists()) return;
        
        if (src.isFile()) {
            copyFile(src, dest);
            return;
        }
        
        if (!dest.exists()) {
            dest.mkdirs();
        }
        
        File[] files = src.listFiles();
        if (files == null) return;
        
        for (int i = 0; i < files.length; i++) {
            File child = files[i];
            File target = new File(dest, child.getName());
            if (child.isDirectory()) {
                copyDir(child, target);
            } else {
                copyFile(child, target);
            }
        }
    }
    
    private static void copyFile(File src, File dest) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            byte[] buf = new byte[BUFFER];
            int len;
            while ((len = fis.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } finally {
            if (fis != null) {
                try { fis.close(); } catch (Exception ignored) {}
            }
            if (fos != null) {
                try { fos.close(); } catch (Exception ignored) {}
            }
        }
    }
    
    private static byte[] readFile(String path) throws Exception {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
    
    private static void writeFile(File file, byte[] data) throws Exception {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
        } finally {
            if (fos != null) {
                try { fos.close(); } catch (Exception ignored) {}
            }
        }
    }
    
    private static void deleteDir(File dir) {
        if (dir == null || !dir.exists()) return;
        
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDir(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        dir.delete();
    }
    
    private static String safeStr(String s) {
        return s != null ? s : "";
    }
}