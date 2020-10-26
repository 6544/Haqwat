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
import com.haqwat.databinding.FragmentChargeBinding;
import com.haqwat.databinding.FragmentStarsBinding;

public class Fragment_Stars extends Fragment {
    private FragmentStarsBinding binding;

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

    }
}
