package com.nexusteam.blacklogics.activityfile;

import com.nexusteam.internal.ho;
import com.nexusteam.internal.beans.ProjectFileBean;

public final class HoBridge implements ho {
    private final OnProjectFileChangeListener listener;

    public HoBridge(OnProjectFileChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void a(int type, ProjectFileBean projectFileBean) {
        if (listener != null) {
            listener.onProjectFileSelected(type, projectFileBean);
        }
    }
}
