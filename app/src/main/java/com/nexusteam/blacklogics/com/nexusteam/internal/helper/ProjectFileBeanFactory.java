package com.nexusteam.internal.helper;

import com.nexusteam.internal.beans.ProjectFileBean;

public class ProjectFileBeanFactory {

    /**
     * Create new Activity layout file
     */
    public static ProjectFileBean createActivity(
            String xmlName,
            int orientation,
            boolean fullscreen,
            boolean drawer,
            boolean toolbar,
            boolean fab
    ) {

        ProjectFileBean bean = new ProjectFileBean(0, xmlName);


        bean.fileType = ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY; // usually 1
        bean.fileName = xmlName;
        bean.orientation = orientation;


        bean.options = 0;

        if (fullscreen) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN; // 1
        }
        if (drawer) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_DRAWER; // 2
        }
        if (toolbar) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_TOOLBAR; // 4
        }
        if (fab) {
            bean.options |= ProjectFileBean.OPTION_ACTIVITY_FAB; // 8
        }

        return bean;
    }

    /**
     * Create custom view layout
     */
    public static ProjectFileBean createCustomView(
            String xmlName,
            int orientation
    ) {

        ProjectFileBean bean = new ProjectFileBean(0, xmlName);

        bean.fileType = ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW; // usually 2
        bean.fileName = xmlName;
        bean.orientation = orientation;
        bean.options = 0;

        return bean;
    }
}
