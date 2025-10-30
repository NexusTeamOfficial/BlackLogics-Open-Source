package com.tyron.compiler;

import com.apk.builder.ApplicationLoader;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.tyron.compiler.exception.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import mod.hey.studios.project.stringfog.StringfogHandler;

import com.android.tools.r8.D8;

public class D8Compiler extends Compiler {
    
    private static final String TAG = "D8";
    private final Project mProject;
    private StringfogHandler stringfogHandler;
    private List<String> classpath;
    
    public D8Compiler(Project project) {
        this.mProject = project;
        setProject(project);
        stringfogHandler = new StringfogHandler(mProject.getId());
    }
    
    @Override
    public void prepare() throws CompilerException {
        mProject.getLogger().d(TAG, "Preparing D8 compiler");
        System.gc();
        Runtime.getRuntime().gc();
        mProject.getLogger().d(TAG, "Heap free before prepare: " +
            (Runtime.getRuntime().freeMemory() / (1024 * 1024)) + "MB");
        
        // Validate working directory
        File workingDir = mProject.getWorkingDirectory();
        if (workingDir == null || !workingDir.exists() || !workingDir.isDirectory()) {
            throw new CompilerException("Invalid working directory for D8: " +
                (workingDir != null ? workingDir.getAbsolutePath() : "null"));
        }
        
        // Validate AndroidX libraries
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
        mProject.getLogger().d(TAG, "Heap free before run: " +
            (Runtime.getRuntime().freeMemory() / (1024 * 1024)) + "MB");
        
        List<String> args = new ArrayList<>();
        args.add("--release");
        args.add("--min-api");
        args.add(String.valueOf(mProject.getMinSdk()));
        args.add("--lib");
        args.add(getAndroidJarFile().getAbsolutePath());
        
        // Output directory
        File dexOutput = new File(mProject.getOutputFile(), "bin/dex");
        if (!dexOutput.exists() && !dexOutput.mkdirs()) {
            throw new CompilerException("Failed to create DEX output dir: " + dexOutput.getAbsolutePath());
        }
        args.add("--output");
        args.add(dexOutput.getAbsolutePath());
        
        // Add project class files
        List<File> classes = getClassFiles(new File(mProject.getOutputFile(), "bin/classes"));
        mProject.getLogger().d(TAG, "Found " + classes.size() + " class files");
        for (File file : classes) {
            args.add(file.getAbsolutePath());
        }
        
        // Add libraries (JARs or DEX files)
        Set<String> addedFiles = new HashSet<>();
        for (Library library : mProject.getLibraries()) {
            File file = library.getClassJarFile();
            if (file.exists() && addedFiles.add(file.getAbsolutePath())) {
                if (file.getName().endsWith(".dex")) {
                    mProject.getLogger().d(TAG, "Adding DEX file: " + file.getAbsolutePath());
                } else if (file.getName().endsWith(".jar")) {
                    mProject.getLogger().d(TAG, "Adding JAR file: " + file.getAbsolutePath());
                } else {
                    mProject.getLogger().w(TAG, "Skipping unsupported file: " + file.getAbsolutePath());
                    continue;
                }
                args.add(file.getAbsolutePath());
            }
        }
        
        // Removed args.addAll(classpath()) to avoid duplicate class definitions
        // Classpath is only needed for individual library dexing in dexLibrary()
        
        if (stringfogHandler.isStringfogEnabled()) {
            /* try {
                File classesDir = new File(mProject.getOutputFile(), "bin/classes");
                if (classesDir.exists()) {
                    mProject.getLogger().d(TAG, "Starting StringFog encryption...");
                    
                    StringFogImpl fogImpl = new StringFogImpl();
                    RandomKeyGenerator keyGen = new RandomKeyGenerator();
                    
                    StringFogClassInjector injector = new StringFogClassInjector(
                        fogImpl,
                        keyGen,
                        StringFogMode.XOR,
                        classesDir.getAbsolutePath(),
                        classesDir.getAbsolutePath(),
                        mProject.getPackageName()
                    );
                    
                    injector.startInject();
                    
                    File mappingFile = new File(mProject.getOutputFile(), "stringfog-mapping.txt");
                    StringFogMappingPrinter printer = new StringFogMappingPrinter(mappingFile);
                    printer.print(injector.getMapping());
                    
                    mProject.getLogger().d(TAG, "StringFog encryption done. Mapping: " + mappingFile.getAbsolutePath());
                } else {
                    mProject.getLogger().w(TAG, "Class directory not found for StringFog encryption.");
                }
            } catch (Exception ex) {
                mProject.getLogger().e(TAG, "StringFog encryption failed: " + ex.getMessage());
            } */
        }
        
        mProject.getLogger().d(TAG, "Executing D8...");
        try {
            D8.main(args.toArray(new String[0]));
        } catch (Exception e) {
            mProject.getLogger().e(TAG, "D8 compilation failed: " + e.getMessage());
            throw new CompilerException("D8 compilation failed: " + e.getMessage());
        }
        
        mProject.getLogger().d(TAG, "D8 compilation completed successfully");
    }
    
    public List<File> getDexFiles() {
        List<File> files = new ArrayList<>();
        File dexDir = new File(mProject.getOutputFile(), "bin/dex");
        File[] dexArr = dexDir.listFiles();
        if (dexArr != null) {
            for (File file : dexArr) {
                if (file.getName().endsWith(".dex")) {
                    files.add(file);
                }
            }
        }
        return files;
    }
    
    private List<File> getClassFiles(File dir) {
        List<File> files = new ArrayList<>();
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
        List<String> args = new ArrayList<>();
        File classJar = library.getClassJarFile();
        if (!classJar.exists()) {
            throw new CompilerException("Classes.jar missing for library: " + library.getName());
        }
        args.add(classJar.getAbsolutePath());
        args.add("--release");
        args.add("--min-api");
        args.add(String.valueOf(mProject.getMinSdk()));
        args.add("--lib");
        args.add(getAndroidJarFile().getAbsolutePath());
        args.add("--output");
        args.add(library.getPath().getAbsolutePath());
        args.addAll(classpath());
        
        try {
            D8.main(args.toArray(new String[0]));
        } catch (Exception e) {
            mProject.getLogger().e(TAG, "Failed to dex library " + library.getName() + ": " + e.getMessage());
            throw new CompilerException("Failed to dex library " + library.getName() + ": " + e.getMessage());
        }
    }
    
    public List<String> classpath() {
        if (classpath != null) {
            return classpath;
        }
        classpath = new ArrayList<>();
        Set<String> added = new HashSet<>();
        for (Library library : mProject.getLibraries()) {
            File classJar = library.getClassJarFile();
            if (classJar.exists() && added.add(classJar.getAbsolutePath())) {
                classpath.add("--classpath");
                classpath.add(classJar.getAbsolutePath());
            }
        }
        return classpath;
    }
}