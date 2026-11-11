package com.nexusteam.blacklogics.project.encryption.dex;

import android.content.Context;
import android.util.Log;
import com.nexusteam.blacklogics.project.encryption.ZipOut;
import com.nexusteam.blacklogics.project.encryption.DexFileExtractor;
import com.nexusteam.blacklogics.utils.FileUtil;
import com.apk.axml.aXMLEncoder;
import com.apk.axml.aXMLDecoder;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class BlkDexProtector {

    private static final String TAG = "BlkDexProtector";
    private static final byte[] MAGIC = {'B', 'L', 'K', 'D'};
    private static final byte VERSION = 1;
    private static final String LOADER_CLASS = "com.nexusteam.blacklogics.runtime.SecureDexLoader";
    private static final String LOADER_PACKAGE = "com.nexusteam.blacklogics.runtime";

    private final Context context;

    public BlkDexProtector(Context context) {
        this.context = context;
    }

    public static class Result {
        public String outputApkPath;
        public byte[] maskedKey;
        public byte[] salt;
        public List<String> encryptedAssetNames = new ArrayList<>();
        public String realApplicationClass;
    }

    public Result protect(String inputApkPath, LoaderCompiler loaderCompiler) throws Exception {
        Log.i(TAG, "Starting BlackLogics dex protection for " + inputApkPath);

        String manifestXml;
        String packageName;
        String realAppClass;

        try (ZipFile zipFile = new ZipFile(inputApkPath)) {
            ZipEntry manifestEntry = zipFile.getEntry("AndroidManifest.xml");
            if (manifestEntry == null) throw new IOException("AndroidManifest.xml missing from APK");
            try (InputStream is = zipFile.getInputStream(manifestEntry)) {
                manifestXml = new aXMLDecoder().decode(is).trim();
            }
        }

        android.content.pm.PackageInfo pkgInfo = context.getPackageManager()
                .getPackageArchiveInfo(inputApkPath, 0);
        if (pkgInfo == null) throw new Exception("Could not parse package info from " + inputApkPath);
        packageName = pkgInfo.packageName;

        realAppClass = extractApplicationName(manifestXml);
        if (realAppClass == null || realAppClass.isEmpty()) {
            realAppClass = "";
        } else if (realAppClass.startsWith(".")) {
            realAppClass = packageName + realAppClass;
        }

        SecureRandom rnd = new SecureRandom();
        byte[] key = new byte[32];
        rnd.nextBytes(key);
        byte[] salt = new byte[16];
        rnd.nextBytes(salt);
        byte[] mask = deriveMask(packageName, salt, key.length);
        byte[] maskedKey = xor(key, mask);

        List<String> dexEntries = DexFileExtractor.getDexFileNames(inputApkPath);
        if (dexEntries.isEmpty()) throw new Exception("No dex entries found in " + inputApkPath);

        Map<String, byte[]> encryptedAssets = new LinkedHashMap<>();
        List<String> assetNames = new ArrayList<>();

        try (ZipFile zipFile = new ZipFile(inputApkPath)) {
            int idx = 0;
            for (String dexEntry : dexEntries) {
                byte[] plain = readEntry(zipFile, dexEntry);
                byte[] encrypted = encrypt(plain, key);
                String assetName = hashName(packageName, idx) + ".bin";
                encryptedAssets.put(assetName, encrypted);
                assetNames.add(assetName);
                idx++;
            }
        }
        Arrays.fill(key, (byte) 0);

        String loaderSource = generateLoaderSource(maskedKey, salt, assetNames, realAppClass);
        byte[] loaderDex = loaderCompiler.compile(LOADER_PACKAGE, LOADER_CLASS, loaderSource);

        String patchedManifest = modifyManifestApplicationName(manifestXml, LOADER_CLASS);
        byte[] encodedManifest = new aXMLEncoder().encodeString(context, patchedManifest);

        String outputApkPath = inputApkPath.replace(".apk", "_protected.apk");
        try (ZipFile inputZip = new ZipFile(inputApkPath)) {
            ZipOut zipOut = new ZipOut(outputApkPath);
            zipOut.setInput(inputZip);

            for (String dexEntry : dexEntries) {
                zipOut.removeFile(dexEntry);
            }
            zipOut.addFile("classes.dex", loaderDex);
            for (Map.Entry<String, byte[]> asset : encryptedAssets.entrySet()) {
                zipOut.addFile("assets/" + asset.getKey(), asset.getValue());
            }
            zipOut.addFile("AndroidManifest.xml", encodedManifest);
            zipOut.save();
        }

        Result result = new Result();
        result.outputApkPath = outputApkPath;
        result.maskedKey = maskedKey;
        result.salt = salt;
        result.encryptedAssetNames = assetNames;
        result.realApplicationClass = realAppClass;
        Log.i(TAG, "Protected APK written: " + outputApkPath);
        return result;
    }

    public interface LoaderCompiler {
        byte[] compile(String packageName, String fqClassName, String javaSource) throws Exception;
    }


    private static byte[] encrypt(byte[] plain, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
        byte[] ciphertext = cipher.doFinal(plain);
        ByteBuffer buf = ByteBuffer.allocate(MAGIC.length + 1 + iv.length + ciphertext.length);
        buf.put(MAGIC).put(VERSION).put(iv).put(ciphertext);
        return buf.array();
    }

    private static byte[] deriveMask(String packageName, byte[] salt, int len) throws Exception {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        sha256.update(packageName.getBytes("UTF-8"));
        sha256.update(salt);
        byte[] digest = sha256.digest();
        byte[] mask = new byte[len];
        for (int i = 0; i < len; i++) mask[i] = digest[i % digest.length];
        return mask;
    }

    private static byte[] xor(byte[] a, byte[] b) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) out[i] = (byte) (a[i] ^ b[i]);
        return out;
    }

    private static String hashName(String packageName, int idx) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest((packageName + "#" + idx).getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) sb.append(String.format("%02x", b));
        return sb.substring(0, 16);
    }

    private static byte[] readEntry(ZipFile zip, String name) throws IOException {
        ZipEntry entry = zip.getEntry(name);
        try (InputStream is = zip.getInputStream(entry); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = is.read(buf)) != -1) bos.write(buf, 0, n);
            return bos.toByteArray();
        }
    }


    private String extractApplicationName(String manifestXml) {
        Pattern pattern = Pattern.compile("<application(\\s[^>]*)?\\sandroid:name=\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(manifestXml);
        return matcher.find() ? matcher.group(2) : "";
    }

    private String modifyManifestApplicationName(String originalXml, String newApplicationName) {
        String updated = originalXml.replaceAll(
                "<application(\\s[^>]*)?\\sandroid:name=\"[^\"]*\"",
                "<application$1 android:name=\"" + newApplicationName + "\"");
        if (originalXml.equals(updated)) {
            updated = originalXml.replaceAll(
                    "<application(\\s[^>]*)?>",
                    "<application$1 android:name=\"" + newApplicationName + "\">");
        }
        return updated;
    }


    private String generateLoaderSource(byte[] maskedKey, byte[] salt, List<String> assetNames, String realAppClass) {
        StringBuilder src = new StringBuilder();
        src.append("package ").append(LOADER_PACKAGE).append(";\n\n");
        src.append("public class SecureDexLoader extends com.nexusteam.blacklogics.runtime.SecureDexLoaderBase {\n");
        src.append("    static { \n");
        src.append("        MASKED_KEY = new byte[]{").append(toJavaByteArray(maskedKey)).append("};\n");
        src.append("        SALT = new byte[]{").append(toJavaByteArray(salt)).append("};\n");
        src.append("        ENCRYPTED_ASSETS = new String[]{").append(toJavaStringArray(assetNames)).append("};\n");
        src.append("        REAL_APPLICATION_CLASS = \"").append(realAppClass).append("\";\n");
        src.append("    }\n");
        src.append("}\n");
        return src.toString();
    }

    private String toJavaByteArray(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (i > 0) sb.append(",");
            sb.append((byte) data[i]);
        }
        return sb.toString();
    }

    private String toJavaStringArray(List<String> values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(values.get(i)).append("\"");
        }
        return sb.toString();
    }
}
