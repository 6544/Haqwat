package com.haqwat.mvp.fragment_points_mvp;

import com.haqwat.models.ChargeDataModel;

public interface FragmentPointsView {
    void onSuccess(ChargeDataModel chargeDataModel);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
