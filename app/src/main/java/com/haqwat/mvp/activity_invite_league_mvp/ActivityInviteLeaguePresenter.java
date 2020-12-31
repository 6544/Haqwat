package com.haqwat.mvp.activity_invite_league_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.InvitesLeagueDataModel;
import com.haqwat.models.LeagueDataModel;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2View;
import com.haqwat.preferences.Preferences;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInviteLeaguePresenter {
    private ActivityInviteLeagueView view;
    private Context context;
    private Preferences preferences;
    private UserModel userModel;



    public ActivityInviteLeaguePresenter(ActivityInviteLeagueView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
    }

    public void getLeague(){

        if (userModel==null){
            return;
        }
        view.onShowProgress();
        Api.getService(Tags.base_url)
                .getInvitesLeague("Bearer "+userModel.getToken())
                .enqueue(new Callback<InvitesLeagueDataModel>() {
                    @Override
                    public void onResponse(Call<InvitesLeagueDataModel> call, Response<InvitesLeagueDataModel> response) {
                        view.onHideProgress();
                        if (response.isSuccessful() && response.body() != null&&response.body().getData()!=null) {
                            view.onLeagueDataSuccess(response.body().getData());

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
                    public void onFailure(Call<InvitesLeagueDataModel> call, Throwable t) {
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


}
