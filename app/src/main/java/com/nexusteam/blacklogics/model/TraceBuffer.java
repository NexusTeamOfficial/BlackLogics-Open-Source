package com.nexusteam.blacklogics.model;

import java.util.LinkedList;
import java.util.List;

public class TraceBuffer {

    private int bufferSize;
    private final List<Trace> traces;

    public TraceBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
        traces = new LinkedList<>();
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        removeExceededTracesIfNeeded();
    }

    public int add(List<Trace> traces) {
        this.traces.addAll(traces);
        return removeExceededTracesIfNeeded();
    }

    public List<Trace> getTraces() {
        return traces;
    }

    public void clear() {
        traces.clear();
    }

    private int removeExceededTracesIfNeeded() {
        int tracesToDiscard = getNumberOfTracesToDiscard();
        if (tracesToDiscard > 0) {
            discardTraces(tracesToDiscard);
        }
        return tracesToDiscard;
    }

    private int getNumberOfTracesToDiscard() {
        int currentTracesSize = this.traces.size();
        int tracesToDiscard = currentTracesSize - bufferSize;
        tracesToDiscard = tracesToDiscard < 0 ? 0 : tracesToDiscard;
        return tracesToDiscard;
    }

    private void discardTraces(int tracesToDiscard) {
        if (tracesToDiscard > 0) {
            traces.subList(0, tracesToDiscard).clear();
        }
    }
}
