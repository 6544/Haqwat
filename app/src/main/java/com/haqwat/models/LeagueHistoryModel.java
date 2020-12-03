package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class LeagueHistoryModel implements Serializable {

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
    private List<LeagueInformation> league_information;

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

    public List<LeagueInformation> getLeague_information() {
        return league_information;
    }

    public static class LeagueInformation {
        private int id;
        private int league_id;
        private String name;
        private String image;
        private String result;
        private String team_related;
        private String team_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public int getLeague_id() {
            return league_id;
        }

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

        public String getResult() {
            return result;
        }

        public String getTeam_related() {
            return team_related;
        }

        public String getTeam_id() {
            return team_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }


}
