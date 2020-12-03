package com.haqwat.mvp.fragment_team_rate;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.LeagueRateModel;
import com.haqwat.models.UserModel;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTeamRatePresenter {
    private FragmentTeamRateView view;
    private Context context;

    public FragmentTeamRatePresenter(FragmentTeamRateView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getData(String league_id, UserModel userModel)
    {
        view.showProgressBar();
        Api.getService(Tags.base_url).getLeagueRate("Bearer " + userModel.getToken(),league_id)
                .enqueue(new Callback<LeagueRateModel>() {
                    @Override
                    public void onResponse(Call<LeagueRateModel> call, Response<LeagueRateModel> response) {
                        if (response.isSuccessful()) {
                            view.hideProgressBar();
                            view.onSuccess(response.body());
                        } else {
                            view.hideProgressBar();

                            try {
                                if (response.code()==402){
                                    view.onShowAlertDialog();
                                }else {
                                    view.onFailed(context.getString(R.string.failed));

                                }

                                Log.e("error", response.code() + "_" + response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LeagueRateModel> call, Throwable t) {
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


                        } catch (Exception e) {
                        }

                    }
                });
    }
}
