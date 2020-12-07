package com.haqwat.mvp.fragment_star_mvp;

import com.haqwat.models.LeagueModel;
import com.haqwat.models.SystemModel;

import java.util.List;

public interface FragmentStarView {
    void onSuccess(List<LeagueModel> leagueModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
