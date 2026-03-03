package com.nexusteam.blacklogics.interfaces;

public interface OnWidgetClassSelectedListener {

    /**
     * Called when user selects a widget class
     *
     * @param widgetName simple class name
     * @param classPath full class path
     */
    void onWidgetClassSelected(String widgetName, String classPath);
}
