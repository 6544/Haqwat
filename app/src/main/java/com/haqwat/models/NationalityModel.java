package com.haqwat.models;

import java.io.Serializable;

public class NationalityModel implements Serializable {
    private int id;
    private String countrycode;
    private String countryname;
    private String countryname_ar;
    private String flag_large;

    public NationalityModel(int id, String countrycode, String countryname, String countryname_ar, String flag_large) {
        this.id = id;
        this.countrycode = countrycode;
        this.countryname = countryname;
        this.countryname_ar = countryname_ar;
        this.flag_large = flag_large;
    }

    public int getId() {
        return id;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public String getCountryname() {
        return countryname;
    }

    public String getCountryname_ar() {
        return countryname_ar;
    }

    public String getFlag_large() {
        return flag_large;
    }
}
