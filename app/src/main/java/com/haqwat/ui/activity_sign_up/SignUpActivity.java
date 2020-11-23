package com.haqwat.ui.activity_sign_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivitySignUpBinding;
import com.haqwat.language.Language;
import com.haqwat.models.SignUpModel;
import com.haqwat.models.UserModel;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.haqwat.ui.activity_confirm_code.ConfirmCodeActivity;
import com.haqwat.ui.activity_login.LoginActivity;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                signUp1();
            }

        });

        binding.tvLogin.setOnClickListener(view -> {
            navigateToLoginActivity();
        });
    }

    private void signUp1() {
        ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url).signUp1(signUpModel.getEmail(),signUpModel.getPassword(),"android",signUpModel.withSocial)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()){
                            Intent intent;
                            if (response.body().getIs_confirmed().equals("yes")){
                                intent = new Intent(SignUpActivity.this, CompleteSignUpActivity.class);
                            }else {
                                intent = new Intent(SignUpActivity.this, ConfirmCodeActivity.class);
                            }
                            intent.putExtra("data",response.body());
                            startActivity(intent);
                            finish();

                        }else {
                            try {
                                Log.e("error",response.code()+"_"+response.errorBody().string());
                                if (response.code()==403){
                                    Toast.makeText(SignUpActivity.this, R.string.email_exist, Toast.LENGTH_SHORT).show();
                                    navigateToLoginActivity();
                                }else {
                                    Toast.makeText(SignUpActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(SignUpActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }


                        }catch (Exception e){}

                    }
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