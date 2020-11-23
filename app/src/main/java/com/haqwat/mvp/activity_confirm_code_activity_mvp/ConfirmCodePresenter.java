package com.haqwat.mvp.activity_confirm_code_activity_mvp;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.models.UserModel;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_confirm_code.ConfirmCodeActivity;

import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCodePresenter {
    private ConfirmCodeView view;
    private Context context;
    private CountDownTimer countDownTimer;

    public ConfirmCodePresenter(ConfirmCodeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void validateCode(UserModel userModel,String code)
    {
        view.showProgress();
        Api.getService(Tags.base_url).checkConfirmationCode("Bearer "+userModel.getToken(),code)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.hideProgress();
                        if (response.isSuccessful()){

                            view.onSuccess(response.body());

                        }else {
                            view.hideProgress();

                            try {
                                if (response.code()==403){
                                    view.onFailed(context.getString(R.string.conf_code_incorrect));
                                }else if (response.code()==402){
                                    view.navigateToLoginActivity();
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
                            view.hideProgress();

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

    public void sendCode(UserModel userModel)
    {
        startCounter();
        Api.getService(Tags.base_url).sendConfirmationCode("Bearer "+userModel.getToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                        }else {

                            try {
                                if (response.code()==403){
                                    view.onFailed(context.getString(R.string.conf_code_incorrect));
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
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
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


    private void startCounter()
    {
        countDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) ((millisUntilFinished / 1000) / 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                String time = String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
                view.onCounterStarted(time);

            }

            @Override
            public void onFinish() {
                view.onCounterFinished();




            }
        };

        countDownTimer.start();
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
