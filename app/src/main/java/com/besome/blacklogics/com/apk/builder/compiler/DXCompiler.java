package com.tyron.compiler;

import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.CompilerException;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.writer.io.FileDataStore;
import org.jf.dexlib2.writer.pool.DexPool;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
    }

    @Override
    public void run() throws CompilerException {
        onProgressUpdate("DX > Converting to DEX...");
        mProject.getLogger().d(TAG, "Starting DEX conversion...");

        File outputDex = new File(mProject.getOutputFile(), "bin/classes.dex");
        File classesDir = new File(mProject.getOutputFile(), "bin/classes");

        try {
            DexPool dexPool = new DexPool(Opcodes.forApi(24)); // Android 7.0 API level

            // Process project classes
            processClassFiles(classesDir, dexPool);

            // Process libraries
            for (Library library : mProject.getLibraries()) {
                File jarFile = library.getClassJarFile();
                if (jarFile.exists()) {
                    processJarFile(jarFile, dexPool);
                }
            }

            // Write final DEX file (simplified version)
            dexPool.writeTo(new FileDataStore(outputDex));

            onProgressUpdate("DX > DEX conversion complete");
            mProject.getLogger().d(TAG, "DEX conversion successful");

        } catch (Exception e) {
            throw new CompilerException("DX failed: " + e.getMessage());
        }
    }

    private void processClassFiles(File dir, DexPool dexPool) throws IOException {
        List<File> classFiles = getClassFiles(dir);
        for (File classFile : classFiles) {
            try (FileInputStream fis = new FileInputStream(classFile)) {
                ClassReader classReader = new ClassReader(fis);
                ClassDef classDef = AsmClassDef.fromClassReader(classReader);
                if (classDef != null) {
                    dexPool.internClass(classDef);
                }
            }
        }
    }

    private void processJarFile(File jarFile, DexPool dexPool) throws IOException {
        try (ZipFile zipFile = new ZipFile(jarFile)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    ClassReader classReader = new ClassReader(zipFile.getInputStream(entry));
                    ClassDef classDef = AsmClassDef.fromClassReader(classReader);
                    if (classDef != null) {
                        dexPool.internClass(classDef);
                    }
                }
            }
        }
    }

    private List<File> getClassFiles(File dir) {
        List<File> files = new ArrayList<>();
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
        return files;
    }
}