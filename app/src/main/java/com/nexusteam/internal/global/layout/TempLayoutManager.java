package com.nexusteam.internal.global.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.besome.blacklogics.FileUtil;
import com.besome.blacklogics.SketchwareUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.shapun.layouteditor.*;
import com.shapun.layouteditor.utils.*;

import java.io.File;
import java.io.StringReader;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class TempLayoutManager {

    private static final String TEMP_DIR = "/.blacklogic/.temp_layouts/";
    private static final String AES_KEY = "1234567890123456"; // 16 char key
    private static final String AES_IV = "abcdefghijklmnop"; // 16 char IV
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    // Obfuscated key & IV parts (XOR + split)
    private static final int[] KEY_PARTS = {78,101,120,117,115,84,101,97,109,83,109,97,114,116,73,110,100,105,97,50,48,50,53,76,97,121,111,117,116};
    private static final int[] IV_PARTS  = {49,50,51,52,53,54,55,56,57,48,97,98,99,100,101,102};

    private TempLayoutManager() {
        // Private constructor to prevent instantiation
    }

    /** ---------------- FILE UTILITIES ---------------- **/

    private static String getTempFilePath(Context context, String activityName) {
        return FileUtil.getPackageDataDir(context) + TEMP_DIR + "temp_" + activityName + ".json";
    }

    private static void ensureTempDirExists(Context context) {
        FileUtil.makeDir(FileUtil.getPackageDataDir(context) + TEMP_DIR);
    }

    /** ---------------- AES ENCRYPT/DECRYPT ---------------- **/

    private static SecretKeySpec getKey() {
        byte[] keyBytes = new byte[KEY_PARTS.length];
        for (int i = 0; i < KEY_PARTS.length; i++) {
            keyBytes[i] = (byte) ((KEY_PARTS[i] ^ 0x55) + 3); // XOR + shift obfuscation
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Deterministic runtime IV generation
    private static IvParameterSpec getIV() {
        byte[] ivBytes = new byte[IV_PARTS.length];
        for (int i = 0; i < IV_PARTS.length; i++) {
            ivBytes[i] = (byte) ((IV_PARTS[i] ^ 0x55) + 3); // XOR + shift obfuscation
        }
        return new IvParameterSpec(ivBytes);
    }

    // Encrypt data
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(), getIV());
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey(), getIV());
            byte[] decoded = Base64.decode(encryptedData, Base64.NO_WRAP);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** ---------------- SAVE / RESTORE TEMP ---------------- **/

    public static void saveTempLayout(Context context, String activityName, String xmlCode) {
        try {
            ensureTempDirExists(context);
            String encrypted = encrypt(xmlCode);
            if (encrypted != null) {
                FileUtil.writeFile(getTempFilePath(context, activityName), encrypted);
            }
        } catch (Exception e) {
            SketchwareUtil.showMessage(context, "Temp Save Error: " + e.getMessage());
        }
    }

    private static String restoreTempLayout(Context context, String activityName) {
        try {
            String encrypted = FileUtil.readFile(getTempFilePath(context, activityName));
            return decrypt(encrypted);
        } catch (Exception e) {
            SketchwareUtil.showMessage(context, "Restore Error: " + e.getMessage());
            return null;
        }
    }

    private static void deleteTempFile(Context context, String activityName) {
        File file = new File(getTempFilePath(context, activityName));
        if (file.exists()) file.delete();
    }

    /** ---------------- GLOBAL RESTORE ---------------- **/

    /**
     * Shows a professional global restore dialog.
     * Clicking "Restore All" restores all saved layouts immediately.
     */
    public static void showGlobalRestoreDialog(Activity activity, ViewGroup editorLayout, String savePath, GlobalRestoreCallback callback) {
        File dir = new File(FileUtil.getPackageDataDir(activity) + TEMP_DIR);
        if (!dir.exists()) return;

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) return;

        ArrayList<String> pendingLayouts = new ArrayList<>();
        for (File f : files) {
            String name = f.getName().replace("temp_", "").replace(".json", "");
            pendingLayouts.add(name);
        }

        if (pendingLayouts.isEmpty()) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Restore All Layouts");
        builder.setMessage("There are " + pendingLayouts.size() + " saved layouts. Click Restore to restore all.");

        builder.setPositiveButton("Restore All", (dialog, which) -> {
            restoreAllToEditor(activity, editorLayout, savePath, callback);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Do nothing
        });

        builder.show();
    }

    /** ---------------- RESTORE TO EDITOR ---------------- **/

    public static void restoreAllToEditor(Activity activity, ViewGroup editorLayout, String savePath, GlobalRestoreCallback callback) {
        File dir = new File(FileUtil.getPackageDataDir(activity) + TEMP_DIR);
        if (!dir.exists()) return;

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) return;

        for (File f : files) {
            String layoutName = f.getName().replace("temp_", "").replace(".json", "");

            try {
                // 1️⃣ Read and decrypt temp layout
                String xmlCode = decrypt(FileUtil.readFile(f.getAbsolutePath()));
                if (xmlCode != null) {
                    // 2️⃣ Save to permanent layout system
                    saveLayoutToPermanent(savePath, layoutName, xmlCode);

                    // 3️⃣ Load into editor
                    loadXmlIntoEditor(editorLayout, xmlCode);

                    if (callback != null) callback.onLayoutRestored(layoutName, xmlCode);
                }

            } catch (Exception e) {
                SketchwareUtil.showMessage(activity, "Failed to restore: " + layoutName);
            }

            // 4️⃣ Delete temp after restore
            f.delete();
        }

        if (callback != null) callback.onAllRestored();
    }

    /** ---------------- PERMANENT SAVE ---------------- **/

    private static void saveLayoutToPermanent(String savePath, String layoutName, String xmlCode) {
        try {
            if (savePath == null || savePath.trim().isEmpty() || layoutName == null || layoutName.trim().isEmpty())
                return;

            File layoutFile = new File(savePath + "/root_layout.json");
            ArrayList<HashMap<String, Object>> layoutList = new ArrayList<>();

            // Read existing layouts
            if (FileUtil.isExistFile(layoutFile.getAbsolutePath())) {
                String encrypted = FileUtil.readFile(layoutFile.getAbsolutePath());
                String decrypted = decrypt(encrypted);
                if (decrypted != null) {
                    layoutList = new Gson().fromJson(decrypted,
                            new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
                }
            }

            // Remove old layout with same name
            layoutList.removeIf(layout -> layoutName.equalsIgnoreCase((String) layout.get("name")));

            // Add new layout
            HashMap<String, Object> layoutData = new HashMap<>();
            layoutData.put("name", layoutName);
            layoutData.put("xml", xmlCode);
            layoutList.add(layoutData);

            // Encrypt and save
            String json = new Gson().toJson(layoutList);
            String encryptedJson = encrypt(json);
            if (encryptedJson != null) {
                if (!layoutFile.getParentFile().exists()) layoutFile.getParentFile().mkdirs();
                FileUtil.writeFile(layoutFile.getAbsolutePath(), encryptedJson);
            }

        } catch (Exception e) {
            Log.e("TempLayoutManager", "saveLayoutToPermanent failed: " + e.getMessage(), e);
        }
    }

    /** ---------------- LOAD XML INTO EDITOR ---------------- **/

    private static void loadXmlIntoEditor(ViewGroup editorLayout, String xmlCode) {
        /*try {
            editorLayout.removeAllViews();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(xmlCode));

            ArrayList<View> viewStack = new ArrayList<>();
            viewStack.add(editorLayout);

            for (int eventType = parser.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tag = parser.getName();
                    View view = ReflectionUtils.createView(tag, editorLayout.getContext()); // tumhara view generator
                    if (view != null) viewStack.add(view);

                } else if (eventType == XmlPullParser.END_TAG && viewStack.size() > 1) {
                    View child = viewStack.remove(viewStack.size() - 1);
                    ViewGroup parent = (ViewGroup) viewStack.get(viewStack.size() - 1);
                    parent.addView(child);
                }
            }

        } catch (Exception e) {
            Log.e("TempLayoutManager", "loadXmlIntoEditor failed: " + e.getMessage(), e);
        }*/
    }

    /** ---------------- CALLBACK INTERFACE ---------------- **/

    public interface GlobalRestoreCallback {
        void onLayoutRestored(String activityName, String xmlCode);
        void onAllRestored();
    }
}
