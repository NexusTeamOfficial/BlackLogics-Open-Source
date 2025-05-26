package com.besome.blacklogics.interfaces;

public interface CompilerLogListener {
    void onErrorLog(String errorLog); // For error logs
    void onLog(String log); // For general logs (optional)
}