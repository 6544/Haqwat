package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class StarDataModel implements Serializable {
    private List<StarModel> stars;

    public List<StarModel> getStars() {
        return stars;
    }
}
