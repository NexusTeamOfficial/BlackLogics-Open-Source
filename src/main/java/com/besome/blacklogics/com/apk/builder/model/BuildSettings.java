package com.apk.builder.model;

import java.io.File;

public class BuildSettings {
    
    public enum DexCompilerType {
        D8,  // Use D8 (modern, default)
        DX,  // Use DX (legacy)
        R8
    }
    
    private DexCompilerType dexCompilerType = DexCompilerType.D8; // Default to D8
    private int minSdk = 14; // Default min SDK
    private int targetSdk = 33; // Default target SDK
    private String versionName = "1.0";
    private int versionCode = 1;
    private File proguardFile;
    private String projectName = "MyProject";
    
    public BuildSettings() {
    }
    
    // Getters and Setters
    public DexCompilerType getDexCompilerType() {
        return dexCompilerType;
    }
    
    public void setDexCompilerType(DexCompilerType type) {
        this.dexCompilerType = type;
    }
    
    public int getMinSdk() {
        return minSdk;
    }
    
    public void setMinSdk(int minSdk) {
        this.minSdk = minSdk;
    }
    
    public int getTargetSdk() {
        return targetSdk;
    }
    
    public void setTargetSdk(int targetSdk) {
        this.targetSdk = targetSdk;
    }
    
    public String getVersionName() {
        return versionName;
    }
    
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    
    public int getVersionCode() {
        return versionCode;
    }
    
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
    
    public File getProguardFile() {
        return proguardFile;
    }
    
    public void setProguardFile(File proguardFile) {
        this.proguardFile = proguardFile;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}