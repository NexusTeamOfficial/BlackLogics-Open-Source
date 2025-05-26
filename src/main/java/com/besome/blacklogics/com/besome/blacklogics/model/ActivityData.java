package com.besome.blacklogics.model;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class ActivityData {
    private String name;
    private String layoutName;
    private boolean isMainActivity;
    private List<View> widgets;

    public ActivityData(String name, String layoutName, boolean isMainActivity) {
        this.name = name;
        this.layoutName = layoutName;
        this.isMainActivity = isMainActivity;
        this.widgets = new ArrayList<>();
    }

    // Getters and setters
    public String getName() { return name; }
    public String getLayoutName() { return layoutName; }
    public boolean isMainActivity() { return isMainActivity; }
    public List<View> getWidgets() { return widgets; }
    
    public void addWidget(View widget) {
        widgets.add(widget);
    }
}