package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import android.os.Environment;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class DebugLog {
    private static final String TAG = "DebugLog";
    private static final String LOG_DIR_NAME = "logs"; // stored under Context.getExternalFilesDir
    private static final String LOG_FILE_NAME = "log.txt";
    private static final int MAX_STACK_FRAMES = 200; // cap for stack frames written


    private static final AtomicBoolean logging = new AtomicBoolean(false);

    /**
     * Low-level append to file + Android Log.d
     */
    private static void write(Context context, String tag, String message) {
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .format(new Date());
            String fullMsg = "[" + time + "] " + tag + ": " + message + "\n";


            Log.d(tag, message);


            File dir = null;
            if (context != null) {
                try {
                    dir = new File("/storage/emulated/0/");
                } catch (Throwable t) {

                    dir = null;
                }
            }
            if (dir == null) {

                dir = context != null ? new File("/storage/emulated/0/") : null;
            }
            if (dir == null) {

                return;
            }

            if (!dir.exists()) {

                dir.mkdirs();
            }

            File logFile = new File(dir, LOG_FILE_NAME);


            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.append(fullMsg);
                writer.flush();
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to write debug log file: " + ioe.getMessage());
            }
        } catch (Throwable t) {

            Log.e(TAG, "Unexpected error in DebugLog.write: " + t.getMessage());
        }
    }

    /**
     * Info-level
     */
    public static void i(Context context, String tag, String msg) {

        write(context, tag, "Info: " + msg);
    }

    /**
     * Debug-level success message
     */
    public static void d(Context context, String tag, String msg) {
        write(context, tag, "Success: " + msg);
    }
    
     /**
     * Debug-level warming message
     */
    public static void w(Context context, String tag, String msg) {
        write(context, tag, "Warning ⚠️: " + msg);
    }

    /**
     * Error-level logging without throwing an exception.
     * Writes a capped stack trace and additional contextual information.
     * Includes a recursion guard so repeated calls do not explode.
     */
    public static void e(Context context, String tag, String msg) {


        if (!logging.compareAndSet(false, true)) {

            try {
                Log.e(tag, "Error (reentrant): " + msg);

                write(context, tag, "Error (reentrant): " + msg);
            } finally {

            }
            return;
        }

        try {

            write(context, tag, "Error: " + msg);


            Throwable t = new Throwable("DEBUG_TRACE: " + msg);
            StackTraceElement[] stack = t.getStackTrace();


            StringBuilder deep = new StringBuilder();
            deep.append("FULL_STACK_TRACE (capped to ").append(MAX_STACK_FRAMES).append(" frames):\n");

            int startIdx = 2; // skip Throwable constructor + this method
            int limit = Math.min(stack.length, MAX_STACK_FRAMES + startIdx);
            for (int i = startIdx; i < limit; i++) {
                StackTraceElement e = stack[i];
                deep.append("  → ").append(e.getClassName())
                    .append(".").append(e.getMethodName())
                    .append("(").append(e.getFileName())
                    .append(":").append(e.getLineNumber()).append(")\n");
            }
            if (stack.length > limit) {
                deep.append("  ... and ").append(stack.length - limit).append(" more frames\n");
            }


            write(context, tag, deep.toString());


            String traceForLogcat = android.util.Log.getStackTraceString(t);
            Log.e(tag, "Error: " + msg + "\n" + traceForLogcat);

        } catch (Throwable t) {

            Log.e(TAG, "Unexpected error in DebugLog.e: " + t.getMessage());
            write(context, TAG, "DebugLog.e failure: " + t.getMessage());
        } finally {

            logging.set(false);
        }
    }
}
