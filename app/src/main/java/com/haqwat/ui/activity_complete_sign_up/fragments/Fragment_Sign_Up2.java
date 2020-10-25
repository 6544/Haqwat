package com.haqwat.ui.activity_complete_sign_up.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.haqwat.R;
import com.haqwat.adapters.LeagueAdapter;
import com.haqwat.databinding.FragmentSignUp2Binding;
import com.haqwat.databinding.FragmentSignUp3Binding;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.SignUpModel;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2Presenter;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2View;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Sign_Up2 extends Fragment implements FragmentSignUp2View {
    public static final String TAG= "TAG";
    private FragmentSignUp2Binding binding;
    public SignUpModel signUpModel;
    private FragmentSignUp2Presenter presenter;
    private List<LeagueModel> leagueModelList;
    private CompleteSignUpActivity activity;
    private LeagueAdapter adapter;

    public static Fragment_Sign_Up2 newInstance(SignUpModel signUpModel){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG,signUpModel);
        Fragment_Sign_Up2 fragment_sign_up2 = new Fragment_Sign_Up2();
        fragment_sign_up2.setArguments(bundle);
        return fragment_sign_up2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up2,container,false);
        initView();
        return binding.getRoot();

    }

    private void initView() {
        activity= (CompleteSignUpActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle!=null){
            signUpModel = (SignUpModel) bundle.getSerializable(TAG);
        }
        leagueModelList = new ArrayList<>();

        presenter = new FragmentSignUp2Presenter(this,activity);
        adapter = new LeagueAdapter(leagueModelList,activity,presenter);
        binding.recView.setLayoutManager(new GridLayoutManager(activity,3));
        binding.recView.setAdapter(adapter);
    }

    @Override
    public void onLeagueDataSuccess(List<LeagueModel> list) {
        leagueModelList.clear();
        leagueModelList.addAll(list);
        adapter.notifyDataSetChanged();
        if (list.size()>0){
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
    public void onHideProgress() {
        binding.progBar.setVisibility(View.GONE);
    }

    @Override
    public void onShowProgress() {
        binding.progBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemSelected(LeagueModel leagueModel) {
        signUpModel.setLeague_id(String.valueOf(leagueModel.getId()));

    }
}
