package com.haqwat.mvp.fragment_nomination_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.NominationDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_charge_mvp.FragmentChargeView;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNominationPresenter {
    private FragmentNominationView view;
    private Context context;

    public FragmentNominationPresenter(FragmentNominationView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getNomination(UserModel userModel) {
        view.showProgressBar();
        Api.getService(Tags.base_url).getNomination("Bearer " + userModel.getToken())
                .enqueue(new Callback<NominationDataModel>() {
                    @Override
                    public void onResponse(Call<NominationDataModel> call, Response<NominationDataModel> response) {

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
                    public void onFailure(Call<NominationDataModel> call, Throwable t) {
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

    public void addNomination(UserModel userModel,int league_id,int fav_id,int recommended_id) {
        view.showProgressDialog();
        Api.getService(Tags.base_url).addNomination("Bearer " + userModel.getToken(),league_id,fav_id,recommended_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        view.hideProgressDialog();

                        if (response.isSuccessful()) {
                            if (response.body() != null ) {
                                view.onAddedSuccessfully();

                            }

                        } else {


                            try {
                                Log.e("error", response.code() + "_" + response.errorBody().string());

                                switch (response.code()){
                                    case 402:
                                        view.onFailed(context.getString(R.string.no_season_league));
                                        break;
                                    case 404:
                                        view.onFailed(context.getString(R.string.season_finished));

                                        break;
                                    case 405:
                                        view.onFailed(context.getString(R.string.favorite_team_belongs_league));
                                        break;
                                    case 406:
                                        view.onFailed(context.getString(R.string.recommended_team_not_belongs_league));

                                        break;
                                    default:
                                        view.onFailed(context.getString(R.string.failed));
                                        break;
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            view.hideProgressDialog();

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
