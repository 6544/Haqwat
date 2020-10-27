package com.haqwat.ui.activity_matches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.haqwat.R;
import com.haqwat.adapters.MatchesPagerAdapter;
import com.haqwat.databinding.ActivityMatchesBinding;
import com.haqwat.language.Language;
import com.haqwat.ui.activity_matches.fragments.Fragment_Previous_Matches;
import com.haqwat.ui.activity_matches.fragments.Fragment_UpComing_Matches;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MatchesActivity extends AppCompatActivity {
    private ActivityMatchesBinding binding;
    private List<Fragment> fragmentList;
    private List<String>titles;
    private int pos=0;
    private MatchesPagerAdapter adapter;
    private String lang="ar";
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_matches);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        pos = intent.getIntExtra("data",0);

    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();

        fragmentList.add(Fragment_UpComing_Matches.newInstance());
        fragmentList.add(Fragment_Previous_Matches.newInstance());
        titles.add(getString(R.string.upcoming_matches));
        titles.add(getString(R.string.previous_matches));
        binding.tabLayout.setupWithViewPager(binding.pager);
        adapter = new MatchesPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments_Titles(fragmentList,titles);
        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(pos);

        binding.llBack.setOnClickListener(view -> finish());

    }
}