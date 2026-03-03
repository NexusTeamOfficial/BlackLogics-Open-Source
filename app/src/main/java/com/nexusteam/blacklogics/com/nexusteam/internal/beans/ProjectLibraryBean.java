package com.nexusteam.internal.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Iterator;
import com.nexusteam.blacklogics.R;

public class ProjectLibraryBean implements Parcelable {
    public static final Parcelable.Creator<ProjectLibraryBean> CREATOR = new Parcelable.Creator<ProjectLibraryBean>() {
        public ProjectLibraryBean createFromParcel(Parcel parcel) {
            return new ProjectLibraryBean(parcel);
        }

        public ProjectLibraryBean[] newArray(int i) {
            return new ProjectLibraryBean[i];
        }
    };
    public static final String LIB_USE_N = "N";
    public static final String LIB_USE_Y = "Y";
    public static final int PROJECT_LIB_TYPE_ADMOB = 2;
    public static final int PROJECT_LIB_TYPE_COMPAT = 1;
    public static final int PROJECT_LIB_TYPE_FIREBASE = 0;
    public static final int PROJECT_LIB_TYPE_GOOGLE_MAP = 3;
    @Expose
    public ArrayList<AdUnitBean> adUnits;
    @Expose
    public String data;
    @Expose
    public int libType;
    @Expose
    public String reserved1;
    @Expose
    public String reserved2;
    @Expose
    public String reserved3;
    @Expose
    public ArrayList<AdTestDeviceBean> testDevices;
    @Expose
    public String useYn;

    public static int getLibraryIcon(int i) {
        switch (i) {
            case 0:
                return R.drawable.widget_firebase;
            case 1:
                return R.drawable.connected_96;
            case 2:
                return R.drawable.widget_admob;
            case 3:
                return R.drawable.widget_google_map;
            default:
                return 0;
        }
    }

    public static int getLibraryResDesc(int i) {
        switch (i) {
            case 0:
                return R.string.design_library_description_firebase;
            case 1:
                return R.string.design_library_description_appcompat_and_design;
            case 2:
                return R.string.design_library_description_admob;
            case 3:
                return R.string.design_library_description_google_map;
            default:
                return 0;
        }
    }

    public static int getLibraryResName(int i) {
        switch (i) {
            case 0:
                return R.string.design_library_firebase_title_firebase;
            case 1:
                return R.string.design_library_title_appcompat_and_design;
            case 2:
                return R.string.design_library_admob_title_admob;
            case 3:
                return R.string.design_library_google_map_title;
            default:
                return 0;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public ProjectLibraryBean(int i) {
        this.libType = i;
        this.useYn = LIB_USE_N;
        this.data = "";
        this.reserved1 = "";
        this.reserved2 = "";
        this.reserved3 = "";
        this.adUnits = new ArrayList<>();
        this.testDevices = new ArrayList<>();
    }

    public ProjectLibraryBean(Parcel parcel) {
        this.libType = parcel.readInt();
        this.useYn = parcel.readString();
        this.data = parcel.readString();
        this.reserved1 = parcel.readString();
        this.reserved2 = parcel.readString();
        this.reserved3 = parcel.readString();
        this.adUnits = new ArrayList<>();
        parcel.readTypedList(this.adUnits, AdUnitBean.getCreator());
        this.testDevices = new ArrayList<>();
        parcel.readTypedList(this.testDevices, AdTestDeviceBean.getCreator());
    }

    public void copy(ProjectLibraryBean projectLibraryBean) {
        this.libType = projectLibraryBean.libType;
        this.useYn = projectLibraryBean.useYn;
        this.data = projectLibraryBean.data;
        this.reserved1 = projectLibraryBean.reserved1;
        this.reserved2 = projectLibraryBean.reserved2;
        this.reserved3 = projectLibraryBean.reserved3;
        this.adUnits = new ArrayList<>();
        Iterator<AdUnitBean> it = projectLibraryBean.adUnits.iterator();
        while (it.hasNext()) {
            this.adUnits.add(it.next().clone());
        }
        this.testDevices = new ArrayList<>();
        if (projectLibraryBean.testDevices != null) {
            Iterator<AdTestDeviceBean> it2 = projectLibraryBean.testDevices.iterator();
            while (it2.hasNext()) {
                this.testDevices.add(it2.next().clone());
            }
        }
    }

    public ProjectLibraryBean clone() {
        ProjectLibraryBean projectLibraryBean = new ProjectLibraryBean(this.libType);
        projectLibraryBean.copy(this);
        return projectLibraryBean;
    }

    public boolean isEnabled() {
        return this.useYn != null && !this.useYn.isEmpty() && this.useYn.equals(LIB_USE_Y);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.libType);
        parcel.writeString(this.useYn);
        parcel.writeString(this.data);
        parcel.writeString(this.reserved1);
        parcel.writeString(this.reserved2);
        parcel.writeString(this.reserved3);
        parcel.writeTypedList(this.adUnits);
        parcel.writeTypedList(this.testDevices);
    }

    public static Parcelable.Creator<ProjectLibraryBean> getCreator() {
        return CREATOR;
    }
}
