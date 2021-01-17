package com.haqwat.mvp.fragment_home_mvp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.haqwat.R;
import com.haqwat.databinding.DialogSelectImageBinding;
import com.haqwat.models.BestThreeLeagueDataModel;
import com.haqwat.models.BestThreeTeamDataModel;
import com.haqwat.models.HomeModel;
import com.haqwat.models.UserModel;
import com.haqwat.preferences.Preferences;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_home.HomeActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomePresenter {
    private FragmentHomeView view;
    private Context context;
    private Preferences preferences;
    private UserModel userModel;




    public FragmentHomePresenter(FragmentHomeView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);

    }

    public void getData(UserModel userModel){
        Api.getService(Tags.base_url).getHomeLinks("Bearer "+userModel.getToken())
                .enqueue(new Callback<HomeModel>() {
                    @Override
                    public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                        if (response.isSuccessful()){
                            view.hideLoadRate();
                            view.hideLoadAverageRate();
                            view.onDataSuccess(response.body());

                        }else {

                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeModel> call, Throwable t) {
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

    public void getBestLeague(){
        view.showProgressBestLeague();
        Api.getService(Tags.base_url).getBestLeague()
                .enqueue(new Callback<BestThreeLeagueDataModel>() {
                    @Override
                    public void onResponse(Call<BestThreeLeagueDataModel> call, Response<BestThreeLeagueDataModel> response) {
                        if (response.isSuccessful()){
                            view.hideProgressBestLeague();
                            if (response.body()!=null&&response.body().getData()!=null){
                                view.onBestLeagueSuccess(response.body().getData());

                            }

                        }else {
                            view.hideProgressBestLeague();

                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BestThreeLeagueDataModel> call, Throwable t) {
                        try {
                            view.hideProgressBestLeague();

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

    public void getBestTeams(){
        view.showProgressBestTeam();
        Api.getService(Tags.base_url).getBestTeams()
                .enqueue(new Callback<BestThreeTeamDataModel>() {
                    @Override
                    public void onResponse(Call<BestThreeTeamDataModel> call, Response<BestThreeTeamDataModel> response) {
                        if (response.isSuccessful()){
                            view.hideProgressBestTeam();
                            if (response.body()!=null&&response.body().getData()!=null){
                                view.onBestTeamsSuccess(response.body().getData());

                            }

                        }else {
                            view.hideProgressBestTeam();

                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BestThreeTeamDataModel> call, Throwable t) {
                        try {
                            view.hideProgressBestTeam();

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



}
