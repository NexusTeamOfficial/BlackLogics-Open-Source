package com.tyron.compiler;

import android.util.Log;

import com.tyron.compiler.exception.CompilerException;

import com.apk.builder.FileUtil;
import com.apk.builder.BinaryExecutor;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.ApplicationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.Enumeration;

public class AAPT2Compiler extends Compiler {
    
    private static final String TAG = "AAPT2";
    
    private Project mProject;
    private final File mFilesDir;
    private List<Library> mLibraries;
    private List<Library> mLibrariesCopy;
    
    private File binDir;
    private File genDir;
    private File outputPath;
    private File resPath;
    
    private BinaryExecutor executor;
    
    private Set<String> compiledLibraries;
    
    public AAPT2Compiler(Project project) {
        mProject = project;
        mFilesDir = ApplicationLoader.applicationContext.getFilesDir();
        compiledLibraries = new HashSet<String>();
    }
    
    @Override
    public void prepare() throws CompilerException {
        mProject.getLogger().d(TAG, "Preparing");
        onProgressUpdate("Preparing AAPT2...");
        
        mLibraries = new ArrayList<Library>();
        mLibraries.addAll(mProject.getLibraries());
        mLibrariesCopy = new ArrayList<Library>(mLibraries);
        
        for (Library library : mLibraries) {
            mProject.getLogger().d(TAG, "Library: " + library.getName() + ", Resource Path: " + library.getResourcesFile().getAbsolutePath());
            if (!library.getResourcesFile().exists()) {
                mProject.getLogger().w(TAG, "Library resource directory missing: " + library.getResourcesFile().getAbsolutePath());
            }
        }
        
        binDir = new File(mProject.getOutputFile(), "bin");
        genDir = new File(mProject.getOutputFile(), "gen");
        deleteRecursive(genDir);
        
        File resDir = new File(binDir, "res");
        if (resDir.exists()) {
            File[] files = resDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".zip")) {
                        file.delete();
                        mProject.getLogger().d(TAG, "Deleted cached resource: " + file.getName());
                    }
                }
            }
        }
        
        File[] childs = binDir.listFiles();
        if (childs != null) {
            for (File child : childs) {
                if (child.getName().equals("res")) {
                    continue;
                }
                child.delete();
            }
        }
        
        FileUtil.makeDir(binDir.getPath());
        FileUtil.makeDir(genDir.getPath());
        
        compiledLibraries.clear();
    }
    
    @Override
    public void run() throws CompilerException, IOException {
        ArrayList<String> args = new ArrayList<String>();
        executor = new BinaryExecutor();
        
        onProgressUpdate("AAPT2 > Compiling resources");
        mProject.getLogger().d(TAG, "Compiling project resources. Total resources: " + mProject.getResourcesFiles().size());
        
        resPath = new File(binDir, "res");
        resPath.mkdir();
        
        compiledLibraries.clear();
        
        if (mProject.getResourcesFiles() != null) {
            for (File resourcesDir : mProject.getResourcesFiles()) {
                if (!resourcesDir.exists()) {
                    mProject.getLogger().w(TAG, "Resources directory does not exist: " + resourcesDir.getAbsolutePath());
                    continue;
                }
                
                boolean isLibraryResource = false;
                for (Library library : mLibraries) {
                    if (library.getResourcesFile().equals(resourcesDir)) {
                        isLibraryResource = true;
                        break;
                    }
                }
                
                if (isLibraryResource) {
                    mProject.getLogger().d(TAG, "Skipping library resource in main loop: " + resourcesDir.getAbsolutePath());
                    continue;
                }
                
                mProject.getLogger().d(TAG, "Compiling project resource directory: " + resourcesDir.getAbsolutePath());
                args.clear();
                args.add(getAAPT2File().getAbsolutePath());
                args.add("compile");
                args.add("--dir");
                args.add(resourcesDir.getAbsolutePath());
                args.add("-o");
                
                String outputFileName = "project_" + Math.abs(resourcesDir.getAbsolutePath().hashCode()) + ".zip";
                mProject.getLogger().d(TAG, "Output zip for " + resourcesDir.getAbsolutePath() + " -> " + outputFileName);
                outputPath = createNewFile(resPath, outputFileName);
                
                args.add(outputPath.getAbsolutePath());
                mProject.getLogger().d(TAG, "Compile command: " + args);
                
                executor.setCommands(args);
                String result = executor.execute();
                mProject.getLogger().d(TAG, "Compile output: " + result);
                if (!result.isEmpty()) {
                    mProject.getLogger().e(TAG, "Resource compilation failed: " + result);
                    throw new CompilerException(result);
                }
            }
        }
        
        onProgressUpdate("AAPT2 > Compiling libraries");
        compileLibraries();
        
        args.clear();
        
        onProgressUpdate("AAPT2 > Linking resources");
        mProject.getLogger().d(TAG, "Linking resources");
        
        args.add(getAAPT2File().getAbsolutePath());
        args.add("link");
        args.add("--allow-reserved-package-id");
        args.add("--no-version-vectors");
        args.add("--no-version-transitions");
        args.add("--auto-add-overlay");
        args.add("--min-sdk-version");
        args.add(String.valueOf(mProject.getMinSdk()));
        args.add("--target-sdk-version");
        args.add(String.valueOf(mProject.getTargetSdk()));
        args.add("--version-code");
        args.add(String.valueOf(mProject.getVersionCode()));
        args.add("--version-name");
        args.add(String.valueOf(mProject.getVersionName()));
        args.add("-I");
        args.add(getAndroidJarFile().getAbsolutePath());
        
        if (mProject.getAssetsFile() != null) {
            args.add("-A");
            args.add(mProject.getAssetsFile().getAbsolutePath());
        }
        
        File[] resources = resPath.listFiles();
        if (resources != null) {
            for (File file : resources) {
                if (file.isDirectory()) {
                    continue;
                }
                if (file.getName().endsWith(".zip") && hasValidResources(file)) {
                    args.add("-R");
                    args.add(file.getAbsolutePath());
                    mProject.getLogger().d(TAG, "Adding resource file: " + file.getName());
                }
            }
        }
        
        args.add("--java");
        args.add(genDir.getAbsolutePath() + "/");
        
        args.add("--manifest");
        args.add(mProject.getManifestFile().getAbsolutePath());
        
        StringBuilder sb = new StringBuilder();
        for (Library library : mLibrariesCopy) {
            if (!library.requiresResourceFile()) continue;
            
            File resFile = library.getResourcesFile();
            if (!resFile.exists() || !hasActualResources(resFile)) {
                mProject.getLogger().w(TAG, "Skipping R.java — no real resources: " + library.getName());
                continue;
            }
            
            String packageName = getValidPackageName(library);
            if (packageName == null) {
                mProject.getLogger().w(TAG, "Skipping R.java — invalid package name: " + library.getName());
                continue;
            }
            
            mProject.getLogger().d(TAG, "Adding extra package: " + packageName);
            if (sb.length() > 0) {
                sb.append(":");
            }
            sb.append(packageName);
        }
        
        if (sb.length() > 0) {
            args.add("--extra-packages");
            args.add(sb.substring(0, sb.length()));
            mProject.getLogger().d(TAG, "Extra packages: " + sb.toString());
        } else {
            mProject.getLogger().d(TAG, "No valid extra packages to include");
        }
        
        args.add("-o");
        args.add(createNewFile(binDir, "generated.apk.res").getAbsolutePath());
        mProject.getLogger().d(TAG, "Link command: " + args);
        
        executor.setCommands(args);
        String result = executor.execute();
        mProject.getLogger().d(TAG, "Link output: " + result);
        if (!result.isEmpty()) {
            mProject.getLogger().e(TAG, "Resource linking failed: " + result);
            throw new CompilerException(result);
        }
        
        cleanupTempFiles();
    }
    
    private void compileLibraries() throws CompilerException, IOException {
        ArrayList<String> args = new ArrayList<String>();
        
        for (Library library : mLibraries) {
            if (compiledLibraries.contains(library.getName())) {
                mProject.getLogger().d(TAG, "Skipping duplicate library: " + library.getName());
                continue;
            }
            
            File resFile = library.getResourcesFile();
            if (!resFile.exists()) {
                mProject.getLogger().w(TAG, "Resource folder doesn't exist at path: " + resFile.getAbsolutePath());
                continue;
            }
            
            if (!hasActualResources(resFile)) {
                mProject.getLogger().d(TAG, "Library has no resources, skipping: " + library.getName());
                continue;
            }
            
            String packageName = getValidPackageName(library);
            if (packageName == null) {
                mProject.getLogger().e(TAG, "Cannot compile library without package name: " + library.getName());
                continue;
            }
            
            mProject.getLogger().d(TAG, "Compiling library: " + library.getName() + 
                " with package: " + packageName + " at " + resFile.getAbsolutePath());
            
            args.clear();
            args.add(getAAPT2File().getAbsolutePath());
            args.add("compile");
            args.add("--dir");
            args.add(resFile.getAbsolutePath());
            args.add("-o");
            File output = createNewFile(resPath, "lib_" + library.getName() + "_" +
                Math.abs(resFile.getAbsolutePath().hashCode()) + ".zip");
            args.add(output.getAbsolutePath());
            
            
            mProject.getLogger().d(TAG, "Library compile command: " + args);
            
            executor.setCommands(args);
            String result = executor.execute();
            mProject.getLogger().d(TAG, "Library compile output: " + result);
            if (!result.isEmpty()) {
                mProject.getLogger().e(TAG, "Failed to compile library " + library.getName() + ": " + result);
                throw new CompilerException("AAPT2: " + result);
            }
            
            compiledLibraries.add(library.getName());
        }
    }
    
    private String getValidPackageName(Library library) {
        String libName = library.getName();
        
        if (libName != null && libName.toLowerCase().contains("androidx")) {
            return getAndroidXPackage(libName);
        }
        
        String fromManifest = getPackageFromManifest(library);
        if (isValidPackage(fromManifest)) {
            return fromManifest;
        }
        
        String fromLibrary = library.getPackageName();
        if (isValidPackage(fromLibrary)) {
            return fromLibrary;
        }
        
        if (libName == null || libName.isEmpty()) {
            libName = "library_" + System.currentTimeMillis();
        }
        
        String generated = "com.lib." + libName.toLowerCase()
            .replaceAll("[^a-z0-9]", "_")
            .replaceAll("_+", "_")
            .replaceAll("^_|_$", "");
        
        if (generated.isEmpty() || generated.equals("com.lib.")) {
            generated = "com.lib." + System.currentTimeMillis();
        }
        
        mProject.getLogger().w(TAG, "Generated package for " + libName + ": " + generated);
        return generated;
    }
    
    private String getAndroidXPackage(String libName) {
        if (libName.contains("appcompat")) return "androidx.appcompat";
        if (libName.contains("material")) return "com.google.android.material";
        if (libName.contains("core")) return "androidx.core";
        if (libName.contains("constraintlayout")) return "androidx.constraintlayout";
        if (libName.contains("recyclerview")) return "androidx.recyclerview";
        if (libName.contains("cardview")) return "androidx.cardview";
        if (libName.contains("fragment")) return "androidx.fragment";
        if (libName.contains("lifecycle")) return "androidx.lifecycle";
        if (libName.contains("room")) return "androidx.room";
        if (libName.contains("paging")) return "androidx.paging";
        if (libName.contains("navigation")) return "androidx.navigation";
        if (libName.contains("work")) return "androidx.work";
        if (libName.contains("biometric")) return "androidx.biometric";
        if (libName.contains("camera")) return "androidx.camera";
        if (libName.contains("media")) return "androidx.media";
        if (libName.contains("preference")) return "androidx.preference";
        if (libName.contains("legacy")) return "androidx.legacy";
        if (libName.contains("multidex")) return "androidx.multidex";
        if (libName.contains("test")) return "androidx.test";
        
        String generated = "androidx." + libName.toLowerCase()
            .replace("androidx", "")
            .replaceAll("[^a-z0-9]", "_")
            .replaceAll("_+", "_")
            .replaceAll("^_|_$", "");
        
        if (generated.isEmpty() || generated.equals("androidx.")) {
            generated = "androidx.library";
        }
        
        return generated;
    }
    
    private String getPackageFromManifest(Library library) {
        BufferedReader reader = null;
        try {
            File manifest = new File(library.getPath(), "AndroidManifest.xml");
            if (!manifest.exists()) {
                return null;
            }
            
            reader = new BufferedReader(new FileReader(manifest));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("package=")) {
                    int start = line.indexOf("package=\"");
                    if (start != -1) {
                        start += 9;
                        int end = line.indexOf("\"", start);
                        if (end != -1) {
                            String pkg = line.substring(start, end).trim();
                            return pkg;
                        }
                    }
                }
            }
        } catch (Exception e) {
            mProject.getLogger().w(TAG, "Failed to read manifest for: " + library.getName());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
    
    private boolean isValidPackage(String packageName) {
        if (packageName == null) return false;
        if (packageName.trim().isEmpty()) return false;
        if (packageName.equalsIgnoreCase("null")) return false;
        if (!packageName.matches("[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)+")) {
            return false;
        }
        return true;
    }
    
    private boolean hasActualResources(File resDir) {
        if (!resDir.exists() || !resDir.isDirectory()) return false;
        File[] subDirs = resDir.listFiles();
        if (subDirs == null || subDirs.length == 0) return false;
        for (File sub : subDirs) {
            if (sub.isDirectory()) {
                File[] files = sub.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile()) return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean hasValidResources(File zipFile) {
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && (entry.getName().endsWith(".flat") || 
                    entry.getName().endsWith(".xml") || 
                    entry.getName().endsWith(".png") ||
                    entry.getName().endsWith(".jpg") ||
                    entry.getName().endsWith(".9.png"))) {
                    zip.close();
                    return true;
                }
            }
            zip.close();
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    private void deleteRecursive(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                deleteRecursive(child);
            }
        }
        file.delete();
    }

    private File createNewFile(File parent, String name) throws IOException {
        File createdFile = new File(parent, name);
        parent.mkdirs();
        createdFile.createNewFile();
        return createdFile;
    }
    
    private File getAAPT2File() throws CompilerException, IOException {
        File nativeLibrary = new File(ApplicationLoader.applicationContext.getApplicationInfo().nativeLibraryDir + "/libaapt2.so");
        
        if (!nativeLibrary.exists()) {
            throw new CompilerException("AAPT2 binary not found");
        }
        
        mProject.getLogger().d(TAG, "AAPT2 binary path: " + nativeLibrary.getAbsolutePath());
        return nativeLibrary;
    }
    
    private void cleanupTempFiles() {
        mProject.getLogger().d(TAG, "🧹 Cleaning up AAPT2 temporary files...");
        
        if (resPath != null && resPath.exists()) {
            File[] tempFiles = resPath.listFiles();
            if (tempFiles != null) {
                int deletedCount = 0;
                for (File file : tempFiles) {
                    if (file.getName().endsWith(".zip")) {
                        if (file.delete()) {
                            deletedCount++;
                        }
                    }
                }
                mProject.getLogger().d(TAG, "Deleted " + deletedCount + " temp resource files");
            }
        }
        
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        
        Runtime runtime = Runtime.getRuntime();
        mProject.getLogger().d(TAG, "💾 Memory after AAPT2 cleanup: " +
            (runtime.freeMemory() / (1024 * 1024)) + "MB free / " +
            (runtime.totalMemory() / (1024 * 1024)) + "MB total");
    }
}