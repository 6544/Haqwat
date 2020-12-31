package com.haqwat.mvp.activity_invite_team_mvp;

import com.haqwat.models.TeamModel;

import java.util.List;

public interface ActivityInviteTeamView {
    void onTeamDataSuccess(List<TeamModel> list);
    void onJoinSuccess();
    void onFailed(String msg);
    void onHideProgress();
    void onShowProgress();
}
