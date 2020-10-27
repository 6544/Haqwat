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
import com.haqwat.databinding.FragmentLeagueRankingTableBinding;
import com.haqwat.databinding.FragmentLeagueRatingBinding;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

public class Fragment_League_Ranking_Table extends Fragment {
    private FragmentLeagueRankingTableBinding binding;
    private LeagueDetailsActivity activity;

    public static Fragment_League_Ranking_Table newInstance(){
        return new Fragment_League_Ranking_Table();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_ranking_table,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (LeagueDetailsActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

    }
}
