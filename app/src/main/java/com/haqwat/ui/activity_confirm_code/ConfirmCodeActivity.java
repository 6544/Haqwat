package com.haqwat.ui.activity_confirm_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.haqwat.R;
import com.haqwat.databinding.ActivityConfirmCodeBinding;
import com.haqwat.language.Language;
import com.haqwat.models.SignUpModel;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.haqwat.ui.activity_login.LoginActivity;
import com.haqwat.ui.activity_sign_up.SignUpActivity;

import java.util.Locale;

import io.paperdb.Paper;

public class ConfirmCodeActivity extends AppCompatActivity {
    private ActivityConfirmCodeBinding binding;
    private CountDownTimer countDownTimer;
    private SignUpModel signUpModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_code);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        signUpModel = (SignUpModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        binding.btnConfirm.setOnClickListener(view -> {
            navigateToCompleteSignUpActivity();
        });
    }

    private void navigateToCompleteSignUpActivity() {
        Intent intent = new Intent(this, CompleteSignUpActivity.class);
        intent.putExtra("data",signUpModel);
        startActivity(intent);
        finish();
    }


    private void startCounter() {
        countDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) ((millisUntilFinished / 1000) / 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                String time = String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
                binding.tvCounter.setText(time);
                binding.tvResend.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                binding.tvCounter.setText("");
                binding.tvResend.setVisibility(View.VISIBLE);


            }
        };

        countDownTimer.start();
    }

    private void resendCode() {
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }


}