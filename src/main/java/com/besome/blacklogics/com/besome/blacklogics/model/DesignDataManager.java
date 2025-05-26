package com.besome.blacklogics.model;

public class DesignDataManager {
    // Private static fields
    private static String javaName = "";
    private static String layoutName = "";
    private static String eventName = "";

    // Static getter for javaName
    public static String getJavaName() {
        return javaName;
    }

    // Static setter for javaName
    public static void setJavaName(String javaName) {
        if (javaName != null && !javaName.trim().isEmpty()) {
            DesignDataManager.javaName = javaName;
        } else {
            throw new IllegalArgumentException("Java name cannot be null or empty");
        }
    }

    // Static getter for layoutName
    public static String getLayoutName() {
        return layoutName;
    }

    // Static setter for layoutName
    public static void setLayoutName(String layoutName) {
        if (layoutName != null && !layoutName.trim().isEmpty()) {
            DesignDataManager.layoutName = layoutName;
        } else {
            throw new IllegalArgumentException("Layout name cannot be null or empty");
        }
    }

    // Static getter for eventName
    public static String getEventName() {
        return eventName;
    }

    // Static setter for eventName
    public static void setEventName(String eventName) {
        if (eventName != null && !eventName.trim().isEmpty()) {
            DesignDataManager.eventName = eventName;
        } else {
            throw new IllegalArgumentException("Event name cannot be null or empty");
        }
    }

    // Static method: Reset all fields to default values
    public static void resetToDefaults() {
        javaName = "MainActivity";
        layoutName = "main";
        eventName = "onCreate";
    }

    // Static method: Check if all fields are set to default values
    public static boolean isDefaultConfiguration() {
        return "MainActivity".equals(javaName) &&
               "main".equals(layoutName) &&
               "onCreate".equals(eventName);
    }

    // Static method: Validate configuration
    public static boolean isValidConfiguration() {
        return javaName != null && !javaName.trim().isEmpty() &&
               layoutName != null && !layoutName.trim().isEmpty() &&
               eventName != null && !eventName.trim().isEmpty();
    }

    // Static method: Get a formatted configuration summary
    public static String getConfigurationSummary() {
        return String.format("Java: %s, Layout: %s, Event: %s", 
                            javaName, layoutName, eventName);
    }

    // Static method: Update all fields at once
    public static void updateConfiguration(String newJavaName, String newLayoutName, String newEventName) {
        setJavaName(newJavaName);
        setLayoutName(newLayoutName);
        setEventName(newEventName);
    }

    // Static method: Check if a specific javaName is set
    public static boolean hasJavaName(String name) {
        return javaName != null && javaName.equals(name);
    }

    // Static method: Check if a specific layoutName is set
    public static boolean hasLayoutName(String name) {
        return layoutName != null && layoutName.equals(name);
    }

    // Static method: Check if a specific eventName is set
    public static boolean hasEventName(String name) {
        return eventName != null && eventName.equals(name);
    }

    // Private constructor to prevent instantiation
    private DesignDataManager() {
        // Prevent instantiation
    }
}