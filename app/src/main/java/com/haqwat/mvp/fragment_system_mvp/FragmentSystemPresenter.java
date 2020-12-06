package com.haqwat.mvp.fragment_system_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.SystemDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_charge_mvp.FragmentChargeView;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSystemPresenter {
    private FragmentSystemView view;
    private Context context;

    public FragmentSystemPresenter(FragmentSystemView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getSystems(UserModel userModel) {
        view.showProgressBar();
        Api.getService(Tags.base_url).getSystem("Bearer " + userModel.getToken())
                .enqueue(new Callback<SystemDataModel>() {
                    @Override
                    public void onResponse(Call<SystemDataModel> call, Response<SystemDataModel> response) {

                        if (response.isSuccessful()) {
                            view.hideProgressBar();
                            if (response.body() != null ) {
                                view.onSuccess(response.body().getData());

                            }

                        } else {

                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error", response.code() + "_" + response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SystemDataModel> call, Throwable t) {
                        try {

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));

                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }


                        } catch (Exception e) {
                        }

                    }
                });
    }

}
