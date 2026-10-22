package %PACKAGE_NAME%;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class provides a mechanism to read and broadcast logcat messages to a designated receiver.
 * It utilizes a dedicated thread to continuously read logcat output and send it via a broadcast intent.
 *
 * Usage:
 * - Call `BlackLogger.startLogging()` to begin logging.
 * - Call `BlackLogger.stopLogging()` to stop logging.
 * - Use `BlackLogger.broadcastLog(String)` to manually send a debug log message.
 */
public class BlackLogger {
    private static volatile boolean isRunning = false;
    private static Thread loggerThread = new Thread() {
        @Override
        public void run() {
            isRunning = true;

            try {
                Runtime.getRuntime().exec("logcat -c");
                Process process = Runtime.getRuntime().exec("logcat");

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String logTxt = bufferedReader.readLine();
                    do {
                        broadcastLog(logTxt);
                    } while (isRunning && (logTxt = bufferedReader.readLine()) != null);

                    if (isRunning) {
                        broadcastLog("Logger got killed. Restarting.");
                        startLogging();
                    } else {
                        broadcastLog("Logger stopped.");
                    }
                }
            } catch (IOException e) {
                broadcastLog(e.getMessage());
            }
        }
    };

    public static synchronized void startLogging() {
        if (!isRunning) {
            loggerThread.start();
        } else {
            broadcastLog("Logger already running");
        }
    }

    public static synchronized void stopLogging() {
        if (isRunning) {
            isRunning = false;
            broadcastLog("Stopping logger by user request.");
        } else {
            broadcastLog("Logger not running");
        }
    }

    public static void broadcastLog(String log) {
        Context context = BlackApplication.getContext();

        Intent intent = new Intent();
        intent.setAction("com.besome.blacklogics.ACTION_NEW_BLACKLOG");
        intent.putExtra("log", log);
        intent.putExtra("packageName", context.getPackageName());
        context.sendBroadcast(intent);
    }
}