package com.haqwat.ui.activity_home.fragments;

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
import com.haqwat.adapters.ChargeAdapter;
import com.haqwat.adapters.LeagueSpinnerAdapter;
import com.haqwat.databinding.FragmentChargeBinding;
import com.haqwat.databinding.FragmentStarsBinding;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_star_mvp.FragmentStarPresenter;
import com.haqwat.mvp.fragment_star_mvp.FragmentStarView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Stars extends Fragment  implements FragmentStarView {
    private FragmentStarsBinding binding;
    private List<LeagueModel> leagueModelList;
    private LeagueSpinnerAdapter adapter;
    private HomeActivity activity;
    private FragmentStarPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Stars newInstance(){
        return new Fragment_Stars();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stars,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        leagueModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new LeagueSpinnerAdapter(leagueModelList,activity);
        binding.spinner.setAdapter(adapter);
        presenter = new FragmentStarPresenter(this,activity);
        presenter.getLeague();

    }

    @Override
    public void onSuccess(List<LeagueModel> leagueModelList) {
        this.leagueModelList.clear();
        this.leagueModelList.addAll(leagueModelList);
        if (this.leagueModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            activity.runOnUiThread(() -> adapter.notifyDataSetChanged());
            binding.spinner.setVisibility(View.VISIBLE);

        }else {
            binding.spinner.setVisibility(View.GONE);
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
