package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class LeagueDataModel implements Serializable {
    private List<LeagueModel> data;

    public List<LeagueModel> getData() {
        return data;
    }
}
