package com.haqwat.mvp.activity_forget_password_code_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_forget_password_mvp.ActivityForgetPasswordView;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityForgetPasswordCodePresenter {
    private Context context;
    private ActivityForgetPasswordCodeView view;

    public ActivityForgetPasswordCodePresenter(Context context, ActivityForgetPasswordCodeView view) {
        this.context = context;
        this.view = view;
    }



    public void sendCode(String code,UserModel userModel)
    {

        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .sendVerificationCodeForgetPassword("Bearer "+userModel.getToken(),code)
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
                                case 402:
                                    view.onFailed(context.getString(R.string.verification_code_incorrect));
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
