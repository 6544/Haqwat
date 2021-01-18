package com.haqwat.models;

import java.io.Serializable;

public class ChargeModel implements Serializable {
    private int id;
    private String title;
    private String flag;
    private String logo;
    private String type;
    private int default_rounds_count;
    private String details;
    private String is_active;
    private String created_at;
    private String updated_at;
    private String in_haqawat_competition="";
    private int true_expectations_count;
    private int all_expectations_count;
    private int rate;
    private int my_points;

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

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
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

    public int getMy_points() {
        return my_points;
    }
}
