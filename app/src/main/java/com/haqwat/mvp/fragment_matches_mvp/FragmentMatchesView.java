package com.haqwat.mvp.fragment_matches_mvp;

import com.haqwat.models.MatchesModel;

import java.util.List;

public interface FragmentMatchesView {
    void onSuccess(List<MatchesModel> matchesModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
    void showProgressDialog();
    void hideProgressDialog();
    void onExpectationSuccess(int firstExpectation, int secondExpectation);
}
