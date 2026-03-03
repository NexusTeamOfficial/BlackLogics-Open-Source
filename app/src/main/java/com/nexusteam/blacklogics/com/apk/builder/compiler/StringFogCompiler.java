package com.tyron.compiler;

import com.tyron.compiler.exception.CompilerException;
import com.apk.builder.model.Project;
import com.apk.builder.FileUtil;
import com.apk.builder.util.Decompress;
import com.apk.builder.ApplicationLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class StringFogCompiler extends Compiler {

    private static final String TAG = "StringFog";
    private Project mProject;
    private File tempDir;

    public StringFogCompiler(Project project) {
        mProject = project;
        tempDir = new File(ApplicationLoader.applicationContext.getFilesDir(), "temp/stringfog");
    }

    @Override
    public void prepare() throws CompilerException {
        mProject.getLogger().d(TAG, "Preparing StringFog compiler");
        onProgressUpdate("Preparing StringFog...");


        FileUtil.makeDir(tempDir.getAbsolutePath());


        File stringFogImpl = new File(tempDir, "com/github/megatronking/stringfog/StringFogImpl.class");
        if (!stringFogImpl.exists()) {
            try {

                Decompress.unzipFromAssets(
                    ApplicationLoader.applicationContext,
                    "stringfog.zip",
                    tempDir.getAbsolutePath()
                );
                mProject.getLogger().d(TAG, "Extracted stringfog.zip to " + tempDir.getAbsolutePath());
            } catch (Exception e) {
                throw new CompilerException("Failed to extract stringfog.zip: " + e.getMessage());
            }
        } else {
            mProject.getLogger().d(TAG, "StringFog classes already exist in " + tempDir.getAbsolutePath());
        }
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("StringFog > Processing DEX files...");
        mProject.getLogger().d(TAG, "Running StringFog on DEX files");

        try {

            URLClassLoader classLoader = new URLClassLoader(
                new URL[]{tempDir.toURI().toURL()},
                this.getClass().getClassLoader()
            );


            Class<?> stringFogImplClass = classLoader.loadClass("com.github.megatronking.stringfog.StringFogImpl");




            Method processDexMethod;
            try {
                processDexMethod = stringFogImplClass.getDeclaredMethod("processDex", File.class, File.class);
            } catch (NoSuchMethodException e) {
                mProject.getLogger().w(TAG, "No DEX processing method found in StringFog. Reverting to warning.");
                throw new CompilerException("StringFog does not support DEX file processing. Consider running before D8Compiler.");
            }


            File dexDir = new File(mProject.getOutputFile(), "bin");
            File outputDir = new File(mProject.getOutputFile(), "bin/stringfog_output");
            FileUtil.makeDir(outputDir.getAbsolutePath());


            for (File dexFile : getDexFiles(dexDir)) {
                mProject.getLogger().d(TAG, "Processing: " + dexFile.getName());
                processDexMethod.invoke(null, dexFile, new File(outputDir, dexFile.getName()));
            }


            FileUtil.copyDir(outputDir.getAbsolutePath(), dexDir.getAbsolutePath());
            mProject.getLogger().d(TAG, "Copied processed DEX files to " + dexDir.getAbsolutePath());

            classLoader.close();
        } catch (Exception e) {
            throw new CompilerException("StringFog DEX processing failed: " + e.getMessage());
        }
    }

    private List<File> getDexFiles(File dir) {
        List<File> files = new ArrayList<>();
        File[] childs = dir.listFiles();
        if (childs != null) {
            for (File child : childs) {
                if (child.isDirectory()) {
                    files.addAll(getDexFiles(child));
                } else if (child.getName().endsWith(".dex")) {
                    files.add(child);
                }
            }
        }
        return files;
    }
}