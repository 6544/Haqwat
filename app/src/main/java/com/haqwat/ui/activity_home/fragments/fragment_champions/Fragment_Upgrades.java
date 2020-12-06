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
import com.haqwat.adapters.UpgradeAdapter;
import com.haqwat.databinding.FragmentUpgradeBinding;
import com.haqwat.models.SystemModel;
import com.haqwat.models.UpgradeModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_system_mvp.FragmentSystemPresenter;
import com.haqwat.mvp.fragment_system_mvp.FragmentSystemView;
import com.haqwat.mvp.fragment_upgrade_mvp.FragmentUpgradePresenter;
import com.haqwat.mvp.fragment_upgrade_mvp.FragmentUpgradeView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Upgrades extends Fragment implements FragmentUpgradeView {
    private FragmentUpgradeBinding binding;
    private List<UpgradeModel> upgradeModelList;
    private UpgradeAdapter adapter;
    private HomeActivity activity;
    private FragmentUpgradePresenter presenter;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Upgrades newInstance(){
        return new Fragment_Upgrades();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upgrade,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        upgradeModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new UpgradeAdapter(upgradeModelList,activity);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentUpgradePresenter(this,activity);
        presenter.getUpgrades(userModel);

    }

    @Override
    public void onSuccess(List<UpgradeModel> upgradeModelList) {
        this.upgradeModelList.clear();
        this.upgradeModelList.addAll(upgradeModelList);
        if (this.upgradeModelList.size()>0){
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
