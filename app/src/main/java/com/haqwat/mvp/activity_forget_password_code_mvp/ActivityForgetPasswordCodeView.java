package com.haqwat.mvp.activity_forget_password_code_mvp;

import com.haqwat.models.UserModel;

public interface ActivityForgetPasswordCodeView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);

}
