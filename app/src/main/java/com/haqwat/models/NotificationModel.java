package com.haqwat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationModel implements Serializable {

    private String updated_at;
    private String created_at;
    private String is_read;
    private long notification_date;
    private String action_type;
    private int to_user_id;
    private String message;
    private String title;
    private int id;

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getIs_read() {
        return is_read;
    }

    public long getNotification_date() {
        return notification_date;
    }

    public String getAction_type() {
        return action_type;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
