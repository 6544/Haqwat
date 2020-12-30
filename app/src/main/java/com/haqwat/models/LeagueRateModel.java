package com.haqwat.models;

import java.io.Serializable;

public class LeagueRateModel implements Serializable {

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
    private int league_rate;
    private int matches_count;
    private int true_expectations;
    private int false_expectations;
    private int all_expectations;
    private UserModel user;
    private FavoriteTeam favorite_team;
    private RecommendedTeam recommended_team;

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

    public int getLeague_rate() {
        return league_rate;
    }

    public int getMatches_count() {
        return matches_count;
    }

    public int getTrue_expectations() {
        return true_expectations;
    }

    public int getFalse_expectations() {
        return false_expectations;
    }

    public int getAll_expectations() {
        return all_expectations;
    }

    public UserModel getUser() {
        return user;
    }

    public FavoriteTeam getFavorite_team() {
        return favorite_team;
    }

    public RecommendedTeam getRecommended_team() {
        return recommended_team;
    }

    public static class FavoriteTeam{
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

    public static class RecommendedTeam{
        private int id;
        private String title;
        private String logo;
        private String started_at;
        private String details;
        private String is_active;
        private String  created_at;
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
