package com.haqwat.mvp.fragment_star_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.LeagueDataModel;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.SystemDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_system_mvp.FragmentSystemView;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStarPresenter {
    private FragmentStarView view;
    private Context context;

    public FragmentStarPresenter(FragmentStarView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getLeague() {
        view.showProgressBar();
        Api.getService(Tags.base_url)
                .getLeague()
                .enqueue(new Callback<LeagueDataModel>() {
                    @Override
                    public void onResponse(Call<LeagueDataModel> call, Response<LeagueDataModel> response) {
                        view.hideProgressBar();
                        if (response.isSuccessful() && response.body() != null&&response.body().getData()!=null) {
                            List<LeagueModel> leagueModelList = new ArrayList<>(response.body().getData());
                            view.onSuccess(leagueModelList);

                        } else {
                            view.hideProgressBar();
                            switch (response.code()){
                                case 500:
                                    view.onFailed("Server Error");
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
                    public void onFailure(Call<LeagueDataModel> call, Throwable t) {
                        try {
                            view.hideProgressBar();

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
