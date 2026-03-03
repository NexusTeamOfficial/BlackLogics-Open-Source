package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AssetItem implements Parcelable {
    private String path;
    private String name;
    private boolean isFolder;
    private boolean isImage;

    public AssetItem() {}

    public AssetItem(String path, String name, boolean isFolder, boolean isImage) {
        this.path = path;
        this.name = name;
        this.isFolder = isFolder;
        this.isImage = isImage;
    }

    protected AssetItem(Parcel in) {
        path = in.readString();
        name = in.readString();
        isFolder = in.readByte() != 0;
        isImage = in.readByte() != 0;
    }

    public static final Creator<AssetItem> CREATOR = new Creator<AssetItem>() {
        @Override
        public AssetItem createFromParcel(Parcel in) {
            return new AssetItem(in);
        }

        @Override
        public AssetItem[] newArray(int size) {
            return new AssetItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        dest.writeByte((byte) (isFolder ? 1 : 0));
        dest.writeByte((byte) (isImage ? 1 : 0));
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }
}