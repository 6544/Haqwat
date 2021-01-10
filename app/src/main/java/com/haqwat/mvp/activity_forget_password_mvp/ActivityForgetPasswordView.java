package com.haqwat.mvp.activity_forget_password_mvp;

import com.haqwat.models.UserModel;

public interface ActivityForgetPasswordView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);

}
