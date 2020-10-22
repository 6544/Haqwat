package com.haqwat.ui.activity_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.haqwat.R;
import com.haqwat.databinding.ActivityLoginBinding;
import com.haqwat.databinding.ActivitySplashBinding;
import com.haqwat.language.Language;
import com.haqwat.mvp.splash_mvp.SplashPresenter;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.updateResources(newBase,"ar"));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        initView();
    }

    private void initView() {

    }
}