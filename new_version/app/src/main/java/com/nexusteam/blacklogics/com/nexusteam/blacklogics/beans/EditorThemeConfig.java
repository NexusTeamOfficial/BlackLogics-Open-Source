package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EditorThemeConfig implements Parcelable {
    private String themeName;
    private String themeFile;
    private String displayName;
    private boolean isDarkTheme;

    public EditorThemeConfig(String themeName, String themeFile, String displayName, boolean isDarkTheme) {
        this.themeName = themeName;
        this.themeFile = themeFile;
        this.displayName = displayName;
        this.isDarkTheme = isDarkTheme;
    }

    protected EditorThemeConfig(Parcel in) {
        themeName = in.readString();
        themeFile = in.readString();
        displayName = in.readString();
        isDarkTheme = in.readByte() != 0;
    }

    public static final Creator<EditorThemeConfig> CREATOR = new Creator<EditorThemeConfig>() {
        @Override
        public EditorThemeConfig createFromParcel(Parcel in) {
            return new EditorThemeConfig(in);
        }

        @Override
        public EditorThemeConfig[] newArray(int size) {
            return new EditorThemeConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(themeName);
        dest.writeString(themeFile);
        dest.writeString(displayName);
        dest.writeByte((byte) (isDarkTheme ? 1 : 0));
    }


    public String getThemeName() {
        return themeName;
    }

    public String getThemeFile() {
        return themeFile;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }
}