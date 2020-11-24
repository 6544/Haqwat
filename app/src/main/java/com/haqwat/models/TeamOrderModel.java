package com.haqwat.models;

import java.io.Serializable;

public class TeamOrderModel implements Serializable {
    private int id;
    private String title;
    private String logo;
    private String started_at;
    private String details;
    private int rate_to_display;

    public TeamOrderModel() {
    }

    public TeamOrderModel(int rate_to_display) {
        this.rate_to_display = rate_to_display;
    }

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

    public int getRate_to_display() {
        return rate_to_display;
    }
}
