package com.haqwat.mvp.fragment_system_mvp;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.SystemModel;

import java.util.List;

public interface FragmentSystemView {
    void onSuccess(List<SystemModel> systemModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
