package com.nexusteam.internal.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class AdTestDeviceBean implements Parcelable {
    public static final Parcelable.Creator<AdTestDeviceBean> CREATOR = new Parcelable.Creator<AdTestDeviceBean>() {
        public AdTestDeviceBean createFromParcel(Parcel parcel) {
            return new AdTestDeviceBean(parcel);
        }

        public AdTestDeviceBean[] newArray(int i) {
            return new AdTestDeviceBean[i];
        }
    };
    @Expose
    public String deviceId;

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public AdTestDeviceBean() {
        this("");
    }

    public AdTestDeviceBean(String str) {
        this.deviceId = str;
    }

    public AdTestDeviceBean(Parcel parcel) {
        this.deviceId = parcel.readString();
    }

    public void copy(AdTestDeviceBean adTestDeviceBean) {
        this.deviceId = adTestDeviceBean.deviceId;
    }

    public AdTestDeviceBean clone() {
        AdTestDeviceBean adTestDeviceBean = new AdTestDeviceBean();
        adTestDeviceBean.copy(this);
        return adTestDeviceBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
    }

    public static Parcelable.Creator<AdTestDeviceBean> getCreator() {
        return CREATOR;
    }
}
