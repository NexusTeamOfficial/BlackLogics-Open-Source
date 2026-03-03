package com.nexusteam.internal;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CRASH_HANDLER";
    private final Thread.UncaughtExceptionHandler defaultHandler;

    public CrashHandler() {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        Log.e(TAG, "Uncaught exception", throwable);

        // Android 11+ permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                defaultHandler.uncaughtException(thread, throwable);
                return;
            }
        }

        try {
            File file = new File("/storage/emulated/0/nexus_crash_log.txt");
            FileWriter writer = new FileWriter(file, true);

            writer.write("\n\n===== CRASH =====\n");
            writer.write("Thread: " + thread.getName() + "\n");
            writer.write("Activity: " + AppLifecycleTracker.getCurrentActivity() + "\n");

            // FULL STACKTRACE
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);

            writer.write(sw.toString());
            writer.write("\n=================\n");

            writer.close();

        } catch (Throwable e) {
            Log.e(TAG, "File write failed", e);
        }

        // Let system kill app properly
        defaultHandler.uncaughtException(thread, throwable);
    }
}