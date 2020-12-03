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
import com.haqwat.adapters.PreviousRoundAdapter;
import com.haqwat.databinding.FragmentLeaguePreviousMatchesBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_league_matches_mvp.FragmentMatchesPresenter;
import com.haqwat.mvp.fragment_league_matches_mvp.FragmentMatchesView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;
import com.haqwat.ui.activity_matches.MatchesActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_League_Previous_Matches extends Fragment implements FragmentMatchesView {
    private static final String TAG="DATA";
    private FragmentLeaguePreviousMatchesBinding binding;
    private LeagueDetailsActivity activity;
    private FragmentMatchesPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;
    private List<MatchesModel.MatchModel> matchesModelList;
    private MatchPreviousAdapter adapter;
    private String league_id;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LeagueDetailsActivity) context;

    }

    public static Fragment_League_Previous_Matches newInstance(String league_id){
        Bundle bundle = new Bundle();
        bundle.putString(TAG,league_id);
        Fragment_League_Previous_Matches fragment_league_previous_matches = new Fragment_League_Previous_Matches();
        fragment_league_previous_matches.setArguments(bundle);
        return fragment_league_previous_matches;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_previous_matches,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle!=null) {
            league_id = bundle.getString(TAG);
        }
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        matchesModelList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MatchPreviousAdapter(matchesModelList,activity);
        binding.recView.setAdapter(adapter);

        presenter = new FragmentMatchesPresenter(this,activity);
        presenter.getRounds(userModel,league_id);
    }

    @Override
    public void onSuccess(MatchesModel matchesModel) {
        this.matchesModelList.clear();
        if (matchesModel.getPrev_matches()!=null&&matchesModel.getPrev_matches().size()>0){
            this.matchesModelList.addAll(matchesModel.getPrev_matches());

        }
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

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void onExpectationSuccess() {

    }
}
