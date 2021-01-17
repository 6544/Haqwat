package com.haqwat.models;

import java.io.Serializable;

public class TeamModel2 implements Serializable {
   private int id;
   private int user_id;
   private int league_id;
   private int season_id;
   private int favorite_team_id;
   private int recommended_team_id;
   private int points_count;
   private String is_finished;
   private String created_at;
   private String updated_at;
   private int recommendeds;
   private FavoriteTeamModel favorite_team;
   private FavoriteTeamModel recommended_team;

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getLeague_id() {
        return league_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public int getFavorite_team_id() {
        return favorite_team_id;
    }

    public int getRecommended_team_id() {
        return recommended_team_id;
    }

    public int getPoints_count() {
        return points_count;
    }

    public String getIs_finished() {
        return is_finished;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getRecommendeds() {
        return recommendeds;
    }

    public FavoriteTeamModel getFavorite_team() {
        return favorite_team;
    }

    public FavoriteTeamModel getRecommended_team() {
        return recommended_team;
    }
}
