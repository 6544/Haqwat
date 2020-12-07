package com.haqwat.mvp.fragment_nomination_mvp;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.NominationModel;

import java.util.List;

public interface FragmentNominationView {
    void onSuccess(List<NominationModel> nominationModelList);
    void onAddedSuccessfully();
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
    void showProgressDialog();
    void hideProgressDialog();
}
