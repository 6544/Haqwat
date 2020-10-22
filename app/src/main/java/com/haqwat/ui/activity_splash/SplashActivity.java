package com.haqwat.ui.activity_splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haqwat.R;
import com.haqwat.databinding.ActivitySplashBinding;
import com.haqwat.language.Language;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.splash_mvp.SplashPresenter;
import com.haqwat.mvp.splash_mvp.SplashView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_login.LoginActivity;

public class SplashActivity extends AppCompatActivity implements SplashView {
    private ActivitySplashBinding binding;
    private SplashPresenter presenter;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase,"ar"));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        initView();
    }

    private void initView() {
       presenter = new SplashPresenter(this,this);
       presenter.startSplashCounter();
    }

    @Override
    public void onNavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNavigateToHomeActivity() {

    }
}