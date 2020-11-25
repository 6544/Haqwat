package com.haqwat.ui.activity_matches.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.haqwat.R;
import com.haqwat.adapters.MatchAdapter;
import com.haqwat.adapters.RoundAdapter;
import com.haqwat.databinding.FragmentLeagueUpcomingMatchesBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_matches_mvp.FragmentMatchesPresenter;
import com.haqwat.mvp.fragment_matches_mvp.FragmentMatchesView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_matches.MatchesActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_UpComing_Matches extends Fragment implements FragmentMatchesView {
    private FragmentLeagueUpcomingMatchesBinding binding;
    private MatchesActivity activity;
    private FragmentMatchesPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;
    private List<MatchesModel> matchesModelList;
    private RoundAdapter adapter;

    public static Fragment_UpComing_Matches newInstance(){
        return new Fragment_UpComing_Matches();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_upcoming_matches,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        matchesModelList = new ArrayList<>();
        activity = (MatchesActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new RoundAdapter(matchesModelList,activity);
        binding.recView.setAdapter(adapter);

        presenter = new FragmentMatchesPresenter(this,activity);
        presenter.getRounds(userModel);
    }

    @Override
    public void onSuccess(List<MatchesModel> matchesModelList) {
        this.matchesModelList.clear();
        this.matchesModelList.addAll(matchesModelList);
        if (this.matchesModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
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
