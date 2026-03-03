package com.besome.blacklogics.project;

import android.content.Context;
import android.content.SharedPreferences;

public class ProjectDataHelper {

    private static final String PREF_NAME = "ProjectDataPrefs";
    private static final String KEY_SC_ID = "sc_id";
    private static final String KEY_ACTIVITY_NAME = "activity_name";


    public static void setScId(Context context, String scId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_SC_ID, scId).apply();
    }


    public static String getScId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_SC_ID, null);
    }


    public static void setActivityName(Context context, String activityName) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_ACTIVITY_NAME, activityName).apply();
    }


    public static String getActivityName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_ACTIVITY_NAME, null);
    }


    public static void resetData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
