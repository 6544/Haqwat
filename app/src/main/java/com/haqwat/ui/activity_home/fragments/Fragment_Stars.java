package com.haqwat.ui.activity_home.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.haqwat.adapters.StarAdapter;
import com.haqwat.databinding.FragmentChargeBinding;
import com.haqwat.databinding.FragmentStarsBinding;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.StarModel;
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
    private List<StarModel> starModelList;
    private StarAdapter starAdapter;
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
        starModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        starAdapter = new StarAdapter(starModelList,activity);
        binding.recView.setAdapter(starAdapter);
        adapter = new LeagueSpinnerAdapter(leagueModelList,activity);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.getStars(leagueModelList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    public void onStarSuccess(List<StarModel> starModelList) {

        if (starModelList.size()>0){
            this.starModelList.clear();

            this.starModelList.addAll(starModelList);
            starAdapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);
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
        this.starModelList.clear();
        starAdapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);

    }

}
