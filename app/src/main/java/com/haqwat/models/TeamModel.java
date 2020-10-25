package com.haqwat.models;

import java.io.Serializable;

public class TeamModel implements Serializable {
    private int id;
    private String title;
    private String logo;
    private String details;
    private boolean isSelected = false;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
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
