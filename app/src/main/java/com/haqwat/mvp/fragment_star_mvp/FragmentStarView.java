package com.haqwat.mvp.fragment_star_mvp;

import com.haqwat.models.LeagueModel;
import com.haqwat.models.StarModel;
import com.haqwat.models.SystemModel;

import java.util.List;

public interface FragmentStarView {
    void onSuccess(List<LeagueModel> leagueModelList);
    void onStarSuccess(List<StarModel> starModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
