package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EditorTheme implements Parcelable {
    private String name;
    private int id;
    private int colorScheme;

    public EditorTheme(String name, int id, int colorScheme) {
        this.name = name;
        this.id = id;
        this.colorScheme = colorScheme;
    }

    protected EditorTheme(Parcel in) {
        name = in.readString();
        id = in.readInt();
        colorScheme = in.readInt();
    }

    public static final Creator<EditorTheme> CREATOR = new Creator<EditorTheme>() {
        @Override
        public EditorTheme createFromParcel(Parcel in) {
            return new EditorTheme(in);
        }

        @Override
        public EditorTheme[] newArray(int size) {
            return new EditorTheme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(colorScheme);
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getColorScheme() {
        return colorScheme;
    }
}