package com.haqwat.mvp.activity_more_mvp;

import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;

public interface ActivityMoreView {
    void onUserUpdate(UserModel userModel);
    void onFailed(String msg);
    void onStatusSuccess(UserModel userModel);


}
