package com.haqwat.mvp.fragment_charge_mvp;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.ChargeModel;

import java.util.List;

public interface FragmentChargeView {
    void onSuccess(ChargeDataModel chargeDataModel);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
