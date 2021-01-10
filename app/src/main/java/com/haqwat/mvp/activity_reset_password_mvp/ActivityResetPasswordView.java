package com.haqwat.mvp.activity_reset_password_mvp;

import com.haqwat.models.UserModel;

public interface ActivityResetPasswordView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);

}
