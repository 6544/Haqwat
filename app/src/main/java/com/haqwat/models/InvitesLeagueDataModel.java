package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class InvitesLeagueDataModel implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private int allowable_leagues_count;
        private int count_of_user_leagues;
        private int count_of_remaining;
        private List<UserLeague> user_leagues;
        private List<OtherLeague> other_leagues;

        public int getAllowable_leagues_count() {
            return allowable_leagues_count;
        }

        public int getCount_of_user_leagues() {
            return count_of_user_leagues;
        }

        public int getCount_of_remaining() {
            return count_of_remaining;
        }

        public List<UserLeague> getUser_leagues() {
            return user_leagues;
        }

        public List<OtherLeague> getOther_leagues() {
            return other_leagues;
        }
    }

    public static class UserLeague implements Serializable {
        private int id;
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

    public static class OtherLeague implements Serializable{
        private int id;
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
