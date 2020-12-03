package com.haqwat.models;

import java.io.Serializable;

public class TableArrangementModel implements Serializable {

    public int id;
    public int league_id;
    public int season_id;
    public int team_id;
    public int points_count;
    public int matches_count;
    public int played_matches_count;
    public int delay_matches_count;
    public int win_matches_count;
    public int lose_matches_count;
    public int draw_matches_count;
    public int goals_counts;
    public int goals_counts_from_other;
    public Object last_match_id;
    public String created_at;
    public String updated_at;
    public int difference_between_goals;
    public League league;
    public Team team;

    public static class League{
        private int id;
        private String title;
        private String flag;
        private String logo;
        private String type;
        private int default_rounds_count;
        private String details;
        private String is_active;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getFlag() {
            return flag;
        }

        public String getLogo() {
            return logo;
        }

        public String getType() {
            return type;
        }

        public int getDefault_rounds_count() {
            return default_rounds_count;
        }

        public String getDetails() {
            return details;
        }

        public String getIs_active() {
            return is_active;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public static class Team{
        private int id;
        private String title;
        private String logo;
        private String started_at;
        private String details;
        private String is_active;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getLogo() {
            return logo;
        }

        public String getStarted_at() {
            return started_at;
        }

        public String getDetails() {
            return details;
        }

        public String getIs_active() {
            return is_active;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }


}
