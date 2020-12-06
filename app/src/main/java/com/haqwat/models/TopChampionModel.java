package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public  class TopChampionModel implements Serializable {

    private List<UserModel> users;
    private Competition competition;

    public List<UserModel> getUsers() {
        return users;
    }

    public Competition getCompetition() {
        return competition;
    }

    public static class Competition implements Serializable{
        private String updated_at;
        private String created_at;
        private String is_finished;
        private String league_type;
        private String type;
        private String finished_at;
        private String started_at;
        private String logo;
        private String flag;
        private String title;
        private int id;

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getIs_finished() {
            return is_finished;
        }

        public String getLeague_type() {
            return league_type;
        }

        public String getType() {
            return type;
        }

        public String getFinished_at() {
            return finished_at;
        }

        public String getStarted_at() {
            return started_at;
        }

        public String getLogo() {
            return logo;
        }

        public String getFlag() {
            return flag;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }
    }
}
