package com.haqwat.mvp.fragment_matches_mvp;

import android.content.Context;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.models.HomeModel;
import com.haqwat.models.MatchesDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import org.objectweb.asm.ByteVector;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMatchesPresenter {
    private FragmentMatchesView view;
    private Context context;

    public FragmentMatchesPresenter(FragmentMatchesView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getRounds(UserModel userModel) {
        view.showProgressBar();
        Api.getService(Tags.base_url).getCurrentRound("Bearer " + userModel.getToken())
                .enqueue(new Callback<MatchesDataModel>() {
                    @Override
                    public void onResponse(Call<MatchesDataModel> call, Response<MatchesDataModel> response) {

                        if (response.isSuccessful()) {
                            view.hideProgressBar();
                            if (response.body() != null && response.body().getData() != null) {
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
                    public void onFailure(Call<MatchesDataModel> call, Throwable t) {
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

    public void makeExpectation(UserModel userModel,int match_id,int firstExpectation,int secondExpectation) {
        view.showProgressDialog();
        Api.getService(Tags.base_url).makeExpectation("Bearer " + userModel.getToken(),match_id,firstExpectation,secondExpectation)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            view.hideProgressDialog();
                            view.onExpectationSuccess(firstExpectation,secondExpectation);
                        } else {
                            view.hideProgressDialog();

                            try {
                                if (response.code()==422)
                                {
                                    view.onFailed(context.getString(R.string.connot_expt_match));

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
