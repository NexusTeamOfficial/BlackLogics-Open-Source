package com.nexusteam.blacklogics.project.encryption.dex;

import android.content.Context;
import android.util.Log;
import com.nexusteam.blacklogics.project.encryption.*;
import com.nexusteam.blacklogics.utils.FileUtil;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.apk.axml.aXMLEncoder;
import com.apk.axml.aXMLDecoder;
import com.google.gson.Gson;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/*
• Created By Smart India Gaming
• Date 27/12/2025
• This File Under MIT License
• This File Used In BlackLogics Project Encryption Dex
• All Right Reversed
*/

public class ApkProtector {
    
    private Context context;
    private String packageName;
    private String originalApplicationName;
    private Gson gson = new Gson();
    
    public ApkProtector(Context context) {
        this.context = context;
    }
    /**
* Main method to protect APK with DEX encryption (BlackLogics)
*/    
    public String protectWithDexEncryption(String inputApkPath) throws Exception {
        Log.i("ApkProtector", "Starting DEX encryption protection...");
        

        android.content.pm.PackageInfo pckgInfo = context.getPackageManager()
        .getPackageArchiveInfo(inputApkPath, android.content.pm.PackageManager.GET_ACTIVITIES);
        if (pckgInfo == null) {
            throw new Exception("Failed to parse APK package info");
        }
        pckgInfo.applicationInfo.sourceDir = inputApkPath;
        pckgInfo.applicationInfo.publicSourceDir = inputApkPath;
        packageName = pckgInfo.packageName;
        

        String manifestXml = parseAndroidManifest(inputApkPath);
        originalApplicationName = extractApplicationName(manifestXml);
        
        if (originalApplicationName.isEmpty()) {
            throw new Exception("Could not extract application name from manifest");
        }
        

        String tempApkPath = inputApkPath.replace(".apk", "_protected.apk");
        

        String modifiedManifest = modifyManifestForProtection(manifestXml, "com.SecShell.SecShell.AP");
        byte[] encodedManifest = encodeXmlToBinary(modifiedManifest);
        

        List<String> dexFiles = DexFileExtractor.getDexFileNames(inputApkPath);
        

        String tempDir = "/storage/emulated/0/BlackLogics/tmp/";
        String tempAssetsDir = tempDir + "assets/";
        FileUtil.makeDir(tempDir);
        FileUtil.makeDir(tempAssetsDir);
        

        try {
            InputStream input = context.getAssets().open("classes.dex");
            OutputStream output = new FileOutputStream(tempDir + "classes.dex");
            byte[] data = new byte[1024];
            int count;
            while ((count = input.read(data)) > 0) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("ApkProtector", "Error copying loader dex", e);
        }
        
        try (ZipFile inputZip = new ZipFile(inputApkPath)) {
            ZipOut zipOut = new ZipOut(tempApkPath);
            zipOut.setInput(inputZip);
            

            List<String> dexNames = new ArrayList<>();
            for (int i = 0; i < dexFiles.size(); i++) {
                String dexFile = dexFiles.get(i);
                String dexConfig = dexFile.replace("classes", "ijiami").replace("dex", "dat");
                String aaa = tempAssetsDir + dexConfig;
                String entryName = "assets/" + dexConfig;
                

                ZipXOREncoder zipXOREncoder = new ZipXOREncoder(inputApkPath);
                File outputFile = new File(aaa);
                zipXOREncoder.encodeFileFromZip(dexFile, outputFile, packageName);
                

                byte[] fileData = Files.readAllBytes(outputFile.toPath());
                zipOut.addFile(entryName, fileData);
                

                zipOut.removeFile(dexFile);
                
                dexNames.add(dexConfig);
            }
            

            Map<String, Object> config = new HashMap<>();
            config.put("dex", String.join(",", dexNames));
            config.put("sub", "com.SecShell.SecShell.AP");
            config.put("application", packageName + "." + removeFirstDot(originalApplicationName));
            config.put("checkVPN", "false");
            config.put("checkRoot", "false");
            config.put("checkXposed", "false");
            config.put("checkVirtual", "false");
            
            String configJson = gson.toJson(config);
            String configFilePath = tempAssetsDir + "3676D55F84497CBEADFC614C1B1B62FC";
            FileUtil.writeFile(configFilePath, configJson);
            FileUtil.writeFile("/storage/emulated/0/config.json", configJson);
            

            FileEncryptor fileEncryptor = new FileEncryptor(packageName);
            fileEncryptor.encryptFile(configFilePath, configFilePath);
            

            byte[] configData = Files.readAllBytes(new File(configFilePath).toPath());
            zipOut.addFile("assets/3676D55F84497CBEADFC614C1B1B62FC", configData);
            
            byte[] manifestData = encodedManifest;
            zipOut.addFile("AndroidManifest.xml", manifestData);
            
            byte[] loaderDexData = Files.readAllBytes(new File(tempDir + "classes.dex").toPath());
            zipOut.addFile("classes.dex", loaderDexData);
            

            zipOut.save();
            
            Log.i("ApkProtector", "Protected APK created: " + tempApkPath);
            

            cleanup();
            
            return tempApkPath;
            
        } catch (Exception e) {
            Log.e("ApkProtector", "Error creating protected APK", e);
            throw e;
        }
    }
    
    /**
* Add security checks to APK (VPN, Emulator, Screen recording detection)
*/    
    public String addSecurityChecks(String inputApkPath, String checkType) throws Exception {
        Log.i("ApkProtector", "Adding security check: " + checkType);
        
        if (!checkType.equals("vpn") && !checkType.equals("sec") && 
        !checkType.equals("emu") && !checkType.equals("upd")) {
            throw new IllegalArgumentException("Invalid check type: " + checkType);
        }
        

        String manifestXml = parseAndroidManifest(inputApkPath);
        

        String modifiedManifest;
        switch (checkType) {
            case "vpn":
            modifiedManifest = manifestXml.replace("</application>", 
            "        <provider\n            android:name=\"arm.VPNChecker\"\n            android:exported=\"false\"\n            android:authorities=\"VPNChecker\"\n            android:initOrder=\"19999999\" />\n    </application>\n    <uses-permission android:name=\"Armadillo\" />\n    <!-- Have full network access -->\n    <uses-permission android:name=\"android.permission.INTERNET\" />\n    <!-- View Wi-Fi connections -->\n    <uses-permission android:name=\"android.permission.ACCESS_WIFI_STATE\" />\n    <!-- View network connections -->\n    <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />");
            break;
            case "sec":
            modifiedManifest = manifestXml.replace("</application>", 
            "        <provider\n            android:name=\"arm.sec\"\n            android:exported=\"false\"\n            android:authorities=\"arm.sec\"\n            android:initOrder=\"19999999\" />\n    </application>");
            break;
            case "emu":
            modifiedManifest = manifestXml.replace("</application>", 
            "        <provider\n            android:name=\"arm.emu\"\n            android:exported=\"false\"\n            android:authorities=\"arm.emu\"\n            android:initOrder=\"19999999\" />\n    </application>");
            break;
            case "upd":
            modifiedManifest = manifestXml.replace("</application>", 
            "        <provider\n            android:name=\"arm.upd\"\n            android:exported=\"false\"\n            android:authorities=\"arm.upd\"\n            android:initOrder=\"19999999\" />\n    </application>");
            break;
            default:
            throw new IllegalArgumentException("Unknown check type: " + checkType);
        }
        
        byte[] encodedManifest = encodeXmlToBinary(modifiedManifest);
        

        String tempDir = "/storage/emulated/0/BlackLogics/vpn/";
        FileUtil.makeDir(tempDir);
        

        String tmpdexname;
        try {
            List<String> dexFiles = DexFileExtractor.getDexFileNames(inputApkPath);
            tmpdexname = "classes" + (dexFiles.size() + 1) + ".dex";
            
            InputStream input = context.getAssets().open(checkType);
            OutputStream output = new FileOutputStream(tempDir + tmpdexname);
            byte[] data = new byte[1024];
            int count;
            while ((count = input.read(data)) > 0) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("ApkProtector", "Error copying security dex", e);
            throw e;
        }
        

        String outputApkPath = inputApkPath.replace(".apk", "_" + checkType + ".apk");
        

        try (ZipFile inputZip = new ZipFile(inputApkPath)) {
            ZipOut zipOut = new ZipOut(outputApkPath);
            zipOut.setInput(inputZip);
            

            byte[] securityDexData = Files.readAllBytes(new File(tempDir + tmpdexname).toPath());
            zipOut.addFile(tmpdexname, securityDexData);
            

            zipOut.addFile("AndroidManifest.xml", encodedManifest);
            

            zipOut.save();
            
            Log.i("ApkProtector", "Security check added: " + outputApkPath);
            return outputApkPath;
            
        } catch (Exception e) {
            Log.e("ApkProtector", "Error adding security check", e);
            throw e;
        }
    }
    
    /**
* Encrypt APK resources (obfuscate resources.arsc)
*/    
    public String encryptResources(String inputApkPath) throws Exception {
        Log.i("ApkProtector", "Encrypting resources...");
        
        String outputApkPath = inputApkPath.replace(".apk", "_RD.apk");
        
        try (ZipFile zipFile = new ZipFile(inputApkPath)) {
            ZipOut zipOut = new ZipOut(outputApkPath);
            zipOut.setInput(zipFile);
            

            zipOut.removeFile("resources.arsc");
            

            ArscObfuser arscObfuser = new ArscObfuser(getZipInputStream(zipFile, "resources.arsc"));
            zipOut.addFile("resources.arsc", arscObfuser.getData());
            

            HashMap<String, String> map = arscObfuser.getMap();
            for (String key : map.keySet()) {
                zipOut.removeFile(key);
                zipOut.addFile(map.get(key), toByteArray(getZipInputStream(zipFile, key)));
            }
            

            zipOut.save();
            
            Log.i("ApkProtector", "Resources encrypted: " + outputApkPath);
            return outputApkPath;
            
        } catch (Exception e) {
            Log.e("ApkProtector", "Error encrypting resources", e);
            throw e;
        }
    }
    

    private String parseAndroidManifest(String apkPath) throws Exception {
        try (ZipFile zipFile = new ZipFile(apkPath)) {
            ZipEntry entry = zipFile.getEntry("AndroidManifest.xml");
            if (entry == null) {
                throw new Exception("AndroidManifest.xml not found in APK");
            }
            
            try (InputStream inputStream = zipFile.getInputStream(entry)) {
                return new aXMLDecoder().decode(inputStream).trim();
            }
        }
    }
    
    private String extractApplicationName(String manifestXml) {
        Pattern pattern = Pattern.compile(
        "<application(\\s[^>]*)?\\sandroid:name=\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(manifestXml);
        
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }
    
    private String modifyManifestForProtection(String originalXml, String newApplicationName) {
        String updatedContent = originalXml.replaceAll(
        "<application(\\s[^>]*)?\\sandroid:name=\"[^\"]*\"",
        "<application$1 android:name=\"" + newApplicationName + "\""
        );
        

        if (originalXml.equals(updatedContent)) {
            updatedContent = originalXml.replaceAll(
            "<application(\\s[^>]*)?>",
            "<application$1 android:name=\"" + newApplicationName + "\">"
            );
        }
        
        return updatedContent;
    }
    
    private byte[] encodeXmlToBinary(String xml) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            aXMLEncoder encoder = new aXMLEncoder();
            byte[] encoded = encoder.encodeString(context, xml);
            baos.write(encoded);
            return baos.toByteArray();
        } catch (Exception e) {
            Log.e("ApkProtector", "Error encoding XML", e);
            return new byte[0];
        }
    }
    
    private String removeFirstDot(String str) {
        return str.replaceFirst("\\.", "");
    }
    
    private InputStream getZipInputStream(ZipFile zipFile, String entryName) throws IOException {
        ZipEntry entry = zipFile.getEntry(entryName);
        if (entry == null) {
            throw new IOException("Entry " + entryName + " not found in the zip file.");
        }
        return zipFile.getInputStream(entry);
    }
    
    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
    
    private byte[] xorEncode(byte[] data, String key) {
        return ZipXOREncoder.xorEncode(data, key);
    }
    
    /**
* Clean up temp directories
*/    
    public void cleanup() {
        try {
            FileUtil.deleteFile("/storage/emulated/0/BlackLogics/tmp/");
        } catch (Exception e) {
            Log.e("ApkProtector", "Error cleaning up", e);
        }
    }
    
    /**
* Prepare temp directories
*/    
    public void prepareTempDirs() {
        if (FileUtil.isExistFile("/storage/emulated/0/BlackLogics/tmp")) {
            FileUtil.deleteFile("/storage/emulated/0/BlackLogics/tmp");
        }
        FileUtil.makeDir("/storage/emulated/0/BlackLogics/tmp/assets/");
        FileUtil.makeDir("/storage/emulated/0/BlackLogics/vpn/");
    }
}
