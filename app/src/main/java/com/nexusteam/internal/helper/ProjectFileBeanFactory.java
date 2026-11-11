package com.nexusteam.internal.helper;

import com.nexusteam.internal.beans.ProjectFileBean;

public class ProjectFileBeanFactory {

    public static ProjectFileBean createActivity(
            String xmlName,
            int orientation,
            boolean fullscreen,
            boolean drawer,
            boolean toolbar,
            boolean fab
    ) {

        ProjectFileBean bean = new ProjectFileBean(0, xmlName);


        bean.fileType = ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY;
        bean.fileName = xmlName;
        bean.orientation = orientation;


        bean.options = 0;

        if (fullscreen) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN;
        }
        if (drawer) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_DRAWER;
        }
        if (toolbar) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_TOOLBAR;
        }
        if (fab) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_FAB;
        }

        return bean;
    }

    public static ProjectFileBean createCustomView(
            String xmlName,
            int orientation
    ) {

        ProjectFileBean bean = new ProjectFileBean(0, xmlName);

        bean.fileType = ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW;
        bean.fileName = xmlName;
        bean.orientation = orientation;
        bean.options = 0;

        return bean;
    }
}
