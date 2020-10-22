package com.haqwat.mvp.splash_mvp;

import android.content.Context;
import android.os.Handler;

import com.haqwat.models.UserModel;
import com.haqwat.preferences.Preferences;

public class SplashPresenter {
    private SplashView splashView;
    private Preferences preferences;
    private Context context;

    public SplashPresenter(SplashView splashView,Context context) {
        this.splashView = splashView;
        this.context = context;
        preferences = Preferences.getInstance();


    }

    private UserModel getUser(){
       return preferences.getUserData(context);
    }
    public void startSplashCounter(){
        new Handler().postDelayed(()->{
            UserModel userModel = getUser();
            if (userModel==null){
                splashView.onNavigateToLoginActivity();
            }else {
                splashView.onNavigateToHomeActivity();
            }
        },3000);
    }
}
