package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class TableArrangementDataModel implements Serializable {
    private List<TableArrangementModel> data;

    public List<TableArrangementModel> getData() {
        return data;
    }
}
