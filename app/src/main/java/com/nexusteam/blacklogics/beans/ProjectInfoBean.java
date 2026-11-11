package com.nexusteam.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectInfoBean implements Parcelable {

    private String activityName;
    private String layoutName;
    private String scId;


    public ProjectInfoBean(String scId, String activityName, String layoutName) {
        this.activityName = activityName;
        this.layoutName = layoutName;
        this.scId = scId;
    }


    public ProjectInfoBean() {
    }


    protected ProjectInfoBean(Parcel in) {
        activityName = in.readString();
        layoutName = in.readString();
        scId = in.readString();
    }

    public static final Creator<ProjectInfoBean> CREATOR = new Creator<ProjectInfoBean>() {
        @Override
        public ProjectInfoBean createFromParcel(Parcel in) {
            return new ProjectInfoBean(in);
        }

        @Override
        public ProjectInfoBean[] newArray(int size) {
            return new ProjectInfoBean[size];
        }
    };


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activityName);
        dest.writeString(layoutName);
        dest.writeString(scId);
    }
}
