package com.nexusteam.blacklogics.activityfile;

import com.nexusteam.internal.beans.ProjectFileBean;

public interface OnProjectFileChangeListener {
    void onProjectFileSelected(int type, ProjectFileBean file);
}
