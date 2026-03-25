package com.nexusteam.blacklogics.model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.nexusteam.blacklogics.exception.IllegalTraceException;

import java.util.LinkedList;
import java.util.List;

public class Logcat {

    private final int TIME_BETWEEN_TRACE_CONSTANT = 1;
    private final int MAX_DELAY_TIME_CONSTANT = 2;
    private final long TIME_BETWEEN_TRACE = 700;
    private final long MAX_DELAY_TIME = 5000;

    private LogThread logcat;
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            forceNotifyNewTraces();
        }
    };
    private final List<Trace> tracesToNotify;
    private Listener listener;

    public Logcat() {
        this.tracesToNotify = new LinkedList<>();
        this.logcat = new LogThread();
    }

    public void startReading() {
        logcat.setListener(new LogThread.TraceListener() {
            @Override
            public void onNewTraceRead(String trace) {
                try {
                    Logcat.this.addTraceToTheBuffer(trace);
                } catch (IllegalTraceException e) {
                    return;
                }
                Logcat.this.notifyNewTraces();
            }
        });
        boolean logcatWasNotStarted = Thread.State.NEW.equals(logcat.getState());
        if (logcatWasNotStarted) {
            logcat.start();
        } else restart();
    }

    public void stopReading() {
        logcat.stopReading();
        logcat.interrupt();
    }

    public void restart() {
        logcat.stopReading();
        logcat.interrupt();
        logcat = (LogThread) logcat.clone();
        tracesToNotify.clear();
        mainThreadHandler.removeMessages(TIME_BETWEEN_TRACE_CONSTANT);
        mainThreadHandler.removeMessages(MAX_DELAY_TIME_CONSTANT);
        logcat.start();
    }

    public synchronized void setListener(Logcat.Listener listener) {
        this.listener = listener;
    }

    private synchronized void addTraceToTheBuffer(String logcatTrace) throws IllegalTraceException {
        Trace trace = Trace.fromString(logcatTrace);
        tracesToNotify.add(trace);
    }

    private void notifyNewTraces() {
        if (!mainThreadHandler.hasMessages(MAX_DELAY_TIME_CONSTANT)) {
            mainThreadHandler.sendEmptyMessageDelayed(MAX_DELAY_TIME_CONSTANT, MAX_DELAY_TIME);
        }
        mainThreadHandler.removeMessages(TIME_BETWEEN_TRACE_CONSTANT);
        mainThreadHandler.sendEmptyMessageDelayed(TIME_BETWEEN_TRACE_CONSTANT, TIME_BETWEEN_TRACE);
    }

    private void forceNotifyNewTraces() {
        mainThreadHandler.removeMessages(MAX_DELAY_TIME_CONSTANT);
        mainThreadHandler.sendEmptyMessageDelayed(MAX_DELAY_TIME_CONSTANT, MAX_DELAY_TIME);

        if (tracesToNotify.size() > 0) {
            final List<Trace> traces = new LinkedList<>(tracesToNotify);
            tracesToNotify.clear();
            notifyListeners(traces);
        }
    }

    private synchronized void notifyListeners(final List<Trace> traces) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) listener.onNewTraces(traces);
            }
        });
    }

    public interface Listener {
        void onNewTraces(List<Trace> traces);
    }
}
