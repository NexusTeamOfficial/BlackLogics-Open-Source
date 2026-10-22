package com.tyron.compiler;

import android.os.Environment;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.model.Library;
import com.apk.builder.model.Project;
import com.tyron.compiler.exception.CompilerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dalvik.system.DexClassLoader;

public class ProguardCompiler extends Compiler {

    private static final String TAG = "Proguard";
    private static final String ASSET_JAR = "proguard.jar";

    private final Project mProject;
    private File rulesFile;
    private File externalRulesFile;
    private File defaultRulesFile;
    private File inputDir;
    private File outputDir;
    private File mappingFile;

    public ProguardCompiler(Project project) {
        this.mProject = project;
        this.inputDir = new File(project.getOutputFile(), "bin/classes");
        this.outputDir = new File(project.getOutputFile(), "bin/classes_proguard");
        this.mappingFile = new File(project.getOutputFile(), "bin/mapping.txt");
        this.defaultRulesFile = new File(project.getOutputFile(), "bin/proguard-default.pro");

        String scId = null;
        try {
            scId = project.getId();
        } catch (Throwable ignored) {}

        if (scId != null && !scId.trim().isEmpty()) {
            File ext = new File(
                    Environment.getExternalStorageDirectory(),
                    ".blacklogics/data/" + scId + "/proguard-rules.pro"
            );
            if (ext.exists() && ext.length() > 10) {
                this.externalRulesFile = ext;
                mProject.getLogger().d(TAG, "Found external rules: " + ext.getAbsolutePath());
            } else {
                mProject.getLogger().d(TAG, "No external rules at: " + ext.getAbsolutePath());
            }
        }

        try {
            String pf = project.getProguardFile();
            if (pf != null && !pf.trim().isEmpty()) {
                this.rulesFile = new File(pf);
            }
        } catch (Throwable t) {
            mProject.getLogger().w(TAG, "getProguardFile() failed: " + t.getMessage());
        }
        if (this.rulesFile == null) {
            this.rulesFile = new File(project.getOutputFile(), "proguard-rules.pro");
        }
    }

    public void setRulesFile(File file) {
        if (file != null && file.exists()) {
            this.rulesFile = file;
        }
    }

    public void setExternalRulesFile(File file) {
        if (file != null && file.exists()) {
            this.externalRulesFile = file;
        }
    }

    @Override
    public void prepare() throws CompilerException {
        onProgressUpdate("ProGuard > Preparing...");
        mProject.getLogger().d(TAG, "Preparing ProGuard (isolated ClassLoader)");

        if (!inputDir.exists() || !inputDir.isDirectory()) {
            throw new CompilerException("Input directory does not exist: " + inputDir.getAbsolutePath());
        }
        File[] inputFiles = inputDir.listFiles();
        if (inputFiles == null || inputFiles.length == 0) {
            throw new CompilerException("No class files found in: " + inputDir.getAbsolutePath());
        }
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new CompilerException("Failed to create output directory: " + outputDir.getAbsolutePath());
        }

        createDefaultRulesFile(defaultRulesFile);
        if (!defaultRulesFile.exists() || defaultRulesFile.length() < 20) {
            throw new CompilerException("Failed to create default ProGuard rules: "
                    + defaultRulesFile.getAbsolutePath());
        }
        mProject.getLogger().d(TAG, "Default rules ready: " + defaultRulesFile.getAbsolutePath());

        if (externalRulesFile != null && externalRulesFile.exists()) {
            mProject.getLogger().d(TAG, "External rules: " + externalRulesFile.getAbsolutePath());
        }
        if (rulesFile != null && rulesFile.exists() && rulesFile.length() > 10) {
            mProject.getLogger().d(TAG, "Project rules: " + rulesFile.getAbsolutePath());
        }

        getProguardJarFile();
        mProject.getLogger().d(TAG, "ProGuard ready. mapping → " + mappingFile.getAbsolutePath());
    }

    private void createDefaultRulesFile(File target) {
        try {
            File parent = target.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            String defaultRules =
                    "# ============================================================\n" +
                    "# BlackLogics DEFAULT ProGuard rules (always applied first)\n" +
                    "# ============================================================\n" +
                    "-dontoptimize\n" +
                    "-dontshrink\n" +
                    "-dontpreverify\n" +
                    "-ignorewarnings\n" +
                    "-verbose\n" +
                    "-allowaccessmodification\n" +
                    "-repackageclasses ''\n" +
                    "-flattenpackagehierarchy ''\n" +
                    "-useuniqueclassmembernames\n\n" +

                    "# Keep Android components + constructors (REQUIRED)\n" +
                    "-keep public class * extends android.app.Activity {\n" +
                    "    public <init>();\n" +
                    "}\n" +
                    "-keep public class * extends android.app.Application {\n" +
                    "    public <init>();\n" +
                    "}\n" +
                    "-keep public class * extends android.app.Service {\n" +
                    "    public <init>();\n" +
                    "}\n" +
                    "-keep public class * extends android.content.BroadcastReceiver {\n" +
                    "    public <init>();\n" +
                    "}\n" +
                    "-keep public class * extends android.content.ContentProvider {\n" +
                    "    public <init>();\n" +
                    "}\n" +
                    "-keep public class * extends android.app.Fragment {\n" +
                    "    public <init>();\n" +
                    "}\n" +
                    "-keep public class * extends androidx.fragment.app.Fragment {\n" +
                    "    public <init>();\n" +
                    "}\n\n" +

                    "# R classes\n" +
                    "-keep class **.R\n" +
                    "-keep class **.R$* { <fields>; }\n\n" +

                    "# Parcelable\n" +
                    "-keepclassmembers class * implements android.os.Parcelable {\n" +
                    "    public static final ** CREATOR;\n" +
                    "}\n\n" +

                    "# Native\n" +
                    "-keepclassmembers class * {\n" +
                    "    native <methods>;\n" +
                    "}\n\n" +

                    "# Enums\n" +
                    "-keepclassmembers enum * {\n" +
                    "    public static **[] values();\n" +
                    "    public static ** valueOf(java.lang.String);\n" +
                    "}\n\n" +

                    "# Attributes\n" +
                    "-keepattributes *Annotation*,EnclosingMethod,Signature,InnerClasses,SourceFile,LineNumberTable\n\n" +

                    "# Dontwarn\n" +
                    "-dontwarn android.**\n" +
                    "-dontwarn androidx.**\n" +
                    "-dontwarn com.google.**\n" +
                    "-dontwarn com.android.**\n" +
                    "-dontwarn dalvik.**\n" +
                    "-dontwarn javax.**\n" +
                    "-dontwarn org.**\n" +
                    "-dontwarn sun.**\n" +
                    "-dontwarn java.**\n" +
                    "-dontwarn kotlin.**\n";

            FileWriter w = new FileWriter(target, false);
            w.write(defaultRules);
            w.close();
            mProject.getLogger().d(TAG, "Wrote default rules → " + target.getAbsolutePath());
        } catch (Exception e) {
            mProject.getLogger().e(TAG, "Default rules failed: " + e.getMessage());
        }
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("ProGuard > Optimizing and obfuscating...");
        mProject.getLogger().d(TAG, "Running ProGuard (isolated)");

        File[] inputCheck = inputDir.listFiles();
        if (!inputDir.exists() || inputCheck == null || inputCheck.length == 0) {
            mProject.getLogger().w(TAG, "No class files. Skipping.");
            return;
        }

        clearDirectory(outputDir);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new CompilerException("Failed to create output directory");
        }

        File mappingParent = mappingFile.getParentFile();
        if (mappingParent != null && !mappingParent.exists()) {
            mappingParent.mkdirs();
        }
        if (mappingFile.exists()) {
            mappingFile.delete();
        }

        createDefaultRulesFile(defaultRulesFile);

        File androidJar = getAndroidJarFile();
        if (androidJar == null || !androidJar.exists()) {
            throw new CompilerException("android.jar not found");
        }

        List<String> args = new ArrayList<String>();
        args.add("-injars");
        args.add(inputDir.getAbsolutePath());
        args.add("-outjars");
        args.add(outputDir.getAbsolutePath());
        args.add("-libraryjars");
        args.add(androidJar.getAbsolutePath());

        for (Library lib : mProject.getLibraries()) {
            File jar = lib.getClassJarFile();
            if (jar != null && jar.exists() && jar.length() > 0) {
                args.add("-libraryjars");
                args.add(jar.getAbsolutePath());
            }
        }

        args.add("-dontshrink");
        args.add("-dontoptimize");
        args.add("-dontpreverify");
        args.add("-ignorewarnings");
        args.add("-verbose");
        args.add("-forceprocessing");

        args.add("-printmapping");
        args.add(mappingFile.getAbsolutePath());
        mProject.getLogger().d(TAG, "Mapping → " + mappingFile.getAbsolutePath());

        args.add("-include");
        args.add(defaultRulesFile.getAbsolutePath());
        mProject.getLogger().d(TAG, "Including DEFAULT rules: " + defaultRulesFile.getAbsolutePath());

        if (externalRulesFile != null
                && externalRulesFile.exists()
                && externalRulesFile.length() > 10) {
            args.add("-include");
            args.add(externalRulesFile.getAbsolutePath());
            mProject.getLogger().d(TAG, "Including EXTERNAL rules: " + externalRulesFile.getAbsolutePath());
        }

        if (rulesFile != null
                && rulesFile.exists()
                && rulesFile.length() > 10) {
            boolean sameAsExternal = externalRulesFile != null
                    && externalRulesFile.getAbsolutePath().equals(rulesFile.getAbsolutePath());
            boolean sameAsDefault = defaultRulesFile.getAbsolutePath().equals(rulesFile.getAbsolutePath());
            if (!sameAsExternal && !sameAsDefault) {
                args.add("-include");
                args.add(rulesFile.getAbsolutePath());
                mProject.getLogger().d(TAG, "Including PROJECT rules: " + rulesFile.getAbsolutePath());
            }
        }

        copyRulesToOutput();

        String[] argsArray = args.toArray(new String[0]);
        File proguardFile = getProguardJarFile();

        try {
            System.setProperty("log4j2.disableJmx", "true");
            System.setProperty("log4j2.shutdownHookEnabled", "false");
            System.setProperty("log4j2.is.webapp", "true");

            File optDir = new File(ApplicationLoader.applicationContext.getCodeCacheDir(), "pg_opt");
            if (!optDir.exists()) {
                optDir.mkdirs();
            }
            optDir.setWritable(true, false);

            ClassLoader bootParent = Object.class.getClassLoader();
            DexClassLoader loader = new DexClassLoader(
                    proguardFile.getAbsolutePath(),
                    optDir.getAbsolutePath(),
                    null,
                    bootParent
            );

            Class<?> configClass = loader.loadClass("proguard.Configuration");
            Class<?> parserClass = loader.loadClass("proguard.ConfigurationParser");
            Class<?> proguardClass = loader.loadClass("proguard.ProGuard");

            Object configuration = configClass.getConstructor().newInstance();

            Constructor<?> parserCtor = parserClass.getConstructor(String[].class, Properties.class);
            Object parser = parserCtor.newInstance((Object) argsArray, new Properties());
            try {
                Method parse = parserClass.getMethod("parse", configClass);
                parse.invoke(parser, configuration);
            } finally {
                try {
                    parserClass.getMethod("close").invoke(parser);
                } catch (Throwable ignored) {}
            }

            Object proguard = proguardClass.getConstructor(configClass).newInstance(configuration);
            mProject.getLogger().d(TAG, "Executing ProGuard (isolated)...");
            proguardClass.getMethod("execute").invoke(proguard);
            mProject.getLogger().d(TAG, "ProGuard completed successfully");

        } catch (Throwable t) {
            Throwable real = t;
            while (real.getCause() != null && real != real.getCause()) {
                real = real.getCause();
            }
            String detail = buildErrorDetail("ProGuard failed", real);
            mProject.getLogger().e(TAG, detail);
            throw new CompilerException(detail);
        }

        File[] out = outputDir.listFiles();
        if (out == null || out.length == 0) {
            throw new CompilerException("ProGuard produced no output in: " + outputDir.getAbsolutePath());
        }

        if (mappingFile.exists() && mappingFile.length() > 0) {
            mProject.getLogger().d(TAG, "✅ mapping.txt: " + mappingFile.getAbsolutePath()
                    + " (" + mappingFile.length() + " bytes)");
        } else {
            mProject.getLogger().w(TAG, "⚠️ mapping.txt missing or empty");
        }

        onProgressUpdate("ProGuard > Complete");
    }

    private void copyRulesToOutput() {
        try {
            File outDir = new File(mProject.getOutputFile(), "bin");
            if (!outDir.exists()) {
                outDir.mkdirs();
            }

            if (defaultRulesFile.exists()) {
                mProject.getLogger().d(TAG, "Default rules in output: " + defaultRulesFile.getAbsolutePath());
            }

            if (externalRulesFile != null && externalRulesFile.exists()) {
                File dest = new File(outDir, "proguard-rules-external.pro");
                copyFile(externalRulesFile, dest);
                mProject.getLogger().d(TAG, "Copied external rules → " + dest.getAbsolutePath());
            }

            if (rulesFile != null && rulesFile.exists() && rulesFile.length() > 10) {
                File dest = new File(outDir, "proguard-rules-project.pro");
                copyFile(rulesFile, dest);
                mProject.getLogger().d(TAG, "Copied project rules → " + dest.getAbsolutePath());
            }
        } catch (Exception e) {
            mProject.getLogger().w(TAG, "Could not copy rules to output: " + e.getMessage());
        }
    }

    private void copyFile(File src, File dest) throws Exception {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dest);
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
    }

    private File getProguardJarFile() throws CompilerException {
        File dir = new File(ApplicationLoader.applicationContext.getCodeCacheDir(), "proguard_rt");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dir.setWritable(true, false);

        File jar = new File(dir, "proguard.jar");

        if (jar.exists() && jar.length() > 500000) {
            jar.setReadOnly();
            return jar;
        }

        try {
            if (jar.exists()) {
                jar.delete();
            }

            InputStream in = ApplicationLoader.applicationContext.getAssets().open(ASSET_JAR);
            FileOutputStream out = new FileOutputStream(jar);
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();

            if (!jar.exists() || jar.length() < 1000) {
                throw new CompilerException("proguard.jar extract failed (size too small)");
            }

            jar.setReadable(true, false);
            jar.setWritable(false);

            mProject.getLogger().d(TAG, "Extracted proguard: " + jar.length() + " bytes → " + jar.getAbsolutePath());
            return jar;
        } catch (Exception e) {
            throw new CompilerException("Failed to extract proguard.jar: " + e.getMessage());
        }
    }

    private String buildErrorDetail(String header, Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return header + "\n========================================\n"
                + "Error: " + t.getClass().getName() + ": " + t.getMessage() + "\n"
                + "----------------------------------------\n"
                + sw.toString()
                + "========================================\n";
    }

    private void clearDirectory(File dir) {
        if (!dir.exists()) return;
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.isDirectory()) clearDirectory(f);
            f.delete();
        }
    }
}