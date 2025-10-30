package com.besome.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ActivityBean implements Parcelable {

    // Listener system for fast refresh
    public interface OnChangeListener {
        void onChanged(ActivityBean bean);
    }

    private final List<OnChangeListener> listeners = new ArrayList<>();

    public String activityName;
    public String layoutName;

    // Constructors
    public ActivityBean() {
        activityName = "";
        layoutName = "";
    }

    public ActivityBean(String activityName, String layoutName) {
        this.activityName = activityName;
        this.layoutName = layoutName;
    }

    protected ActivityBean(Parcel in) {
        activityName = in.readString();
        layoutName = in.readString();
    }

    public static final Creator<ActivityBean> CREATOR = new Creator<ActivityBean>() {
        @Override
        public ActivityBean createFromParcel(Parcel in) {
            return new ActivityBean(in);
        }

        @Override
        public ActivityBean [] newArray(int size) {
            return new ActivityBean[size];
        }
    };

    // Gson Support
    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ActivityBean fromJson(String json) {
        return new Gson().fromJson(json, ActivityBean.class);
    }

    // Refresh system
    public void addListener(OnChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(OnChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyChange() {
        for (OnChangeListener l : listeners) {
            l.onChanged(this);
        }
    }

    // Getters & Setters with auto-refresh
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
        notifyChange();
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
        notifyChange();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activityName);
        dest.writeString(layoutName);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
