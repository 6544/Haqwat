package com.haqwat.ui.activity_forget_password_code;

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
import com.haqwat.databinding.ActivityForgetPasswordCodeBinding;
import com.haqwat.language.Language;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_forget_password_code_mvp.ActivityForgetPasswordCodePresenter;
import com.haqwat.mvp.activity_forget_password_code_mvp.ActivityForgetPasswordCodeView;
import com.haqwat.mvp.activity_forget_password_mvp.ActivityForgetPasswordPresenter;
import com.haqwat.mvp.activity_forget_password_mvp.ActivityForgetPasswordView;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_rest_password.ResetPasswordActivity;

import io.paperdb.Paper;

public class ForgetPasswordCodeActivity extends AppCompatActivity implements ActivityForgetPasswordCodeView {
    private ActivityForgetPasswordCodeBinding binding;
    private ActivityForgetPasswordCodePresenter presenter;
    private UserModel userModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_password_code);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("data");
    }


    private void initView() {
        binding.setEmail(userModel.getEmail());
        presenter = new ActivityForgetPasswordCodePresenter(this,this);
        binding.btnSend.setOnClickListener(view -> {
            String code = binding.edtCode.getText().toString();
           if (!code.isEmpty()){
               binding.edtCode.setError(null);
               Common.CloseKeyBoard(this,binding.edtCode);
               presenter.sendCode(code,userModel);

           }else {
               binding.edtCode.setError(getString(R.string.field_required));
           }
        });


    }


    @Override
    public void onSuccess(UserModel userModel) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("data",userModel);
        startActivityForResult(intent,100);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK ) {
            setResult(RESULT_OK);
            finish();
        }
    }
}