package com.haqwat.mvp.fragment_charge_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.MatchesDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_matches_mvp.FragmentMatchesView;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChargePresenter {
    private FragmentChargeView view;
    private Context context;

    public FragmentChargePresenter(FragmentChargeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getCharge(UserModel userModel) {
        view.showProgressBar();
        Api.getService(Tags.base_url).getCharge("Bearer " + userModel.getToken())
                .enqueue(new Callback<ChargeDataModel>() {
                    @Override
                    public void onResponse(Call<ChargeDataModel> call, Response<ChargeDataModel> response) {

                        if (response.isSuccessful()) {
                            view.hideProgressBar();
                            if (response.body() != null ) {
                                view.onSuccess(response.body());

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
                    public void onFailure(Call<ChargeDataModel> call, Throwable t) {
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
