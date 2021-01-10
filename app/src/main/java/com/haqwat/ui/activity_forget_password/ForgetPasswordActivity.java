package com.haqwat.ui.activity_forget_password;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityForgetPasswordBinding;
import com.haqwat.databinding.ActivityLoginBinding;
import com.haqwat.language.Language;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.LoginModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_forget_password_mvp.ActivityForgetPasswordPresenter;
import com.haqwat.mvp.activity_forget_password_mvp.ActivityForgetPasswordView;
import com.haqwat.mvp.login_mvp.LoginPresenter;
import com.haqwat.preferences.Preferences;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.haqwat.ui.activity_confirm_code.ConfirmCodeActivity;
import com.haqwat.ui.activity_forget_password_code.ForgetPasswordCodeActivity;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_sign_up.SignUpActivity;

import io.paperdb.Paper;

public class ForgetPasswordActivity extends AppCompatActivity implements ActivityForgetPasswordView {
    private ActivityForgetPasswordBinding binding;
    private ActivityForgetPasswordPresenter presenter;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        initView();
    }


    private void initView() {
        presenter = new ActivityForgetPasswordPresenter(this, this);
        binding.btnSend.setOnClickListener(view -> {
            String email = binding.edtEmail.getText().toString();
            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.setError(null);
                Common.CloseKeyBoard(this, binding.edtEmail);
                presenter.sendCode(email);
            } else if (email.isEmpty()) {
                binding.edtEmail.setError(getString(R.string.field_required));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.setError(getString(R.string.inv_email));

            }
        });


    }


    @Override
    public void onSuccess(UserModel userModel) {
        Intent intent = new Intent(this, ForgetPasswordCodeActivity.class);
        intent.putExtra("data", userModel);
        startActivityForResult(intent,100);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}