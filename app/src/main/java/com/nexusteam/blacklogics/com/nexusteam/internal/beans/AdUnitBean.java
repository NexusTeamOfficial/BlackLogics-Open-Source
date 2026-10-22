package com.nexusteam.internal.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class AdUnitBean implements Parcelable {
    public static final Parcelable.Creator<AdUnitBean> CREATOR = new Parcelable.Creator<AdUnitBean>() {
        public AdUnitBean createFromParcel(Parcel parcel) {
            return new AdUnitBean(parcel);
        }

        public AdUnitBean[] newArray(int i) {
            return new AdUnitBean[i];
        }
    };
    @Expose
    public String id;
    @Expose
    public String name;

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public AdUnitBean() {
        this("", "");
    }

    public AdUnitBean(String str, String str2) {
        this.id = str;
        this.name = str2;
    }

    public AdUnitBean(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
    }

    public void copy(AdUnitBean adUnitBean) {
        this.id = adUnitBean.id;
        this.name = adUnitBean.name;
    }

    public AdUnitBean clone() {
        AdUnitBean adUnitBean = new AdUnitBean();
        adUnitBean.copy(this);
        return adUnitBean;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
    }

    public static Parcelable.Creator<AdUnitBean> getCreator() {
        return CREATOR;
    }
}
