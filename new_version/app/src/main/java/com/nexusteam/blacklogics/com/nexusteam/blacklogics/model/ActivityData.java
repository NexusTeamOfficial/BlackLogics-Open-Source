package com.besome.blacklogics.model;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class ActivityData {
    private String name;
    private String layoutName;
    private String activityName;
    private boolean isMainActivity;
    private List<View> widgets;

    public ActivityData(String name, String layoutName, boolean isMainActivity) {
        this.name = name;
        this.layoutName = layoutName;
        this.isMainActivity = isMainActivity;
        this.widgets = new ArrayList<>();
    }


    public String getName() { return name; }
    public String getLayoutName() { return layoutName; }
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
    public void setLayoutName(String layoutName) { this.layoutName = layoutName; }
    public boolean isMainActivity() { return isMainActivity; }
    public List<View> getWidgets() { return widgets; }
    
    public void addWidget(View widget) {
        widgets.add(widget);
    }
}