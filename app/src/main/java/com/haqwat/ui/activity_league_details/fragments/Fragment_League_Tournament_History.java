package com.haqwat.ui.activity_league_details.fragments;

import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.haqwat.R;
import com.haqwat.adapters.LeagueHistoryAdapter;
import com.haqwat.adapters.MatchPreviousAdapter;
import com.haqwat.databinding.FragmentLeaguePreviousMatchesBinding;
import com.haqwat.databinding.FragmentLeagueTournamentHistoryBinding;
import com.haqwat.models.LeagueHistoryModel;
import com.haqwat.models.MatchesModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_league_history_mvp.FragmentLeagueHistoryPresenter;
import com.haqwat.mvp.fragment_league_history_mvp.FragmentLeagueHistoryView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_League_Tournament_History extends Fragment implements FragmentLeagueHistoryView {
    private final static String TAG ="DATA";
    private FragmentLeagueTournamentHistoryBinding binding;
    private LeagueDetailsActivity activity;
    private Preferences preferences;
    private UserModel userModel;
    private List<LeagueHistoryModel.LeagueInformation> leagueInformationList;
    private LeagueHistoryAdapter adapter;
    private String league_id;
    private FragmentLeagueHistoryPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LeagueDetailsActivity) context;

    }

    public static Fragment_League_Tournament_History newInstance(String league_id){
        Bundle bundle = new Bundle();
        bundle.putString(TAG,league_id);
        Fragment_League_Tournament_History fragment_league_tournament_history = new Fragment_League_Tournament_History();
        fragment_league_tournament_history.setArguments(bundle);
        return fragment_league_tournament_history;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_tournament_history,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {

        Bundle bundle = getArguments();
        if (bundle!=null) {
            league_id = bundle.getString(TAG);
        }
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        leagueInformationList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new GridLayoutManager(activity,2));
        adapter = new LeagueHistoryAdapter(leagueInformationList,activity);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentLeagueHistoryPresenter(this,activity);
        presenter.getLeagueHistory(userModel,league_id);



    }

    @Override
    public void onSuccess(LeagueHistoryModel leagueHistoryModel) {
        leagueInformationList.clear();
        leagueInformationList.addAll(leagueHistoryModel.getLeague_information());
        if (leagueInformationList.size()>0){
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
