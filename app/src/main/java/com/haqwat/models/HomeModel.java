package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class HomeModel implements Serializable {
    private int allowable_leagues_count;
    private double currentUserRate;
    private double allUsersRate;
    private UserDefaultLeague userDefaultLeague;


    public int getAllowable_leagues_count() {
        return allowable_leagues_count;
    }

    private List<HomeJoinedTeamsModel> list_of_leagues_with_teams;

    public double getCurrentUserRate() {
        return currentUserRate;
    }

    public double getAllUsersRate() {
        return allUsersRate;
    }

    public UserDefaultLeague getUserDefaultLeague() {
        return userDefaultLeague;
    }

    public List<HomeJoinedTeamsModel> getList_of_leagues_with_teams() {
        return list_of_leagues_with_teams;
    }

}
