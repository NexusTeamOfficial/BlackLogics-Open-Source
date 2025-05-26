package com.apk.builder;

import android.app.Application;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import com.besome.blacklogics.*;
import com.besome.blacklogics.development.Complex;

import androidx.multidex.MultiDex; // Import MultiDex

public class ApplicationLoader extends Application {
    
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    
    public static Context applicationContext;
    public static volatile Handler applicationHandler;
    
    public static Complex complex = new Complex();
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this); // Enable MultiDex
    }
    
    @Override
    public void onCreate() {
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        complex.setC(getApplicationContext());
        
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            String error = getStackTrace(ex);
            intent.putExtra("error", error);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 
                11111, 
                intent, 
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE // Add FLAG_IMMUTABLE for API 23+
            );

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(2);
            
            uncaughtExceptionHandler.uncaughtException(thread, ex);
        });
        super.onCreate();
        
        applicationContext = this;
        applicationHandler = new Handler(applicationContext.getMainLooper());
        
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("AppSettings", getApplicationContext().MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        
    }
    
    private String getStackTrace(Throwable th) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        Throwable cause = th;
        
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        final String stacktraceAsString = result.toString();
        printWriter.close();
        
        return stacktraceAsString;
    }
    
    public static Context getContext() {
        return applicationContext;
    }   
}