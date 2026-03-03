package com.tyron.compiler;

import android.content.Context;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.model.Library;
import com.apk.builder.model.Project;
import com.tyron.compiler.exception.CompilerException;

import dalvik.system.DexClassLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ProGuard Compiler wrapper that dynamically loads proguard.jar
 * from assets instead of direct import.
 *
 * Developer: NexusTeam
 */
public class ProguardCompiler extends Compiler {

    private static final String TAG = "Proguard";

    private final Project mProject;
    private File rulesFile; // ProGuard configuration file
    private File inputDir;  // Directory containing input .class files
    private File outputDir; // Directory for optimized .class files

    public ProguardCompiler(Project project) {
        this.mProject = project;
        this.rulesFile = new File(project.getProguardFile());
        this.inputDir = new File(project.getOutputFile(), "bin/classes");
        this.outputDir = new File(project.getOutputFile(), "bin/classes_proguard");
    }

    public void setRulesFile(File file) {
        if (file != null && file.exists()) {
            this.rulesFile = file;
        }
    }

    @Override
    public void prepare() {
        onProgressUpdate("ProGuard > Preparing...");
        mProject.getLogger().d(TAG, "Preparing ProGuard");

        if (!inputDir.exists() || !inputDir.isDirectory()) {
            throw new IllegalStateException("Input directory " + inputDir.getAbsolutePath() + " does not exist or is not a directory");
        }

        if (!outputDir.exists()) {
            outputDir.mkdirs();
            mProject.getLogger().d(TAG, "Created output directory: " + outputDir.getAbsolutePath());
        }

        if (!rulesFile.exists()) {
            throw new IllegalStateException("ProGuard rules file not found at: " + rulesFile.getAbsolutePath());
        }
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("ProGuard > Optimizing and obfuscating...");
        mProject.getLogger().d(TAG, "Running ProGuard on " + inputDir.getAbsolutePath());

        List<String> args = new ArrayList<>();
        args.add("-injars");
        args.add(inputDir.getAbsolutePath());
        args.add("-outjars");
        args.add(outputDir.getAbsolutePath());
        args.add("-libraryjars");
        args.add(getAndroidJarFile().getAbsolutePath());

        for (Library lib : mProject.getLibraries()) {
            File jar = lib.getClassJarFile();
            if (jar.exists()) {
                args.add("-libraryjars");
                args.add(jar.getAbsolutePath());
                mProject.getLogger().d(TAG, "Added library JAR: " + jar.getAbsolutePath());
            } else {
                mProject.getLogger().w(TAG, "Library JAR not found: " + jar.getAbsolutePath());
            }
        }

        args.add("@" + rulesFile.getAbsolutePath());
        mProject.getLogger().d(TAG, "Using rules file: " + rulesFile.getAbsolutePath());
        args.add("-dontwarn");
        args.add("-verbose");

        try {

            Context context = ApplicationLoader.applicationContext;
            File filesDir = context.getFilesDir();
            File proguardJar = new File(filesDir, "proguard.jar");


            if (!proguardJar.exists()) {
                com.apk.builder.util.Decompress.copyAsset(context, "proguard.jar", proguardJar.getAbsolutePath());
                mProject.getLogger().d(TAG, "Extracted proguard.jar to: " + proguardJar.getAbsolutePath());
            }


            DexClassLoader dexLoader = new DexClassLoader(
                    proguardJar.getAbsolutePath(),
                    context.getCacheDir().getAbsolutePath(),
                    null,
                    getClass().getClassLoader()
            );


            Class<?> proguardClass = dexLoader.loadClass("proguard.ProGuard");
            Method mainMethod = proguardClass.getMethod("main", String[].class);

            mProject.getLogger().d(TAG, "Executing ProGuard with args: " + args);


            mainMethod.invoke(null, (Object) args.toArray(new String[0]));

            mProject.getLogger().d(TAG, "ProGuard completed successfully");
            onProgressUpdate("ProGuard > Optimization complete");

        } catch (Exception e) {
            mProject.getLogger().e(TAG, "ProGuard failed: " + e.getMessage());
            throw new CompilerException("ProGuard error: " + e.getMessage());
        }

        if (outputDir.listFiles() == null || outputDir.listFiles().length == 0) {
            throw new CompilerException("ProGuard produced no output in " + outputDir.getAbsolutePath());
        }
    }
}
