package com.apk.builder.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        if (destination == null || destination.isEmpty()) {
            destination = context.getFilesDir().getAbsolutePath();
        }

        Log.d(TAG, "Unzipping from assets: " + zipFile + " to " + destination);

        try (InputStream stream = context.getAssets().open(zipFile)) {
            unzip(stream, destination);
        } catch (IOException e) {
            Log.e(TAG, "Error unzipping from assets", e);
        }
    }

    /**
     * Copies a single asset file (non-zip) to a given output path.
     */
    public static void copyAsset(Context context, String assetName, String outPath) {
        try (InputStream in = context.getAssets().open(assetName);
             OutputStream out = new FileOutputStream(outPath)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error copying asset: " + assetName, e);
        }
    }

    /**
     * Extracts a ZIP file from a file path.
     */
    public static void unzip(String zipFile, String location) {
        try (FileInputStream fin = new FileInputStream(zipFile)) {
            unzip(fin, location);
        } catch (IOException e) {
            Log.e(TAG, "Error unzipping file: " + zipFile, e);
        }
    }

    /**
     * Extracts a ZIP file from an InputStream.
     */
    public static void unzip(InputStream stream, String destination) {
        Log.d(TAG, "Starting unzip to: " + destination);

        try {
            File destDir = new File(destination);
            if (!destDir.exists()) {
                if (!destDir.mkdirs()) {
                    throw new IOException("Failed to create destination directory: " + destination);
                }
            }

            byte[] buffer = new byte[BUFFER_SIZE];
            try (ZipInputStream zin = new ZipInputStream(stream)) {
                ZipEntry ze;
                while ((ze = zin.getNextEntry()) != null) {
                    Log.d(TAG, "Extracting: " + ze.getName());

                    File outFile = new File(destination, ze.getName());

                    if (ze.isDirectory()) {
                        if (!outFile.exists() && !outFile.mkdirs()) {
                            Log.w(TAG, "Failed to create directory: " + outFile.getAbsolutePath());
                        }
                    } else {
                        File parent = outFile.getParentFile();
                        if (parent != null && !parent.exists() && !parent.mkdirs()) {
                            Log.w(TAG, "Failed to create parent directory: " + parent.getAbsolutePath());
                        }

                        try (FileOutputStream fout = new FileOutputStream(outFile)) {
                            int count;
                            while ((count = zin.read(buffer)) != -1) {
                                fout.write(buffer, 0, count);
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Error writing file: " + outFile.getAbsolutePath(), e);
                        }
                    }
                    zin.closeEntry();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error during unzip", e);
        }

        Log.d(TAG, "Unzip complete: " + destination);
    }
}
