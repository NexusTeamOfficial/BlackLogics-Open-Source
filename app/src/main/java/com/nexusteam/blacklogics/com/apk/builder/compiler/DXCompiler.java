package com.tyron.compiler;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.util.Decompress;
import com.tyron.compiler.exception.CompilerException;
import java.io.File;
import java.io.IOException;

public class DXCompiler extends Compiler {

    private static final String TAG = "DX";
    private final Project mProject;

    public DXCompiler(Project project) {
        this.mProject = project;
    }

    @Override
    public void prepare() throws CompilerException {
        onProgressUpdate("DX > Initializing...");
        mProject.getLogger().d(TAG, "Preparing DX compiler...");

        getDxJarFile();
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("DX > Converting to DEX");
        mProject.getLogger().d(TAG, "Starting DEX conversion with dx-27.0.0.jar...");

        File outputDex = new File(mProject.getOutputFile(), "bin/classes.dex");
        File classesDir = new File(mProject.getOutputFile(), "bin/classes");
        File dxJar = getDxJarFile();

        try {

            File outputDir = outputDex.getParentFile();
            if (!outputDir.exists() && !outputDir.mkdirs()) {
                throw new CompilerException("Failed to create output directory: " + outputDir.getAbsolutePath());
            }


            String[] command = {
                "java",
                "-jar",
                dxJar.getAbsolutePath(),
                "--dex",
                "--output=" + outputDex.getAbsolutePath(),
                classesDir.getAbsolutePath()
            };
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Combine stdout and stderr
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new CompilerException("DX process failed for classes with exit code: " + exitCode);
            }


            for (Library library : mProject.getLibraries()) {
                File jarFile = library.getClassJarFile();
                if (jarFile.exists()) {
                    command = new String[]{
                        "java",
                        "-jar",
                        dxJar.getAbsolutePath(),
                        "--dex",
                        "--output=" + outputDex.getAbsolutePath(),
                        jarFile.getAbsolutePath()
                    };
                    processBuilder = new ProcessBuilder(command);
                    processBuilder.redirectErrorStream(true);
                    process = processBuilder.start();
                    exitCode = process.waitFor();
                    if (exitCode != 0) {
                        throw new CompilerException("DX process failed for library " + jarFile.getName() + ": " + exitCode);
                    }
                }
            }

            onProgressUpdate("DX > DEX conversion complete");
            mProject.getLogger().d(TAG, "DEX conversion successful");

        } catch (IOException | InterruptedException e) {
            mProject.getLogger().e(TAG, "DEX conversion failed: " + e.getMessage());
            throw new CompilerException("DX failed: " + e.getMessage());
        }
    }

    private File getDxJarFile() throws CompilerException {
        File dxJar = new File(ApplicationLoader.applicationContext.getFilesDir() + "/temp/dx-27.0.0.jar");
        if (dxJar.exists()) {
            return dxJar;
        }
        try {

            Decompress.unzipFromAssets(ApplicationLoader.applicationContext, "dx-27.0.0.jar", dxJar.getParentFile().getAbsolutePath());
            if (!dxJar.exists()) {
                throw new CompilerException("Failed to extract dx-27.0.0.jar: File not found after extraction");
            }
            mProject.getLogger().d(TAG, "Extracted dx-27.0.0.jar to: " + dxJar.getAbsolutePath());
            return dxJar;
        } catch (Exception e) {
            mProject.getLogger().e(TAG, "Failed to extract dx-27.0.0.jar: " + e.getMessage());
            throw new CompilerException("Failed to extract dx-27.0.0.jar: " + e.getMessage());
        }
    }
}