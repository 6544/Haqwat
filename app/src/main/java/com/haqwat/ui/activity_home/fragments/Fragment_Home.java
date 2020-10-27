package com.haqwat.ui.activity_home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.R;
import com.haqwat.databinding.FragmentHomeBinding;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

public class Fragment_Home extends Fragment {
    private FragmentHomeBinding binding;
    private HomeActivity activity;

    public static Fragment_Home newInstance(){
        return new Fragment_Home();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        binding.img1.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.champions_league));
        });
        binding.img2.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.french_league));
        });
        binding.img3.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.la_liga));
        });
        binding.img4.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.italian_league));
        });
        binding.img5.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.saudi_league));
        });
        binding.img6.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.egyptian_league));
        });
        binding.img7.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.bundesliga));
        });
        binding.img8.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.premier_league));
        });
    }

    private void navigateToLeagueDetailsActivity(String name) {
        Intent intent = new Intent(activity, LeagueDetailsActivity.class);
        intent.putExtra("data",name);
        startActivity(intent);

    }
}
