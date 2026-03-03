package com.nexusteam.blacklogics.model;

/**
 * Logcat trace levels used to indicate the trace importance.
 */
public enum TraceLevel {
    VERBOSE("V"), DEBUG("D"), INFO("I"), WARN("W"), ERROR("E"), ASSERT("A"), WTF("F");

    private final String value;

    TraceLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        String name = name().toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

    public static TraceLevel getTraceLevel(char trace) {
        TraceLevel traceLevel;
        switch (trace) {
            case 'V':
                traceLevel = VERBOSE;
                break;
            case 'A':
                traceLevel = ASSERT;
                break;
            case 'I':
                traceLevel = INFO;
                break;
            case 'W':
                traceLevel = WARN;
                break;
            case 'E':
                traceLevel = ERROR;
                break;
            case 'F':
                traceLevel = WTF;
                break;
            default:
                traceLevel = DEBUG;
        }
        return traceLevel;
    }

    public static TraceLevel getTraceLevel(String trace) {
        trace = trace.toUpperCase();
        for (TraceLevel value : TraceLevel.values()) {
            if (value.name().equals(trace)) return value;
        }
        return TraceLevel.DEBUG;
    }
}
