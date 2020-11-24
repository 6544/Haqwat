package com.haqwat.ui.activity_home.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.R;
import com.haqwat.databinding.FragmentHomeBinding;
import com.haqwat.models.HomeModel;
import com.haqwat.models.TeamOrderModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_home_mvp.FragmentHomePresenter;
import com.haqwat.mvp.fragment_home_mvp.FragmentHomeView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

import java.util.List;
import java.util.Locale;

public class Fragment_Home extends Fragment implements FragmentHomeView {
    private FragmentHomeBinding binding;
    private HomeActivity activity;
    private FragmentHomePresenter presenter;
    private Preferences preferences;
    private UserModel userModel;


    public static Fragment_Home newInstance(){
        return new Fragment_Home();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView()
    {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        presenter = new FragmentHomePresenter(this,activity);
        presenter.getData(userModel);

        binding.img1.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.champions_league));
        });
        binding.img2.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.french_league));
        });
        binding.img3.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.la_liga));
        });
        binding.img4.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.italian_league));
        });
        binding.img5.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.saudi_league));
        });
        binding.img6.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.egyptian_league));
        });
        binding.img7.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.bundesliga));
        });
        binding.img8.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.premier_league));
        });
        binding.progBarLoadAverageRate.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.progBarLoadRate.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.progBarLoadTeam.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

    }

    private void navigateToLeagueDetailsActivity(String name)
    {
        Intent intent = new Intent(activity, LeagueDetailsActivity.class);
        intent.putExtra("data",name);
        startActivity(intent);

    }

    @Override
    public void hideLoadRate() {
        binding.progBarLoadRate.setVisibility(View.GONE);
        binding.progBarLoadTeam.setVisibility(View.GONE);

    }

    @Override
    public void hideLoadAverageRate() {
        binding.progBarLoadAverageRate.setVisibility(View.GONE);
        binding.progBarLoadTeam.setVisibility(View.GONE);

    }

    @Override
    public void onDataSuccess(HomeModel homeModel) {
        String title = "أكثر الفرق المفضلة فى "+homeModel.getUserDefaultLeague().getTitle();
        binding.tvLeagueName.setText(title);
        binding.progBarAverageRate.setVisibility(View.VISIBLE);
        binding.progBarYourRate.setVisibility(View.VISIBLE);
        binding.progBarAverageRate.setProgress(homeModel.getAllUsersRate());
        binding.tvAverageRate.setText(String.format(Locale.ENGLISH,"%s%s",homeModel.getAllUsersRate(),"%"));
        binding.progBarYourRate.setProgress(homeModel.getCurrentUserRate());
        binding.tvYourRate.setText(String.format(Locale.ENGLISH,"%s%s",homeModel.getCurrentUserRate(),"%"));
        updateProgress(homeModel.getTeamsOrderInDesc());

    }

    private void updateProgress(List<TeamOrderModel> teamsOrderInDesc) {
        teamsOrderInDesc.clear();
        teamsOrderInDesc.add(new TeamOrderModel(80));
        teamsOrderInDesc.add(new TeamOrderModel(10));
        teamsOrderInDesc.add(new TeamOrderModel(10));

        int size = teamsOrderInDesc.size();
        int gap = 2;//%100
        double gap_degree = gap*360/100;// degree 360
        double base_progress = (360-(gap_degree*size))/360;
        if (size==0){
            binding.progBar1.setProgress(0);
            binding.progBar2.setProgress(0);
            binding.progBar3.setProgress(0);
        }else if (size==1){

            binding.progBar1.setProgress(teamsOrderInDesc.get(0).getRate_to_display());
            binding.progBar2.setProgress(0);
            binding.progBar3.setProgress(0);

        }else if (size==2){

            int progress1 = (int) Math.round(base_progress*teamsOrderInDesc.get(0).getRate_to_display());
            int progress2 = (int) Math.round(base_progress*teamsOrderInDesc.get(1).getRate_to_display());
            int end_first = (int) ((int) Math.round(progress1*3.6)+gap_degree);
            binding.progBar1.setProgress(progress1);
            binding.progBar2.setProgress(progress2);
            binding.progBar3.setProgress(0);
            binding.progBar2.setRotation(end_first);

        }else if (size==3){
            int progress1 = (int) Math.round(base_progress*teamsOrderInDesc.get(0).getRate_to_display());
            int progress2 = (int) Math.round(base_progress*teamsOrderInDesc.get(1).getRate_to_display());
            int progress3 = (int) Math.round(base_progress*teamsOrderInDesc.get(2).getRate_to_display());
            int end_first = (int) ((int) Math.round(progress1*3.6)+gap_degree);
            int end_second = end_first + (int) ((int) Math.round(progress2*3.6)+gap_degree);

            Log.e("p1",progress1+"__"+end_first);
            Log.e("p2",progress2+"__"+end_second);
            Log.e("p3",progress3+"__");

            binding.progBar1.setProgress(progress1);
            binding.progBar2.setProgress(progress2);
            binding.progBar3.setProgress(progress3);
            binding.progBar2.setRotation(end_first);
            binding.progBar3.setRotation(end_second);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
