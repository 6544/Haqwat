package com.haqwat.ui.activity_league_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.haqwat.R;
import com.haqwat.adapters.LeagueCategoryAdapter;
import com.haqwat.databinding.ActivityCompleteSignUpBinding;
import com.haqwat.databinding.ActivityLeagueDetailsBinding;
import com.haqwat.language.Language;
import com.haqwat.models.LeagueCategory;
import com.haqwat.models.SignUpModel;
import com.haqwat.mvp.activity_league_details_mvp.LeagueDetailsPresenter;
import com.haqwat.mvp.activity_league_details_mvp.LeagueDetailsView;
import com.haqwat.mvp.sign_up_mpv.SignUpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class LeagueDetailsActivity extends AppCompatActivity implements LeagueDetailsView {
    private ActivityLeagueDetailsBinding binding;
    private FragmentManager fragmentManager;
    private LeagueDetailsPresenter presenter;
    private String leagueName="";
    private LeagueCategoryAdapter adapter;
    private List<LeagueCategory> leagueCategoryList;
    private String lang="ar";


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_league_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        leagueName = intent.getStringExtra("data");
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        leagueCategoryList = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        binding.setLeagueName(leagueName);
        binding.setLang(lang);
        presenter = new LeagueDetailsPresenter(this,this,fragmentManager);
        adapter = new LeagueCategoryAdapter(leagueCategoryList,this,presenter);
        binding.recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recView.setAdapter(adapter);
        presenter.getLeagueCategory();
        binding.llBack.setOnClickListener(view -> finish());
    }

    @Override
    public void onLeagueCategorySuccess(List<LeagueCategory> list) {
        leagueCategoryList.clear();
        leagueCategoryList.addAll(list);
        LeagueCategory leagueCategory = leagueCategoryList.get(0);
        leagueCategory.setSelected(true);
        leagueCategoryList.set(0,leagueCategory);
        adapter.notifyDataSetChanged();
    }
}