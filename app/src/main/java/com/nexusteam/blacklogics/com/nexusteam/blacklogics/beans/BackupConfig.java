
package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class BackupConfig implements Parcelable {
    private boolean includeLocalLibraries;
    private boolean includeCustomBlocks;
    private boolean restoreLocalLibs;
    private String projectId;
    private String projectName;
    private HashMap<Integer, Boolean> dialogStates;
    
    public BackupConfig() {
        this.includeLocalLibraries = false;
        this.includeCustomBlocks = false;
        this.restoreLocalLibs = false;
        this.projectId = "";
        this.projectName = "";
        this.dialogStates = new HashMap<>();
    }
    
    public BackupConfig(String projectId, String projectName) {
        this();
        this.projectId = projectId;
        this.projectName = projectName;
    }
    
    protected BackupConfig(Parcel in) {
        includeLocalLibraries = in.readByte() != 0;
        includeCustomBlocks = in.readByte() != 0;
        restoreLocalLibs = in.readByte() != 0;
        projectId = in.readString();
        projectName = in.readString();
        int size = in.readInt();
        dialogStates = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            int key = in.readInt();
            boolean value = in.readByte() != 0;
            dialogStates.put(key, value);
        }
    }
    
    public static final Creator<BackupConfig> CREATOR = new Creator<BackupConfig>() {
        @Override
        public BackupConfig createFromParcel(Parcel in) {
            return new BackupConfig(in);
        }
        
        @Override
        public BackupConfig[] newArray(int size) {
            return new BackupConfig[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (includeLocalLibraries ? 1 : 0));
        dest.writeByte((byte) (includeCustomBlocks ? 1 : 0));
        dest.writeByte((byte) (restoreLocalLibs ? 1 : 0));
        dest.writeString(projectId);
        dest.writeString(projectName);
        dest.writeInt(dialogStates.size());
        for (HashMap.Entry<Integer, Boolean> entry : dialogStates.entrySet()) {
            dest.writeInt(entry.getKey());
            dest.writeByte((byte) (entry.getValue() ? 1 : 0));
        }
    }
    

    public boolean isIncludeLocalLibraries() { return includeLocalLibraries; }
    public void setIncludeLocalLibraries(boolean includeLocalLibraries) { 
        this.includeLocalLibraries = includeLocalLibraries; 
    }
    
    public boolean isIncludeCustomBlocks() { return includeCustomBlocks; }
    public void setIncludeCustomBlocks(boolean includeCustomBlocks) { 
        this.includeCustomBlocks = includeCustomBlocks; 
    }
    
    public boolean isRestoreLocalLibs() { return restoreLocalLibs; }
    public void setRestoreLocalLibs(boolean restoreLocalLibs) { 
        this.restoreLocalLibs = restoreLocalLibs; 
    }
    
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    
    public HashMap<Integer, Boolean> getDialogStates() { return dialogStates; }
    public void setDialogStates(HashMap<Integer, Boolean> dialogStates) { 
        this.dialogStates = dialogStates; 
    }
}