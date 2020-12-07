package com.haqwat.ui.activity_about_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityAboutAppBinding;
import com.haqwat.language.Language;
import com.haqwat.models.AppDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.preferences.Preferences;
import com.haqwat.remote.Api;
import com.haqwat.tags.Tags;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutAppActivity extends AppCompatActivity {
    private ActivityAboutAppBinding binding;
    private String lang;
    private int type;
    private UserModel userModel;
    private Preferences preferences;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_app);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null&&intent.hasExtra("type"))
        {
            type = intent.getIntExtra("type",0);

        }
    }


    private void initView()
    {
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.llBack.setOnClickListener(view -> finish());
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        if (type==1)
        {
            binding.setTitle(getString(R.string.terms_and_conditions));
        }else if (type ==2)
        {
            binding.setTitle(getString(R.string.about_app));

        }


        getAppData();

    }

    private void getAppData()
    {

        /*Api.getService(Tags.base_url)
                .getAppData("Bearer "+userModel.getToken(),lang)
                .enqueue(new Callback<AppDataModel>() {
                    @Override
                    public void onResponse(Call<AppDataModel> call, Response<AppDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {

                            if (type==1)
                            {
                                if (lang.equals("ar")){
                                    binding.setContent(response.body().);

                                }else {
                                    binding.setContent(response.body().getSettings().getTermis_condition());

                                }
                            }else
                            {

                            }

                        } else {
                            try {

                                Log.e("error", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (response.code() == 500) {
                                Toast.makeText(AboutAppActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(AboutAppActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppDataModel> call, Throwable t) {
                        try {
                            binding.progBar.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(AboutAppActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AboutAppActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {
                        }
                    }
                });*/

    }



}