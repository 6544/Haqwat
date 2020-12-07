package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppDataModel implements Serializable {
    private String desc;
    private String title;
    private String updated_at;
    private String created_at;
    private int stories_time_in_days;
    private int debt_limt;
    private int site_commission;
    private String whatsapp;
    private String snapchat_ghost;
    private String google_plus;
    private String youtube;
    private String telegram;
    private String linkedin;
    private String instagram;
    private String twitter;
    private String facebook;
    private int offer_notification;
    private String default_language;
    private double longitude;
    private double latitude;
    private String address2;
    private String address1;
    private String en_desc;
    private String ar_desc;
    private String en_title;
    private String ar_title;
    private String footer_logo;
    private String header_logo;
    private int id;

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getStories_time_in_days() {
        return stories_time_in_days;
    }

    public int getDebt_limt() {
        return debt_limt;
    }

    public int getSite_commission() {
        return site_commission;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public String getSnapchat_ghost() {
        return snapchat_ghost;
    }

    public String getGoogle_plus() {
        return google_plus;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getTelegram() {
        return telegram;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public int getOffer_notification() {
        return offer_notification;
    }

    public String getDefault_language() {
        return default_language;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress1() {
        return address1;
    }

    public String getEn_desc() {
        return en_desc;
    }

    public String getAr_desc() {
        return ar_desc;
    }

    public String getEn_title() {
        return en_title;
    }

    public String getAr_title() {
        return ar_title;
    }

    public String getFooter_logo() {
        return footer_logo;
    }

    public String getHeader_logo() {
        return header_logo;
    }

    public int getId() {
        return id;
    }
}
