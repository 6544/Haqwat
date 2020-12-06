package com.haqwat.mvp.fragment_top_champion_mvp;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.UserModel;

import java.util.List;

public interface FragmentChampionView {
    void onSuccess(List<UserModel> userModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
