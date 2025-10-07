package com.apk.builder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.util.Log;

/**
 * Utility class for handling asset extraction and zip decompression.
 * 
 * Developer: NexusTeam
 */
public class Decompress {
    private static final int BUFFER_SIZE = 1024 * 10;
    private static final String TAG = "Decompress";

    /**
     * Extracts a ZIP file stored in assets to a destination directory.
     */
    public static void unzipFromAssets(Context context, String zipFile, String destination) {
        try {
            if (destination == null || destination.length() == 0) {
                destination = context.getFilesDir().getAbsolutePath();
            }
            InputStream stream = context.getAssets().open(zipFile);
            unzip(stream, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Copies a single asset file (non-zip) to a given output path.
     * Useful for extracting JAR files like proguard.jar from assets.
     */
    public static void copyAsset(Context context, String assetName, String outPath) throws IOException {
        try (InputStream in = context.getAssets().open(assetName);
             OutputStream out = new FileOutputStream(outPath)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
    }

    /**
     * Extracts a ZIP file from a file path.
     */
    public static void unzip(String zipFile, String location) {
        try {
            FileInputStream fin = new FileInputStream(zipFile);
            unzip(fin, location);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts a ZIP file from an InputStream.
     */
    public static void unzip(InputStream stream, String destination) {
        dirChecker(destination, "");
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            ZipInputStream zin = new ZipInputStream(stream);
            ZipEntry ze;

            while ((ze = zin.getNextEntry()) != null) {
                Log.v(TAG, "Unzipping " + ze.getName());

                if (ze.isDirectory()) {
                    dirChecker(destination, ze.getName());
                } else {
                    File f = new File(destination, ze.getName());
                    if (!f.exists()) {
                        boolean success = f.createNewFile();
                        if (!success) {
                            Log.w(TAG, "Failed to create file " + f.getName());
                            continue;
                        }
                        FileOutputStream fout = new FileOutputStream(f);
                        int count;
                        while ((count = zin.read(buffer)) != -1) {
                            fout.write(buffer, 0, count);
                        }
                        zin.closeEntry();
                        fout.close();
                    }
                }
            }
            zin.close();
        } catch (Exception e) {
            Log.e(TAG, "unzip", e);
        }
    }

    /**
     * Ensures a directory exists, creates it if missing.
     */
    private static void dirChecker(String destination, String dir) {
        File f = new File(destination, dir);
        if (!f.isDirectory()) {
            boolean success = f.mkdirs();
            if (!success) {
                Log.w(TAG, "Failed to create folder " + f.getName());
            }
        }
    }
}
