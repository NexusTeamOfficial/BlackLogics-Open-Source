package com.nexusteam.blacklogics.bean;

import com.google.gson.annotations.SerializedName;

public class ChangeLogItem {

    @SerializedName("title")
    private String title;

    @SerializedName("isTitled")
    private boolean isTitle;

    @SerializedName("isBeta")
    private boolean isBeta;

    @SerializedName("releaseDate")
    private long releaseDate;

    @SerializedName("description")
    private String description;

    private boolean showingAdditionalInfo = false;

    public String getTitle() {
        return title;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public boolean isBeta() {
        return isBeta;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isShowingAdditionalInfo() {
        return showingAdditionalInfo;
    }

    public void setShowingAdditionalInfo(boolean value) {
        this.showingAdditionalInfo = value;
    }
}
