package com.nexusteam.blacklogics.parser;

import com.apk.builder.model.Project;
import com.apk.builder.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResourceCompiler {


    private static final Set<String> VALID_RESOURCE_DIRS = new HashSet<>(Arrays.asList(
        "anim", "animator", "color", "drawable", "font", "layout", "menu", "mipmap",
        "navigation", "raw", "transition", "values", "xml"
    ));

    public static void compileResources(Project project, String customResourceDirPath) throws IOException {
    }

    private static void copyValidResources(File sourceDir, File destDir, Project project) throws IOException {
        File[] files = sourceDir.listFiles();
        if (files == null) {
            project.getLogger().w("ResourceCompiler", "No resources found in directory: " + sourceDir.getAbsolutePath());
            return;
        }

        for (File file : files) {
            File destFile = new File(destDir, file.getName());


            if (file.isDirectory()) {
                String dirName = file.getName();

                if (isValidResourceDir(dirName)) {

                    if (!destFile.exists() && !destFile.mkdirs()) {
                        throw new IOException("Failed to create resource directory: " + destFile.getAbsolutePath());
                    }

                    copyValidResources(file, destFile, project);
                } else {
                    project.getLogger().w("ResourceCompiler", "Skipping invalid resource directory: " + dirName);
                }
            } else {

                if (isValidResourceFile(file)) {
                    FileUtil.copyFile(file.getAbsolutePath(), destFile.getAbsolutePath());
                    project.getLogger().d("ResourceCompiler", "Copied resource: " + file.getName() + " to " + destFile.getAbsolutePath());
                } else {
                    project.getLogger().w("ResourceCompiler", "Skipping invalid resource file: " + file.getName());
                }
            }
        }
    }

    private static boolean isValidResourceDir(String dirName) {

        String baseDirName = dirName.split("-")[0];
        return VALID_RESOURCE_DIRS.contains(baseDirName);
    }

    private static boolean isValidResourceFile(File file) {
        if (!file.exists() || !file.canRead()) {
            return false;
        }

        String name = file.getName().toLowerCase();

        return name.endsWith(".xml") ||
               name.endsWith(".png") ||
               name.endsWith(".jpg") ||
               name.endsWith(".jpeg") ||
               name.endsWith(".gif") ||
               name.endsWith(".webp") ||
               name.endsWith(".mp3") ||
               name.endsWith(".ogg") ||
               name.endsWith(".wav") ||
               name.endsWith(".ttf") ||
               name.endsWith(".otf");
    }
}