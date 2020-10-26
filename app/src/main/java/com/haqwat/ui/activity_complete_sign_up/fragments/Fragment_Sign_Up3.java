package com.haqwat.ui.activity_complete_sign_up.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.haqwat.adapters.TeamAdapter;
import com.haqwat.databinding.FragmentSignUp2Binding;
import com.haqwat.databinding.FragmentSignUp3Binding;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.SignUpModel;
import com.haqwat.models.TeamModel;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2Presenter;
import com.haqwat.mvp.fragment_sign_up3_mvp.FragmentSignUp3Presenter;
import com.haqwat.mvp.fragment_sign_up3_mvp.FragmentSignUp3View;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Sign_Up3 extends Fragment implements FragmentSignUp3View {
    public static final String TAG= "TAG";
    private FragmentSignUp3Binding binding;
    public SignUpModel signUpModel;
    private FragmentSignUp3Presenter presenter;
    private List<TeamModel> teamModelList;
    private CompleteSignUpActivity activity;
    private TeamAdapter adapter;

    public static Fragment_Sign_Up3 newInstance(SignUpModel signUpModel){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG,signUpModel);
        Fragment_Sign_Up3 fragment_sign_up3 = new Fragment_Sign_Up3();
        fragment_sign_up3.setArguments(bundle);
        return fragment_sign_up3;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up3,container,false);
        initView();
        return binding.getRoot();

    }

    private void initView()
    {
        activity= (CompleteSignUpActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle!=null){
            signUpModel = (SignUpModel) bundle.getSerializable(TAG);
        }
        teamModelList = new ArrayList<>();
        presenter = new FragmentSignUp3Presenter(this,activity);
        adapter = new TeamAdapter(teamModelList,activity,presenter);
        binding.recView.setLayoutManager(new GridLayoutManager(activity,3));
        binding.recView.setAdapter(adapter);
        getTeams();
    }

    public void getTeams() {
        teamModelList.clear();
        adapter.notifyDataSetChanged();
        presenter.getTeams(Integer.parseInt(signUpModel.getLeague_id()));
    }

    @Override
    public void onTeamDataSuccess(List<TeamModel> list)
    {
        teamModelList.clear();
        teamModelList.addAll(list);
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
    public void onItemSelected(TeamModel teamModel) {
        signUpModel.setTeam_id(String.valueOf(teamModel.getId()));

    }


}
