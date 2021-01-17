package com.haqwat.models;

import java.io.Serializable;

public class FavoriteTeamModel implements Serializable {
    private int id;
    private String title;
    private String logo;
    private String started_at;
    private String details;
    private String is_active;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    public String getStarted_at() {
        return started_at;
    }

    public String getDetails() {
        return details;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
