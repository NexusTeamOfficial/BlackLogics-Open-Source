package com.nexusteam.blacklogics.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AboutTeamData {

    @SerializedName("discordInviteLink")
    private String discordInviteLink;

    @SerializedName("moddersteam")
    private List<TeamMember> teamMembers;

    @SerializedName("changelog")
    private List<ChangeLogItem> changeLogItems;

    public String getDiscordInviteLink() {
        return discordInviteLink;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public List<ChangeLogItem> getChangeLogItems() {
        return changeLogItems;
    }
}
