package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TextMateGrammar implements Parcelable {
    private String name;
    private String fileName;
    private String languageId;
    private String fileExtension;

    public TextMateGrammar(String name, String fileName, String languageId, String fileExtension) {
        this.name = name;
        this.fileName = fileName;
        this.languageId = languageId;
        this.fileExtension = fileExtension;
    }

    protected TextMateGrammar(Parcel in) {
        name = in.readString();
        fileName = in.readString();
        languageId = in.readString();
        fileExtension = in.readString();
    }

    public static final Creator<TextMateGrammar> CREATOR = new Creator<TextMateGrammar>() {
        @Override
        public TextMateGrammar createFromParcel(Parcel in) {
            return new TextMateGrammar(in);
        }

        @Override
        public TextMateGrammar[] newArray(int size) {
            return new TextMateGrammar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(fileName);
        dest.writeString(languageId);
        dest.writeString(fileExtension);
    }


    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getLanguageId() {
        return languageId;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}