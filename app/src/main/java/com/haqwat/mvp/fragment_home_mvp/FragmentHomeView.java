package com.haqwat.mvp.fragment_home_mvp;

import com.haqwat.models.BestThreeLeagueModel;
import com.haqwat.models.BestThreeTeamModel;
import com.haqwat.models.HomeModel;
import com.haqwat.models.UserModel;

import java.util.List;

public interface FragmentHomeView {
    void hideLoadRate();
    void hideLoadAverageRate();

    void showProgressBestLeague();
    void hideProgressBestLeague();

    void onDataSuccess(HomeModel homeModel);
    void onBestLeagueSuccess(List<BestThreeLeagueModel> data);
    void onBestTeamsSuccess(List<BestThreeTeamModel> data);

    void showProgressBestTeam();
    void hideProgressBestTeam();
    void onFailed(String msg);

}
