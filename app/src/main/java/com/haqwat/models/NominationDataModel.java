package com.haqwat.models;

import com.haqwat.models.NominationModel;

import java.io.Serializable;
import java.util.List;

public class NominationDataModel implements Serializable {
    private List<NominationModel> data;

    public List<NominationModel> getData() {
        return data;
    }
}
