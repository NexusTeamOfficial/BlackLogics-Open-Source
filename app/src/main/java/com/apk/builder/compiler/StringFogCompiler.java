package com.tyron.compiler;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.FileUtil;
import com.apk.builder.model.Project;
import com.apk.builder.util.Decompress;
import com.tyron.compiler.exception.CompilerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class StringFogCompiler extends Compiler {

    private static final String TAG = "StringFog";

    private final Project mProject;
    private File runtimeDir;

    public StringFogCompiler(Project project) {
        this.mProject = project;
        setProject(project);
        this.runtimeDir = new File(
                ApplicationLoader.applicationContext.getFilesDir(),
                "temp/stringfog");
    }

    @Override
    public void prepare() throws CompilerException, IOException {
        onProgressUpdate("StringFog > Preparing...");
        mProject.getLogger().d(TAG, "Preparing StringFog runtime");

        if (!isEnabled()) {
            mProject.getLogger().d(TAG, "StringFog disabled – skip prepare");
            return;
        }

        FileUtil.makeDir(runtimeDir.getAbsolutePath());

        File marker = new File(runtimeDir,
                "com/github/megatronking/stringfog/xor/StringFogImpl.class");
        if (marker.exists()) {
            mProject.getLogger().d(TAG, "Runtime already extracted");
            return;
        }

        Exception last = null;

        try {
            extractAssetJar("classes.jar", runtimeDir);
            if (marker.exists()) {
                mProject.getLogger().d(TAG, "Extracted classes.jar");
                return;
            }
        } catch (Exception e) {
            last = e;
            mProject.getLogger().d(TAG, "classes.jar: " + e.getMessage());
        }

        try {
            Decompress.unzipFromAssets(
                    ApplicationLoader.applicationContext,
                    "stringfog.zip",
                    runtimeDir.getAbsolutePath());
            if (marker.exists()) {
                mProject.getLogger().d(TAG, "Extracted stringfog.zip");
                return;
            }
        } catch (Exception e) {
            last = e;
            mProject.getLogger().d(TAG, "stringfog.zip: " + e.getMessage());
        }

        try {
            Decompress.unzipFromAssets(
                    ApplicationLoader.applicationContext,
                    "classes.zip",
                    runtimeDir.getAbsolutePath());
            if (marker.exists()) {
                mProject.getLogger().d(TAG, "Extracted classes.zip");
                return;
            }
        } catch (Exception e) {
            last = e;
        }

        throw new CompilerException(
                "StringFog runtime not found. Put classes.jar or stringfog.zip in assets/. "
                        + (last != null ? last.getMessage() : ""));
    }

    @Override
    public void run() throws CompilerException, IOException {
        if (!isEnabled()) {
            onProgressUpdate("StringFog > Skipped (disabled)");
            return;
        }

        onProgressUpdate("StringFog > Injecting runtime into classes...");
        mProject.getLogger().d(TAG, "Copying StringFog classes into class output dir");

        File destDir;
        try {
            destDir = getClassOutputDir();
        } catch (CompilerException e) {
            destDir = new File(mProject.getOutputFile(), "bin/classes");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
        }

        int copied = copyClassFiles(runtimeDir, destDir);
        if (copied == 0) {
            throw new CompilerException(
                    "No StringFog .class files copied. Check assets/stringfog.zip or classes.jar");
        }

        mProject.getLogger().d(TAG, "Injected " + copied + " class file(s) into "
                + destDir.getAbsolutePath());
        onProgressUpdate("StringFog > Complete (" + copied + " classes)");
    }

    private boolean isEnabled() {
        try {
            Class<?> cls = Class.forName(
                    "com.nexusteam.sdk.project.stringfog.StringfogHandler");
            Object h = cls.getConstructor(String.class).newInstance(mProject.getId());
            Object r = cls.getMethod("isStringfogEnabled").invoke(h);
            return r instanceof Boolean && (Boolean) r;
        } catch (Throwable t) {
            mProject.getLogger().d(TAG, "Handler check: " + t.getMessage());
            return false;
        }
    }

    private int copyClassFiles(File srcRoot, File destRoot) throws IOException {
        return copyClassFilesRecursive(srcRoot, srcRoot, destRoot);
    }

    private int copyClassFilesRecursive(File root, File current, File destRoot)
            throws IOException {
        int count = 0;
        File[] files = current.listFiles();
        if (files == null) {
            return 0;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                count += copyClassFilesRecursive(root, f, destRoot);
            } else if (f.getName().endsWith(".class")) {
                String rel = f.getAbsolutePath()
                        .substring(root.getAbsolutePath().length() + 1)
                        .replace('\\', '/');
                File dest = new File(destRoot, rel);
                File parent = dest.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                copyFile(f, dest);
                count++;
            }
        }
        return count;
    }

    private void copyFile(File src, File dest) throws IOException {
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

    private void extractAssetJar(String assetName, File destDir) throws Exception {
        InputStream in = ApplicationLoader.applicationContext.getAssets().open(assetName);
        File tmp = new File(destDir, assetName);
        FileOutputStream out = new FileOutputStream(tmp);
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        unzipFile(tmp, destDir);
    }

    private void unzipFile(File zipFile, File destDir) throws Exception {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry;
        byte[] buf = new byte[8192];
        while ((entry = zis.getNextEntry()) != null) {
            File out = new File(destDir, entry.getName());
            if (entry.isDirectory()) {
                out.mkdirs();
            } else {
                File parent = out.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(out);
                int len;
                while ((len = zis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fos.close();
            }
            zis.closeEntry();
        }
        zis.close();
    }
}
