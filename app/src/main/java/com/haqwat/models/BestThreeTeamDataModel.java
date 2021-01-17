package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class BestThreeTeamDataModel implements Serializable {
    private List<BestThreeTeamModel> data;

    public List<BestThreeTeamModel> getData() {
        return data;
    }
}
