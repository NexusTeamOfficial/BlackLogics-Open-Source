package com.besome.blacklogics.model;

public class DesignDataManager {

    private static String javaName = "";
    private static String layoutName = "";
    private static String eventName = "";


    public static String getJavaName() {
        return javaName;
    }


    public static void setJavaName(String javaName) {
        if (javaName != null && !javaName.trim().isEmpty()) {
            DesignDataManager.javaName = javaName;
        } else {
            throw new IllegalArgumentException("Java name cannot be null or empty");
        }
    }


    public static String getLayoutName() {
        return layoutName;
    }


    public static void setLayoutName(String layoutName) {
        if (layoutName != null && !layoutName.trim().isEmpty()) {
            DesignDataManager.layoutName = layoutName;
        } else {
            throw new IllegalArgumentException("Layout name cannot be null or empty");
        }
    }


    public static String getEventName() {
        return eventName;
    }


    public static void setEventName(String eventName) {
        if (eventName != null && !eventName.trim().isEmpty()) {
            DesignDataManager.eventName = eventName;
        } else {
            throw new IllegalArgumentException("Event name cannot be null or empty");
        }
    }


    public static void resetToDefaults() {
        javaName = "MainActivity";
        layoutName = "main";
        eventName = "onCreate";
    }


    public static boolean isDefaultConfiguration() {
        return "MainActivity".equals(javaName) &&
               "main".equals(layoutName) &&
               "onCreate".equals(eventName);
    }


    public static boolean isValidConfiguration() {
        return javaName != null && !javaName.trim().isEmpty() &&
               layoutName != null && !layoutName.trim().isEmpty() &&
               eventName != null && !eventName.trim().isEmpty();
    }


    public static String getConfigurationSummary() {
        return String.format("Java: %s, Layout: %s, Event: %s", 
                            javaName, layoutName, eventName);
    }


    public static void updateConfiguration(String newJavaName, String newLayoutName, String newEventName) {
        setJavaName(newJavaName);
        setLayoutName(newLayoutName);
        setEventName(newEventName);
    }


    public static boolean hasJavaName(String name) {
        return javaName != null && javaName.equals(name);
    }


    public static boolean hasLayoutName(String name) {
        return layoutName != null && layoutName.equals(name);
    }


    public static boolean hasEventName(String name) {
        return eventName != null && eventName.equals(name);
    }


    private DesignDataManager() {

    }
}