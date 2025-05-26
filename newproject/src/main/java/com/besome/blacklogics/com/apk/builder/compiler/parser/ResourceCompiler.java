/**
 * A utility class for compiling resources from a custom directory into a project.
 * <p>
 * This class provides methods to copy and validate resources (such as drawables, layouts, etc.)
 * from a specified custom directory into the project's resource directory, ensuring compatibility
 * with the AAPT2 compiler.
 * </p>
 *
 * @author NexusTeam
 * @version 1.0
 * @since 1.0
 * @see Project
 */
/*
 * This is free and unencumbered software released into the public domain.
 * 
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to <http://unlicense.org>
 * 
 * Note: When using this class, please credit @NexusTeam or @SmartIndiaGaming
 */
package com.besome.blacklogics.parser;

import com.apk.builder.model.Project;
import com.apk.builder.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResourceCompiler {

    // Valid resource directory names as per Android resource structure
    private static final Set<String> VALID_RESOURCE_DIRS = new HashSet<>(Arrays.asList(
        "anim", "animator", "color", "drawable", "font", "layout", "menu", "mipmap",
        "navigation", "raw", "transition", "values", "xml"
    ));

    /**
     * Compiles resources from a custom directory into the project's resource directory.
     * <p>
     * This method validates the resource directory structure, copies valid resources to the
     * project's resource directory, and ensures compatibility with AAPT2 compilation.
     * </p>
     *
     * @param project The project to compile resources for
     * @param customResourceDirPath The path to the custom resource directory
     * @throws IOException If there is an error copying resources or accessing directories
     */
    public static void compileResources(Project project, String customResourceDirPath) throws IOException {
      /*  if (project == null || customResourceDirPath == null || customResourceDirPath.isEmpty()) {
            throw new IOException("Project or custom resource directory path cannot be null or empty");
        }

        File customDir = new File(customResourceDirPath);
        if (!customDir.exists() || !customDir.isDirectory()) {
            throw new IOException("Custom resource directory does not exist or is not a directory: " + customResourceDirPath);
        }
/*
        File projectResDir = project.getResourcesFiles();
        if (projectResDir == null) {
            throw new IOException("Project resource directory is not set");
        }*/
/*
        // Create project resource directory if it doesn't exist
        if (!projectResDir.exists() && !projectResDir.mkdirs()) {
            throw new IOException("Failed to create project resource directory: " + projectResDir.getAbsolutePath());
        }

        // Process resources
        copyValidResources(customDir, projectResDir, project);*/
    }

    /**
     * Recursively copies valid resources from source to destination directory.
     *
     * @param sourceDir The source directory containing resources
     * @param destDir The destination directory (project's resource directory)
     * @param project The project for logging purposes
     * @throws IOException If there is an error during copying
     */
    private static void copyValidResources(File sourceDir, File destDir, Project project) throws IOException {
        File[] files = sourceDir.listFiles();
        if (files == null) {
            project.getLogger().w("ResourceCompiler", "No resources found in directory: " + sourceDir.getAbsolutePath());
            return;
        }

        for (File file : files) {
            File destFile = new File(destDir, file.getName());

            // Validate resource directories
            if (file.isDirectory()) {
                String dirName = file.getName();
                // Check if it's a valid resource directory Ascertain if the directory name is valid
                if (isValidResourceDir(dirName)) {
                    // Create destination directory
                    if (!destFile.exists() && !destFile.mkdirs()) {
                        throw new IOException("Failed to create resource directory: " + destFile.getAbsolutePath());
                    }
                    // Recursive copy for valid resource directories
                    copyValidResources(file, destFile, project);
                } else {
                    project.getLogger().w("ResourceCompiler", "Skipping invalid resource directory: " + dirName);
                }
            } else {
                // Validate and copy individual resource files
                if (isValidResourceFile(file)) {
                    FileUtil.copyFile(file.getAbsolutePath(), destFile.getAbsolutePath());
                    project.getLogger().d("ResourceCompiler", "Copied resource: " + file.getName() + " to " + destFile.getAbsolutePath());
                } else {
                    project.getLogger().w("ResourceCompiler", "Skipping invalid resource file: " + file.getName());
                }
            }
        }
    }

    /**
     * Checks if the directory name is a valid Android resource directory.
     *
     * @param dirName The name of the directory
     * @return True if the directory is a valid resource directory
     */
    private static boolean isValidResourceDir(String dirName) {
        // Handle qualified resource directories (e.g., drawable-hdpi, values-en)
        String baseDirName = dirName.split("-")[0];
        return VALID_RESOURCE_DIRS.contains(baseDirName);
    }

    /**
     * Validates if the file is a valid resource file based on its extension or type.
     *
     * @param file The file to validate
     * @return True if the file is a valid resource
     */
    private static boolean isValidResourceFile(File file) {
        if (!file.exists() || !file.canRead()) {
            return false;
        }

        String name = file.getName().toLowerCase();
        // Common resource file extensions
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