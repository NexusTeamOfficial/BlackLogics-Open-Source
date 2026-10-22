package com.nexusteam.blacklogics.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.nexusteam.blacklogics.manager.ActivityManager;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.generator.source.AndroidManifestGenerator;

public class ActivityBridge {

    private static final String TAG     = "ActivityBridge";
    private static final String AES_KEY = "sketchwaresecure";
    private static final String CHARSET = "UTF-8";
    private static final String ALGO    = "AES/CBC/PKCS5Padding";

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface Callback {
        void onSuccess(List<ActivityManager.ViewItem> activities);
        void onError(String reason);
    }


    public static void importActivities(
            final Context context,
            final String swFilePath,
            final String newScId,
            final Callback callback) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    final List<ActivityManager.ViewItem> items =
                            importActivitiesSync(context, swFilePath, newScId);
                    mainHandler.post(new Runnable() {
                        public void run() {
                            callback.onSuccess(items);
                        }
                    });
                } catch (final Exception e) {
                    Log.e(TAG, "importActivities failed", e);
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

    public static List<ActivityManager.ViewItem> importActivitiesSync(
            Context context,
            String swFilePath,
            String newScId) throws Exception {

        Log.d(TAG, "========================================");
        Log.d(TAG, "📁 Importing activities from: " + swFilePath);
        Log.d(TAG, "📁 Target sc_id: " + newScId);
        
        byte[] encrypted = readFile(swFilePath);
        String raw = decrypt(encrypted);
        Log.d(TAG, "🔓 Decrypted content length: " + raw.length());
        
        if (raw.length() > 0) {
            int previewLen = Math.min(500, raw.length());
            Log.d(TAG, "📄 Raw content preview:\n" + raw.substring(0, previewLen));
        }

        List<String> fileNames = parseActivitySection(raw);
        Log.d(TAG, "📋 Found " + fileNames.size() + " activities: " + fileNames);

        ActivityManager manager = new ActivityManager();
        manager.load(context, newScId);
        
        List<ActivityManager.ViewItem> items = new ArrayList<ActivityManager.ViewItem>();

        Complex complex = null;
        if (context instanceof DesignActivity) {
            complex = ((DesignActivity) context).complex;
            Log.d(TAG, "🔧 Complex instance obtained from DesignActivity");
        } else {
            complex = new Complex();
            complex.setC(context);
            complex.setId(newScId);
            complex.setActivityName("MainActivity");
            Log.d(TAG, "🔧 New Complex instance created");
        }

        AndroidManifestGenerator manifestGenerator = new AndroidManifestGenerator();
        manifestGenerator.load(context, newScId);
        Log.d(TAG, "🔧 AndroidManifestGenerator loaded");

        String packageName = complex.getPkgName();
        String projectName = complex.getProjectName();

        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            
            if (fileName.endsWith(".xml")) {
                fileName = fileName.substring(0, fileName.length() - 4);
            }
            
            if (fileName == null || fileName.trim().isEmpty()) {
                continue;
            }
            fileName = fileName.trim();
            
            String javaName = toJavaName(fileName);
            String xmlName = fileName + ".xml";
            
            boolean existsInXq = manager.exists(javaName);
            
            if (!existsInXq) {
                manager.addActivity(javaName, xmlName);
                Log.d(TAG, "✅ Added to xq: " + javaName + " → " + xmlName);
                
                try {
                    complex.addActivity(javaName, xmlName, false, false, false);
                    Log.d(TAG, "✅ Registered in Complex: " + javaName + " → " + xmlName);
                    
                    Map<String, String> activityAttrs = new HashMap<String, String>();
                    
                    if (i == 0) {
                        activityAttrs.put("exported", "true");
                        activityAttrs.put("launchMode", "singleTop");
                        activityAttrs.put("configChanges", "orientation|keyboardHidden|screenSize");
                        activityAttrs.put("windowSoftInputMode", "adjustResize");
                        
                        manifestGenerator.setLauncherActivity(javaName);
                        Log.d(TAG, "✅ Set as Launcher: " + javaName);
                    } else {
                        activityAttrs.put("exported", "true");
                        activityAttrs.put("configChanges", "orientation|keyboardHidden|screenSize");
                        activityAttrs.put("windowSoftInputMode", "adjustResize");
                    }
                    
                    manifestGenerator.addActivity("." + javaName, activityAttrs);
                    Log.d(TAG, "✅ Added to Manifest: " + javaName);
                    
                } catch (Exception e) {
                    Log.e(TAG, "❌ Failed to register: " + javaName, e);
                }
            } else {
                Log.d(TAG, "⏭️ Already exists: " + javaName);
            }
            
            items.add(new ActivityManager.ViewItem(xmlName, xmlName,
                    javaName, javaName + ".java"));
        }

        manager.save(context, newScId);
        Log.d(TAG, "💾 xq saved");
        
        try {
            manifestGenerator.save(context, newScId);
            Log.d(TAG, "💾 Manifest saved");
        } catch (Exception e) {
            Log.e(TAG, "❌ Failed to save manifest", e);
        }
        
        try {
            if (complex != null) {
                complex.saveJson();
                Log.d(TAG, "💾 Complex data saved");
                
                complex.refreshData();
                
                if (context instanceof DesignActivity) {
                    DesignActivity designActivity = (DesignActivity) context;
                    Log.d(TAG, "🔄 Spinners updated");
                    
                    if (!fileNames.isEmpty()) {
                        String firstActivity = toJavaName(fileNames.get(0));
                        designActivity._setCurrentActivity(firstActivity);
                        Log.d(TAG, "🔄 Set current activity: " + firstActivity);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "❌ Failed to save Complex data", e);
        }
        
        Log.d(TAG, "✅✅✅ Activity import complete!");
        Log.d(TAG, "========================================");

        return items;
    }


    private static List<String> parseActivitySection(String raw) {
        List<String> fileNames = new ArrayList<String>();
        
        if (raw == null || raw.isEmpty()) {
            Log.w(TAG, "⚠️ Raw content is empty!");
            return fileNames;
        }
        
        String[] lines = raw.split("\n");
        boolean inActivitySection = false;
        boolean inJsonArray = false;
        StringBuilder jsonBuffer = new StringBuilder();

        Log.d(TAG, "🔍 Parsing " + lines.length + " lines");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            if (line.equals("@activity")) {
                inActivitySection = true;
                Log.d(TAG, "📍 Entered @activity section at line " + i);
                continue;
            }

            if (line.startsWith("@") && !line.equals("@activity")) {
                inActivitySection = false;
                continue;
            }

            if (inActivitySection) {
                if (line.startsWith("[")) {
                    inJsonArray = true;
                    jsonBuffer.setLength(0);
                    jsonBuffer.append(line);
                    if (line.endsWith("]")) {
                        inJsonArray = false;
                        processJsonArray(jsonBuffer.toString(), fileNames);
                        jsonBuffer.setLength(0);
                    }
                    continue;
                }
                
                if (inJsonArray) {
                    jsonBuffer.append(line);
                    if (line.endsWith("]")) {
                        inJsonArray = false;
                        processJsonArray(jsonBuffer.toString(), fileNames);
                        jsonBuffer.setLength(0);
                    }
                    continue;
                }
                
                if (line.startsWith("{")) {
                    if (line.endsWith("}")) {
                        processJsonObject(line, fileNames);
                    } else {
                        jsonBuffer.setLength(0);
                        jsonBuffer.append(line);
                        int j = i + 1;
                        while (j < lines.length) {
                            String nextLine = lines[j].trim();
                            jsonBuffer.append(nextLine);
                            if (nextLine.endsWith("}")) {
                                break;
                            }
                            j++;
                        }
                        processJsonObject(jsonBuffer.toString(), fileNames);
                        jsonBuffer.setLength(0);
                    }
                    continue;
                }
                
                String name = line.replace(".xml", "").trim();
                if (!name.isEmpty() && Character.isLetter(name.charAt(0))) {
                    if (!name.startsWith("{") && !name.startsWith("[")) {
                        fileNames.add(name);
                        Log.d(TAG, "📄 Plain filename: " + name);
                    }
                }
            }
        }

        return fileNames;
    }

    private static void processJsonArray(String jsonStr, List<String> fileNames) {
        try {
            JSONArray array = new JSONArray(jsonStr);
            Log.d(TAG, "📊 Processing JSON array with " + array.length() + " items");
            
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String fileName = obj.optString("fileName", "");
                int fileType = obj.optInt("fileType", 0);
                
                if (!fileName.isEmpty() && fileType == 0) {
                    if (fileName.endsWith(".xml")) {
                        fileName = fileName.substring(0, fileName.length() - 4);
                    }
                    fileNames.add(fileName);
                    Log.d(TAG, "📄 From JSON array [" + i + "]: " + fileName);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON array: " + e.getMessage());
        }
    }

    private static void processJsonObject(String jsonStr, List<String> fileNames) {
        try {
            JSONObject obj = new JSONObject(jsonStr);
            String fileName = obj.optString("fileName", "");
            int fileType = obj.optInt("fileType", 0);
            
            if (!fileName.isEmpty() && fileType == 0) {
                if (fileName.endsWith(".xml")) {
                    fileName = fileName.substring(0, fileName.length() - 4);
                }
                fileNames.add(fileName);
                Log.d(TAG, "📄 From JSON object: " + fileName);
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to parse JSON object: " + jsonStr);
        }
    }


    public static String toJavaName(String fileName) {
        if (fileName == null || fileName.isEmpty()) return "MainActivity";

        if (fileName.endsWith(".xml")) {
            fileName = fileName.substring(0, fileName.length() - 4);
        }

        if (fileName.endsWith("Activity") && Character.isUpperCase(fileName.charAt(0))) {
            return fileName;
        }

        String[] parts = fileName.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.isEmpty()) continue;
            sb.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                sb.append(part.substring(1).toLowerCase());
            }
        }

        String result = sb.toString();
        if (!result.endsWith("Activity")) {
            result = result + "Activity";
        }

        return result;
    }

    public static String toFileName(String javaName) {
        if (javaName == null || javaName.isEmpty()) return "main";

        String name = javaName;
        if (name.endsWith("Activity")) {
            name = name.substring(0, name.length() - 8);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append('_');
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }


    private static String decrypt(byte[] encrypted) throws Exception {
        byte[] keyBytes = AES_KEY.getBytes(CHARSET);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE,
                new SecretKeySpec(keyBytes, "AES"),
                new IvParameterSpec(keyBytes));
        return new String(cipher.doFinal(encrypted), CHARSET);
    }


    private static byte[] readFile(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception("File not found: " + path);
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return data;
        } finally {
            if (fis != null) {
                try { fis.close(); } catch (Exception e) {}
            }
        }
    }
}