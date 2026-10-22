package com.nexusteam.blacklogics.file;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyUtil {

    public static boolean copyFile(String sourcePath, String destinationPath) {
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            File sourceFile = new File(sourcePath);
            File destinationFile = new File(destinationPath);


            if (!destinationFile.getParentFile().exists()) {
                destinationFile.getParentFile().mkdirs();
            }

            inStream = new FileInputStream(sourceFile);
            outStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inStream != null) inStream.close();
                if (outStream != null) outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getExternalStoragePath() {
        return "/storage/emulated/0/";
    }

    public static String getInternalStoragePath(android.content.Context context) {
        return context.getFilesDir().getAbsolutePath();
    }
}
