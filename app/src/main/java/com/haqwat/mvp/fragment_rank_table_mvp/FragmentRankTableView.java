package com.haqwat.mvp.fragment_rank_table_mvp;

import com.haqwat.models.MatchesModel;
import com.haqwat.models.TableArrangementModel;

import java.util.List;

public interface FragmentRankTableView {
    void onSuccess(List<TableArrangementModel> tableArrangementModelList);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();

}
