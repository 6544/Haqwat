package com.haqwat.mvp.activity_confirm_code_activity_mvp;

import com.haqwat.models.UserModel;

public interface ConfirmCodeView {
    void onCounterStarted(String time);
    void onCounterFinished();
    void navigateToLoginActivity();
    void onSuccess(UserModel userModel);
    void onFailed(String msg);
    void showProgress();
    void hideProgress();
}
