package com.haqwat.mvp.login_mvp;

import android.content.Context;

import com.haqwat.models.LoginModel;

public class LoginPresenter {
    private Context context;
    private LoginView view;

    public LoginPresenter(Context context, LoginView view) {
        this.context = context;
        this.view = view;
    }

    public void isDataValid(LoginModel loginModel){
        if (loginModel.isDataValid(context)){
            login(loginModel);
        }
    }

    private void login(LoginModel loginModel) {

    }


}
