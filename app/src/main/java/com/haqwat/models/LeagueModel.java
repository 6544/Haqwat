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
    private String in_haqawat_competition;
    private int true_expectations_count;
    private int all_expectations_count;
    private int rate;
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

    public String getIn_haqawat_competition() {
        return in_haqawat_competition;
    }

    public int getTrue_expectations_count() {
        return true_expectations_count;
    }

    public int getAll_expectations_count() {
        return all_expectations_count;
    }

    public int getRate() {
        return rate;
    }


}
