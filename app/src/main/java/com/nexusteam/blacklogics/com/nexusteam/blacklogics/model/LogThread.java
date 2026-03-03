package com.nexusteam.blacklogics.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class LogThread extends Thread implements Cloneable {
    private static final String LOGTAG = "LogThread";

    private Process process;
    private BufferedReader bufferReader;
    private TraceListener listener;
    private boolean continueReading = true;

    public void setListener(TraceListener listener) {
        this.listener = listener;
    }

    public TraceListener getListener() {
        return listener;
    }

    @Override
    public void run() {
        super.run();
        try {
            process = Runtime.getRuntime().exec("logcat -v time");
        } catch (IOException e) {
            Log.e(LOGTAG, "IOException executing logcat command.", e);
        }
        readLogcat();
    }

    public void stopReading() {
        continueReading = false;
    }

    private void readLogcat() {
        BufferedReader bufferedReader = getBufferReader();
        try {
            String trace = bufferedReader.readLine();
            while (trace != null && continueReading) {
                notifyListener(trace);
                trace = bufferedReader.readLine();
            }
        } catch (IOException e) {
            Log.e(LOGTAG, "IOException reading logcat trace.", e);
        }
    }

    private void notifyListener(String trace) {
        if (listener != null) {
            listener.onNewTraceRead(trace);
        }
    }

    private BufferedReader getBufferReader() {
        if (bufferReader == null) {
            bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        }
        return bufferReader;
    }

    @Override
    public Object clone() {
        LogThread logcat = new LogThread();
        logcat.setListener(listener);
        return logcat;
    }

    interface TraceListener {

        void onNewTraceRead(String trace);
    }
}
