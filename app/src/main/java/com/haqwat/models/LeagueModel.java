package com.haqwat.models;

import java.io.Serializable;

public class LeagueModel implements Serializable {
    private int id;
    private String title;
    private String flag;
    private String logo;
    private String type;
    private int default_rounds_count;
    private String details;
    private boolean isSelected= false;

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
