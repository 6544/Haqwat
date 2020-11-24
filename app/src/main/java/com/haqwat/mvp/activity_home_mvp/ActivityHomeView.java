package com.haqwat.mvp.activity_home_mvp;

import com.haqwat.models.HomeModel;

public interface ActivityHomeView {
    void onBackPressed(int itemId);
    void onLogoutSuccess();
    void onLogoutFailed(String msg);
    void showProgressDialog();
    void hideProgressDialog();


}
