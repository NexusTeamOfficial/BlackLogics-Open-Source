package com.besome.blacklogics.util;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import android.util.Base64;

public class FileHandler {
    public static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String getPattle = basePath + "/.blacklogics/resources/My Block/";
    public static String getPattleBlocks = basePath + "/.blacklogics/resources/My Block/blocks.json";
    public static String codeSavePath = basePath + "/.blacklogics/data/";
    public static String colleationPath = basePath + "/.blacklogics/collection/";
    public static String eventsPath = basePath + "/.blacklogics/data/events/";
    
    // Returns the path as String
    public static String getPettlePath() {
        return getPattle;
    }
    
    // Checks if the path exists (returns boolean)
    public static boolean isPettlePathValid() {
        return new File(getPattle).exists();
    }
    
    public static boolean isPettlePathBlocksValid() {
        return new File(getPattleBlocks).exists();
    }
    
    public static String getCodeSavePath(String sc_id) {
        return codeSavePath + sc_id + "/codelogic";
    }
    
    // Similarly for code path validation
    public static boolean isCodePathValid(String sc_id) {
        return new File(getCodeSavePath(sc_id)).exists();
    }
    
    public static String getColletionPath() {
        return colleationPath + "/blocks/list";
    }
    
    // New method to read file content
    public static String readFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
    
    // New method to save file content
    public static void saveFile(String path, String content) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }
    
    // New method to get events path for a specific project
    public static String getEventsPath(String sc_id) {
        return codeSavePath + sc_id + "/events/";
    }
    
    // New method to create necessary directories
    public static void createProjectDirs(String sc_id) {
        new File(codeSavePath + sc_id).mkdirs();
        new File(getEventsPath(sc_id)).mkdirs();
    }
    
    // New method to check if events file exists
    public static boolean eventsFileExists(String sc_id) {
        return new File(getEventsPath(sc_id) + "events.json").exists();
    }
    
    // New method to encode content to Base64
    public static String encodeBase64(String content) {
        return Base64.encodeToString(content.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }
    
    // New method to decode Base64 content
    public static String decodeBase64(String encoded) {
        return new String(Base64.decode(encoded, Base64.DEFAULT), StandardCharsets.UTF_8);
    }
}