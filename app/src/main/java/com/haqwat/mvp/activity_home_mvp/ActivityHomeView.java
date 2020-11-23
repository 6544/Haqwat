package com.haqwat.mvp.activity_home_mvp;

public interface ActivityHomeView {
    void onBackPressed(int itemId);
    void onLogoutSuccess();
    void onLogoutFailed(String msg);
    void showProgressDialog();
    void hideProgressDialog();
}
