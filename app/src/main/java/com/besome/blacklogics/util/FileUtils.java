package com.besome.blacklogics.util;

import android.content.Context;
import android.os.Environment;
import java.io.*;
import java.util.zip.*;

public class FileUtils {

    // Existing extractZipFromAssets method
    public static void extractZipFromAssets(Context context, String assetZipName, String outputDirName) throws IOException {
        InputStream is = context.getAssets().open(assetZipName);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));

        File outputDir = new File(Environment.getExternalStorageDirectory(), outputDirName);
        if (!outputDir.exists()) outputDir.mkdirs();

        ZipEntry entry;
        byte[] buffer = new byte[1024];

        while ((entry = zis.getNextEntry()) != null) {
            File outFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                outFile.mkdirs();
            } else {
                File parent = outFile.getParentFile();
                if (!parent.exists()) parent.mkdirs();

                FileOutputStream fos = new FileOutputStream(outFile);
                int count;
                while ((count = zis.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
            }
            zis.closeEntry();
        }

        zis.close();
        is.close();
    }

    /**
     * Writes a String content into a file.
     *
     * @param file    The file to write into
     * @param content The string content to write
     * @throws IOException
     */
    public static void writeStringToFile(File file, String content) throws IOException {
        if (file == null) return;

        // Ensure parent directories exist
        File parent = file.getParentFile();
        if (!parent.exists()) parent.mkdirs();

        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
