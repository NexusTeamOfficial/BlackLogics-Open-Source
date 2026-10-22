package com.nexusteam.blacklogics.model;

public class ProjectTemplate {

    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_BASIC = 1;
    public static final int TYPE_DRAWER = 2;
    public static final int TYPE_BOTTOM_NAV = 3;
    public static final int TYPE_TABS = 4;
    public static final int TYPE_FULLSCREEN = 5;
    public static final int TYPE_LOGIN = 6;
    public static final int TYPE_FAB = 7;

    private int type;
    private String name;
    private String description;
    private int previewDrawable;
    private boolean statusBar;
    private boolean toolbar;
    private boolean drawer;
    private boolean fab;

    public ProjectTemplate(int type, String name, String description,
                           int previewDrawable,
                           boolean statusBar, boolean toolbar,
                           boolean drawer, boolean fab) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.previewDrawable = previewDrawable;
        this.statusBar = statusBar;
        this.toolbar = toolbar;
        this.drawer = drawer;
        this.fab = fab;
    }

    public int getType() { return type; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPreviewDrawable() { return previewDrawable; }
    public boolean hasStatusBar() { return statusBar; }
    public boolean hasToolbar() { return toolbar; }
    public boolean hasDrawer() { return drawer; }
    public boolean hasFab() { return fab; }

    public int getPreviewMask() {
        int m = 0;
        if (statusBar) m |= 8;
        if (toolbar) m |= 4;
        if (drawer) m |= 2;
        if (fab) m |= 1;
        return m;
    }

    public boolean usesCustomChrome() {
        return toolbar || drawer;
    }
}
