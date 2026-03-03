package com.nexusteam.internal;

import android.app.Activity;

public class AppLifecycleTracker {

    private static String currentActivity = "Unknown";

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity.getClass().getName();
    }

    public static String getCurrentActivity() {
        return currentActivity;
    }
}