package com.haqwat.mvp.fragment_team_rate;

import com.haqwat.models.LeagueRateModel;

public interface FragmentTeamRateView {
    void onSuccess(LeagueRateModel leagueRateModel);
    void onFailed(String msg);
    void onShowAlertDialog();
    void showProgressBar();
    void hideProgressBar();
}
