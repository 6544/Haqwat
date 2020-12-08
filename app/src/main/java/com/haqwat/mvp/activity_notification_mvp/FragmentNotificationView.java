package com.haqwat.mvp.activity_notification_mvp;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.NotificationModel;

import java.util.List;

public interface FragmentNotificationView {
    void onSuccess(List<NotificationModel> notificationModelList);
    void onLoadMoreSuccess(List<NotificationModel> notificationModelList,int page);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
