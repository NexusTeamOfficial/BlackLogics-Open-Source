package com.nexusteam.blacklogics.interfaces;

public interface CompilerLogListener {
    void onErrorLog(String errorLog);
    void onLog(String log);
}