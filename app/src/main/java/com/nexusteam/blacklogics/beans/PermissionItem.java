
package com.nexusteam.blacklogics.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PermissionItem implements Parcelable {
    private String permissionName;
    private boolean isSelected;

    public PermissionItem() {
    }

    public PermissionItem(String permissionName) {
        this.permissionName = permissionName;
        this.isSelected = false;
    }

    public PermissionItem(String permissionName, boolean isSelected) {
        this.permissionName = permissionName;
        this.isSelected = isSelected;
    }

    protected PermissionItem(Parcel in) {
        permissionName = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(permissionName);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PermissionItem> CREATOR = new Creator<PermissionItem>() {
        @Override
        public PermissionItem createFromParcel(Parcel in) {
            return new PermissionItem(in);
        }

        @Override
        public PermissionItem[] newArray(int size) {
            return new PermissionItem[size];
        }
    };

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}