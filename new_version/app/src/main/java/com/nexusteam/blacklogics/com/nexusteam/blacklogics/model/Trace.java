package com.nexusteam.blacklogics.model;

import com.nexusteam.blacklogics.exception.IllegalTraceException;

public class Trace {
    private static final String TAG = "TraceLOG";
    private static final char TRACE_LEVEL_SEPARATOR = '/';
    private static final int START_OF_DATE_INDEX = 6;
    private static final int END_OF_DATE_INDEX = 18;
    private static final int START_OF_MESSAGE_INDEX = 21;
    private static final int MIN_TRACE_SIZE = 21;
    static final int TRACE_LEVEL_INDEX = 19;

    public final TraceLevel level;
    public final String time;
    public final String tag;
    public final String message;

    public Trace(TraceLevel level, String time, String tag, String message) {
        this.level = level;
        this.time = time;
        this.tag = tag;
        this.message = message;
    }

    public static Trace fromString(String logcatTrace) throws IllegalTraceException {
        if (logcatTrace == null
                || logcatTrace.length() < MIN_TRACE_SIZE
                || logcatTrace.charAt(20) != TRACE_LEVEL_SEPARATOR
                || logcatTrace.contains(TAG)) {
            throw new IllegalTraceException(
                    "You are trying to create a Trace object from a invalid String. Your trace have to be "
                            + "something like: '07-19 15:24:11.162 D/TAG(30345): Some message'.");
        }
        TraceLevel level = TraceLevel.getTraceLevel(logcatTrace.charAt(TRACE_LEVEL_INDEX));
        String time = logcatTrace.substring(START_OF_DATE_INDEX, END_OF_DATE_INDEX);
        String message = logcatTrace.substring(START_OF_MESSAGE_INDEX);

        String tag = message.substring(0, message.indexOf(':'));
        tag = tag.replaceFirst(" *\\(.+\\)", "");
        message = message.substring(message.indexOf(':') + 2);

        return new Trace(level, time, tag, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trace trace = (Trace) o;

        if (level != trace.level) return false;
        if (time != null ? !time.equals(trace.time) : trace.time != null) return false;
        if (tag != null ? !tag.equals(trace.tag) : trace.tag != null) return false;
        return message != null ? message.equals(trace.message) : trace.message == null;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}