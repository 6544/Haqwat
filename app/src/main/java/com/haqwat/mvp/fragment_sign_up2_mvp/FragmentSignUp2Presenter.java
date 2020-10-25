package com.haqwat.mvp.fragment_sign_up2_mvp;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.haqwat.R;
import com.haqwat.models.LeagueDataModel;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.NationalityDataModel;
import com.haqwat.models.NationalityModel;
import com.haqwat.models.SignUpModel;
import com.haqwat.mvp.fragment_sign_up1_mvp.FragmentSignUp1View;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSignUp2Presenter{
    private FragmentSignUp2View view;
    private Context context;



    public FragmentSignUp2Presenter(FragmentSignUp2View view, Context context) {
        this.view = view;
        this.context = context;
        getLeague();
    }

    private void getLeague(){
        view.onShowProgress();
        Api.getService(Tags.base_url)
                .getLeague()
                .enqueue(new Callback<LeagueDataModel>() {
                    @Override
                    public void onResponse(Call<LeagueDataModel> call, Response<LeagueDataModel> response) {
                        view.onHideProgress();
                        if (response.isSuccessful() && response.body() != null&&response.body().getData()!=null) {
                            List<LeagueModel> leagueModelList = new ArrayList<>(response.body().getData());
                            view.onLeagueDataSuccess(leagueModelList);

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
                    public void onFailure(Call<LeagueDataModel> call, Throwable t) {
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

    public void setSelectedItem(LeagueModel leagueModel){
        view.onItemSelected(leagueModel);
    }

}
