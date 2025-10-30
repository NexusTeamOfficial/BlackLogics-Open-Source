package com.apk.builder.model;

import com.apk.builder.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Library {
    
    private static final String TAG = "Library";
    
    private String mLibraryName;
    private File mPath;
    private File mManifestFile;
    private File mResFile;
    
    private Pattern mPackagePattern = Pattern.compile("(package\\=\".*\")");
    
    public Library(String path) {
        mPath = new File(path);
        mLibraryName = mPath.getName();
        mManifestFile = new File(mPath, "AndroidManifest.xml");
        mResFile = new File(mPath, "res");
        // Log initialization
        System.out.println("[" + TAG + "] Initializing library: " + mLibraryName + ", Path: " + mPath.getAbsolutePath());
        if (mResFile.exists()) {
            System.out.println("[" + TAG + "] Resource path exists: " + mResFile.getAbsolutePath());
        } else {
            System.out.println("[" + TAG + "] Resource path does not exist: " + mResFile.getAbsolutePath());
        }
        File classJar = getClassJarFile();
        if (classJar.exists()) {
            System.out.println("[" + TAG + "] Classes JAR exists: " + classJar.getAbsolutePath());
        } else {
            System.out.println("[" + TAG + "] Classes JAR missing: " + classJar.getAbsolutePath());
        }
    }
    
    public static List<Library> fromFile(File file) {
        List<Library> libraries = new ArrayList<>();
        
        if (!file.exists()) {
            System.out.println("[" + TAG + "] Directory does not exist: " + file.getAbsolutePath());
            return libraries;
        }
        
        File[] childs = file.listFiles();
        if (childs == null) {
            System.out.println("[" + TAG + "] No files found in directory: " + file.getAbsolutePath());
            return libraries;
        }
        
        for (File child : childs) {
            if (child.isDirectory()) {
                File classesJar = new File(child, "classes.jar");
                if (classesJar.exists()) {
                    Library lib = new Library(child.getAbsolutePath());
                    libraries.add(lib);
                    System.out.println("[" + TAG + "] Added library: " + lib.getName() + ", classes.jar: " + classesJar.getAbsolutePath());
                } else {
                    System.out.println("[" + TAG + "] No classes.jar found in: " + child.getAbsolutePath());
                }
            }
        }
        
        if (libraries.isEmpty()) {
            System.out.println("[" + TAG + "] No valid libraries found in: " + file.getAbsolutePath());
        }
        return libraries;
    }
    
    public File getPath() {
        return mPath;
    }
    
    public String getName() {
        return mLibraryName;
    }
    
    public void setName(String name) {
        this.mLibraryName = name;
    }
    
    public File getResourcesFile() {
        return mResFile;
    }
    
    public void setResPath(String resPath) {
        this.mResFile = new File(resPath);
    }
    
    public File getClassJarFile() {
        return new File(mPath, "classes.jar");
    }
    
    public File getManifestFile() {
        return mManifestFile;
    }
    
    public void setManifestPath(String manifestPath) {
        this.mManifestFile = new File(manifestPath);
    }
    
    public List<File> getDexFiles() {
        List<File> files = new ArrayList<>();
        File[] fileArr = mPath.listFiles();
        if (fileArr == null) {
            System.out.println("[" + TAG + "] No files found in library path: " + mPath.getAbsolutePath());
            return files;
        }
        
        for (File file : fileArr) {
            if (file.getName().endsWith(".dex")) {
                files.add(file);
                System.out.println("[" + TAG + "] Found DEX file: " + file.getAbsolutePath());
            }
        }
        
        if (files.isEmpty()) {
            File classJar = getClassJarFile();
            if (classJar.exists()) {
                files.add(classJar);
                System.out.println("[" + TAG + "] No DEX files, using JAR: " + classJar.getAbsolutePath());
            } else {
                System.out.println("[" + TAG + "] No DEX or JAR files found in: " + mPath.getAbsolutePath());
            }
        }
        
        return files;
    }
    
    public boolean requiresResourceFile() {
        boolean exists = mResFile.exists();
        if (!exists) {
            System.out.println("[" + TAG + "] Resource file missing for library " + mLibraryName + ": " + mResFile.getAbsolutePath());
        }
        return exists;
    }
    
    public String getPackageName() {
        String manifest = FileUtil.readFile(mManifestFile.getAbsolutePath());
        if (manifest == null || manifest.isEmpty()) {
            System.out.println("[" + TAG + "] Manifest file empty or missing for library: " + mLibraryName);
            return null;
        }
        Matcher matcher = mPackagePattern.matcher(manifest);
        if (matcher.find()) {
            String packageName = matcher.group(1).substring(9, matcher.group(1).length() - 1);
            System.out.println("[" + TAG + "] Package name for " + mLibraryName + ": " + packageName);
            return packageName;
        }
        System.out.println("[" + TAG + "] Package name not found in manifest for: " + mLibraryName);
        return null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(mLibraryName, library.mLibraryName) &&
               Objects.equals(mPath.getAbsolutePath(), library.mPath.getAbsolutePath());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mLibraryName, mPath.getAbsolutePath());
    }
}