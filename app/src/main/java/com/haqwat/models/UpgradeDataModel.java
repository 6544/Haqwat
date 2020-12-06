package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class UpgradeDataModel implements Serializable {
    private List<UpgradeModel> data;

    public List<UpgradeModel> getData() {
        return data;
    }
}
