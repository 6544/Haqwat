package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class HomeModel implements Serializable {
    private int currentUserRate;
    private int allUsersRate;
    private UserDefaultLeague userDefaultLeague;
    private List<TeamOrderModel> teamsOrderInDesc;

    public int getCurrentUserRate() {
        return currentUserRate;
    }

    public int getAllUsersRate() {
        return allUsersRate;
    }

    public UserDefaultLeague getUserDefaultLeague() {
        return userDefaultLeague;
    }

    public List<TeamOrderModel> getTeamsOrderInDesc() {
        return teamsOrderInDesc;
    }
}
