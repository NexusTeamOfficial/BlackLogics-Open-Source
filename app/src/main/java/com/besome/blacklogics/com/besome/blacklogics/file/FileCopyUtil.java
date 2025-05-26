package com.besome.blacklogics.file;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyUtil {

    /**
     * Copy file from source to destination
     *
     * @param sourcePath Full path of the source file
     * @param destinationPath Full path of the destination file
     * @return true if file copied successfully, false otherwise
     */
    public static boolean copyFile(String sourcePath, String destinationPath) {
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            File sourceFile = new File(sourcePath);
            File destinationFile = new File(destinationPath);

            // Create parent directories if they do not exist
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

    /**
     * Get path to external storage directory (SDCard or internal shared storage)
     */
    public static String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Get path to internal app-specific storage directory
     */
    public static String getInternalStoragePath(android.content.Context context) {
        return context.getFilesDir().getAbsolutePath();
    }
}
