package com.haqwat.ui.activity_home.fragments.fragment_champions;

import android.app.ProgressDialog;
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
import com.haqwat.adapters.NominationAdapter;
import com.haqwat.adapters.TopChampionAdapter;
import com.haqwat.databinding.FragmentNominationsBinding;
import com.haqwat.models.NominationModel;
import com.haqwat.models.TeamModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_nomination_mvp.FragmentNominationPresenter;
import com.haqwat.mvp.fragment_nomination_mvp.FragmentNominationView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentChampionView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentTopChampionPresenter;
import com.haqwat.preferences.Preferences;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Nomination extends Fragment implements FragmentNominationView {
    private FragmentNominationsBinding binding;
    private List<NominationModel> nominationModelList;
    private NominationAdapter adapter;
    private HomeActivity activity;
    private FragmentNominationPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;
    private ProgressDialog  dialog;

    public static Fragment_Nomination newInstance() {
        return new Fragment_Nomination();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nominations, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        nominationModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NominationAdapter(nominationModelList, activity,this);
        binding.recView.setAdapter(adapter);
        dialog = Common.createProgressDialog(activity,getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        presenter = new FragmentNominationPresenter(this, activity);
        presenter.getNomination(userModel);



    }

    @Override
    public void onSuccess(List<NominationModel> nominationModelList) {
        this.nominationModelList.clear();
        if (nominationModelList.size() > 0) {

            TeamModel teamModel = new TeamModel(0,getString(R.string.ch));
            for (NominationModel model:nominationModelList){
                List<TeamModel> teams = model.getTeams();
                teams.add(0,teamModel);

            }
            this.nominationModelList.addAll(nominationModelList);

            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onAddedSuccessfully() {
        presenter.getNomination(userModel);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        this.nominationModelList.clear();
        adapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    public void setItemsData(TeamModel teamModelFav, TeamModel teamModelRecommended, NominationModel nominationModel) {
        presenter.addNomination(userModel,nominationModel.getId(),teamModelFav.getId(),teamModelRecommended.getId());
    }
}
