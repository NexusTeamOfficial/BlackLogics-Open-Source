package com.besome.blacklogics.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

public class WidgetProperties implements Parcelable {
    private String widgetId;
    private int backgroundColor;

    public WidgetProperties() {
        this.backgroundColor = Color.TRANSPARENT;
    }

    public String getWidgetId() { return widgetId; }
    public void setWidgetId(String widgetId) { this.widgetId = widgetId; }
    public int getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(int backgroundColor) { this.backgroundColor = backgroundColor; }

    // Parcelable implementation
    protected WidgetProperties(Parcel in) {
        widgetId = in.readString();
        backgroundColor = in.readInt();
    }

    public static final Creator<WidgetProperties> CREATOR = new Creator<WidgetProperties>() {
        @Override
        public WidgetProperties createFromParcel(Parcel in) {
            return new WidgetProperties(in);
        }

        @Override
        public WidgetProperties[] newArray(int size) {
            return new WidgetProperties[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(widgetId);
        dest.writeInt(backgroundColor);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}