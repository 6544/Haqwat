package com.haqwat.ui.activity_sign_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.haqwat.R;
import com.haqwat.databinding.ActivitySignUpBinding;
import com.haqwat.language.Language;
import com.haqwat.models.SignUpModel;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.haqwat.ui.activity_confirm_code.ConfirmCodeActivity;
import com.haqwat.ui.activity_login.LoginActivity;

import java.util.Locale;

import io.paperdb.Paper;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpModel signUpModel;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        initView();
    }

    private void initView() {
        if (signUpModel==null){
            signUpModel = new SignUpModel();
        }
        binding.setModel(signUpModel);

        binding.btnSignUp.setOnClickListener(view -> {
            if (signUpModel.isStep1Valid(getApplicationContext())){
                Intent intent  = new Intent(this, ConfirmCodeActivity.class);
                intent.putExtra("data",signUpModel);
                startActivity(intent);
                finish();
            }

        });

        binding.tvLogin.setOnClickListener(view -> {
            navigateToLoginActivity();
        });
    }

    private void navigateToLoginActivity() {
        Intent intent  = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        navigateToLoginActivity();
    }
}