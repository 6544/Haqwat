package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RewardModel implements Serializable {

    private String updated_at;
    private String created_at;
    private int competition_id;
    private String image;
    private String currency;
    private int value;
    private int order_number;
    private int id;

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getCompetition_id() {
        return competition_id;
    }

    public String getImage() {
        return image;
    }

    public String getCurrency() {
        return currency;
    }

    public int getValue() {
        return value;
    }

    public int getOrder_number() {
        return order_number;
    }

    public int getId() {
        return id;
    }
}
