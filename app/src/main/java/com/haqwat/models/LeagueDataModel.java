package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LeagueDataModel implements Serializable {

    @SerializedName(value = "data",alternate = {"leagues"})
    private List<LeagueModel> data;

    public List<LeagueModel> getData() {
        return data;
    }
}
