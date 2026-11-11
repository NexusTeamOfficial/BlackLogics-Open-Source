package com.nexusteam.blacklogics.project.encryption;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtil {

    public static void unzipFromAssets(Context context, String zipFileName, String outputDirectory) throws IOException {
        InputStream is = context.getAssets().open(zipFileName);
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            File outFile = new File(outputDirectory, entry.getName());
            if (entry.isDirectory()) {
                if (!outFile.exists()) {
                    outFile.mkdirs();
                }
            } else {
                File parent = outFile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zis.closeEntry();
        }
        zis.close();
        is.close();
    }
}
