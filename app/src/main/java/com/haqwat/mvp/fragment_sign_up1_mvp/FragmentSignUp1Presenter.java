package com.haqwat.mvp.fragment_sign_up1_mvp;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.haqwat.R;
import com.haqwat.models.NationalityDataModel;
import com.haqwat.models.NationalityModel;
import com.haqwat.models.SignUpModel;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_login.LoginActivity;
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

public class FragmentSignUp1Presenter implements DatePickerDialog.OnDateSetListener{
    private FragmentSignUp1View view;
    private Context context;
    private DatePickerDialog datePickerDialog;
    private FragmentManager fragmentManager;
    private SignUpModel signUpModel;



    public FragmentSignUp1Presenter(FragmentSignUp1View view, Context context,SignUpModel signUpModel,FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.signUpModel = signUpModel;
        this.fragmentManager = fragmentManager;
        createDateDialog();
        getGender();
        getNationality();
    }
    private void createDateDialog() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOkText(context.getString(R.string.select));
        datePickerDialog.setCancelText(context.getString(R.string.cancel));
        datePickerDialog.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        datePickerDialog.setOkColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        datePickerDialog.setCancelColor(ContextCompat.getColor(context, R.color.color7));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
        datePickerDialog.setMaxDate(calendar);

    }
    private void getGender(){
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.ch_gender));
        list.add(context.getString(R.string.male));
        list.add(context.getString(R.string.female));
        view.onGenderSuccess(list);
    }
    private void getNationality(){
        Api.getService(Tags.base_url)
                .getNationality()
                .enqueue(new Callback<NationalityDataModel>() {
                    @Override
                    public void onResponse(Call<NationalityDataModel> call, Response<NationalityDataModel> response) {

                        if (response.isSuccessful() && response.body() != null&&response.body().getData()!=null) {
                            List<NationalityModel> nationalityModelList = new ArrayList<>(response.body().getData());
                            view.onNationalitySuccess(nationalityModelList);

                        } else {

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
                    public void onFailure(Call<NationalityDataModel> call, Throwable t) {
                        try {

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
    public void showDateDialog(){
        try {
            datePickerDialog.show(fragmentManager,"");

        }catch (Exception e){}
    }
    public void selectImage(){
        view.onSelectImage();
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        signUpModel.setBirth_date(date);
        this.view.onDateSelected(signUpModel);
    }
}
