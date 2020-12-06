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
import com.haqwat.adapters.SystemAdapter;
import com.haqwat.adapters.TopChampionAdapter;
import com.haqwat.databinding.FragmentSystemBinding;
import com.haqwat.models.SystemModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_system_mvp.FragmentSystemPresenter;
import com.haqwat.mvp.fragment_system_mvp.FragmentSystemView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentChampionView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentTopChampionPresenter;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_System extends Fragment implements FragmentSystemView {
    private FragmentSystemBinding binding;
    private List<SystemModel> systemModelList;
    private SystemAdapter adapter;
    private HomeActivity activity;
    private FragmentSystemPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_System newInstance(){
        return new Fragment_System();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_system,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        systemModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new SystemAdapter(systemModelList,activity);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentSystemPresenter(this,activity);
        presenter.getSystems(userModel);

    }

    @Override
    public void onSuccess(List<SystemModel> systemModelList) {
        this.systemModelList.clear();
        this.systemModelList.addAll(systemModelList);
        if (this.systemModelList.size()>0){
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
