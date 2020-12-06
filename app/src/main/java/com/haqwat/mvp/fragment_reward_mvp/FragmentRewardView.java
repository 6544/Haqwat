package com.haqwat.mvp.fragment_reward_mvp;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.RewardModel;

import java.util.List;

public interface FragmentRewardView {
    void onSuccess(List<RewardModel> rewardModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
