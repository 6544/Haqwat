package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class NationalityDataModel implements Serializable {
    private List<NationalityModel> data;

    public List<NationalityModel> getData() {
        return data;
    }
}
