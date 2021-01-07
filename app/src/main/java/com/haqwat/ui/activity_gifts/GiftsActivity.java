package com.haqwat.ui.activity_gifts;

import androidx.annotation.Nullable;
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
import com.haqwat.databinding.ActivityAboutAppBinding;
import com.haqwat.databinding.ActivityGiftsBinding;
import com.haqwat.language.Language;
import com.haqwat.models.InvitesLeagueDataModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_invite_league_mvp.ActivityInviteLeaguePresenter;
import com.haqwat.mvp.activity_invite_league_mvp.ActivityInviteLeagueView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_invite_teams.InvitesTeamsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class GiftsActivity extends AppCompatActivity implements ActivityInviteLeagueView {
    private ActivityGiftsBinding binding;
    private String lang;
    private ActivityInviteLeaguePresenter presenter;
    private List<InvitesLeagueDataModel.OtherLeague> otherLeagueList;
    private InviteLeagueAdapter adapter;
    private int countAvailableLeague = 0;



    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gifts);
        initView();
    }



    private void initView()
    {
        otherLeagueList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        presenter = new ActivityInviteLeaguePresenter(this,this);
        adapter = new InviteLeagueAdapter(otherLeagueList,this);
        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recView.setAdapter(adapter);
        presenter.getLeague();
        binding.llBack.setOnClickListener(view -> onBackPressed());


    }


    @Override
    public void onLeagueDataSuccess(InvitesLeagueDataModel.Data data) {
        countAvailableLeague = data.getAllowable_leagues_count();
        binding.setCount(countAvailableLeague);
        if (data.getOther_leagues().size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            otherLeagueList.addAll(data.getOther_leagues());
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
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
        otherLeagueList.clear();
        adapter.notifyDataSetChanged();

    }

    public void joinLeague(InvitesLeagueDataModel.OtherLeague model) {
        if (countAvailableLeague>0){
            Intent intent = new Intent(this, InvitesTeamsActivity.class);
            intent.putExtra("data",model);
            startActivityForResult(intent,100);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            presenter.getLeague();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("gift",countAvailableLeague);
        setResult(RESULT_OK,intent);
        finish();
    }
}