package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StarModel implements Serializable {


    private String total;
    private String updated_at;
    private String created_at;
    private String expect_status;
    private int points_count;
    private String final_expect_in_string;
    private String final_expect_result;
    private int second_team_goals_count;
    private int first_team_goals_count;
    private int user_id;
    private int match_id;
    private int round_id;
    private int season_id;
    private int league_id;
    private int id;
    private UserModel user;



    public String getTotal() {
        return total;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getExpect_status() {
        return expect_status;
    }

    public int getPoints_count() {
        return points_count;
    }

    public String getFinal_expect_in_string() {
        return final_expect_in_string;
    }

    public String getFinal_expect_result() {
        return final_expect_result;
    }

    public int getSecond_team_goals_count() {
        return second_team_goals_count;
    }

    public int getFirst_team_goals_count() {
        return first_team_goals_count;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public int getRound_id() {
        return round_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public int getLeague_id() {
        return league_id;
    }

    public int getId() {
        return id;
    }

    public UserModel getUser() {
        return user;
    }
}
