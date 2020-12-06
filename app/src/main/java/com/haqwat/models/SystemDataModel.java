package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class SystemDataModel implements Serializable {

    private List<SystemModel>data;
    public List<SystemModel> getData() {
        return data;
    }
}
