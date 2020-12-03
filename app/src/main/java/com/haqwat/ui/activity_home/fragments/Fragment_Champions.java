package com.haqwat.ui.activity_home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.haqwat.R;
import com.haqwat.adapters.MyPagerAdapter;
import com.haqwat.databinding.CustomTabRowBinding;
import com.haqwat.databinding.FragmentChampionsBinding;
import com.haqwat.databinding.FragmentChargeBinding;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Champions extends Fragment {
    private FragmentChampionsBinding binding;
    private HomeActivity activity;
    private int icons [] = {R.drawable.ic_charge_points,R.drawable.ic_top_tornament,R.drawable.ic_reward,R.drawable.ic_system,R.drawable.ic_upgrade,R.drawable.ic_fixed_recommended};
    private String [] titles = {"رصيد النقاط","صدارة البطولة","مكافئة البطولة","نظام البطولة","ترقية الاشتراك","الترشيحات المثبتة"};
    private MyPagerAdapter adapter;
    private List<Fragment> fragmentList;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    public static Fragment_Champions newInstance(){
        return new Fragment_Champions();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_champions,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(Fragment_Stars.newInstance());
        fragmentList.add(Fragment_Stars.newInstance());
        fragmentList.add(Fragment_Stars.newInstance());
        fragmentList.add(Fragment_Stars.newInstance());
        fragmentList.add(Fragment_Stars.newInstance());
        fragmentList.add(Fragment_Stars.newInstance());

        binding.tab.setupWithViewPager(binding.pager);

        adapter = new MyPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentList,titles);
        binding.pager.setAdapter(adapter);
        addTabs();
    }

    private void addTabs() {
        for (int index =0;index<icons.length;index++){
            int icon = icons[index];
            String title = titles[index];
            CustomTabRowBinding customTabRowBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.custom_tab_row,null,false);
            customTabRowBinding.image.setBackgroundResource(icon);
            customTabRowBinding.setData(title);
            binding.tab.getTabAt(index).setCustomView(customTabRowBinding.getRoot());
        }
    }
}
