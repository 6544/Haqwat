package com.haqwat.ui.activity_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.haqwat.R;
import com.haqwat.databinding.ActivityLoginBinding;
import com.haqwat.language.Language;
import com.haqwat.models.LoginModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.login_mvp.LoginPresenter;
import com.haqwat.mvp.login_mvp.LoginView;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.haqwat.ui.activity_sign_up.SignUpActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private ActivityLoginBinding binding;
    private LoginModel loginModel;
    private LoginPresenter presenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        initView();
    }

    private void initView() {
        loginModel = new LoginModel();
        binding.setModel(loginModel);
        presenter = new LoginPresenter(this,this);
        binding.tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();

        });

        binding.btnLogin.setOnClickListener(view -> {
            presenter.isDataValid(loginModel);
        });
    }

    @Override
    public void onSuccess(UserModel userModel) {

    }

    @Override
    public void onFailed(String msg) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}