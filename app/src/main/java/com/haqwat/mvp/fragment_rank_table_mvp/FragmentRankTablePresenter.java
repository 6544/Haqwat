package com.haqwat.mvp.fragment_rank_table_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.MatchesModel;
import com.haqwat.models.TableArrangementDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_league_matches_mvp.FragmentMatchesView;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRankTablePresenter {
    private FragmentRankTableView view;
    private Context context;

    public FragmentRankTablePresenter(FragmentRankTableView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getRankTable(UserModel userModel,String league_id)
    {
        view.showProgressBar();
        Api.getService(Tags.base_url).getTableArrangement("Bearer " + userModel.getToken(),league_id)
                .enqueue(new Callback<TableArrangementDataModel>() {
                    @Override
                    public void onResponse(Call<TableArrangementDataModel> call, Response<TableArrangementDataModel> response) {

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
                    public void onFailure(Call<TableArrangementDataModel> call, Throwable t) {
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
