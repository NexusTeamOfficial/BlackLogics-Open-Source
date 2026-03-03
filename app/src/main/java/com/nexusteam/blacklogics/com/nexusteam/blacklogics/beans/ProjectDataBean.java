package com.besome.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ProjectDataBean
 * Simple Parcelable model with only sc_id
 *
 * Developer: NexusTeam
 * Date: 2025-09-27
 */
public class ProjectDataBean implements Parcelable {

    private String sc_id;

    /**
     * Default constructor
     */
    public ProjectDataBean() {
    }

    /**
     * Parameterized constructor
     */
    public ProjectDataBean(String sc_id) {
        this.sc_id = sc_id;
    }

    /**
     * Parcelable constructor
     */
    protected ProjectDataBean(Parcel in) {
        sc_id = in.readString();
    }

    /**
     * Getter and Setter
     */
    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    /**
     * Parcelable methods
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sc_id);
    }

    public static final Creator<ProjectDataBean> CREATOR = new Creator<ProjectDataBean>() {
        @Override
        public ProjectDataBean createFromParcel(Parcel in) {
            return new ProjectDataBean(in);
        }

        @Override
        public ProjectDataBean[] newArray(int size) {
            return new ProjectDataBean[size];
        }
    };

    @Override
    public String toString() {
        return "ProjectDataBean{" +
                "sc_id='" + sc_id + '\'' +
                '}';
    }
}
