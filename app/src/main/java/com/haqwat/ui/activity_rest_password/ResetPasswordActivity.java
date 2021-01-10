package com.haqwat.ui.activity_rest_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.haqwat.R;
import com.haqwat.databinding.ActivityForgetPasswordBinding;
import com.haqwat.databinding.ActivityResetPasswordBinding;
import com.haqwat.language.Language;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_forget_password_mvp.ActivityForgetPasswordPresenter;
import com.haqwat.mvp.activity_forget_password_mvp.ActivityForgetPasswordView;
import com.haqwat.mvp.activity_reset_password_mvp.ActivityResetPasswordPresenter;
import com.haqwat.mvp.activity_reset_password_mvp.ActivityResetPasswordView;
import com.haqwat.preferences.Preferences;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_forget_password_code.ForgetPasswordCodeActivity;

import io.paperdb.Paper;

public class ResetPasswordActivity extends AppCompatActivity implements ActivityResetPasswordView {
    private ActivityResetPasswordBinding binding;
    private ActivityResetPasswordPresenter presenter;
    private UserModel userModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("data");
    }


    private void initView() {

        presenter = new ActivityResetPasswordPresenter(this, this);
        binding.btnSend.setOnClickListener(view -> {
            String newPassword = binding.edtNewPassword.getText().toString();
            if (!newPassword.isEmpty() && newPassword.length()>=6) {
                binding.edtNewPassword.setError(null);
                Common.CloseKeyBoard(this, binding.edtNewPassword);
                presenter.resetCode(newPassword,userModel);
            } else if (newPassword.isEmpty()) {
                binding.edtNewPassword.setError(getString(R.string.field_required));
            } else if (newPassword.length()<6) {
                binding.edtNewPassword.setError(getString(R.string.password_short));

            }
        });


    }


    @Override
    public void onSuccess(UserModel userModel) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}