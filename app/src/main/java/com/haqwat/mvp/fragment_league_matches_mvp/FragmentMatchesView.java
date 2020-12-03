package com.haqwat.mvp.fragment_league_matches_mvp;

import com.haqwat.models.MatchesModel;

import java.util.List;

public interface FragmentMatchesView {
    void onSuccess(MatchesModel matchesModel);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
    void showProgressDialog();
    void hideProgressDialog();
    void onExpectationSuccess();
}
