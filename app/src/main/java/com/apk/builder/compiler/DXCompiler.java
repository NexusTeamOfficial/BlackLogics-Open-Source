package com.tyron.compiler;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.util.Decompress;
import com.tyron.compiler.exception.CompilerException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DXCompiler extends Compiler {

    private static final String TAG = "DX";
    private final Project mProject;
    private Set<String> processedLibraries;

    public DXCompiler(Project project) {
        this.mProject = project;
        this.processedLibraries = new HashSet<String>();
    }

    @Override
    public void prepare() throws CompilerException {
        onProgressUpdate("DX > Initializing...");
        mProject.getLogger().d(TAG, "Preparing DX compiler...");

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
        mProject.getLogger().d(TAG, "Memory before DX: " +
            (runtime.freeMemory() / (1024 * 1024)) + "MB free / " +
            (runtime.totalMemory() / (1024 * 1024)) + "MB total");

        getDxJarFile();

        File classesDir = getClassOutputDir();
        File[] checkFiles = classesDir.listFiles();
        if (checkFiles == null || checkFiles.length == 0) {
            throw new CompilerException("Classes directory is empty: " + classesDir.getAbsolutePath());
        }
        mProject.getLogger().d(TAG, "✅ Using class dir: " + classesDir.getAbsolutePath());

        File outputDir = new File(mProject.getOutputFile(), "bin/dex");
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new CompilerException("Failed to create DEX output directory");
        }

        processedLibraries.clear();
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("DX > Converting to DEX...");
        mProject.getLogger().d(TAG, "Running DX compilation...");

        Runtime runtime = Runtime.getRuntime();
        mProject.getLogger().d(TAG, "Memory before DX run: " +
            (runtime.freeMemory() / (1024 * 1024)) + "MB free");

        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        File dxJar = getDxJarFile();
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
            throw new CompilerException(
                "No compiled .class files found in " + classesBase.getAbsolutePath() +
                " - ECJ produced no output. Check that the previous compile step (ECJ) " +
                "actually ran and found Java source files, and wasn't skipped by a stale/incremental build.");
        }

        int patchedCount = 0;
        for (File classFile : classFiles) {
            try {
                if (patchClassFileVersionForDx(classFile)) {
                    patchedCount++;
                }
            } catch (IOException e) {
                throw new CompilerException(
                    "Failed to patch class file version for DX compatibility: " +
                    classFile.getAbsolutePath() + " - " + e.getMessage());
            }
        }
        mProject.getLogger().d(TAG, "Patched " + patchedCount + " class file(s) to version 51 for DX compatibility");

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
                mProject.getLogger().d(TAG, "Memory after " + libraryCount + " libraries: " +
                    (runtime.freeMemory() / (1024 * 1024)) + "MB free");
            }
        }

        mProject.getLogger().d(TAG, "Total libraries added: " + libraryCount);

        List<String> inputPaths = new ArrayList<String>();
        inputPaths.add(classesBase.getAbsolutePath());

        for (File lib : libraryFiles) {
            inputPaths.add(lib.getAbsolutePath());
        }

        List<String> args = new ArrayList<String>();
        args.add("--dex");
        args.add("--output=" + dexOutput.getAbsolutePath() + "/classes.dex");

        for (String path : inputPaths) {
            args.add(path);
        }

        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        PrintStream errorPrintStream = new PrintStream(errorStream);
        System.setErr(errorPrintStream);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream outPrintStream = new PrintStream(outStream);
        System.setOut(outPrintStream);

        try {
            mProject.getLogger().d(TAG, "Loading DX from: " + dxJar.getAbsolutePath());

            URLClassLoader classLoader = new URLClassLoader(
                new URL[]{dxJar.toURI().toURL()},
                getClass().getClassLoader()
            );

            List<String> dexerArgs = new ArrayList<String>();
            for (String arg : args) {
                if (!"--dex".equals(arg)) {
                    dexerArgs.add(arg);
                }
            }
            String[] argsArray = dexerArgs.toArray(new String[0]);

            mProject.getLogger().d(TAG, "Executing DX (dexer.Main.run): " + dexerArgs.toString());

            Class<?> dexerMainClass = classLoader.loadClass("com.android.dx.command.dexer.Main");
            Class<?> argumentsClass = classLoader.loadClass("com.android.dx.command.dexer.Main$Arguments");

            Object argumentsInstance = argumentsClass.getConstructor().newInstance();
            Method parseMethod = argumentsClass.getMethod("parse", String[].class);
            parseMethod.invoke(argumentsInstance, (Object) argsArray);

            Method runMethod = dexerMainClass.getMethod("run", argumentsClass);
            Object resultObj = runMethod.invoke(null, argumentsInstance);
            int result = (resultObj instanceof Integer) ? (Integer) resultObj : -1;

            System.setErr(originalErr);
            System.setOut(originalOut);

            String errorMsg = errorStream.toString();
            String outMsg = outStream.toString();

            if (result != 0) {
                mProject.getLogger().e(TAG, "DX dexer.Main.run() returned failure code: " + result);
                mProject.getLogger().e(TAG, "DX stderr: " + errorMsg);
                mProject.getLogger().e(TAG, "DX stdout: " + outMsg);
                String detail = !errorMsg.trim().isEmpty() ? errorMsg
                        : (!outMsg.trim().isEmpty() ? outMsg : "exit code " + result);
                throw new CompilerException("DX compilation failed: " + detail);
            }
            if (!outMsg.isEmpty()) {
                mProject.getLogger().d(TAG, "DX output: " + outMsg);
            }
            if (!errorMsg.isEmpty()) {
                mProject.getLogger().w(TAG, "DX stderr: " + errorMsg);
            }

        } catch (Throwable t) {
            System.setErr(originalErr);
            System.setOut(originalOut);

            Throwable real = t;
            while (real instanceof java.lang.reflect.InvocationTargetException
                    && real.getCause() != null) {
                real = real.getCause();
            }

            if (real instanceof OutOfMemoryError) {
                errorStream.reset();
                outStream.reset();
                System.gc();

                long freeMb = 0;
                long maxMb = 0;
                try {
                    Runtime r = Runtime.getRuntime();
                    freeMb = r.freeMemory() / (1024 * 1024);
                    maxMb = r.maxMemory() / (1024 * 1024);
                } catch (Throwable ignored) {
                }

                mProject.getLogger().e(TAG, "DX ran out of memory. Free=" + freeMb + "MB Max=" + maxMb + "MB");
                throw new CompilerException("DX out of memory (Free=" + freeMb + "MB, Max=" + maxMb + "MB)");
            } else {
                String errorMsg = errorStream.toString();
                String outMsg = outStream.toString();

                mProject.getLogger().e(TAG, "DX System.err: " + errorMsg);
                mProject.getLogger().e(TAG, "DX System.out: " + outMsg);
                mProject.getLogger().e(TAG, "DX Throwable: " + real.getClass().getSimpleName() + ": " + real.getMessage());

                StringWriter sw = new StringWriter();
                real.printStackTrace(new PrintWriter(sw));
                mProject.getLogger().e(TAG, "DX Stack trace: " + sw.toString());

                String detail;
                if (real.getMessage() != null && !real.getMessage().isEmpty()) {
                    detail = real.getMessage();
                } else if (!errorMsg.trim().isEmpty()) {
                    detail = errorMsg;
                } else if (!outMsg.trim().isEmpty()) {
                    detail = outMsg;
                } else {
                    detail = real.getClass().getSimpleName();
                }

                throw new CompilerException("DX compilation failed: " + detail);
            }
        }

        File[] dexFiles = dexOutput.listFiles();
        if (dexFiles == null || dexFiles.length == 0) {
            throw new CompilerException("No DEX files generated!");
        }

        mProject.getLogger().d(TAG, "Generated " + dexFiles.length + " DEX files");
        for (File f : dexFiles) {
            mProject.getLogger().d(TAG, "  - " + f.getName() + " (" + f.length() + " bytes)");
        }

        onProgressUpdate("DX > DEX conversion complete");
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

        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File a, File b) {
                String nameA = a.getName();
                String nameB = b.getName();
                if ("classes.dex".equals(nameA)) {
                    return -1;
                }
                if ("classes.dex".equals(nameB)) {
                    return 1;
                }
                return nameA.compareTo(nameB);
            }
        });

        return files;
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

    private File getDxJarFile() throws CompilerException {
        File dxDir = new File(ApplicationLoader.applicationContext.getFilesDir(), "temp");
        if (!dxDir.exists() && !dxDir.mkdirs()) {
            throw new CompilerException("Failed to create temp directory");
        }

        File dxJar = new File(dxDir, "dx-27.0.0.jar");

        if (dxJar.exists() && dxJar.length() > 0) {
            mProject.getLogger().d(TAG, "DX jar already exists: " + dxJar.getAbsolutePath());
            return dxJar;
        }

        try {
            mProject.getLogger().d(TAG, "Extracting dx-27.0.0.jar from assets...");

            if (dxJar.exists()) {
                dxJar.delete();
            }

            boolean success = copyAssetToFile("dx-27.0.0.jar", dxJar);

            if (!success || !dxJar.exists() || dxJar.length() == 0) {
                throw new CompilerException("Failed to extract dx-27.0.0.jar");
            }

            mProject.getLogger().d(TAG, "✅ Extracted dx-27.0.0.jar: " + dxJar.getAbsolutePath() +
                    " (" + dxJar.length() + " bytes)");
            return dxJar;

        } catch (Exception e) {
            mProject.getLogger().e(TAG, "Failed to extract dx-27.0.0.jar: " + e.getMessage());
            e.printStackTrace();
            throw new CompilerException("Failed to extract dx-27.0.0.jar: " + e.getMessage());
        }
    }

    private boolean patchClassFileVersionForDx(File classFile) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(classFile, "rw");

            byte[] header = new byte[8];
            raf.readFully(header);

            if ((header[0] & 0xFF) != 0xCA || (header[1] & 0xFF) != 0xFE ||
                (header[2] & 0xFF) != 0xBA || (header[3] & 0xFF) != 0xBE) {
                mProject.getLogger().w(TAG, "Skipping version patch, bad magic: " + classFile.getAbsolutePath());
                return false;
            }

            int majorVersion = ((header[6] & 0xFF) << 8) | (header[7] & 0xFF);
            if (majorVersion <= 51) {
                return false;
            }

            raf.seek(6);
            raf.writeByte(0x00);
            raf.writeByte(0x33);
            return true;
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
    }

    private boolean copyAssetToFile(String assetName, File destination) {
        InputStream in = null;
        FileOutputStream out = null;

        try {
            in = ApplicationLoader.applicationContext.getAssets().open(assetName);

            File parentDir = destination.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            out = new FileOutputStream(destination);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.flush();
            return true;

        } catch (IOException e) {
            mProject.getLogger().e(TAG, "Error copying asset: " + e.getMessage());
            return false;
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) { }
            try {
                if (out != null) out.close();
            } catch (IOException e) { }
        }
    }

    public void enableMultiDex() throws CompilerException {
        mProject.getLogger().d(TAG, "Multi-dex enabled for DX");
    }
}