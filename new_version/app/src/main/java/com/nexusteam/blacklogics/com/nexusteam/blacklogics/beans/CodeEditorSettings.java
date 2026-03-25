package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CodeEditorSettings implements Parcelable {
    private int textSize;
    private int theme;
    private boolean wordWrap;
    private boolean autoComplete;
    private boolean symbolPairAutoComplete;
    private String language;

    public CodeEditorSettings() {
        this.textSize = 12;
        this.theme = 3;
        this.wordWrap = false;
        this.autoComplete = true;
        this.symbolPairAutoComplete = true;
        this.language = "java";
    }

    protected CodeEditorSettings(Parcel in) {
        textSize = in.readInt();
        theme = in.readInt();
        wordWrap = in.readByte() != 0;
        autoComplete = in.readByte() != 0;
        symbolPairAutoComplete = in.readByte() != 0;
        language = in.readString();
    }

    public static final Creator<CodeEditorSettings> CREATOR = new Creator<CodeEditorSettings>() {
        @Override
        public CodeEditorSettings createFromParcel(Parcel in) {
            return new CodeEditorSettings(in);
        }

        @Override
        public CodeEditorSettings[] newArray(int size) {
            return new CodeEditorSettings[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(textSize);
        dest.writeInt(theme);
        dest.writeByte((byte) (wordWrap ? 1 : 0));
        dest.writeByte((byte) (autoComplete ? 1 : 0));
        dest.writeByte((byte) (symbolPairAutoComplete ? 1 : 0));
        dest.writeString(language);
    }


    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public boolean isWordWrap() {
        return wordWrap;
    }

    public void setWordWrap(boolean wordWrap) {
        this.wordWrap = wordWrap;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }

    public void setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
    }

    public boolean isSymbolPairAutoComplete() {
        return symbolPairAutoComplete;
    }

    public void setSymbolPairAutoComplete(boolean symbolPairAutoComplete) {
        this.symbolPairAutoComplete = symbolPairAutoComplete;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}