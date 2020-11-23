package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

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
}
