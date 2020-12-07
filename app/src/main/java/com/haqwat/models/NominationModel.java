package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NominationModel implements Serializable {
    private String updated_at;
    private String created_at;
    private String is_active;
    private int default_rounds_count;
    private String type;
    private String logo;
    private String flag;
    private String title;
    private int id;
    private List<TeamModel> teams = new ArrayList<>();
    private SingleNomination single_nomination;


    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getIs_active() {
        return is_active;
    }

    public int getDefault_rounds_count() {
        return default_rounds_count;
    }

    public String getType() {
        return type;
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

    public List<TeamModel> getTeams() {
        return teams;
    }

    public SingleNomination getSingle_nomination() {
        return single_nomination;
    }

    public void setTeams(List<TeamModel> teams) {
        this.teams = teams;
    }

    public static class SingleNomination implements Serializable
    {

        private Recommended_Team recommended_team;
        private Favorite_Team favorite_team;
        private String updated_at;
        private String created_at;
        private String is_finished;
        private int points_count;
        private int recommended_team_id;
        private int favorite_team_id;
        private int season_id;
        private int league_id;
        private int user_id;
        private int id;

        public Recommended_Team getRecommended_team() {
            return recommended_team;
        }

        public Favorite_Team getFavorite_team() {
            return favorite_team;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getIs_finished() {
            return is_finished;
        }

        public int getPoints_count() {
            return points_count;
        }

        public int getRecommended_team_id() {
            return recommended_team_id;
        }

        public int getFavorite_team_id() {
            return favorite_team_id;
        }

        public int getSeason_id() {
            return season_id;
        }

        public int getLeague_id() {
            return league_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getId() {
            return id;
        }

        public static class Recommended_Team {
            private String updated_at;
            private String created_at;
            private String is_active;
            private String started_at;
            private String logo;
            private String title;
            private int id;

            public String getUpdated_at() {
                return updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getIs_active() {
                return is_active;
            }

            public String getStarted_at() {
                return started_at;
            }

            public String getLogo() {
                return logo;
            }

            public String getTitle() {
                return title;
            }

            public int getId() {
                return id;
            }
        }

        public static class Favorite_Team implements Serializable{
            private String updated_at;
            private String created_at;
            private String is_active;
            private String started_at;
            private String logo;
            private String title;
            private int id;

            public String getUpdated_at() {
                return updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getIs_active() {
                return is_active;
            }

            public String getStarted_at() {
                return started_at;
            }

            public String getLogo() {
                return logo;
            }

            public String getTitle() {
                return title;
            }

            public int getId() {
                return id;
            }
        }
    }
}
