package com.haqwat.ui.activity_league_details.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.R;
import com.haqwat.databinding.FragmentLeagueRatingBinding;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

public class Fragment_League_Rating extends Fragment {
    private FragmentLeagueRatingBinding binding;

    public static Fragment_League_Rating newInstance(){
        return new Fragment_League_Rating();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_rating,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {

    }
}
