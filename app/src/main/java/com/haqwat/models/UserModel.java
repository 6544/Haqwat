package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {
    private int id;
    private String name;
    private String email;
    private String code;
    private String has_haqawat_subscribe;
    private String is_social_media_register;
    private int allowable_teams_count;
    private String country_id;
    private String league_id;
    private String team_id;
    private String phone_code;
    private String phone;
    private String logo;
    private String is_confirmed;
    private String token;
    private String firebase_token;
    private String points_count;
    private Country country;



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public String getHas_haqawat_subscribe() {
        return has_haqawat_subscribe;
    }

    public String getIs_social_media_register() {
        return is_social_media_register;
    }

    public int getAllowable_teams_count() {
        return allowable_teams_count;
    }

    public String getCountry_id() {
        return country_id;
    }

    public String getLeague_id() {
        return league_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public String getLogo() {
        return logo;
    }

    public String getToken() {
        return token;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public String getIs_confirmed() {
        return is_confirmed;
    }

    public String getPoints_count() {
        return points_count;
    }

    public Country getCountry() {
        return country;
    }

    /////////////////////////

    public static  class Country implements Serializable {

        private String updated_at;
        private String created_at;
        private String flag_extra_larg;
        private String flag_large;
        private String flag_medium;
        private String flag;
        private String code;
        private String countryname_ar;
        private String countryname;
        private String countrycode;
        private int id;

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getFlag_extra_larg() {
            return flag_extra_larg;
        }

        public String getFlag_large() {
            return flag_large;
        }

        public String getFlag_medium() {
            return flag_medium;
        }

        public String getFlag() {
            return flag;
        }

        public String getCode() {
            return code;
        }

        public String getCountryname_ar() {
            return countryname_ar;
        }

        public String getCountryname() {
            return countryname;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public int getId() {
            return id;
        }
    }


}
