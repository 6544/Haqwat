package com.haqwat.models;

import com.haqwat.models.RewardModel;

import java.io.Serializable;
import java.util.List;

public class RewardDataModel implements Serializable {
    private List<RewardModel> data;
    private Competition Competition;
    public List<RewardModel> getData() {
        return data;
    }

    public RewardDataModel.Competition getCompetition() {
        return Competition;
    }

    public static class Competition implements Serializable{
        private int id;
        private String title;
        private String flag;
        private String logo;
        private String started_at;
        private String finished_at;
        private String details;
        private String type;
        private String league_type;
        private String is_finished;
        private String match_id;
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

        public String getStarted_at() {
            return started_at;
        }

        public String getFinished_at() {
            return finished_at;
        }

        public String getDetails() {
            return details;
        }

        public String getType() {
            return type;
        }

        public String getLeague_type() {
            return league_type;
        }

        public String getIs_finished() {
            return is_finished;
        }

        public String getMatch_id() {
            return match_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }


}
