package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class MatchesDataModel implements Serializable {
    private List<MatchesModel> data;

    public List<MatchesModel> getData() {
        return data;
    }
}
