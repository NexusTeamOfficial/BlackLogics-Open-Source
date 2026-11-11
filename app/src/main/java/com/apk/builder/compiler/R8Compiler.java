package com.tyron.compiler;

import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.CompilerException;
import com.android.tools.r8.R8;
import com.android.tools.r8.R8Command;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.CompilationFailedException;

import java.io.File;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class R8Compiler extends Compiler {
    private static final String TAG = "R8";
    private final Project mProject;
    private File dexDir;

    public R8Compiler(Project project) {
        this.mProject = project;
        this.dexDir = new File(project.getOutputFile(), "bin/dex");
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
        if (androidJar == null || !androidJar.exists()) {
            throw new CompilerException("android.jar not found: " +
                (androidJar != null ? androidJar.getAbsolutePath() : "null"));
        }
        mProject.getLogger().d(TAG, "✅ android.jar found: " + androidJar.getAbsolutePath());

        File classDir = getClassOutputDir();
        List<File> classFiles = getClassFiles(classDir);
        if (classFiles.isEmpty()) {
            throw new CompilerException("No class files found in: " + classDir.getAbsolutePath());
        }

        mProject.getLogger().d(TAG, "✅ Found " + classFiles.size() + " class files in: " + classDir.getAbsolutePath());

        if (dexDir.exists()) {
            File[] oldDex = dexDir.listFiles();
            if (oldDex != null) {
                for (File f : oldDex) {
                    if (f.getName().endsWith(".dex")) {
                        f.delete();
                        mProject.getLogger().d(TAG, "Deleted old dex: " + f.getName());
                    }
                }
            }
        }
        if (!dexDir.exists() && !dexDir.mkdirs()) {
            throw new CompilerException("Failed to create dex directory: " + dexDir.getAbsolutePath());
        }
        mProject.getLogger().d(TAG, "✅ Dex output directory: " + dexDir.getAbsolutePath());
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("R8 > Converting to DEX...");
        mProject.getLogger().d(TAG, "Running R8 compilation");

        try {
            File classDir = getClassOutputDir();

            List<File> classFileList = getClassFiles(classDir);
            if (classFileList.isEmpty()) {
                throw new CompilerException("No class files found in: " + classDir.getAbsolutePath());
            }

            mProject.getLogger().d(TAG, "📁 Processing " + classFileList.size() + " class files from " + classDir.getName());

            int logCount = Math.min(10, classFileList.size());
            for (int i = 0; i < logCount; i++) {
                mProject.getLogger().d(TAG, "  Class " + (i+1) + ": " + classFileList.get(i).getName());
            }
            if (classFileList.size() > logCount) {
                mProject.getLogger().d(TAG, "  ... and " + (classFileList.size() - logCount) + " more");
            }

            List<Path> programFiles = new ArrayList<Path>();
            for (File file : classFileList) {
                programFiles.add(file.toPath());
            }

            int libCount = 0;
            int skippedCount = 0;
            for (Library library : mProject.getLibraries()) {
                File classJar = library.getClassJarFile();
                if (classJar != null && classJar.exists() && classJar.length() > 0) {
                    programFiles.add(classJar.toPath());
                    libCount++;
                    mProject.getLogger().d(TAG, "📚 Added library as program: " + classJar.getName() +
                        " (" + classJar.length() + " bytes)");
                } else {
                    skippedCount++;
                    mProject.getLogger().w(TAG, "⚠️ Skipping library (missing/empty): " +
                        library.getName() + " -> " + (classJar != null ? classJar.getAbsolutePath() : "null"));
                }
            }
            mProject.getLogger().d(TAG, "✅ Added " + libCount + " libraries as program, skipped " + skippedCount);

            List<Path> libraryFiles = new ArrayList<Path>();
            File androidJar = getAndroidJarFile();
            if (androidJar != null && androidJar.exists() && androidJar.length() > 0) {
                libraryFiles.add(androidJar.toPath());
                mProject.getLogger().d(TAG, "📚 Added android.jar as library (" + androidJar.length() + " bytes)");
            } else {
                mProject.getLogger().e(TAG, "❌ android.jar is missing or empty!");
                throw new CompilerException("android.jar not found or empty");
            }

            if (!dexDir.exists() && !dexDir.mkdirs()) {
                throw new CompilerException("Failed to create dex directory: " + dexDir.getAbsolutePath());
            }

            Path outputPath = dexDir.toPath();
            mProject.getLogger().d(TAG, "📤 Output directory: " + outputPath.toString());
            mProject.getLogger().d(TAG, "📤 R8 will create: " + new File(dexDir, "classes.dex").getAbsolutePath());

            R8Command.Builder builder = R8Command.builder()
                    .addProgramFiles(programFiles)
                    .addLibraryFiles(libraryFiles)
                    .setMinApiLevel(mProject.getMinSdk())
                    .setOutput(outputPath, OutputMode.DexIndexed)
                    .setMode(CompilationMode.RELEASE);

            File proguardFile = new File(mProject.getProguardFile());
            if (proguardFile.exists() && proguardFile.length() > 0) {
                try {
                    builder.addProguardConfigurationFiles(proguardFile.toPath());
                    mProject.getLogger().d(TAG, "📄 Using ProGuard rules: " + proguardFile.getAbsolutePath());
                } catch (Exception e) {
                    mProject.getLogger().w(TAG, "⚠️ Failed to add ProGuard rules: " + e.getMessage());
                }
            } else {
                mProject.getLogger().w(TAG, "⚠️ No ProGuard rules file found at: " + proguardFile.getAbsolutePath());
                File defaultRules = createDefaultRulesFile();
                if (defaultRules != null && defaultRules.exists()) {
                    builder.addProguardConfigurationFiles(defaultRules.toPath());
                    mProject.getLogger().d(TAG, "📄 Using default rules: " + defaultRules.getAbsolutePath());
                }
            }

            mProject.getLogger().d(TAG, "🚀 Starting R8 compilation...");

            try {
                R8.run(builder.build());
                mProject.getLogger().d(TAG, "✅ R8 compilation completed successfully!");
            } catch (CompilationFailedException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);

                StringBuilder errorDetail = new StringBuilder();
                errorDetail.append("R8 Compilation Failed!\n");
                errorDetail.append("========================================\n");
                errorDetail.append("Error: ").append(e.getMessage()).append("\n");
                errorDetail.append("----------------------------------------\n");

                Throwable cause = e.getCause();
                if (cause != null) {
                    errorDetail.append("Cause: ").append(cause.getClass().getName()).append("\n");
                    errorDetail.append("Cause Message: ").append(cause.getMessage()).append("\n");
                    errorDetail.append("----------------------------------------\n");
                }

                String exceptionMsg = e.getMessage();
                if (exceptionMsg != null && !exceptionMsg.isEmpty()) {
                    errorDetail.append("Exception Details:\n");
                    errorDetail.append(exceptionMsg).append("\n");
                    errorDetail.append("----------------------------------------\n");
                }

                errorDetail.append("Stack Trace:\n");
                errorDetail.append(sw.toString());
                errorDetail.append("========================================\n");

                String fullError = errorDetail.toString();
                mProject.getLogger().e(TAG, fullError);

                throw new CompilerException("R8 compilation failed:\n" + fullError);
            }

            File dexFile = new File(dexDir, "classes.dex");
            if (!dexFile.exists() || dexFile.length() == 0) {
                String errorMsg = "R8 did not generate classes.dex! Path: " + dexFile.getAbsolutePath();
                mProject.getLogger().e(TAG, "❌ " + errorMsg);

                File[] dexFiles = dexDir.listFiles();
                if (dexFiles != null && dexFiles.length > 0) {
                    mProject.getLogger().d(TAG, "Found other dex files:");
                    for (File f : dexFiles) {
                        mProject.getLogger().d(TAG, "  - " + f.getName() + " (" + f.length() + " bytes)");
                    }
                }

                throw new CompilerException(errorMsg);
            }

            mProject.getLogger().d(TAG, "📦 Output: " + dexFile.getName() + " (" + dexFile.length() + " bytes)");
            onProgressUpdate("R8 > DEX conversion complete ✅");

        } catch (CompilerException e) {
            throw e;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            String fullError = "Unexpected error during R8 compilation:\n" +
                              "Error: " + e.getMessage() + "\n" +
                              "Stack Trace:\n" + sw.toString();

            mProject.getLogger().e(TAG, fullError);
            throw new CompilerException(fullError);
        }
    }

    public List<File> getDexFiles() {
        List<File> files = new ArrayList<File>();
        if (dexDir.exists() && dexDir.isDirectory()) {
            File[] dexArr = dexDir.listFiles();
            if (dexArr != null) {
                for (File f : dexArr) {
                    if (f.getName().endsWith(".dex")) {
                        files.add(f);
                    }
                }
            }
        }
        return files;
    }

    private List<File> getClassFiles(File dir) {
        List<File> files = new ArrayList<File>();
        if (!dir.exists() || !dir.isDirectory()) {
            return files;
        }

        File[] fileArr = dir.listFiles();
        if (fileArr == null) {
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

    private File createDefaultRulesFile() {
        try {
            File rulesDir = new File(mProject.getOutputFile(), "rules");
            if (!rulesDir.exists() && !rulesDir.mkdirs()) {
                return null;
            }

            File rulesFile = new File(rulesDir, "default-rules.pro");
            if (rulesFile.exists()) {
                return rulesFile;
            }

            StringBuilder defaultRules = new StringBuilder();
            defaultRules.append("-dontoptimize\n");
            defaultRules.append("-dontshrink\n");
            defaultRules.append("-keep public class * extends android.app.Activity {\n");
            defaultRules.append("    public <init>();\n");
            defaultRules.append("}\n");
            defaultRules.append("-keep public class * extends android.app.Service {\n");
            defaultRules.append("    public <init>();\n");
            defaultRules.append("}\n");
            defaultRules.append("-keep public class * extends android.content.BroadcastReceiver {\n");
            defaultRules.append("    public <init>();\n");
            defaultRules.append("}\n");
            defaultRules.append("-keep public class * extends android.content.ContentProvider {\n");
            defaultRules.append("    public <init>();\n");
            defaultRules.append("}\n");
            defaultRules.append("-keep public class * extends android.app.Application {\n");
            defaultRules.append("    public <init>();\n");
            defaultRules.append("}\n");
            defaultRules.append("-keep class **.R\n");
            defaultRules.append("-keep class **.R$* {\n    <fields>;\n}\n");
            defaultRules.append("-keepclassmembers class * implements android.os.Parcelable {\n");
            defaultRules.append("    public static final ** CREATOR;\n");
            defaultRules.append("}\n");
            defaultRules.append("-keepclassmembers class * {\n");
            defaultRules.append("    native <methods>;\n");
            defaultRules.append("}\n");
            defaultRules.append("-keepclassmembers enum * {\n");
            defaultRules.append("    public static **[] values();\n");
            defaultRules.append("    public static ** valueOf(java.lang.String);\n");
            defaultRules.append("}\n");
            defaultRules.append("-keepattributes *Annotation*,EnclosingMethod,Signature,InnerClasses\n");
            defaultRules.append("-dontwarn android.**\n");
            defaultRules.append("-dontwarn androidx.**\n");
            defaultRules.append("-dontwarn com.google.**\n");
            defaultRules.append("-dontwarn com.android.**\n");
            defaultRules.append("-dontwarn dalvik.**\n");
            defaultRules.append("-dontwarn javax.**\n");
            defaultRules.append("-dontwarn org.**\n");
            defaultRules.append("-dontwarn sun.**\n");
            defaultRules.append("-dontwarn java.**\n");
            defaultRules.append("-ignorewarnings\n");
            defaultRules.append("-verbose\n");

            java.nio.file.Files.write(rulesFile.toPath(), defaultRules.toString().getBytes());
            mProject.getLogger().d(TAG, "Created default rules file: " + rulesFile.getAbsolutePath());

            return rulesFile;
        } catch (Exception e) {
            mProject.getLogger().e(TAG, "Failed to create default rules: " + e.getMessage());
            return null;
        }
    }
}