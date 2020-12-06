package com.haqwat.models;

import com.haqwat.models.RewardModel;

import java.io.Serializable;
import java.util.List;

public class RewardDataModel implements Serializable {
    private List<RewardModel> data;

    public List<RewardModel> getData() {
        return data;
    }


}
