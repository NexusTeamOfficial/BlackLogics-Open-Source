package com.theblocklogics.gms.model;


public class Properties {
    private int icon;
    private String title;

    public Properties(String str, int i) {
        this.title = str;
        this.icon = i;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setIcon(int i) {
        this.icon = i;
    }

    public int getIcon() {
        return this.icon;
    }
}

