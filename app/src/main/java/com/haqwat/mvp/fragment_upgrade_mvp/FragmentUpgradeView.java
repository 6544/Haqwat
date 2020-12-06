package com.haqwat.mvp.fragment_upgrade_mvp;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.UpgradeModel;

import java.util.List;

public interface FragmentUpgradeView {
    void onSuccess(List<UpgradeModel> upgradeModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
