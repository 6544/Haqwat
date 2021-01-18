package com.haqwat.models;

import java.io.Serializable;

public class ChargeTotalModel implements Serializable {
    private int total_true_expectation_count;
    private int total_expectation_count;
    private int total_true_expectation_rate;
    private int total_my_points;

    public int getTotal_true_expectation_count() {
        return total_true_expectation_count;
    }

    public int getTotal_expectation_count() {
        return total_expectation_count;
    }

    public int getTotal_true_expectation_rate() {
        return total_true_expectation_rate;
    }

    public int getTotal_my_points() {
        return total_my_points;
    }
}
