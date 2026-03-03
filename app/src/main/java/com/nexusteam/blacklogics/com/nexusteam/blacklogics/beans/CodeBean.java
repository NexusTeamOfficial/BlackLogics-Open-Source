package com.besome.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class CodeBean implements Parcelable {

    public static final Parcelable.Creator<CodeBean> CREATOR = new Parcelable.Creator<CodeBean>() {
        @Override
        public CodeBean createFromParcel(Parcel source) {
            return new CodeBean(source);
        }

        @Override
        public CodeBean[] newArray(int size) {
            return new CodeBean[size];
        }
    };

    public String fileName; 
    public String codeContent;
    public String language;

    public CodeBean() {
        fileName = "";
        codeContent = "";
        language = "";
    }

    public CodeBean(Parcel other) {
        fileName = other.readString();
        codeContent = other.readString();
        language = other.readString();
    }

    public static Parcelable.Creator<CodeBean> getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(codeContent);
        dest.writeString(language);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}