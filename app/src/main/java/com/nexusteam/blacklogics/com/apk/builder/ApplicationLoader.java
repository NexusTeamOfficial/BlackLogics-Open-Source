package com.apk.builder;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatDelegate;

import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.development.Complex;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ApplicationLoader extends Application {

    private static final String TAG = "ApplicationLoader";

    private static final String INIT_PREF = "app_init_guard";
    private static final String KEY_INITIALIZED = "initialized";
    private static final String KEY_INIT_VERSION = "init_version";
    private static final int CURRENT_INIT_VERSION = 1;

    private static final String CRASH_PREF = "crash_guard";
    private static final String LAST_CRASH = "last_crash_time";
    private static final String CRASH_COUNT = "crash_count";

    private static final String THEME_PREF = "AppSettings";
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String KEY_DARK_MODE = "dark_mode";

    private static final String LANG_PREF = "LANG";
    private static final String KEY_LANG = "lang";

    public static Context applicationContext;
    public static volatile Handler applicationHandler;
    public static volatile boolean isAppInitialized = false;

    public static Complex complex = new Complex();

    private Thread.UncaughtExceptionHandler defaultUncaughtHandler;

    @Override
    protected void attachBaseContext(Context base) {
        SharedPreferences sp = base.getSharedPreferences(LANG_PREF, MODE_PRIVATE);
        String lang = sp.getString(KEY_LANG, "en");
        Context localeContext = com.nexusteam.blacklogics.utils.LocaleHelper
                .setLocale(base, lang);

        androidx.multidex.MultiDex.install(localeContext);

        super.attachBaseContext(localeContext);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;
        if (applicationHandler == null) {
            applicationHandler = new Handler(Looper.getMainLooper());
        }

        installCrashHandler();
        applyThemeIfChanged();

        if (isAlreadyInitialized()) {
            Log.d(TAG, "onCreate: one-time init already done");
            return;
        }

        Log.d(TAG, "onCreate: FIRST-TIME initialization starting...");

        complex.setC(getApplicationContext());

        registerActivityLifecycleCallbacks(new AppLifecycleCallbacks());

        markInitialized();
        isAppInitialized = true;

        Log.d(TAG, "onCreate: first-time initialization done");
    }

    private boolean isAlreadyInitialized() {
        try {
            SharedPreferences sp = getSharedPreferences(INIT_PREF, MODE_PRIVATE);
            boolean inited = sp.getBoolean(KEY_INITIALIZED, false);
            int version = sp.getInt(KEY_INIT_VERSION, 0);
            return inited && version == CURRENT_INIT_VERSION;
        } catch (Throwable t) {
            return false;
        }
    }

    private void markInitialized() {
        try {
            SharedPreferences sp = getSharedPreferences(INIT_PREF, MODE_PRIVATE);
            sp.edit()
                    .putBoolean(KEY_INITIALIZED, true)
                    .putInt(KEY_INIT_VERSION, CURRENT_INIT_VERSION)
                    .apply();
        } catch (Throwable ignored) {}
    }

    private class AppLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            com.nexusteam.internal.AppLifecycleTracker.setCurrentActivity(activity);
            resetCrashLoopGuard();
        }

        @Override
        public void onActivityResumed(Activity activity) {
            com.nexusteam.internal.AppLifecycleTracker.setCurrentActivity(activity);
        }

        @Override public void onActivityPaused(Activity activity) {}
        @Override public void onActivityStopped(Activity activity) {}
        @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
        @Override public void onActivityDestroyed(Activity activity) {}
    }

    private void installCrashHandler() {
        defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread thread, final Throwable ex) {

                Log.e(TAG, "🔥 Uncaught exception in thread: " + thread.getName(), ex);

                if (isCrashLoop()) {
                    Log.e(TAG, "Crash loop detected — bypassing custom handler");
                    if (defaultUncaughtHandler != null) {
                        defaultUncaughtHandler.uncaughtException(thread, ex);
                    } else {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                    return;
                }

                final String report = buildCrashReport(ex, getRootCause(ex));

                try {
                    File f = new File(getExternalFilesDir(null), "last_crash.txt");
                    FileWriter fw = new FileWriter(f, false);
                    fw.write(report);
                    fw.close();
                } catch (Throwable t) {
                    Log.e(TAG, "Failed to write crash file", t);
                }

                Thread crashThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        launchDebugActivity(report);
                        killProcess(thread, ex);
                    }
                }, "crash-launcher");
                crashThread.start();
            }
        });
    }

    private void launchDebugActivity(final String report) {
        final Intent intent = new Intent(getApplicationContext(),
                com.nexusteam.blacklogics.activities.debug.DebugActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("error", report);

        final Object lock = new Object();
        final boolean[] launched = new boolean[] { false };

        try {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        getApplicationContext().startActivity(intent);
                        launched[0] = true;
                        Log.d(TAG, "DebugActivity launched via main looper ✅");
                    } catch (Throwable t) {
                        Log.e(TAG, "Main looper launch failed", t);
                    } finally {
                        synchronized (lock) { lock.notifyAll(); }
                    }
                }
            });

            synchronized (lock) {
                if (!launched[0]) {
                    try {
                        lock.wait(800);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (Throwable t) {
            Log.e(TAG, "main looper post failed", t);
        }

        if (!launched[0]) {
            try {
                getApplicationContext().startActivity(intent);
                launched[0] = true;
                Log.d(TAG, "DebugActivity launched via fallback ✅");
            } catch (Throwable t) {
                Log.e(TAG, "Fallback launch failed", t);
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private void killProcess(final Thread thread, final Throwable ex) {
        Log.d(TAG, "Killing process now");
        try {
            if (defaultUncaughtHandler != null) {
                defaultUncaughtHandler.uncaughtException(thread, ex);
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        } catch (Throwable t) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private String buildCrashReport(Throwable ex, Throwable root) {
        StringBuilder sb = new StringBuilder();
        sb.append("🔥 APP CRASHED\n\n");

        if (ex instanceof StackOverflowError) {
            sb.append("TYPE: StackOverflowError\n");
            sb.append("REASON: Infinite recursion / loop\n\n");
        } else if (ex instanceof VerifyError
                || ex instanceof NoClassDefFoundError
                || ex instanceof IncompatibleClassChangeError) {
            sb.append("TYPE: Dex / Bytecode / Obfuscation Error\n\n");
        } else {
            sb.append("TYPE: ").append(ex.getClass().getName()).append("\n\n");
        }

        sb.append("📌 ROOT CAUSE\n");
        sb.append(root.getClass().getName()).append("\n");
        sb.append(root.getMessage()).append("\n\n");

        StackTraceElement line = findUsefulLine(root.getStackTrace());
        if (line != null) {
            sb.append("📍 LOCATION\n");
            sb.append(line.getClassName())
                    .append(".")
                    .append(line.getMethodName())
                    .append(" (")
                    .append(line.getFileName())
                    .append(":")
                    .append(line.getLineNumber())
                    .append(")\n\n");
        }

        sb.append("🧵 FULL STACKTRACE\n");
        sb.append(getStackTrace(ex));
        return sb.toString();
    }

    private StackTraceElement findUsefulLine(StackTraceElement[] stack) {
        for (StackTraceElement e : stack) {
            if (e.getClassName().startsWith("com.")) {
                return e;
            }
        }
        return stack.length > 0 ? stack[0] : null;
    }

    private Throwable getRootCause(Throwable th) {
        Throwable cause = th;
        while (cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }
        return cause;
    }

    private String getStackTrace(Throwable th) {
        Writer result = new StringWriter();
        PrintWriter pw = new PrintWriter(result);
        th.printStackTrace(pw);
        pw.close();
        return result.toString();
    }

    private boolean isCrashLoop() {
        try {
            SharedPreferences sp = getSharedPreferences(CRASH_PREF, MODE_PRIVATE);
            long last = sp.getLong(LAST_CRASH, 0);
            long now = System.currentTimeMillis();
            int count = sp.getInt(CRASH_COUNT, 0);

            if (now - last > 15000) count = 0;
            count++;

            sp.edit()
                    .putLong(LAST_CRASH, now)
                    .putInt(CRASH_COUNT, count)
                    .apply();

            return count >= 3;
        } catch (Throwable t) {
            return false;
        }
    }

    private void resetCrashLoopGuard() {
        try {
            SharedPreferences sp = getSharedPreferences(CRASH_PREF, MODE_PRIVATE);
            sp.edit()
                    .putInt(CRASH_COUNT, 0)
                    .putLong(LAST_CRASH, 0)
                    .apply();
        } catch (Throwable ignored) {}
    }

    private int getSavedThemeMode() {
    SharedPreferences prefs = getSharedPreferences(THEME_PREF, MODE_PRIVATE);

    if (prefs.contains(KEY_THEME_MODE)) {
        return prefs.getInt(KEY_THEME_MODE,
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
    if (prefs.contains(KEY_DARK_MODE)) {
        return prefs.getBoolean(KEY_DARK_MODE, false)
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO;
    }
    return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
}

    private void applyThemeIfChanged() {
        try {
            int saved = getSavedThemeMode();
            int current = AppCompatDelegate.getDefaultNightMode();
            if (current != saved) {
                Log.d(TAG, "Theme change: " + current + " -> " + saved);
                AppCompatDelegate.setDefaultNightMode(saved);
            }
        } catch (Throwable t) {
            Log.e(TAG, "applyThemeIfChanged failed", t);
        }
    }

    public static Context getContext() {
        return applicationContext;
    }
}