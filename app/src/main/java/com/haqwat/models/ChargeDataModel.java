package com.haqwat.models;

import java.util.List;

public class ChargeDataModel {
    private List<ChargeModel> leagues;
    private ChargeTotalModel total;

    public List<ChargeModel> getLeagues() {
        return leagues;
    }

    public ChargeTotalModel getTotal() {
        return total;
    }
}
