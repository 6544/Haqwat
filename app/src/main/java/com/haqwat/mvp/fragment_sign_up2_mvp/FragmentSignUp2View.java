package com.haqwat.mvp.fragment_sign_up2_mvp;

import com.haqwat.models.LeagueModel;
import com.haqwat.models.NationalityModel;
import com.haqwat.models.SignUpModel;

import java.util.List;

public interface FragmentSignUp2View {
    void onLeagueDataSuccess(List<LeagueModel> list);
    void onFailed(String msg);
    void onHideProgress();
    void onShowProgress();
    void onItemSelected(LeagueModel leagueModel);
}
