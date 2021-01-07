package com.haqwat.ui.activity_champion_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.adapters.SystemAdapter;
import com.haqwat.databinding.ActivityChampionSystemBinding;
import com.haqwat.databinding.ActivityPasswordBinding;
import com.haqwat.language.Language;
import com.haqwat.models.SystemModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_system_mvp.FragmentSystemPresenter;
import com.haqwat.mvp.fragment_system_mvp.FragmentSystemView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ChampionSystemActivity extends AppCompatActivity  implements FragmentSystemView {
    private String lang;
    private ActivityChampionSystemBinding binding;
    private List<SystemModel> systemModelList;
    private SystemAdapter adapter;
    private FragmentSystemPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_champion_system);
        initView();

    }


    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        systemModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SystemAdapter(systemModelList,this);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentSystemPresenter(this,this);
        presenter.getSystems(userModel);
        binding.llBack.setOnClickListener(view -> {
            finish();
        });


    }


    @Override
    public void onSuccess(List<SystemModel> systemModelList) {
        this.systemModelList.clear();
        this.systemModelList.addAll(systemModelList);
        if (this.systemModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);

    }

}