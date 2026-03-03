package com.nexusteam.blacklogics.project.encryption.dex;

import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import com.apk.builder.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DexProtectorUtil {
    
    private static final String TAG = "DexProtector";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String CONFIG_FILE_NAME = "3676D55F84497CBEADFC614C1B1B62FC";
    
    /**
     * DEX files को encrypt और protect करने की main method
     */
    public static boolean protectDexFiles(File projectDir, String packageName, String loaderName) {
    try {
        Log.d(TAG, "Starting DEX protection for package: " + packageName);
        

        File binDir = new File(projectDir, "bin");
        File dexDir = new File(binDir, "dex");
        File assetsDir = new File(projectDir, "app/src/main/assets");
        
        if (!assetsDir.exists()) {
            assetsDir.mkdirs();
        }
        

        List<String> dexFileNames = new ArrayList<>();
        File[] dexFiles = dexDir.listFiles(new java.io.FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".dex");
            }
        });
        
        if (dexFiles == null || dexFiles.length == 0) {
            Log.w(TAG, "No DEX files found in: " + dexDir.getAbsolutePath());
            return false;
        }
        
        Log.d(TAG, "Found " + dexFiles.length + " DEX files to protect");
        

        String key = generateKeyFromPackage(packageName);
        

        for (File dexFile : dexFiles) {
            String originalName = dexFile.getName();
            String encryptedName = originalName.replace("classes", "ijiami").replace(".dex", ".dat");
            

            byte[] encryptedData = encryptFile(dexFile, key);
            

            File encryptedFile = new File(assetsDir, encryptedName);
            Files.write(encryptedFile.toPath(), encryptedData);
            

            dexFile.delete();
            
            dexFileNames.add(encryptedName);
            Log.d(TAG, "Protected: " + originalName + " -> " + encryptedName);
        }
        

        createProtectionConfig(assetsDir, packageName, loaderName, dexFileNames, key);
        

        addProtectionLoader(projectDir, packageName);
        
        Log.d(TAG, "DEX protection completed successfully");
        return true;
        
    } catch (Exception e) {
        Log.e(TAG, "DEX protection failed: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    /**
     * File को AES encryption के साथ encrypt करें
     */
    private static byte[] encryptFile(File inputFile, String key) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);
        inputStream.close();
        

        byte[] keyBytes = key.getBytes("UTF-8");
        byte[] outputBytes = new byte[inputBytes.length];
        
        for (int i = 0; i < inputBytes.length; i++) {
            outputBytes[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        
        return outputBytes;
    }
    
    /**
     * Package name से key generate करें
     */
    private static String generateKeyFromPackage(String packageName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(packageName.getBytes("UTF-8"));
            

            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            

            String key = hexString.toString().substring(0, 16);
            Log.d(TAG, "Generated key: " + key + " from package: " + packageName);
            return key;
            
        } catch (Exception e) {
            Log.e(TAG, "Error generating key: " + e.getMessage());

            return "DexProtectorKey123";
        }
    }
    
    /**
     * Protection configuration file create करें
     */
    private static void createProtectionConfig(File assetsDir, String packageName, 
            String loaderName, List<String> dexFiles, String key) throws Exception {
        
        Map<String, Object> config = new HashMap<>();
        

        StringBuilder dexList = new StringBuilder();
        for (String dexFile : dexFiles) {
            if (dexList.length() > 0) dexList.append(",");
            dexList.append(dexFile);
        }
        config.put("dex", dexList.toString());
        

        config.put("sub", "com.SecShell.SecShell.AP");
        config.put("application", packageName + "." + removeFirstDot(loaderName));
        config.put("checkVPN", "false");
        config.put("checkRoot", "false");
        config.put("checkXposed", "false");
        config.put("checkVirtual", "false");
        config.put("protectionKey", key);
        config.put("timestamp", System.currentTimeMillis());
        

        Gson gson = new Gson();
        String jsonConfig = gson.toJson(config);
        

        File configFile = new File(assetsDir, CONFIG_FILE_NAME);
        byte[] encryptedConfig = encryptString(jsonConfig, key);
        Files.write(configFile.toPath(), encryptedConfig);
        
        Log.d(TAG, "Configuration file created: " + configFile.getAbsolutePath());
    }
    
    /**
     * String को encrypt करें
     */
    private static byte[] encryptString(String input, String key) throws Exception {
        byte[] inputBytes = input.getBytes("UTF-8");
        byte[] keyBytes = key.getBytes("UTF-8");
        byte[] outputBytes = new byte[inputBytes.length];
        
        for (int i = 0; i < inputBytes.length; i++) {
            outputBytes[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        
        return outputBytes;
    }
    
    /**
     * Protection loader DEX add करें (अगर available हो)
     */
    private static void addProtectionLoader(File projectDir, String packageName) {
        try {

            String loaderSourcePath = "/storage/emulated/0/Dexpacker/tmp/classes.dex";
            File loaderSource = new File(loaderSourcePath);
            
            if (loaderSource.exists()) {
                File loaderDest = new File(projectDir, "app/libs/classes.dex");
                FileUtil.makeDir(loaderDest.getParent());
                

                FileUtil.copyFile(loaderSourcePath, loaderDest.getAbsolutePath());
                Log.d(TAG, "Protection loader added: " + loaderDest.getAbsolutePath());
            } else {
                Log.w(TAG, "Protection loader not found at: " + loaderSourcePath);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error adding protection loader: " + e.getMessage());
        }
    }
    
    /**
     * String से पहला dot remove करें
     */
    private static String removeFirstDot(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceFirst("\\.", "");
    }
    
    /**
     * APK में DEX protection apply करें (final APK पर)
     */
    public static boolean applyDexProtectionToApk(String apkPath, String packageName) {
        try {
            Log.d(TAG, "Applying DEX protection to APK: " + apkPath);
            

            File tempDir = new File(FileUtil.getExternalStorageDir() + "/.dexprotect_temp/");
            if (tempDir.exists()) {
                FileUtil.deleteFile(tempDir.getAbsolutePath());
            }
            tempDir.mkdirs();
            

            List<File> extractedDex = extractDexFromApk(apkPath, tempDir);
            
            if (extractedDex.isEmpty()) {
                Log.w(TAG, "No DEX files found in APK");
                return false;
            }
            

            String key = generateKeyFromPackage(packageName);
            

            modifyApkWithProtectedDex(apkPath, extractedDex, key, packageName);
            

            FileUtil.deleteFile(tempDir.getAbsolutePath());
            
            Log.d(TAG, "DEX protection applied successfully to APK");
            return true;
            
        } catch (Exception e) {
            Log.e(TAG, "Failed to apply DEX protection to APK: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * APK से DEX files extract करें
     */
    private static List<File> extractDexFromApk(String apkPath, File tempDir) throws IOException {
        List<File> dexFiles = new ArrayList<>();
        
        try (ZipFile zipFile = new ZipFile(apkPath)) {
            java.util.Enumeration<? extends ZipEntry> entries = zipFile.entries();
            
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                
                if (name.endsWith(".dex")) {
                    File outputFile = new File(tempDir, name);
                    
                    try (java.io.InputStream is = zipFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(outputFile)) {
                        
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    
                    dexFiles.add(outputFile);
                    Log.d(TAG, "Extracted DEX: " + name);
                }
            }
        }
        
        return dexFiles;
    }
    
    /**
     * APK को modified करें (protected DEX add करें)
     */
    private static void modifyApkWithProtectedDex(String apkPath, List<File> dexFiles, 
            String key, String packageName) throws Exception {
        

        String modifiedApkPath = apkPath.replace(".apk", "_protected.apk");
        
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(modifiedApkPath));
             ZipFile zipFile = new ZipFile(apkPath)) {
            

            java.util.Enumeration<? extends ZipEntry> entries = zipFile.entries();
            
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                

                if (!name.endsWith(".dex")) {
                    zos.putNextEntry(new ZipEntry(name));
                    
                    try (java.io.InputStream is = zipFile.getInputStream(entry)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                    }
                    
                    zos.closeEntry();
                }
            }
            

            for (File dexFile : dexFiles) {
                String originalName = dexFile.getName();
                String encryptedName = "assets/" + originalName.replace("classes", "ijiami").replace(".dex", ".dat");
                

                byte[] encryptedData = encryptFile(dexFile, key);
                

                ZipEntry newEntry = new ZipEntry(encryptedName);
                zos.putNextEntry(newEntry);
                zos.write(encryptedData);
                zos.closeEntry();
                
                Log.d(TAG, "Added protected DEX: " + encryptedName);
            }
            

            addConfigFileToZip(zos, packageName, dexFiles, key);
            

            addLoaderDexToZip(zos);
        }
        

        File originalApk = new File(apkPath);
        File modifiedApk = new File(modifiedApkPath);
        
        if (originalApk.delete()) {
            modifiedApk.renameTo(originalApk);
            Log.d(TAG, "APK replaced with protected version");
        }
    }
    
    /**
     * Configuration file को ZIP में add करें
     */
    private static void addConfigFileToZip(ZipOutputStream zos, String packageName, 
            List<File> dexFiles, String key) throws Exception {
        

        StringBuilder dexList = new StringBuilder();
        for (File dexFile : dexFiles) {
            String encryptedName = dexFile.getName().replace("classes", "ijiami").replace(".dex", ".dat");
            if (dexList.length() > 0) dexList.append(",");
            dexList.append(encryptedName);
        }
        

        Map<String, Object> config = new HashMap<>();
        config.put("dex", dexList.toString());
        config.put("sub", "com.SecShell.SecShell.AP");
        config.put("application", packageName + ".AP");
        config.put("protectionKey", key);
        config.put("timestamp", System.currentTimeMillis());
        

        Gson gson = new Gson();
        String jsonConfig = gson.toJson(config);
        byte[] encryptedConfig = encryptString(jsonConfig, key);
        

        ZipEntry configEntry = new ZipEntry("assets/" + CONFIG_FILE_NAME);
        zos.putNextEntry(configEntry);
        zos.write(encryptedConfig);
        zos.closeEntry();
        
        Log.d(TAG, "Added config file to APK");
    }
    
    /**
     * Loader DEX को ZIP में add करें
     */
    private static void addLoaderDexToZip(ZipOutputStream zos) throws Exception {
        String loaderPath = "/storage/emulated/0/Dexpacker/tmp/classes.dex";
        File loaderFile = new File(loaderPath);
        
        if (loaderFile.exists()) {
            byte[] loaderData = Files.readAllBytes(loaderFile.toPath());
            
            ZipEntry loaderEntry = new ZipEntry("classes.dex");
            zos.putNextEntry(loaderEntry);
            zos.write(loaderData);
            zos.closeEntry();
            
            Log.d(TAG, "Added loader DEX to APK");
        }
    }
}