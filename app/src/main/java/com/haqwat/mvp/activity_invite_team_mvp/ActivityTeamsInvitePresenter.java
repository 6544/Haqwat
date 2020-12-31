package com.haqwat.mvp.activity_invite_team_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.TeamDataModel;
import com.haqwat.models.TeamModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_sign_up3_mvp.FragmentSignUp3View;
import com.haqwat.preferences.Preferences;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTeamsInvitePresenter {
    private ActivityInviteTeamView view;
    private Context context;
    private Preferences preferences;
    private UserModel userModel;



    public ActivityTeamsInvitePresenter(ActivityInviteTeamView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
    }

    public void getTeams(int league_id)
    {
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
    public void join(int league_id,int team_id)
    {
        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .joinToNewTeam("Bearer "+userModel.getToken(),league_id,team_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            view.onJoinSuccess();

                        } else {
                            dialog.dismiss();
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
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
