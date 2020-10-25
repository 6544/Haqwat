package com.haqwat.mvp.fragment_sign_up3_mvp;

import com.haqwat.models.LeagueModel;
import com.haqwat.models.TeamModel;

import java.util.List;

public interface FragmentSignUp3View {
    void onTeamDataSuccess(List<TeamModel> list);
    void onFailed(String msg);
    void onHideProgress();
    void onShowProgress();
    void onItemSelected(TeamModel  teamModel);
}
