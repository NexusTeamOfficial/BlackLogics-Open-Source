package com.tyron.compiler;

import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.CompilerException;

import proguard.ProGuard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProguardCompiler extends Compiler {

    private static final String TAG = "Proguard";

    private final Project mProject;
    private File rulesFile; // ProGuard configuration file
    private File inputDir;  // Directory containing input .class files
    private File outputDir; // Directory for optimized .class files

    public ProguardCompiler(Project project) {
        this.mProject = project;
        // Default ProGuard rules file in the project root
        this.rulesFile = new File(project.getProguardFile());
        // Input: .class files from ECJCompiler
        this.inputDir = new File(project.getOutputFile(), "bin/classes");
        // Output: optimized .class files
        this.outputDir = new File(project.getOutputFile(), "bin/classes_proguard");
    }

    // Allow custom rules file if provided
    public void setRulesFile(File file) {
        if (file != null && file.exists()) {
            this.rulesFile = file;
        }
    }

    @Override
    public void prepare() {
        onProgressUpdate("ProGuard > Preparing...");
        mProject.getLogger().d(TAG, "Preparing ProGuard");

        // Ensure input directory exists (should contain .class files from ECJCompiler)
        if (!inputDir.exists() || !inputDir.isDirectory()) {
            throw new IllegalStateException("Input directory " + inputDir.getAbsolutePath() + " does not exist or is not a directory");
        }

        // Create output directory if it doesn’t exist
        if (!outputDir.exists()) {
            outputDir.mkdirs();
            mProject.getLogger().d(TAG, "Created output directory: " + outputDir.getAbsolutePath());
        }

        // Verify rules file exists
        if (!rulesFile.exists()) {
            throw new IllegalStateException("ProGuard rules file not found at: " + rulesFile.getAbsolutePath());
        }
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("ProGuard > Optimizing and obfuscating...");
        mProject.getLogger().d(TAG, "Running ProGuard on " + inputDir.getAbsolutePath());

        // Build ProGuard command-line arguments
        List<String> args = new ArrayList<>();

        // Input JARs or directories containing .class files
        args.add("-injars");
        args.add(inputDir.getAbsolutePath());

        // Output directory for optimized .class files
        args.add("-outjars");
        args.add(outputDir.getAbsolutePath());

        // Library JARs (dependencies not to be optimized/obfuscated)
        args.add("-libraryjars");
        args.add(getAndroidJarFile().getAbsolutePath());

        // Add library dependencies (e.g., classes.jar from libraries)
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

        // Include the ProGuard rules file
        args.add("@" + rulesFile.getAbsolutePath());
        mProject.getLogger().d(TAG, "Using rules file: " + rulesFile.getAbsolutePath());

        // Optional: Add common ProGuard flags (can be customized via rules file)
        args.add("-dontwarn"); // Suppress warnings (useful for missing classes)
        args.add("-verbose");  // Detailed logging for debugging

        try {
            // Execute ProGuard with the arguments
            ProGuard.main(args.toArray(new String[0]));
            mProject.getLogger().d(TAG, "ProGuard completed successfully");
            onProgressUpdate("ProGuard > Optimization complete");
        } catch (Exception e) {
            mProject.getLogger().e(TAG, "ProGuard failed: " + e.getMessage());
            throw new CompilerException("ProGuard error: " + e.getMessage());
        }

        // Verify output directory has content
        if (outputDir.listFiles() == null || outputDir.listFiles().length == 0) {
            throw new CompilerException("ProGuard produced no output in " + outputDir.getAbsolutePath());
        }
    }
}