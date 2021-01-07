package com.haqwat.mvp.activity_update_profile_mvp;

import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;

public interface ActivityUpdateProfileView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);


}
