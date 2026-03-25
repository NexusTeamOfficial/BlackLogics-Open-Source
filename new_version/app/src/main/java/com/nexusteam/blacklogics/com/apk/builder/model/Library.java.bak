package com.apk.builder.model;

import com.apk.builder.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Library {
    
    private String mLibraryName;
    private File mPath;
    private File mManifestFile;
    private File mResFile;
    
    private Pattern mPackagePattern = Pattern.compile("(package\\=\".*\")");
    
    public Library(String path) {
        mPath = new File(path);
        mLibraryName = mPath.getName();
        // Initialize with default paths
        mManifestFile = new File(mPath, "AndroidManifest.xml");
        mResFile = new File(mPath, "res");
        // Log initialization for debugging
        if (mResFile.exists()) {
            System.out.println("Library " + mLibraryName + " resource path: " + mResFile.getAbsolutePath());
        } else {
            System.out.println("Library " + mLibraryName + " resource path does not exist: " + mResFile.getAbsolutePath());
        }
    }
    
    public static List<Library> fromFile(File file){
        List<Library> libraries = new ArrayList<>();
        
        if (!file.exists()) {
            System.out.println("Library directory does not exist: " + file.getAbsolutePath());
            return libraries;
        }
        
        File[] childs = file.listFiles();
        if (childs == null) {
            System.out.println("No files found in library directory: " + file.getAbsolutePath());
            return libraries;
        }
        
        for (File child : childs) {
            if (new File(child, "classes.jar").exists()) {
                Library lib = new Library(child.getAbsolutePath());
                libraries.add(lib);
                System.out.println("Found library: " + lib.getName());
            }
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
            System.out.println("No files found in library path: " + mPath.getAbsolutePath());
            return files;
        }
        
        for (File file : fileArr) {
            if (file.getName().endsWith(".dex")) {
                files.add(file);
            }
        }
        return files;
    }
    
    public boolean requiresResourceFile() {
        boolean exists = mResFile.exists();
        if (!exists) {
            System.out.println("Resource file does not exist for library " + mLibraryName + ": " + mResFile.getAbsolutePath());
        }
        return exists;
    }
    
    public String getPackageName() {
        String manifest = FileUtil.readFile(mManifestFile.getAbsolutePath());
        Matcher matcher = mPackagePattern.matcher(manifest);
        
        if (matcher.find()) {
            return matcher.group(1).substring(9, matcher.group(1).length() -1);
        }
        
        System.out.println("Package name not found in manifest for library: " + mLibraryName);
        return null;
    }
}