package com.haqwat.mvp.activity_forget_password_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.haqwat.R;
import com.haqwat.models.InvitesLeagueDataModel;
import com.haqwat.models.LoginModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.login_mvp.LoginView;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityForgetPasswordPresenter {
    private Context context;
    private ActivityForgetPasswordView view;

    public ActivityForgetPasswordPresenter(Context context, ActivityForgetPasswordView view) {
        this.context = context;
        this.view = view;
    }



    public void sendCode(String email){
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .forgetPassword(email)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            view.onSuccess(response.body());
                        } else {
                            dialog.dismiss();
                            switch (response.code()){
                                case 500:
                                    view.onFailed("Server Error");
                                    break;
                                case 422:
                                    view.onFailed(context.getString(R.string.email_incorrect));
                                    break;
                                default:
                                    view.onFailed(context.getString(R.string.failed));
                                    break;
                            }
                            try {
                                Log.e("error_code",response.code()+"_"+response.errorBody().string());
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
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                }
                                else if (t.getMessage().toLowerCase().contains("socket")||t.getMessage().toLowerCase().contains("canceled")){ }
                                else {
                                    view.onFailed(t.getMessage());
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }



}
