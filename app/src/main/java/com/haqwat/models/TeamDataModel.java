package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class TeamDataModel implements Serializable {
    private List<TeamModel> data;

    public List<TeamModel> getData() {
        return data;
    }
}
