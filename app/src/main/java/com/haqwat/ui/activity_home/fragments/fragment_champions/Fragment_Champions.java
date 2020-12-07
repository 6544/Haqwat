package com.haqwat.ui.activity_home.fragments.fragment_champions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.haqwat.R;
import com.haqwat.adapters.MyPagerAdapter;
import com.haqwat.databinding.CustomTabRowBinding;
import com.haqwat.databinding.FragmentChampionsBinding;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_home.fragments.Fragment_Stars;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Champions extends Fragment {
    private FragmentChampionsBinding binding;
    private HomeActivity activity;
    private int icons [] = {R.drawable.ic_charge_points,R.drawable.ic_top_tornament,R.drawable.ic_reward,R.drawable.ic_system,R.drawable.ic_upgrade,R.drawable.ic_fixed_recommended};
    private String [] titles ;
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
        titles = new String[]{getString(R.string.points_balance),getString(R.string.top_tournament),getString(R.string.champion_bonus),getString(R.string.champion_system),getString(R.string.upgrade),getString(R.string.pinned_nominations)};
        fragmentList = new ArrayList<>();
        fragmentList.add(Fragment_Point.newInstance());
        fragmentList.add(Fragment_Top_Champions.newInstance());
        fragmentList.add(Fragment_Rewards.newInstance());
        fragmentList.add(Fragment_System.newInstance());
        fragmentList.add(Fragment_Upgrades.newInstance());
        fragmentList.add(Fragment_Nomination.newInstance());

        binding.tab.setupWithViewPager(binding.pager);

        adapter = new MyPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentList,titles);
        binding.pager.setAdapter(adapter);
        addTabs();
        binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CircleImageView circleImageView = tab.getCustomView().findViewById(R.id.circleImage);
                circleImageView.setBorderColor(ContextCompat.getColor(activity,R.color.color12));
                TextView textView = tab.getCustomView().findViewById(R.id.tvTitle);
                textView.setTextColor(ContextCompat.getColor(activity,R.color.color12));
                ImageView imageView = tab.getCustomView().findViewById(R.id.image);
                imageView.setColorFilter(ContextCompat.getColor(activity,R.color.color12));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                CircleImageView circleImageView = tab.getCustomView().findViewById(R.id.circleImage);
                circleImageView.setBorderColor(ContextCompat.getColor(activity,R.color.color5));
                TextView textView = tab.getCustomView().findViewById(R.id.tvTitle);
                textView.setTextColor(ContextCompat.getColor(activity,R.color.color5));
                ImageView imageView = tab.getCustomView().findViewById(R.id.image);
                imageView.setColorFilter(ContextCompat.getColor(activity,R.color.color5));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.pager.setOffscreenPageLimit(6);
    }

    private void addTabs() {
        for (int index =0;index<icons.length;index++){
            int icon = icons[index];
            String title = titles[index];
            CustomTabRowBinding customTabRowBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.custom_tab_row,null,false);
            customTabRowBinding.image.setBackgroundResource(icon);
            customTabRowBinding.setData(title);
            customTabRowBinding.tvTitle.setTextColor(ContextCompat.getColor(activity,R.color.color5));

            binding.tab.getTabAt(index).setCustomView(customTabRowBinding.getRoot());

        }

        CircleImageView circleImageView = binding.tab.getTabAt(0).getCustomView().findViewById(R.id.circleImage);
        circleImageView.setBorderColor(ContextCompat.getColor(activity,R.color.color12));
        TextView textView = binding.tab.getTabAt(0).getCustomView().findViewById(R.id.tvTitle);
        textView.setTextColor(ContextCompat.getColor(activity,R.color.color12));
        ImageView imageView = binding.tab.getTabAt(0).getCustomView().findViewById(R.id.image);
        imageView.setColorFilter(ContextCompat.getColor(activity,R.color.color12));


    }




}
