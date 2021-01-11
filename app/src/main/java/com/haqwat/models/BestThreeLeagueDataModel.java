package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class BestThreeLeagueDataModel implements Serializable {
    private List<BestThreeLeagueModel> data;

    public List<BestThreeLeagueModel> getData() {
        return data;
    }
}
