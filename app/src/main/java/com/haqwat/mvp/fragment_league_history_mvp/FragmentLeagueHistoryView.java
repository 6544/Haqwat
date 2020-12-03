package com.haqwat.mvp.fragment_league_history_mvp;

import com.haqwat.models.LeagueHistoryModel;
import com.haqwat.models.TableArrangementModel;

import java.util.List;

public interface FragmentLeagueHistoryView {
    void onSuccess(LeagueHistoryModel leagueHistoryModel);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();

}
