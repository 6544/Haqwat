package com.haqwat.mvp.activity_share_mvp;

import com.haqwat.models.LeagueRateModel;

public interface ActivityShareView {
    void onSuccess(LeagueRateModel leagueRateModel);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
