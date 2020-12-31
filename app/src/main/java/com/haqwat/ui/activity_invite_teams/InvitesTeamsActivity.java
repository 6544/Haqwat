package com.haqwat.ui.activity_invite_teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.adapters.InviteLeagueAdapter;
import com.haqwat.adapters.InviteTeamAdapter;
import com.haqwat.databinding.ActivityGiftsBinding;
import com.haqwat.databinding.ActivityInvitesTeamsBinding;
import com.haqwat.language.Language;
import com.haqwat.models.InvitesLeagueDataModel;
import com.haqwat.models.TeamModel;
import com.haqwat.mvp.activity_invite_league_mvp.ActivityInviteLeaguePresenter;
import com.haqwat.mvp.activity_invite_team_mvp.ActivityInviteTeamView;
import com.haqwat.mvp.activity_invite_team_mvp.ActivityTeamsInvitePresenter;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class InvitesTeamsActivity extends AppCompatActivity implements ActivityInviteTeamView {
    private ActivityInvitesTeamsBinding binding;
    private String lang;
    private InvitesLeagueDataModel.OtherLeague otherLeague;
    private InviteTeamAdapter adapter;
    private ActivityTeamsInvitePresenter presenter;
    private List<TeamModel> teamModelList;




    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invites_teams);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        otherLeague = (InvitesLeagueDataModel.OtherLeague) intent.getSerializableExtra("data");
    }


    private void initView()
    {
        teamModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new InviteTeamAdapter(teamModelList,this);
        binding.recView.setAdapter(adapter);
        presenter = new ActivityTeamsInvitePresenter(this,this);
        presenter.getTeams(otherLeague.getId());
        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        binding.llBack.setOnClickListener(view -> finish());


    }

    @Override
    public void onTeamDataSuccess(List<TeamModel> list) {
        if (list.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            teamModelList.addAll(list);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onJoinSuccess() {
        Toast.makeText(this, R.string.suc, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideProgress() {
        binding.progBar.setVisibility(View.GONE);
    }

    @Override
    public void onShowProgress() {
        binding.progBar.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);
        teamModelList.clear();
        adapter.notifyDataSetChanged();
    }

    public void join(TeamModel model) {
        presenter.join(otherLeague.getId(),model.getId());
    }
}