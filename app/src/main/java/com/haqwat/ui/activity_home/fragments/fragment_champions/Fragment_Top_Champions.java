package com.haqwat.ui.activity_home.fragments.fragment_champions;

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
import com.haqwat.adapters.TopChampionAdapter;
import com.haqwat.databinding.FragmentTopChampionBinding;
import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_points_mvp.FragmentPointsPresenter;
import com.haqwat.mvp.fragment_points_mvp.FragmentPointsView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentChampionView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentTopChampionPresenter;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Top_Champions extends Fragment implements FragmentChampionView {
    private FragmentTopChampionBinding binding;
    private List<UserModel> userModelList;
    private TopChampionAdapter adapter;
    private HomeActivity activity;
    private FragmentTopChampionPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Top_Champions newInstance(){
        return new Fragment_Top_Champions();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_champion,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        userModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new TopChampionAdapter(userModelList,activity);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentTopChampionPresenter(this,activity);
        presenter.getChampions(userModel);

    }

    @Override
    public void onSuccess(List<UserModel> userModelList) {
        this.userModelList.clear();
        this.userModelList.addAll(userModelList);
        if (this.userModelList.size()>0){
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
