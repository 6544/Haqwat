package com.haqwat.mvp.activity_league_details_mvp;

import com.haqwat.models.LeagueCategory;

import java.util.List;

public interface LeagueDetailsView {
    void onLeagueCategorySuccess(List<LeagueCategory> leagueCategoryList);
}
