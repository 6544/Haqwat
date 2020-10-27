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
import com.haqwat.databinding.FragmentLeaguePreviousMatchesBinding;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;
import com.haqwat.ui.activity_matches.MatchesActivity;

public class Fragment_League_Previous_Matches extends Fragment {
    private FragmentLeaguePreviousMatchesBinding binding;
    private LeagueDetailsActivity activity;
    public static Fragment_League_Previous_Matches newInstance(){
        return new Fragment_League_Previous_Matches();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_previous_matches,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (LeagueDetailsActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

    }
}
