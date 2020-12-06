package com.haqwat.mvp.fragment_home_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.HomeModel;
import com.haqwat.models.UserModel;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomePresenter {
    private FragmentHomeView view;
    private Context context;



    public FragmentHomePresenter(FragmentHomeView view, Context context) {
        this.view = view;
        this.context = context;

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
}
