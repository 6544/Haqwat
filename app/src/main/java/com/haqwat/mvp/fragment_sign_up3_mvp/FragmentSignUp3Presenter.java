package com.haqwat.mvp.fragment_sign_up3_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.LeagueDataModel;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.TeamDataModel;
import com.haqwat.models.TeamModel;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2View;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSignUp3Presenter {
    private FragmentSignUp3View view;
    private Context context;



    public FragmentSignUp3Presenter(FragmentSignUp3View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getTeams(int league_id){
        view.onShowProgress();
        Api.getService(Tags.base_url)
                .getTeams(league_id)
                .enqueue(new Callback<TeamDataModel>() {
                    @Override
                    public void onResponse(Call<TeamDataModel> call, Response<TeamDataModel> response) {
                        view.onHideProgress();
                        if (response.isSuccessful() && response.body() != null&&response.body().getData()!=null) {
                            List<TeamModel> teamModelList = new ArrayList<>(response.body().getData());
                            view.onTeamDataSuccess(teamModelList);

                        } else {
                            view.onHideProgress();
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
                    public void onFailure(Call<TeamDataModel> call, Throwable t) {
                        try {
                            view.onHideProgress();

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

    public void setSelectedItem(TeamModel teamModel){
        view.onItemSelected(teamModel);
    }

}
