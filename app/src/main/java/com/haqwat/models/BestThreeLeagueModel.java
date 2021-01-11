package com.haqwat.models;

import java.io.Serializable;

public class BestThreeLeagueModel implements Serializable {
    private int id;
    private int user_id;
    private int league_id;
    private int team_id;
    private String is_default;
    private String created_at;
    private String updated_at;
    private int subscribers;
    private League league;

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getLeague_id() {
        return league_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public String getIs_default() {
        return is_default;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public League getLeague() {
        return league;
    }

    public static class League implements Serializable {
        private int id;
        private int order_number;
        private String title;
        private String flag;
        private String logo;
        private String type;
        private int default_rounds_count;
        private String details;
        private String is_active;
        private String link_of_share;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public int getOrder_number() {
            return order_number;
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

        public String getLink_of_share() {
            return link_of_share;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
