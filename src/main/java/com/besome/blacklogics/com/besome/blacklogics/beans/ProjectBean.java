package com.besome.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProjectBean implements Parcelable {

    public static final Parcelable.Creator<ProjectBean> CREATOR = new Parcelable.Creator<ProjectBean>() {
        @Override
        public ProjectBean createFromParcel(Parcel source) {
            return new ProjectBean(source);
        }

        @Override
        public ProjectBean[] newArray(int size) {
            return new ProjectBean[size];
        }
    };

    public String apkName;
    public ArrayList<String> images;
    public String pkgName;
    public ArrayList<String> screens;
    public String currentScreen; 

    public ProjectBean() {
        pkgName = "";
        apkName = "";
        screens = new ArrayList<>();
        images = new ArrayList<>();
        currentScreen = ""; 
    }

    public ProjectBean(Parcel other) {
        pkgName = other.readString();
        apkName = other.readString();
        screens = (ArrayList<String>) other.readSerializable();
        images = (ArrayList<String>) other.readSerializable();
        currentScreen = other.readString(); 
    }

    public static Parcelable.Creator<ProjectBean> getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pkgName);
        dest.writeString(apkName);
        dest.writeSerializable(screens);
        dest.writeSerializable(images);
        dest.writeString(currentScreen); 
    }
    
    public void setCurrentScreen(String screenName) {
        if (screens.contains(screenName)) {
            currentScreen = screenName;
        }
    }

    public String getCurrentScreen() {
        return currentScreen;
    }
}
