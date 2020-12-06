package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpgradeModel implements Serializable {


    private String updated_at;
    private String created_at;
    private String type;
    private String desc;
    private String value_type;
    private String value;
    private String title;
    private int id;

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue_type() {
        return value_type;
    }

    public String getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
