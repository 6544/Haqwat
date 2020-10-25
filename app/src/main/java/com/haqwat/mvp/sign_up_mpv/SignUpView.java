package com.haqwat.mvp.sign_up_mpv;

import com.haqwat.models.SignUpModel;
import com.haqwat.models.UserModel;

public interface SignUpView {

    void onDisplayFragmentStep1();
    void onDisplayFragmentStep2();
    void onDisplayFragmentStep3();
    void onBack();
    void onSuccess(UserModel userModel);
    void onFailed(String msg);
    void hideProgressBar();
    void showProgressBar();


}
