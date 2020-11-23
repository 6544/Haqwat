package com.haqwat.mvp.login_mvp;

import com.haqwat.models.UserModel;

public interface LoginView {
    void onSuccess(UserModel userModel);
    void navigateToConfirmCode(UserModel userModel);
    void navigateToSignUp();
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
