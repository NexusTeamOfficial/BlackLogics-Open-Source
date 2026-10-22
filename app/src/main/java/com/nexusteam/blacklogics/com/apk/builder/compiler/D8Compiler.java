package com.tyron.compiler;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.*;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import com.nexusteam.sdk.project.stringfog.StringfogHandler;

import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.CompilationFailedException;

public class D8Compiler extends Compiler {

    private static final String TAG = "D8";
    private final Project mProject;
    private StringfogHandler stringfogHandler;
    private List<String> classpath;
    private Set<String> processedLibraries;

    public D8Compiler(Project project) {
        this.mProject = project;
        setProject(project);
        stringfogHandler = new StringfogHandler(mProject.getId());
        processedLibraries = new HashSet<String>();
    }

    @Override
    public void prepare() throws CompilerException {
        mProject.getLogger().d(TAG, "Preparing D8 compiler");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        Runtime runtime = Runtime.getRuntime();
        mProject.getLogger().d(TAG, "💾 Memory before D8: " +
            (runtime.freeMemory() / (1024 * 1024)) + "MB free / " +
            (runtime.totalMemory() / (1024 * 1024)) + "MB total");

        File workingDir = mProject.getWorkingDirectory();
        if (workingDir == null || !workingDir.exists() || !workingDir.isDirectory()) {
            throw new CompilerException("Invalid working directory for D8: " +
                (workingDir != null ? workingDir.getAbsolutePath() : "null"));
        }

        File classesBase = getClassOutputDir();
        File[] checkFiles = classesBase.listFiles();
        if (checkFiles == null || checkFiles.length == 0) {
            throw new CompilerException("Class directory is empty: " + classesBase.getAbsolutePath());
        }
        mProject.getLogger().d(TAG, "✅ Using class dir: " + classesBase.getAbsolutePath());

        String androidxPath = ApplicationLoader.applicationContext.getFilesDir() + "/temp/androidx";
        File androidxDir = new File(androidxPath);
        if (androidxDir.exists() && androidxDir.isDirectory()) {
            for (Library library : mProject.getLibraries()) {
                if (library.getName().contains("androidx")) {
                    File classJar = library.getClassJarFile();
                    if (!classJar.exists()) {
                        mProject.getLogger().e(TAG, "AndroidX classes.jar missing: " + classJar.getAbsolutePath());
                        throw new CompilerException("AndroidX classes.jar missing: " + library.getName());
                    }
                    if (library.requiresResourceFile() && !library.getResourcesFile().exists()) {
                        mProject.getLogger().w(TAG, "AndroidX resources missing for: " + library.getName());
                    }
                }
            }
        } else {
            mProject.getLogger().w(TAG, "AndroidX directory not found: " + androidxPath);
        }
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("D8 > Compiling to DEX...");
        mProject.getLogger().d(TAG, "Running D8 compilation...");

        processedLibraries.clear();

        Runtime runtime = Runtime.getRuntime();
        mProject.getLogger().d(TAG, "💾 Memory before D8 run: " +
            (runtime.freeMemory() / (1024 * 1024)) + "MB free");

        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        File androidJar = getAndroidJarFile();
        if (!androidJar.exists()) {
            throw new CompilerException("Android.jar not found: " + androidJar.getAbsolutePath());
        }

        File dexOutput = new File(mProject.getOutputFile(), "bin/dex");
        if (!dexOutput.exists() && !dexOutput.mkdirs()) {
            throw new CompilerException("Failed to create DEX output dir: " + dexOutput.getAbsolutePath());
        }

        File classesBase = getClassOutputDir();
        List<File> classFiles = getClassFiles(classesBase);
        mProject.getLogger().d(TAG, "Found " + classFiles.size() + " class files in " + classesBase.getName());

        if (classFiles.isEmpty()) {
            throw new CompilerException("No .class files found in: " + classesBase.getAbsolutePath());
        }

        List<File> libraryFiles = new ArrayList<File>();
        Set<String> addedFiles = new HashSet<String>();
        int libraryCount = 0;

        for (Library lib : mProject.getLibraries()) {
            if (processedLibraries.contains(lib.getName())) {
                mProject.getLogger().d(TAG, "Skipping duplicate library: " + lib.getName());
                continue;
            }

            File file = lib.getClassJarFile();
            if (!file.exists()) {
                mProject.getLogger().w(TAG, "Library file missing, skipping: " + lib.getName());
                continue;
            }

            if (addedFiles.contains(file.getAbsolutePath())) {
                mProject.getLogger().d(TAG, "File already added: " + file.getName());
                continue;
            }

            libraryFiles.add(file);
            addedFiles.add(file.getAbsolutePath());
            processedLibraries.add(lib.getName());
            libraryCount++;

            mProject.getLogger().d(TAG, "Added library: " + lib.getName() + " (" + file.getName() + ")");

            if (libraryCount % 5 == 0) {
                runtime = Runtime.getRuntime();
                mProject.getLogger().d(TAG, "💾 After " + libraryCount + " libraries: " +
                    (runtime.freeMemory() / (1024 * 1024)) + "MB free");
            }
        }

        mProject.getLogger().d(TAG, "Total libraries added: " + libraryCount);

        if (stringfogHandler.isStringfogEnabled()) {
            mProject.getLogger().d(TAG, "StringFog encryption is enabled.");
        }

        mProject.getLogger().d(TAG, "D8 program inputs: " + classFiles.size() +
            " class files + " + libraryFiles.size() + " libraries");

        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        PrintStream errorPrintStream = new PrintStream(errorStream);
        System.setErr(errorPrintStream);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream outPrintStream = new PrintStream(outStream);
        System.setOut(outPrintStream);

        try {
            mProject.getLogger().d(TAG, "Building D8Command...");

            D8Command.Builder commandBuilder = D8Command.builder()
                .setMode(CompilationMode.RELEASE)
                .setMinApiLevel(mProject.getMinSdk())
                .setOutput(dexOutput.toPath(), OutputMode.DexIndexed)
                .addLibraryFiles(androidJar.toPath());

            for (File f : classFiles) {
                commandBuilder.addProgramFiles(f.toPath());
            }
            for (File f : libraryFiles) {
                commandBuilder.addProgramFiles(f.toPath());
            }

            D8Command command = commandBuilder.build();

            mProject.getLogger().d(TAG, "Executing D8...");
            D8.run(command);

            System.setErr(originalErr);
            System.setOut(originalOut);

            String errorMsg = errorStream.toString();
            String outMsg = outStream.toString();

            mProject.getLogger().d(TAG, "D8 compilation completed successfully");
            if (!outMsg.isEmpty()) {
                mProject.getLogger().d(TAG, "D8 output: " + outMsg);
            }
            if (!errorMsg.isEmpty()) {
                mProject.getLogger().w(TAG, "D8 stderr (non-fatal): " + errorMsg);
            }

        } catch (Throwable t) {
            System.setErr(originalErr);
            System.setOut(originalOut);

            if (t instanceof OutOfMemoryError) {
                errorStream.reset();
                outStream.reset();
                System.gc();

                long freeMb = 0, maxMb = 0;
                try {
                    Runtime r = Runtime.getRuntime();
                    freeMb = r.freeMemory() / (1024 * 1024);
                    maxMb = r.maxMemory() / (1024 * 1024);
                } catch (Throwable ignored) {
                }

                mProject.getLogger().e(TAG, "D8 ran out of memory. Free=" + freeMb + "MB Max=" + maxMb + "MB. "
                    + "Likely cause: too many libraries added at once (e.g. AndroidX enable added all libs unfiltered).");

                throw new CompilerException("D8 out of memory (Free=" + freeMb + "MB, Max=" + maxMb
                    + "MB). Reduce the number of libraries being dexed, or enable android:largeHeap=\"true\" "
                    + "in the built app's manifest.");
            }

            String errorMsg = errorStream.toString();
            String outMsg = outStream.toString();

            mProject.getLogger().e(TAG, "D8 System.err: " + errorMsg);
            mProject.getLogger().e(TAG, "D8 System.out: " + outMsg);
            mProject.getLogger().e(TAG, "D8 Throwable: " + t.getClass().getSimpleName() + ": " + t.getMessage());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            mProject.getLogger().e(TAG, "D8 Stack trace: " + sw.toString());

            StringBuilder fullError = new StringBuilder();
            fullError.append("D8 compilation failed: ");
            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                fullError.append(t.getMessage());
            } else if (!errorMsg.isEmpty()) {
                fullError.append(errorMsg);
            } else {
                fullError.append(t.getClass().getSimpleName());
            }
            fullError.append("\nStack trace: ").append(sw.toString());

            throw new CompilerException(fullError.toString());
        }

        File[] dexFiles = dexOutput.listFiles();
        if (dexFiles == null || dexFiles.length == 0) {
            throw new CompilerException("No DEX files generated!");
        }

        mProject.getLogger().d(TAG, "Generated " + dexFiles.length + " DEX files");
        for (File f : dexFiles) {
            mProject.getLogger().d(TAG, "  - " + f.getName() + " (" + f.length() + " bytes)");
        }
    }

    public List<File> getDexFiles() {
        List<File> files = new ArrayList<File>();
        File dexDir = new File(mProject.getOutputFile(), "bin/dex");
        File[] dexArr = dexDir.listFiles();
        if (dexArr != null) {
            for (File file : dexArr) {
                if (file.getName().endsWith(".dex")) {
                    files.add(file);
                }
            }
        }
        java.util.Collections.sort(files, new java.util.Comparator<File>() {
            @Override
            public int compare(File a, File b) {
                return dexSortIndex(a.getName()) - dexSortIndex(b.getName());
            }
        });
        return files;
    }

    private static int dexSortIndex(String name) {
        if ("classes.dex".equals(name)) {
            return 0;
        }
        String digits = name.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    private List<File> getClassFiles(File dir) {
        List<File> files = new ArrayList<File>();
        if (dir.exists() && dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            if (fileArr != null) {
                for (File file : fileArr) {
                    if (file.isDirectory()) {
                        files.addAll(getClassFiles(file));
                    } else if (file.getName().endsWith(".class")) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    private void dexLibrary(Library library) throws Exception {
        File classJar = library.getClassJarFile();
        if (!classJar.exists()) {
            throw new CompilerException("Classes.jar missing for library: " + library.getName());
        }

        try {
            D8Command.Builder commandBuilder = D8Command.builder()
                .setMode(CompilationMode.RELEASE)
                .setMinApiLevel(mProject.getMinSdk())
                .setOutput(library.getPath().toPath(), OutputMode.DexIndexed)
                .addLibraryFiles(getAndroidJarFile().toPath())
                .addProgramFiles(classJar.toPath());

            for (File cpJar : classpathFiles()) {
                commandBuilder.addClasspathFiles(cpJar.toPath());
            }

            D8Command command = commandBuilder.build();
            D8.run(command);
        } catch (CompilationFailedException e) {
            mProject.getLogger().e(TAG, "Failed to dex library " + library.getName() + ": " + e.getMessage());
            throw new CompilerException("Failed to dex library " + library.getName() + ": " + e.getMessage());
        } catch (Exception e) {
            mProject.getLogger().e(TAG, "Failed to dex library " + library.getName() + ": " + e.getMessage());
            throw new CompilerException("Failed to dex library " + library.getName() + ": " + e.getMessage());
        }
    }

    public List<String> classpath() {
        if (classpath != null) {
            return classpath;
        }
        classpath = new ArrayList<String>();
        Set<String> added = new HashSet<String>();
        for (Library library : mProject.getLibraries()) {
            File classJar = library.getClassJarFile();
            if (classJar.exists() && added.add(classJar.getAbsolutePath())) {
                classpath.add("--classpath");
                classpath.add(classJar.getAbsolutePath());
            }
        }
        return classpath;
    }

    private List<File> classpathFiles() {
        List<File> files = new ArrayList<File>();
        Set<String> added = new HashSet<String>();
        for (Library library : mProject.getLibraries()) {
            File classJar = library.getClassJarFile();
            if (classJar.exists() && added.add(classJar.getAbsolutePath())) {
                files.add(classJar);
            }
        }
        return files;
    }
}