package com.nexusteam.blacklogics.bean;

import com.nexusteam.internal.beans.ProjectFileBean;

public class AppState {

    private static ProjectFileBean currentFile;

    public static void setCurrentFile(ProjectFileBean bean) {
        currentFile = bean;
    }

    public static ProjectFileBean getCurrentFile() {
        return currentFile;
    }

    public static void clear() {
        currentFile = null;
    }
}