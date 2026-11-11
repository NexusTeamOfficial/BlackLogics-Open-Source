package com.nexusteam.blacklogics.utils;

import java.util.ArrayList;

import com.nexusteam.blacklogics.utils.FileUtil;

public class AssetFileUtils {

    public static ArrayList<String> listFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            FileUtil.listDir(path, files);
        }
        return files;
    }

    public static boolean isImageFile(String path) {
        return FileUtil.isImageFile(path);
    }

    public static boolean isDirectory(String path) {
        return FileUtil.isDirectory(path);
    }

    public static String getFileNameFromPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}