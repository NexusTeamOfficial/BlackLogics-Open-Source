package com.nexusteam.blacklogics.runtime;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.util.Log;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureDexLoaderBase extends Application {

    private static final String TAG = "SecureDexLoader";

    protected static byte[] MASKED_KEY;
    protected static byte[] SALT;
    protected static String[] ENCRYPTED_ASSETS;
    protected static String REAL_APPLICATION_CLASS;

    private static final byte[] MAGIC = {'B', 'L', 'K', 'D'};

    private static Application sRealApplication;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            loadProtectedDex(base);
        } catch (Throwable e) {
            Log.e(TAG, "Failed to load protected dex", e);
            throw new RuntimeException("Integrity check failed, refusing to start: "
                    + e.getClass().getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (sRealApplication != null) {
            try {
                sRealApplication.onCreate();
            } catch (Throwable t) {
                Log.e(TAG, "Real Application.onCreate() crashed", t);
                throw new RuntimeException(t);
            }
        }
    }

    private void loadProtectedDex(Context base) throws Exception {
        if (MASKED_KEY == null || ENCRYPTED_ASSETS == null || ENCRYPTED_ASSETS.length == 0) {
            throw new IllegalStateException("Loader was not initialized with build data "
                    + "(MASKED_KEY/ENCRYPTED_ASSETS null). Check BlkLoaderCompiler output.");
        }

        String packageName = base.getPackageName();
        byte[] key = rebuildKey(packageName);

        File codeCacheDir = new File(base.getCodeCacheDir(), "sdl");
        if (!codeCacheDir.exists() && !codeCacheDir.mkdirs()) {
            codeCacheDir = new File(base.getCacheDir(), "sdl");
            codeCacheDir.mkdirs();
        }
        File optDir = new File(base.getCodeCacheDir(), "sdl_opt");
        if (!optDir.exists()) optDir.mkdirs();

        List<File> dexFiles = new ArrayList<File>();
        AssetManager am = base.getAssets();

        for (String assetName : ENCRYPTED_ASSETS) {
            byte[] encrypted = readAsset(am, assetName);
            byte[] plainDex = decrypt(encrypted, key);

            if (plainDex.length < 8
                    || plainDex[0] != 0x64 || plainDex[1] != 0x65
                    || plainDex[2] != 0x78 || plainDex[3] != 0x0a) {
                throw new SecurityException("Decrypted payload is not a valid dex (wrong key or corrupt). asset="
                        + assetName + " size=" + plainDex.length);
            }

            File outFile = new File(codeCacheDir, assetName.replace(".bin", "") + ".dex");
            FileOutputStream fos = new FileOutputStream(outFile);
            try {
                fos.write(plainDex);
            } finally {
                fos.close();
            }
            outFile.setReadable(true, false);
            dexFiles.add(outFile);
            Log.d(TAG, "Decrypted " + assetName + " → " + outFile.getName()
                    + " (" + plainDex.length + " bytes)");
        }
        Arrays.fill(key, (byte) 0);

        ClassLoader hostCl = base.getClassLoader();
        installDexes(hostCl, dexFiles, optDir);

        if (REAL_APPLICATION_CLASS == null || REAL_APPLICATION_CLASS.length() == 0) {
            Log.d(TAG, "No REAL_APPLICATION_CLASS – loader is the Application");
            return;
        }

        Log.d(TAG, "Loading real Application: " + REAL_APPLICATION_CLASS);
        Class<?> realAppClass = hostCl.loadClass(REAL_APPLICATION_CLASS);
        Application realApp = (Application) realAppClass.newInstance();

        Method attach = Application.class.getDeclaredMethod("attach", Context.class);
        attach.setAccessible(true);
        attach.invoke(realApp, base);

        replaceApplication(realApp);

        sRealApplication = realApp;
        Log.d(TAG, "Real Application installed successfully");
    }

    private void installDexes(ClassLoader hostCl, List<File> dexFiles, File optDir) throws Exception {
        if (dexFiles.isEmpty()) return;

        StringBuilder path = new StringBuilder();
        for (File f : dexFiles) {
            if (path.length() > 0) path.append(File.pathSeparator);
            path.append(f.getAbsolutePath());
        }

        DexClassLoader dcl = new DexClassLoader(
                path.toString(),
                optDir.getAbsolutePath(),
                null,
                hostCl);

        Object hostPathList = getField(hostCl, "pathList");
        Object newPathList = getField(dcl, "pathList");

        Object hostDexElements = getField(hostPathList, "dexElements");
        Object newDexElements = getField(newPathList, "dexElements");

        Object merged = mergeArrays(hostDexElements, newDexElements);
        setField(hostPathList, "dexElements", merged);

        Log.d(TAG, "Injected " + dexFiles.size() + " dex into host ClassLoader");
    }

    private static Object mergeArrays(Object first, Object second) {
        Class<?> component = first.getClass().getComponentType();
        int len1 = Array.getLength(first);
        int len2 = Array.getLength(second);
        Object result = Array.newInstance(component, len1 + len2);
        System.arraycopy(first, 0, result, 0, len1);
        System.arraycopy(second, 0, result, len1, len2);
        return result;
    }

    private void replaceApplication(Application realApp) throws Exception {
        Class<?> atClass = Class.forName("android.app.ActivityThread");
        Method current = atClass.getMethod("currentActivityThread");
        Object activityThread = current.invoke(null);

        try {
            Field f = atClass.getDeclaredField("mInitialApplication");
            f.setAccessible(true);
            f.set(activityThread, realApp);
        } catch (NoSuchFieldException ignored) {}

        try {
            Field f = atClass.getDeclaredField("mAllApplications");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<Application> list = (List<Application>) f.get(activityThread);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == this) {
                        list.set(i, realApp);
                    }
                }
            }
        } catch (NoSuchFieldException ignored) {}

        try {
            Field fBound = atClass.getDeclaredField("mBoundApplication");
            fBound.setAccessible(true);
            Object loadedApk = fBound.get(activityThread);
            if (loadedApk != null) {
                Field fApp = loadedApk.getClass().getDeclaredField("mApplication");
                fApp.setAccessible(true);
                fApp.set(loadedApk, realApp);

                try {
                    Field fInfo = loadedApk.getClass().getDeclaredField("mApplicationInfo");
                    fInfo.setAccessible(true);
                    ApplicationInfo info = (ApplicationInfo) fInfo.get(loadedApk);
                    if (info != null) {
                        info.className = realApp.getClass().getName();
                    }
                } catch (Exception ignored) {}
            }
        } catch (NoSuchFieldException ignored) {}
    }

    private byte[] readAsset(AssetManager am, String name) throws IOException {
        InputStream is = am.open(name);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int n;
            while ((n = is.read(buf)) != -1) bos.write(buf, 0, n);
            return bos.toByteArray();
        } finally {
            is.close();
        }
    }

    private byte[] rebuildKey(String packageName) throws Exception {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        sha256.update(packageName.getBytes("UTF-8"));
        sha256.update(SALT);
        byte[] digest = sha256.digest();
        byte[] mask = new byte[MASKED_KEY.length];
        for (int i = 0; i < mask.length; i++) {
            mask[i] = digest[i % digest.length];
        }
        byte[] key = new byte[MASKED_KEY.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) (MASKED_KEY[i] ^ mask[i]);
        }
        return key;
    }

    private byte[] decrypt(byte[] blob, byte[] key) throws Exception {
        ByteBuffer buf = ByteBuffer.wrap(blob);
        byte[] magic = new byte[4];
        buf.get(magic);
        for (int i = 0; i < 4; i++) {
            if (magic[i] != MAGIC[i]) {
                throw new SecurityException("Bad header — asset tampered or corrupted");
            }
        }
        buf.get();
        byte[] iv = new byte[12];
        buf.get(iv);
        byte[] ciphertext = new byte[buf.remaining()];
        buf.get(ciphertext);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
        return cipher.doFinal(ciphertext);
    }

    private static Object getField(Object obj, String name) throws Exception {
        Class<?> c = obj.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField(name);
                f.setAccessible(true);
                return f.get(obj);
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name + " in " + obj.getClass());
    }

    private static void setField(Object obj, String name, Object value) throws Exception {
        Class<?> c = obj.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField(name);
                f.setAccessible(true);
                f.set(obj, value);
                return;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name + " in " + obj.getClass());
    }
}
