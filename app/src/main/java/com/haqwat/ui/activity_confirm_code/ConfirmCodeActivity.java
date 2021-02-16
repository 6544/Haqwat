package com.haqwat.ui.activity_confirm_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityConfirmCodeBinding;
import com.haqwat.language.Language;
import com.haqwat.models.SignUpModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_confirm_code_activity_mvp.ConfirmCodePresenter;
import com.haqwat.mvp.activity_confirm_code_activity_mvp.ConfirmCodeView;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.haqwat.ui.activity_login.LoginActivity;
import com.haqwat.ui.activity_sign_up.SignUpActivity;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCodeActivity extends AppCompatActivity implements ConfirmCodeView {
    private ActivityConfirmCodeBinding binding;
    private UserModel userModel;
    private ConfirmCodePresenter presenter;
    private ProgressDialog dialog;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_code);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        presenter = new ConfirmCodePresenter(this, this);
        binding.btnConfirm.setOnClickListener(view -> {
            String code = binding.edtCode.getText().toString();
            if (code != null && !code.isEmpty()) {
                presenter.validateCode(userModel,code);
            }

        });

        binding.tvResend.setOnClickListener(view -> {
           presenter.sendCode(userModel);
        });

       // presenter.sendCode(userModel);
        dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);

    }


    private void navigateToCompleteSignUpActivity() {
        Intent intent = new Intent(this, CompleteSignUpActivity.class);
        intent.putExtra("data", userModel);
        startActivity(intent);
        finish();
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
        presenter.stopTimer();
    }


    @Override
    public void onCounterStarted(String time) {
        binding.tvCounter.setText(time);
        binding.tvResend.setVisibility(View.GONE);
    }

    @Override
    public void onCounterFinished() {
        binding.tvCounter.setText("");
        binding.tvResend.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(UserModel userModel) {
        this.userModel = userModel;
        if (userModel.getHas_haqawat_subscribe().equals("no")){
            navigateToCompleteSignUpActivity();
        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }
}