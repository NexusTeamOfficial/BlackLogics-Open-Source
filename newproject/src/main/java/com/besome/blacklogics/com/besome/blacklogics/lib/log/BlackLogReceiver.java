package com.besome.blacklogics.lib.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlackLogReceiver extends BroadcastReceiver {
    // Constants
    public static final String ACTION_NEW_BLACKLOG = "com.besome.blacklogics.ACTION_NEW_BLACKLOG";
    public static final String EXTRA_LOG = "log";
    public static final String EXTRA_PACKAGE = "packageName";
    public static final String EXTRA_LOG_LEVEL = "logLevel";
    public static final String EXTRA_TIMESTAMP = "timestamp";
    
    // Log levels
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    
    // Configuration
    private static final int MAX_LOG_FILES = 10;
    private static final long MAX_LOG_FILE_SIZE = 2 * 1024 * 1024; // 2MB
    private static final String LOG_DIR = "BlackLogs";
    private static final String LOG_PREFIX = "blacklog_";
    private static final String LOG_EXT = ".txt";
    
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    
    // Interface for UI updates
    public interface LogListener {
        void onNewLog(String packageName, String log, int level, long timestamp);
    }
    
    private static LogListener logListener;
    
    public static void setLogListener(LogListener listener) {
        logListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_NEW_BLACKLOG.equals(intent.getAction())) {
            final String log = intent.getStringExtra(EXTRA_LOG);
            final String packageName = intent.getStringExtra(EXTRA_PACKAGE);
            final int logLevel = intent.getIntExtra(EXTRA_LOG_LEVEL, LEVEL_INFO);
            final long timestamp = intent.getLongExtra(EXTRA_TIMESTAMP, System.currentTimeMillis());
            
            // Process on background thread
            executor.execute(() -> {
                // 1. Print to Android Logcat
                printToLogcat(packageName, log, logLevel);
                
                // 2. Save to file
                saveToFile(context, packageName, log, logLevel, timestamp);
                
                // 3. Notify UI (if listener is registered)
                notifyUI(packageName, log, logLevel, timestamp);
            });
        }
    }
    
    private void printToLogcat(String packageName, String log, int level) {
        String tag = "BlackLog/" + packageName;
        switch (level) {
            case LEVEL_VERBOSE:
                Log.v(tag, log);
                break;
            case LEVEL_DEBUG:
                Log.d(tag, log);
                break;
            case LEVEL_INFO:
                Log.i(tag, log);
                break;
            case LEVEL_WARNING:
                Log.w(tag, log);
                break;
            case LEVEL_ERROR:
                Log.e(tag, log);
                break;
            default:
                Log.i(tag, log);
        }
    }
    
    private void saveToFile(Context context, String packageName, String log, int level, long timestamp) {
        File logDir = new File(context.getExternalFilesDir(null), LOG_DIR);
        if (!logDir.exists() && !logDir.mkdirs()) {
            Log.e("BlackLog", "Failed to create log directory");
            return;
        }
        
        // Rotate logs if needed
        rotateLogs(logDir);
        
        // Current log file
        File logFile = new File(logDir, LOG_PREFIX + getCurrentDate() + LOG_EXT);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            String levelStr = getLevelString(level);
            
            writer.write(String.format("[%s] [%s] [%s] %s\n", 
                sdf.format(new Date(timestamp)),
                levelStr,
                packageName,
                log));
        } catch (IOException e) {
            Log.e("BlackLog", "Error writing to log file", e);
        }
    }
    
    private void rotateLogs(File logDir) {
        File[] logFiles = logDir.listFiles((dir, name) -> name.startsWith(LOG_PREFIX) && name.endsWith(LOG_EXT));
        
        if (logFiles != null) {
            // Delete oldest files if we have too many
            if (logFiles.length >= MAX_LOG_FILES) {
                // Sort by last modified (oldest first)
                Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified));
                
                // Delete the oldest files
                for (int i = 0; i <= logFiles.length - MAX_LOG_FILES; i++) {
                    logFiles[i].delete();
                }
            }
            
            // Check current file size
            File currentFile = new File(logDir, LOG_PREFIX + getCurrentDate() + LOG_EXT);
            if (currentFile.exists() && currentFile.length() > MAX_LOG_FILE_SIZE) {
                // Archive current file with timestamp
                String newName = LOG_PREFIX + getCurrentDate() + "_" + 
                    System.currentTimeMillis() + LOG_EXT;
                currentFile.renameTo(new File(logDir, newName));
            }
        }
    }
    
    private void notifyUI(String packageName, String log, int level, long timestamp) {
        if (logListener != null) {
            mainHandler.post(() -> {
                logListener.onNewLog(packageName, log, level, timestamp);
            });
        }
    }
    
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
    }
    
    private String getLevelString(int level) {
        switch (level) {
            case LEVEL_VERBOSE: return "VERBOSE";
            case LEVEL_DEBUG: return "DEBUG";
            case LEVEL_INFO: return "INFO";
            case LEVEL_WARNING: return "WARNING";
            case LEVEL_ERROR: return "ERROR";
            default: return "UNKNOWN";
        }
    }
}