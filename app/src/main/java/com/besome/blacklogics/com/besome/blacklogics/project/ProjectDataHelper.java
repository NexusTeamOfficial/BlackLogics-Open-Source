package com.besome.blacklogics.project;

import android.content.Context;
import android.content.SharedPreferences;

public class ProjectDataHelper {

    private static final String PREF_NAME = "ProjectDataPrefs";
    private static final String KEY_SC_ID = "sc_id";
    private static final String KEY_ACTIVITY_NAME = "activity_name";

    // Set sc_id
    public static void setScId(Context context, String scId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_SC_ID, scId).apply();
    }

    // Get sc_id
    public static String getScId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_SC_ID, null);
    }

    // Set activity name
    public static void setActivityName(Context context, String activityName) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_ACTIVITY_NAME, activityName).apply();
    }

    // Get activity name
    public static String getActivityName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_ACTIVITY_NAME, null);
    }

    // Reset all stored data
    public static void resetData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
