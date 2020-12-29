package com.haqwat.mvp.activity_home_mvp;

import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;

public interface ActivityHomeView {
    void onBackPressed(int itemId);
    void onLogoutSuccess(AccountsModel model);
    void onLogoutFailed(String msg);
    void onUserUpdate(UserModel userModel);
    void onStatusSuccess(UserModel userModel);
    void showProgressDialog();
    void hideProgressDialog();
    void onFailed(String msg);


}
