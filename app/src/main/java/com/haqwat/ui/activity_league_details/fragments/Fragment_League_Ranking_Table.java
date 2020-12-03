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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.haqwat.R;
import com.haqwat.adapters.MatchPreviousAdapter;
import com.haqwat.adapters.TableArrangementAdapter;
import com.haqwat.databinding.FragmentLeagueRankingTableBinding;
import com.haqwat.databinding.FragmentLeagueRatingBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.models.TableArrangementModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_rank_table_mvp.FragmentRankTablePresenter;
import com.haqwat.mvp.fragment_rank_table_mvp.FragmentRankTableView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_League_Ranking_Table extends Fragment implements FragmentRankTableView {
    private static final String TAG="DATA";
    private FragmentLeagueRankingTableBinding binding;
    private LeagueDetailsActivity activity;
    private FragmentRankTablePresenter presenter;
    private TableArrangementAdapter adapter;
    private UserModel userModel;
    private Preferences preferences;
    private List<TableArrangementModel> tableList;
    private String league_id;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LeagueDetailsActivity) context;

    }

    public static Fragment_League_Ranking_Table newInstance(String league_id){
        Bundle bundle = new Bundle();
        bundle.putString(TAG,league_id);
        Fragment_League_Ranking_Table fragment_league_ranking_table = new Fragment_League_Ranking_Table();
        fragment_league_ranking_table.setArguments(bundle);
        return fragment_league_ranking_table;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_ranking_table,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle!=null) {
            league_id = bundle.getString(TAG);
        }
        tableList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new TableArrangementAdapter(tableList,activity);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentRankTablePresenter(this,activity);
        presenter.getRankTable(userModel,league_id);

    }

    @Override
    public void onSuccess(List<TableArrangementModel> tableArrangementModelList) {
        tableList.clear();
        tableList.addAll(tableArrangementModelList);
        if (tableList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            binding.llRank.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.llRank.setVisibility(View.GONE);


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
