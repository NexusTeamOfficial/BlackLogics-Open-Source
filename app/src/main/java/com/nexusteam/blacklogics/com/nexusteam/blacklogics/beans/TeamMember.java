package com.nexusteam.blacklogics.bean;

import com.google.gson.annotations.SerializedName;

public class TeamMember {

    @SerializedName("modder_username")
    private String name;

    @SerializedName("modder_description")
    private String description;

    @SerializedName("modder_img")
    private String imageUrl;

    @SerializedName("isTitled")
    private boolean isTitle;

    @SerializedName("title")
    private String title;

    @SerializedName("isMainModder")
    private boolean isMainModder;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public String getTitle() {
        return title;
    }

    public boolean isMainModder() {
        return isMainModder;
    }
}
