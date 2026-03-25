package com.tyron.compiler;

import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.CompilerException;
import com.android.tools.r8.R8;
import com.android.tools.r8.R8Command;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.origin.Origin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class R8Compiler extends Compiler {
    private static final String TAG = "R8";
    private final Project mProject;

    public R8Compiler(Project project) {
        this.mProject = project;
    }

    @Override
    public void prepare() throws CompilerException {
        onProgressUpdate("R8 > Preparing...");
        mProject.getLogger().d(TAG, "Preparing R8 compilation");


        File outputDir = new File(mProject.getOutputFile(), "bin");
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new CompilerException("Failed to create output directory: " + outputDir.getAbsolutePath());
        }


        File androidJar = getAndroidJarFile();
        if (!androidJar.exists()) {
            throw new CompilerException("android.jar not found: " + androidJar.getAbsolutePath());
        }


        File classDir = new File(mProject.getOutputFile(), "bin/classes_proguard");
        if (!classDir.exists()) {
            classDir = new File(mProject.getOutputFile(), "bin/classes");
        }
        if (!classDir.exists() || getClassFiles(classDir).isEmpty()) {
            throw new CompilerException("No class files found in: " + classDir.getAbsolutePath());
        }
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("R8 > Optimizing class files...");
        mProject.getLogger().d(TAG, "Running R8 compilation");

        try {

            File classDir = new File(mProject.getOutputFile(), "bin/classes_proguard");
            if (!classDir.exists()) {
                classDir = new File(mProject.getOutputFile(), "bin/classes");
            }
            List<File> classFileList = getClassFiles(classDir);
            if (classFileList.isEmpty()) {
                throw new CompilerException("No class files found in: " + classDir.getAbsolutePath());
            }
            mProject.getLogger().d(TAG, "Found " + classFileList.size() + " class files");


            List<Path> classFiles = new ArrayList<>();
            for (File file : classFileList) {
                classFiles.add(file.toPath());
            }


            List<Path> libraryFiles = new ArrayList<>();
            libraryFiles.add(getAndroidJarFile().toPath());
            for (Library library : mProject.getLibraries()) {
                File classJar = library.getClassJarFile();
                if (classJar.exists()) {
                    libraryFiles.add(classJar.toPath());
                    mProject.getLogger().d(TAG, "Added library JAR: " + classJar.getAbsolutePath());
                }
            }


            Path outputPath = Paths.get(mProject.getOutputFile().getAbsolutePath(), "bin", "classes.dex");
            Files.createDirectories(outputPath.getParent());


            List<Path> proguardRules = new ArrayList<>();
            File proguardFile = new File(mProject.getProguardFile());
            if (proguardFile.exists()) {
                proguardRules.add(proguardFile.toPath());
                mProject.getLogger().d(TAG, "Using ProGuard rules: " + proguardFile.getAbsolutePath());
            } else {
                mProject.getLogger().w(TAG, "ProGuard rules file not found: " + proguardFile.getAbsolutePath());
            }


            R8Command.Builder builder = R8Command.builder()
                    .addProgramFiles(classFiles)
                    .addLibraryFiles(libraryFiles)
                    .setMinApiLevel(mProject.getMinSdk())
                    .setOutput(outputPath, OutputMode.DexIndexed)
                    .setMode(com.android.tools.r8.CompilationMode.RELEASE);

            if (!proguardRules.isEmpty()) {
                builder.addProguardConfigurationFiles(proguardRules);
            }


            R8.run(builder.build());
            mProject.getLogger().d(TAG, "R8 compilation completed successfully. Output: " + outputPath);





        } catch (Exception e) {
            String errorMsg = "R8 compilation failed: " + e.getMessage();
            mProject.getLogger().e(TAG, errorMsg);
            throw new CompilerException(errorMsg);
        }
    }

    private List<File> getClassFiles(File dir) {
        List<File> files = new ArrayList<>();
        if (!dir.exists() || !dir.isDirectory()) {
            mProject.getLogger().w(TAG, "Class directory not found: " + dir.getAbsolutePath());
            return files;
        }

        File[] fileArr = dir.listFiles();
        if (fileArr == null) {
            mProject.getLogger().w(TAG, "No files found in class directory: " + dir.getAbsolutePath());
            return files;
        }

        for (File file : fileArr) {
            if (file.isDirectory()) {
                files.addAll(getClassFiles(file));
            } else if (file.getName().endsWith(".class")) {
                files.add(file);
            }
        }
        return files;
    }
}