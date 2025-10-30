package com.nexusteam.internal.os.layouteditor.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class ViewBean implements Parcelable {
    @Expose
    private String layoutName;
    @Expose
    private String activityName;
    @Expose
    private List<WidgetInfo> widgets;

    public static class WidgetInfo implements Parcelable {
        @Expose
        private String widgetId;
        @Expose
        private String widgetType;
        @Expose
        private String widgetName;

        public WidgetInfo(String widgetId, String widgetType, String widgetName) {
            this.widgetId = widgetId;
            this.widgetType = widgetType;
            this.widgetName = widgetName;
        }

        protected WidgetInfo(Parcel in) {
            widgetId = in.readString();
            widgetType = in.readString();
            widgetName = in.readString();
        }

        public static final Creator<WidgetInfo> CREATOR = new Creator<WidgetInfo>() {
            @Override
            public WidgetInfo createFromParcel(Parcel in) {
                return new WidgetInfo(in);
            }

            @Override
            public WidgetInfo[] newArray(int size) {
                return new WidgetInfo[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(widgetId);
            dest.writeString(widgetType);
            dest.writeString(widgetName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getWidgetId() { return widgetId; }
        public String getWidgetType() { return widgetType; }
        public String getWidgetName() { return widgetName; }
    }

    public ViewBean() {
        widgets = new ArrayList<>();
    }

    public ViewBean(String layoutName, String activityName, List<WidgetInfo> widgets) {
        this.layoutName = layoutName;
        this.activityName = activityName;
        this.widgets = widgets != null ? widgets : new ArrayList<>();
    }

    protected ViewBean(Parcel in) {
        layoutName = in.readString();
        activityName = in.readString();
        widgets = in.createTypedArrayList(WidgetInfo.CREATOR);
    }

    public static final Creator<ViewBean> CREATOR = new Creator<ViewBean>() {
        @Override
        public ViewBean createFromParcel(Parcel in) {
            return new ViewBean(in);
        }

        @Override
        public ViewBean[] newArray(int size) {
            return new ViewBean[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(layoutName);
        dest.writeString(activityName);
        dest.writeTypedList(widgets);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLayoutName() { return layoutName; }
    public void setLayoutName(String layoutName) { this.layoutName = layoutName; }
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
    public List<WidgetInfo> getWidgets() { return widgets; }
    public void addWidget(String widgetId, String widgetType, String widgetName) {
        widgets.add(new WidgetInfo(widgetId, widgetType, widgetName));
    }
    public List<WidgetInfo> getWidgetsByType(String widgetType) {
        List<WidgetInfo> filtered = new ArrayList<>();
        for (WidgetInfo widget : widgets) {
            if (widget.getWidgetType().equalsIgnoreCase(widgetType)) {
                filtered.add(widget);
            }
        }
        return filtered;
    }
}