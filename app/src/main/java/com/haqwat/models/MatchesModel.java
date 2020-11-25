package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class MatchesModel implements Serializable {

    private int id;
    private String title;
    private String flag;
    private String logo;
    private String type;
    private int default_rounds_count;
    private String details;
    private String is_active;
    private String created_at;
    private String  updated_at;
    private RoundModel next_round;
    private List<MatchModel> next_matches;
    private RoundModel prev_round;
    private List<MatchModel> prev_matches;


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

    public RoundModel getNext_round() {
        return next_round;
    }

    public List<MatchModel> getNext_matches() {
        return next_matches;
    }

    public RoundModel getPrev_round() {
        return prev_round;
    }

    public List<MatchModel> getPrev_matches() {
        return prev_matches;
    }

    public static class RoundModel implements Serializable
    {
        private int id;
        private String title;
        private String started_at;
        private String finished_at;
        private int league_id;
        private int season_id;
        private int order_number;
        private String is_finished;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getStarted_at() {
            return started_at;
        }

        public String getFinished_at() {
            return finished_at;
        }

        public int getLeague_id() {
            return league_id;
        }

        public int getSeason_id() {
            return season_id;
        }

        public int getOrder_number() {
            return order_number;
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
    }
    public static class FirstTeam implements Serializable
    {
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

    public static class MatchModel implements Serializable{
       private int id;
       private String date;
       private String started_time;
       private int league_id;
       private int season_id;
       private int round_id;
       private int first_team_id;
       private int second_team_id;
       private int first_team_goals_count;
       private int second_team_goals_count;
       private String is_delay;
       private String details;
       private String created_at;
       private String updated_at;
       private String match_expectation_result;
       private int win_first_team_rate;
       private int win_second_team_rate;
       private int win_draw_rate;
       private UserExpectationModel user_expectation;
       private String date_for_order;
       private String time_for_order;
       private FirstTeam first_team;
       private FirstTeam second_team;

        public int getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public String getStarted_time() {
            return started_time;
        }

        public int getLeague_id() {
            return league_id;
        }

        public int getSeason_id() {
            return season_id;
        }

        public int getRound_id() {
            return round_id;
        }

        public int getFirst_team_id() {
            return first_team_id;
        }

        public int getSecond_team_id() {
            return second_team_id;
        }

        public int getFirst_team_goals_count() {
            return first_team_goals_count;
        }

        public int getSecond_team_goals_count() {
            return second_team_goals_count;
        }

        public String getIs_delay() {
            return is_delay;
        }

        public String getDetails() {
            return details;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getMatch_expectation_result() {
            return match_expectation_result;
        }

        public int getWin_first_team_rate() {
            return win_first_team_rate;
        }

        public int getWin_second_team_rate() {
            return win_second_team_rate;
        }

        public int getWin_draw_rate() {
            return win_draw_rate;
        }

        public UserExpectationModel getUser_expectation() {
            return user_expectation;
        }

        public String getDate_for_order() {
            return date_for_order;
        }

        public String getTime_for_order() {
            return time_for_order;
        }

        public FirstTeam getFirst_team() {
            return first_team;
        }

        public FirstTeam getSecond_team() {
            return second_team;
        }
    }

    public static class UserExpectationModel implements Serializable{
        private int id;
        private int first_team_goals_count;
        private int second_team_goals_count;
        private String final_expect_result;
        private int points_count;
        private String expect_status;

        public int getId() {
            return id;
        }

        public int getFirst_team_goals_count() {
            return first_team_goals_count;
        }

        public int getSecond_team_goals_count() {
            return second_team_goals_count;
        }

        public String getFinal_expect_result() {
            return final_expect_result;
        }

        public int getPoints_count() {
            return points_count;
        }

        public String getExpect_status() {
            return expect_status;
        }
    }





}
