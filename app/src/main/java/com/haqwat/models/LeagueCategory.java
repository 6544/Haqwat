package com.haqwat.models;

import java.io.Serializable;

public class LeagueCategory implements Serializable {
    private int imageResource;
    private String name;
    private String tag="";
    private boolean isSelected = false;

    public LeagueCategory(int imageResource, String name,String tag) {
        this.imageResource = imageResource;
        this.name = name;
        this.tag = tag;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTag() {
        return tag;
    }
}
