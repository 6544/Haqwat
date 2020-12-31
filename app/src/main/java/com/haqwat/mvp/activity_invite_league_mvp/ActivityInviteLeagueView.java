package com.haqwat.mvp.activity_invite_league_mvp;

import com.haqwat.models.InvitesLeagueDataModel;
import com.haqwat.models.LeagueModel;

import java.util.List;

public interface ActivityInviteLeagueView {
    void onLeagueDataSuccess(InvitesLeagueDataModel.Data model);
    void onFailed(String msg);
    void onHideProgress();
    void onShowProgress();

}
