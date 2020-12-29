package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class HomeJoinedTeamsModel implements Serializable {
    private int id;
    private String title;
    private String flag;
    private String logo;
    private String type;
    private int default_rounds_count;
    private String  details;
    private String is_active;
    private String link_of_share;
    private String created_at;
    private String updated_at;
    private List<TeamOrderModel> teamsOrderInDesc;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFlag() {
        return flag;
    }

    public String getLogo() {
        return logo;
    }

    public String getType() {
        return type;
    }

    public int getDefault_rounds_count() {
        return default_rounds_count;
    }

    public String getDetails() {
        return details;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getLink_of_share() {
        return link_of_share;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public List<TeamOrderModel> getTeamsOrderInDesc() {
        return teamsOrderInDesc;
    }
}
