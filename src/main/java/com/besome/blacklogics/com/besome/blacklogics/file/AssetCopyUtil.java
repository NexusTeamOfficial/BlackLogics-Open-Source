package com.besome.blacklogics.file;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetCopyUtil {

    public static void copyAssetsToExternalStorage(Context context, String assetFolder, String customDirName) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;

        // Get list of files in the assets folder
        try {
            files = assetManager.list(assetFolder);
        } catch (IOException e) {
            //Log.e("AssetCopy", "Failed to get asset file list.", e);
            return;
        }

        if (files == null || files.length == 0) {
            //Log.e("AssetCopy", "No files found in assets folder: " + assetFolder);
            return;
        }

        // Get external storage directory (/storage/emulated/0/)
        File externalDir = new File(Environment.getExternalStorageDirectory(), customDirName);
        if (!externalDir.exists()) {
            if (!externalDir.mkdirs()) {
                //Log.e("AssetCopy", "Failed to create directory: " + externalDir.getAbsolutePath());
                return;
            }
        }

        // Copy each file
        for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(assetFolder + "/" + filename);
                File outFile = new File(externalDir, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                Log.i("AssetCopy", "Copied: " + filename);
            } catch (IOException e) {
                //Log.e("AssetCopy", "Failed to copy asset file: " + filename, e);
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                } catch (IOException e) {
                    //Log.e("AssetCopy", "Error closing streams", e);
                }
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}