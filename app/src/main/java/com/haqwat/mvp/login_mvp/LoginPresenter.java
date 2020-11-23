package com.haqwat.mvp.login_mvp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haqwat.R;
import com.haqwat.models.LoginModel;
import com.haqwat.models.UserModel;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_confirm_code.ConfirmCodeActivity;
import com.haqwat.ui.activity_sign_up.SignUpActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private Context context;
    private LoginView view;

    public LoginPresenter(Context context, LoginView view) {
        this.context = context;
        this.view = view;
    }

    public void isDataValid(LoginModel loginModel){
        if (loginModel.isDataValid(context)){
            login(loginModel);
        }
    }

    private void login(LoginModel loginModel) {
        view.showProgressBar();
        Api.getService(Tags.base_url).login(loginModel.getEmail(),loginModel.getPassword())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.hideProgressBar();
                        if (response.isSuccessful()){

                          view.onSuccess(response.body());
                        }else {
                            view.hideProgressBar();
                            try {
                                if (response.code()==404){
                                   view.navigateToSignUp();
                                }else if (response.code()==406){
                                    view.onFailed(context.getString(R.string.user_is_block));
                                }else if (response.code()==409){
                                    UserModel userModel = new Gson().fromJson(response.errorBody().string(),UserModel.class);
                                    view.navigateToConfirmCode(userModel);
                                }else {
                                    view.onFailed(context.getString(R.string.failed));

                                }

                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.hideProgressBar();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }


                        }catch (Exception e){}

                    }
                });
    }


}
