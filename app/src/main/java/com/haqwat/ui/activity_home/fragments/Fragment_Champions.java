package com.haqwat.ui.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.R;
import com.haqwat.databinding.FragmentChampionsBinding;
import com.haqwat.databinding.FragmentChargeBinding;

public class Fragment_Champions extends Fragment {
    private FragmentChampionsBinding binding;

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

    }
}
