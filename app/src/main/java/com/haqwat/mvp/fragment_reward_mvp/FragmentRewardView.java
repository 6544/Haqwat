package com.haqwat.mvp.fragment_reward_mvp;

import com.haqwat.models.RewardDataModel;

public interface FragmentRewardView {
    void onSuccess(RewardDataModel rewardModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();
}
