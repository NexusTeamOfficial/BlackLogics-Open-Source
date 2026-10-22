package com.nexusteam.blacklogics.resource_compiler;

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


    private static final Set<String> VALID_RESOURCE_DIRS = new HashSet<>(Arrays.asList(
        "anim", "animator", "color", "drawable", "font", "layout", "menu", "mipmap",
        "navigation", "raw", "transition", "values", "xml"
    ));

    public static File compileResources(Project project, List<String> customResourceDirPaths) throws CompilerException, IOException {
        if (project == null) {
            throw new CompilerException("Project cannot be null");
        }
        if (customResourceDirPaths == null || customResourceDirPaths.isEmpty()) {
            project.getLogger().w("ResourceCompiler", "No resource directory paths provided");
            return null;
        }


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


        File binDir = new File(project.getOutputFile(), "bin");
        File resPath = new File(binDir, "res");
        if (!resPath.exists() && !resPath.mkdirs()) {
            project.getLogger().e("ResourceCompiler", "Failed to create resource output directory: " + resPath.getAbsolutePath());
            throw new IOException("Failed to create resource output directory: " + resPath.getAbsolutePath());
        }


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

    private static boolean isValidResourceDir(String dirName) {
        if (dirName == null) {
            return false;
        }
        String baseDirName = dirName.split("-")[0];
        return VALID_RESOURCE_DIRS.contains(baseDirName);
    }

    private static void compileWithAAPT2(Project project, File inputDir, File outputZip) throws CompilerException, IOException {
        ArrayList<String> args = new ArrayList<>();
        BinaryExecutor executor = new BinaryExecutor();


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