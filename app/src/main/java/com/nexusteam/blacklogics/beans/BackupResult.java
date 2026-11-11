
package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class BackupResult implements Parcelable {
    private boolean success;
    private String message;
    private String error;
    private File outputFile;
    
    public BackupResult() {
        this.success = false;
        this.message = "";
        this.error = "";
        this.outputFile = null;
    }
    
    public BackupResult(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }
    
    protected BackupResult(Parcel in) {
        success = in.readByte() != 0;
        message = in.readString();
        error = in.readString();
        outputFile = (File) in.readSerializable();
    }
    
    public static final Creator<BackupResult> CREATOR = new Creator<BackupResult>() {
        @Override
        public BackupResult createFromParcel(Parcel in) {
            return new BackupResult(in);
        }
        
        @Override
        public BackupResult[] newArray(int size) {
            return new BackupResult[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(message);
        dest.writeString(error);
        dest.writeSerializable(outputFile);
    }
    

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    
    public File getOutputFile() { return outputFile; }
    public void setOutputFile(File outputFile) { this.outputFile = outputFile; }
}