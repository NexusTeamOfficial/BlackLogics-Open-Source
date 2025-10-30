/**
 * A utility class for directly compiling resources from multiple custom directories.
 * <p>
 * This class compiles Android resources (e.g., drawables, layouts) from specified directories
 * into a zip file compatible with AAPT2, similar to how JsonParser processes JSON into Library objects.
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
package com.besome.blacklogics.resource_compiler;

import com.apk.builder.model.Project;
import com.apk.builder.BinaryExecutor;
import com.tyron.compiler.exception.CompilerException;

import com.tyron.compiler.*;
import com.apk.builder.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResourceCompiler {

    // Valid resource directory names as per Android resource structure
    private static final Set<String> VALID_RESOURCE_DIRS = new HashSet<>(Arrays.asList(
        "anim", "animator", "color", "drawable", "font", "layout", "menu", "mipmap",
        "navigation", "raw", "transition", "values", "xml"
    ));

    /**
     * Compiles resources from multiple custom directories into a zip file for the project.
     * <p>
     * This method validates the resource directory structure for each provided path and uses AAPT2
     * to compile resources into a single zip file in the project's bin/res directory.
     * </p>
     *
     * @param project The project to compile resources for
     * @param customResourceDirPaths List of paths to custom resource directories
     * @return The compiled resource zip file, or null if no valid resources are found
     * @throws CompilerException If there is an error during compilation
     * @throws IOException If there is an error accessing directories or files
     */
    public static File compileResources(Project project, List<String> customResourceDirPaths) throws CompilerException, IOException {
        if (project == null) {
            throw new CompilerException("Project cannot be null");
        }
        if (customResourceDirPaths == null || customResourceDirPaths.isEmpty()) {
            project.getLogger().w("ResourceCompiler", "No resource directory paths provided");
            return null;
        }

        // Validate directories and collect valid ones
        List<File> validDirs = new ArrayList<>();
        for (String path : customResourceDirPaths) {
            if (path == null || path.trim().isEmpty()) {
                project.getLogger().w("ResourceCompiler", "Skipping null or empty directory path");
                continue;
            }
            project.getLogger().d("ResourceCompiler", "Checking directory: " + path);
            File dir;
            try {
                dir = new File(path);
            } catch (Exception e) {
                project.getLogger().w("ResourceCompiler", "Invalid path format: " + path + ", error: " + e.getMessage());
                continue;
            }
            if (!isValidDirectory(project, dir)) {
                continue;
            }
            if (hasValidResources(project, dir)) {
                validDirs.add(dir);
                project.getLogger().d("ResourceCompiler", "Valid resources found in: " + path);
            } else {
                project.getLogger().w("ResourceCompiler", "No valid resources found in directory: " + path);
            }
        }

        if (validDirs.isEmpty()) {
            project.getLogger().w("ResourceCompiler", "No valid resource directories found across provided paths");
            return null;
        }

        // Prepare output directory
        File binDir = new File(project.getOutputFile(), "bin");
        File resPath = new File(binDir, "res");
        if (!resPath.exists() && !resPath.mkdirs()) {
            project.getLogger().e("ResourceCompiler", "Failed to create resource output directory: " + resPath.getAbsolutePath());
            throw new IOException("Failed to create resource output directory: " + resPath.getAbsolutePath());
        }

        // Compile resources from each valid directory
        File outputZip = createNewFile(resPath, "custom_resources.zip");
        for (File dir : validDirs) {
            compileWithAAPT2(project, dir, outputZip);
        }

        if (!outputZip.exists() || outputZip.length() == 0) {
            project.getLogger().w("ResourceCompiler", "No resources were compiled into: " + outputZip.getAbsolutePath());
            return null;
        }

        project.getLogger().d("ResourceCompiler", "Compiled resources to: " + outputZip.getAbsolutePath());
        return outputZip;
    }

    /**
     * Validates that a directory exists, is a directory, and is readable.
     *
     * @param project The project for logging
     * @param dir The directory to validate
     * @return True if the directory is valid
     */
    private static boolean isValidDirectory(Project project, File dir) {
        if (dir == null) {
            project.getLogger().w("ResourceCompiler", "Directory is null");
            return false;
        }
        if (!dir.exists()) {
            project.getLogger().w("ResourceCompiler", "Directory does not exist: " + dir.getAbsolutePath());
            return false;
        }
        if (!dir.isDirectory()) {
            project.getLogger().w("ResourceCompiler", "Path is not a directory: " + dir.getAbsolutePath());
            return false;
        }
        if (!dir.canRead()) {
            project.getLogger().w("ResourceCompiler", "Directory is not readable: " + dir.getAbsolutePath());
            return false;
        }
        return true;
    }

    /**
     * Checks if the directory contains valid Android resource directories.
     *
     * @param project The project for logging
     * @param dir The directory to check
     * @return True if valid resource directories are found
     */
    private static boolean hasValidResources(Project project, File dir) {
        if (!isValidDirectory(project, dir)) {
            return false;
        }
        try {
            File[] files = dir.listFiles();
            if (files == null) {
                project.getLogger().w("ResourceCompiler", "Unable to list files in directory: " + dir.getAbsolutePath());
                return false;
            }
            for (File file : files) {
                if (file.isDirectory() && isValidResourceDir(file.getName())) {
                    project.getLogger().d("ResourceCompiler", "Found valid resource dir: " + file.getName() + " in " + dir.getAbsolutePath());
                    return true;
                }
            }
            return false;
        } catch (SecurityException e) {
            project.getLogger().w("ResourceCompiler", "Permission denied accessing directory: " + dir.getAbsolutePath() + ", error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            project.getLogger().w("ResourceCompiler", "Error accessing directory: " + dir.getAbsolutePath() + ", error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the directory name is a valid Android resource directory.
     *
     * @param dirName The name of the directory
     * @return True if the directory is a valid resource directory
     */
    private static boolean isValidResourceDir(String dirName) {
        if (dirName == null) {
            return false;
        }
        String baseDirName = dirName.split("-")[0];
        return VALID_RESOURCE_DIRS.contains(baseDirName);
    }

    /**
     * Compiles resources using AAPT2 binary, appending to the output zip.
     *
     * @param project The project for logging
     * @param inputDir The input resource directory
     * @param outputZip The output zip file
     * @throws CompilerException If AAPT2 compilation fails
     * @throws IOException If file operations fail
     */
    private static void compileWithAAPT2(Project project, File inputDir, File outputZip) throws CompilerException, IOException {
        ArrayList<String> args = new ArrayList<>();
        BinaryExecutor executor = new BinaryExecutor();

        // Get AAPT2 binary
        File aapt2File = getAAPT2File();
        args.add(aapt2File.getAbsolutePath());
        args.add("compile");
        args.add("--dir");
        args.add(inputDir.getAbsolutePath());
        args.add("-o");
        args.add(outputZip.getAbsolutePath());

        executor.setCommands(args);
        String result = executor.execute();
        if (!result.isEmpty()) {
            project.getLogger().e("ResourceCompiler", "AAPT2 compilation failed for " + inputDir.getAbsolutePath() + ": " + result);
            throw new CompilerException("AAPT2 compilation failed: " + result);
        }
        project.getLogger().d("ResourceCompiler", "Compiled resources from: " + inputDir.getAbsolutePath());
    }

    /**
     * Creates a new file in the specified directory.
     *
     * @param parent The parent directory
     * @param name The file name
     * @return The created file
     * @throws IOException If file creation fails
     */
    private static File createNewFile(File parent, String name) throws IOException {
        if (parent == null || name == null) {
            throw new IOException("Parent directory or file name cannot be null");
        }
        File createdFile = new File(parent, name);
        parent.mkdirs();
        if (!createdFile.exists()) {
            createdFile.createNewFile();
        }
        return createdFile;
    }

    /**
     * Retrieves the AAPT2 binary file.
     *
     * @return The AAPT2 binary file
     * @throws CompilerException If the binary is not found
     */
    private static File getAAPT2File() throws CompilerException {
        File nativeLibrary = new File(
            com.apk.builder.ApplicationLoader.applicationContext.getApplicationInfo().nativeLibraryDir + "/libaapt2.so"
        );
        if (!nativeLibrary.exists()) {
            throw new CompilerException("AAPT2 binary not found");
        }
        return nativeLibrary;
    }
}