package com.nexusteam.blacklogics.project.encryption.dex;

import android.content.Context;
import android.util.Log;

import com.nexusteam.blacklogics.project.encryption.FileEncryptor;
import com.nexusteam.blacklogics.project.encryption.ZipOut;
import com.nexusteam.blacklogics.project.encryption.ZipXOREncoder;
import com.nexusteam.blacklogics.project.encryption.DexFileExtractor;
import com.nexusteam.blacklogics.utils.FileUtil;
import com.apk.axml.aXMLEncoder;
import com.apk.axml.aXMLDecoder;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ApkProtector {

    private static final String TAG = "ApkProtector";
    private final Context context;
    private final Gson gson = new Gson();

    private String packageName;
    private String originalApplicationName;

    public ApkProtector(Context context) {
        this.context = context;
    }

    public String protectWithDexEncryption(String inputApkPath) throws Exception {
        Log.i(TAG, "Starting ijiami DEX protection: " + inputApkPath);

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

        if (originalApplicationName == null || originalApplicationName.isEmpty()) {
            originalApplicationName = "";
        }

        String modifiedManifest = modifyManifestForProtection(manifestXml, "com.SecShell.SecShell.AP");
        byte[] encodedManifest = encodeXmlToBinary(modifiedManifest);

        String tempDir = "/storage/emulated/0/BlackLogics/tmp/";
        String tempAssetsDir = tempDir + "assets/";
        FileUtil.makeDir(tempDir);
        FileUtil.makeDir(tempAssetsDir);

        try (InputStream input = context.getAssets().open("classes.dex");
             OutputStream output = new FileOutputStream(tempDir + "classes.dex")) {
            byte[] buf = new byte[8192];
            int len;
            while ((len = input.read(buf)) > 0) {
                output.write(buf, 0, len);
            }
        } catch (Exception e) {
            throw new Exception("Loader classes.dex missing from assets!", e);
        }

        String outputApkPath = inputApkPath.replace(".apk", "_protected.apk");

        try (ZipFile inputZip = new ZipFile(inputApkPath)) {
            ZipOut zipOut = new ZipOut(outputApkPath);
            zipOut.setInput(inputZip);

            List<String> dexFiles = DexFileExtractor.getDexFileNames(inputApkPath);
            List<String> dexNames = new ArrayList<>();

            for (String dexFile : dexFiles) {
                String dexConfig = dexFile.replace("classes", "ijiami").replace("dex", "dat");
                String localPath = tempAssetsDir + dexConfig;
                String entryName = "assets/" + dexConfig;

                ZipXOREncoder encoder = new ZipXOREncoder(inputApkPath);
                File outFile = new File(localPath);
                encoder.encodeFileFromZip(dexFile, outFile, packageName);

                byte[] encrypted = Files.readAllBytes(outFile.toPath());
                zipOut.addFile(entryName, encrypted);
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
            String configPath = tempAssetsDir + "3676D55F84497CBEADFC614C1B1B62FC";
            FileUtil.writeFile(configPath, configJson);

            FileEncryptor fileEncryptor = new FileEncryptor(packageName);
            fileEncryptor.encryptFile(configPath, configPath);

            byte[] configData = Files.readAllBytes(new File(configPath).toPath());
            zipOut.addFile("assets/3676D55F84497CBEADFC614C1B1B62FC", configData);

            zipOut.addFile("AndroidManifest.xml", encodedManifest);

            byte[] loaderDex = Files.readAllBytes(new File(tempDir + "classes.dex").toPath());
            zipOut.addFile("classes.dex", loaderDex);

            zipOut.save();
        }

        Log.i(TAG, "Protected APK ready: " + outputApkPath);
        cleanup(tempDir);
        return outputApkPath;
    }


    private String parseAndroidManifest(String apkPath) throws Exception {
        try (ZipFile zip = new ZipFile(apkPath)) {
            ZipEntry entry = zip.getEntry("AndroidManifest.xml");
            if (entry == null) throw new IOException("AndroidManifest.xml missing");
            try (InputStream is = zip.getInputStream(entry)) {
                return new aXMLDecoder().decode(is).trim();
            }
        }
    }

    private String extractApplicationName(String manifestXml) {
        Pattern p = Pattern.compile("<application(\\s[^>]*)?\\sandroid:name=\"([^\"]*)\"");
        Matcher m = p.matcher(manifestXml);
        return m.find() ? m.group(2) : "";
    }

    private String modifyManifestForProtection(String originalXml, String newAppName) {
        String updated = originalXml.replaceAll(
                "<application(\\s[^>]*)?\\sandroid:name=\"[^\"]*\"",
                "<application$1 android:name=\"" + newAppName + "\""
        );
        if (updated.equals(originalXml)) {
            updated = originalXml.replaceAll(
                    "<application(\\s[^>]*)?>",
                    "<application$1 android:name=\"" + newAppName + "\">"
            );
        }
        return updated;
    }

    private byte[] encodeXmlToBinary(String xml) throws Exception {
        aXMLEncoder encoder = new aXMLEncoder();
        return encoder.encodeString(context, xml);
    }

    private String removeFirstDot(String str) {
        if (str == null || str.isEmpty()) return "";
        return str.replaceFirst("\\.", "");
    }

    private void cleanup(String tempDir) {
        try {
            FileUtil.deleteFile(tempDir);
        } catch (Exception ignored) {}
    }
}