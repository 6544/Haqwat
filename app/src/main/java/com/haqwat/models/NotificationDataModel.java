package com.haqwat.models;

import java.io.Serializable;
import java.util.List;

public class NotificationDataModel implements Serializable {
    private int current_page;
    private List<NotificationModel> data;

    public int getCurrent_page() {
        return current_page;
    }

    public List<NotificationModel> getData() {
        return data;
    }
}
