package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BuildSettingsConfig implements Parcelable {
    private String androidJarPath;
    private String classpath;
    private String dexer;
    private String javaVersion;
    private boolean noWarnings;
    private boolean noHttpLegacy;
    private boolean enableLogcat;
    private boolean incrementalBuildActive;
    

    public BuildSettingsConfig() {
        this.androidJarPath = "";
        this.classpath = "";
        this.dexer = "D8";
        this.javaVersion = "1.8";
        this.noWarnings = true;
        this.noHttpLegacy = false;
        this.enableLogcat = true;
        this.incrementalBuildActive = false;
    }
    

    protected BuildSettingsConfig(Parcel in) {
        androidJarPath = in.readString();
        classpath = in.readString();
        dexer = in.readString();
        javaVersion = in.readString();
        noWarnings = in.readByte() != 0;
        noHttpLegacy = in.readByte() != 0;
        enableLogcat = in.readByte() != 0;
        incrementalBuildActive = in.readByte() != 0;
    }
    
    public static final Creator<BuildSettingsConfig> CREATOR = new Creator<BuildSettingsConfig>() {
        @Override
        public BuildSettingsConfig createFromParcel(Parcel in) {
            return new BuildSettingsConfig(in);
        }
        
        @Override
        public BuildSettingsConfig[] newArray(int size) {
            return new BuildSettingsConfig[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(androidJarPath);
        dest.writeString(classpath);
        dest.writeString(dexer);
        dest.writeString(javaVersion);
        dest.writeByte((byte) (noWarnings ? 1 : 0));
        dest.writeByte((byte) (noHttpLegacy ? 1 : 0));
        dest.writeByte((byte) (enableLogcat ? 1 : 0));
        dest.writeByte((byte) (incrementalBuildActive ? 1 : 0));
    }
    

    public String getAndroidJarPath() { return androidJarPath; }
    public void setAndroidJarPath(String androidJarPath) { this.androidJarPath = androidJarPath; }
    
    public String getClasspath() { return classpath; }
    public void setClasspath(String classpath) { this.classpath = classpath; }
    
    public String getDexer() { return dexer; }
    public void setDexer(String dexer) { this.dexer = dexer; }
    
    public String getJavaVersion() { return javaVersion; }
    public void setJavaVersion(String javaVersion) { this.javaVersion = javaVersion; }
    
    public boolean isNoWarnings() { return noWarnings; }
    public void setNoWarnings(boolean noWarnings) { this.noWarnings = noWarnings; }
    
    public boolean isNoHttpLegacy() { return noHttpLegacy; }
    public void setNoHttpLegacy(boolean noHttpLegacy) { this.noHttpLegacy = noHttpLegacy; }
    
    public boolean isEnableLogcat() { return enableLogcat; }
    public void setEnableLogcat(boolean enableLogcat) { this.enableLogcat = enableLogcat; }
    
    public boolean isIncrementalBuildActive() { return incrementalBuildActive; }
    public void setIncrementalBuildActive(boolean incrementalBuildActive) { 
        this.incrementalBuildActive = incrementalBuildActive; 
    }
}