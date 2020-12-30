package com.haqwat.ui.activity_share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityPasswordBinding;
import com.haqwat.databinding.ActivityShareBinding;
import com.haqwat.language.Language;
import com.haqwat.models.LeagueRateModel;
import com.haqwat.mvp.activity_share_mvp.ActivitySharePresenter;
import com.haqwat.mvp.activity_share_mvp.ActivityShareView;

import java.util.List;

import io.paperdb.Paper;

public class ShareActivity extends AppCompatActivity implements ActivityShareView {
    private String lang;
    private ActivityShareBinding binding;
    private ActivitySharePresenter presenter;
    private String league_id="";
    private String user_id ="";


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null){
            List<String> pathSegments = intent.getData().getPathSegments();
            if (pathSegments.size()>0){
                user_id = pathSegments.get(pathSegments.size()-1);
                league_id = pathSegments.get(pathSegments.size()-2);
            }else {
                finish();
            }


        }else {
            finish();
        }
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        presenter = new ActivitySharePresenter(this,this);
        presenter.getData(league_id,user_id);
        binding.llBack.setOnClickListener(view -> {
            finish();
        });


    }

    @Override
    public void onSuccess(LeagueRateModel leagueRateModel) {
        binding.setModel(leagueRateModel);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        binding.flLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.flLoading.setVisibility(View.GONE);

    }
}