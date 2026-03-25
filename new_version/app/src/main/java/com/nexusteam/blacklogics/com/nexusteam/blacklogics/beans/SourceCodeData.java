package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SourceCodeData implements Parcelable {
    private String code;
    private String title;
    private String language;
    private int textSize;
    private boolean editable;
    private boolean wordWrap;
    

    public SourceCodeData() {
        this.code = "";
        this.title = "Source code";
        this.language = "java";
        this.textSize = 12;
        this.editable = false;
        this.wordWrap = false;
    }
    
    public SourceCodeData(String code) {
        this();
        this.code = code;
    }
    

    protected SourceCodeData(Parcel in) {
        code = in.readString();
        title = in.readString();
        language = in.readString();
        textSize = in.readInt();
        editable = in.readByte() != 0;
        wordWrap = in.readByte() != 0;
    }
    
    public static final Creator<SourceCodeData> CREATOR = new Creator<SourceCodeData>() {
        @Override
        public SourceCodeData createFromParcel(Parcel in) {
            return new SourceCodeData(in);
        }
        
        @Override
        public SourceCodeData[] newArray(int size) {
            return new SourceCodeData[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(title);
        dest.writeString(language);
        dest.writeInt(textSize);
        dest.writeByte((byte) (editable ? 1 : 0));
        dest.writeByte((byte) (wordWrap ? 1 : 0));
    }
    

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public int getTextSize() { return textSize; }
    public void setTextSize(int textSize) { this.textSize = textSize; }
    
    public boolean isEditable() { return editable; }
    public void setEditable(boolean editable) { this.editable = editable; }
    
    public boolean isWordWrap() { return wordWrap; }
    public void setWordWrap(boolean wordWrap) { this.wordWrap = wordWrap; }
}