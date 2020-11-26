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
import com.haqwat.databinding.FragmentChargeBinding;
import com.haqwat.databinding.FragmentHomeBinding;
import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.ChargeModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_charge_mvp.FragmentChargePresenter;
import com.haqwat.mvp.fragment_charge_mvp.FragmentChargeView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Charge extends Fragment implements FragmentChargeView {
    private FragmentChargeBinding binding;
    private List<ChargeModel> chargeModelList;
    private ChargeAdapter adapter;
    private HomeActivity activity;
    private FragmentChargePresenter presenter;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Charge newInstance(){
        return new Fragment_Charge();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charge,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        chargeModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new ChargeAdapter(chargeModelList,activity);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentChargePresenter(this,activity);
        presenter.getCharge(userModel);

    }

    @Override
    public void onSuccess(ChargeDataModel chargeDataModel) {
        binding.setModel(chargeDataModel.getTotal());
        binding.progBarTotal.setProgress(chargeDataModel.getTotal().getTotal_true_expectation_rate());
        chargeModelList.clear();
        chargeModelList.addAll(chargeDataModel.getLeagues());
        adapter.notifyDataSetChanged();
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
