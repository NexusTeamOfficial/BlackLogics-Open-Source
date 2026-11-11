package com.nexusteam.blacklogics.project.encryption.dex;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.util.Decompress;
import org.eclipse.jdt.internal.compiler.batch.Main;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.CompilationFailedException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BlkLoaderCompiler implements BlkDexProtector.LoaderCompiler {

    private static final String TAG = "BlkLoaderCompiler";

    private final File baseLoaderSourceFile;
    private final int minSdk;

    public BlkLoaderCompiler(File baseLoaderSourceFile) {
        this(baseLoaderSourceFile, 21);
    }

    public BlkLoaderCompiler(File baseLoaderSourceFile, int minSdk) {
        this.baseLoaderSourceFile = baseLoaderSourceFile;
        this.minSdk = minSdk;
    }

    @Override
    public byte[] compile(String packageName, String fqClassName, String javaSource) throws Exception {
        File workDir = new File(ApplicationLoader.applicationContext.getCacheDir(), "blk_loader_build");
        if (workDir.exists()) deleteRecursive(workDir);
        workDir.mkdirs();

        File srcDir = new File(workDir, "src");
        File pkgDir = new File(srcDir, fqClassName.substring(0, fqClassName.lastIndexOf('.')).replace('.', '/'));
        pkgDir.mkdirs();

        String simpleName = fqClassName.substring(fqClassName.lastIndexOf('.') + 1);
        File generatedSrc = new File(pkgDir, simpleName + ".java");
        Files.write(generatedSrc.toPath(), javaSource.getBytes("UTF-8"));

        File baseSrcDest = new File(pkgDir, baseLoaderSourceFile.getName());
        Files.copy(baseLoaderSourceFile.toPath(), baseSrcDest.toPath());

        File classesOut = new File(workDir, "classes");
        classesOut.mkdirs();

        File androidJar = getAndroidJarFile();
        ArrayList<String> args = new ArrayList<>();
        args.add("-source");
        args.add("1.8");
        args.add("-target");
        args.add("1.8");
        args.add("-proc:none");
        args.add("-nowarn");
        args.add("-bootclasspath");
        args.add(androidJar.getAbsolutePath());
        args.add("-d");
        args.add(classesOut.getAbsolutePath());
        args.add("-cp");
        args.add(androidJar.getAbsolutePath());
        args.add(generatedSrc.getAbsolutePath());
        args.add(baseSrcDest.getAbsolutePath());

        StringWriterOutputStream errBuf = new StringWriterOutputStream();
        StringWriterOutputStream outBuf = new StringWriterOutputStream();
        PrintWriter errWriter = new PrintWriter(errBuf);
        PrintWriter outWriter = new PrintWriter(outBuf);

        Main main = new Main(outWriter, errWriter, false, null, null);
        boolean ok;
        try {
            ok = main.compile(args.toArray(new String[0]));
        } catch (Exception e) {
            errWriter.flush();
            throw new Exception("Loader ECJ compile threw: " + e.getMessage() + "\n" + errBuf.buffer, e);
        }
        errWriter.flush();
        if (!ok || main.globalErrorsCount > 0) {
            throw new Exception("Loader ECJ compile failed:\n" + errBuf.buffer);
        }

        List<File> classFiles = collectClassFiles(classesOut);
        if (classFiles.isEmpty()) {
            throw new Exception("No .class files produced for loader");
        }

        File dexOut = new File(workDir, "dex");
        dexOut.mkdirs();

        try {
            D8Command.Builder builder = D8Command.builder()
                    .setMode(CompilationMode.RELEASE)
                    .setMinApiLevel(minSdk)
                    .setOutput(dexOut.toPath(), OutputMode.DexIndexed)
                    .addLibraryFiles(androidJar.toPath());
            for (File cf : classFiles) {
                builder.addProgramFiles(cf.toPath());
            }
            D8.run(builder.build());
        } catch (CompilationFailedException e) {
            throw new Exception("Loader D8 compile failed: " + e.getMessage(), e);
        }

        File dexFile = new File(dexOut, "classes.dex");
        if (!dexFile.exists()) {
            throw new Exception("D8 did not produce classes.dex for loader");
        }
        byte[] dexBytes = Files.readAllBytes(dexFile.toPath());

        deleteRecursive(workDir);
        return dexBytes;
    }

    private File getAndroidJarFile() throws Exception {
        File check = new File(ApplicationLoader.applicationContext.getFilesDir() + "/temp/android.jar");
        if (check.exists()) return check;
        Decompress.unzipFromAssets(ApplicationLoader.applicationContext, "android.jar.zip", check.getParentFile().getAbsolutePath());
        if (!check.exists()) throw new Exception("Failed to extract android.jar");
        return check;
    }

    private List<File> collectClassFiles(File dir) {
        List<File> files = new ArrayList<>();
        File[] children = dir.listFiles();
        if (children == null) return files;
        for (File child : children) {
            if (child.isDirectory()) {
                files.addAll(collectClassFiles(child));
            } else if (child.getName().endsWith(".class")) {
                files.add(child);
            }
        }
        return files;
    }

    private void deleteRecursive(File f) {
        if (f.isDirectory()) {
            File[] children = f.listFiles();
            if (children != null) {
                for (File child : children) deleteRecursive(child);
            }
        }
        f.delete();
    }

    private static class StringWriterOutputStream extends OutputStream {
        StringBuffer buffer = new StringBuffer();
        @Override public void write(int b) { buffer.append((char) b); }
    }
}
