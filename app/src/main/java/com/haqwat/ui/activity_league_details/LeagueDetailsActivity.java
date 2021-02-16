package com.haqwat.ui.activity_league_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.haqwat.R;
import com.haqwat.adapters.LeagueCategoryAdapter;
import com.haqwat.databinding.ActivityCompleteSignUpBinding;
import com.haqwat.databinding.ActivityLeagueDetailsBinding;
import com.haqwat.language.Language;
import com.haqwat.models.LeagueCategory;
import com.haqwat.models.SignUpModel;
import com.haqwat.mvp.activity_league_details_mvp.LeagueDetailsPresenter;
import com.haqwat.mvp.activity_league_details_mvp.LeagueDetailsView;
import com.haqwat.mvp.sign_up_mpv.SignUpPresenter;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Previous_Matches;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Ranking_Table;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Rating;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Tournament_History;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_UpComing_Matches;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class LeagueDetailsActivity extends AppCompatActivity implements LeagueDetailsView {
    private ActivityLeagueDetailsBinding binding;
    private FragmentManager fragmentManager;
    private LeagueDetailsPresenter presenter;
    private String leagueName="";
    private LeagueCategoryAdapter adapter;
    private List<LeagueCategory> leagueCategoryList;
    private String lang="ar";
    private String league_id="";


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_league_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        leagueName = intent.getStringExtra("data");
        league_id = intent.getStringExtra("id");
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        leagueCategoryList = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        binding.setLeagueName(leagueName);
        binding.setLang(lang);
        presenter = new LeagueDetailsPresenter(this,this,fragmentManager,league_id);
        adapter = new LeagueCategoryAdapter(leagueCategoryList,this,presenter,league_id);
        binding.recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recView.setAdapter(adapter);
        presenter.getLeagueCategory();
        binding.llBack.setOnClickListener(view -> finish());

        switch (league_id){
            case Tags.CHAMPIONS_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img1);
                break;
            case Tags.EGYPTIAN_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img6);

                break;
            case Tags.FRANCE_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img2);

                break;
            case Tags.JERMAN_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img7);

                break;
            case Tags.LA_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img3);

                break;
            case Tags.SAUDI_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img5);

                break;
            case Tags.SERIA_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img4);

                break;
            case Tags.PREMIER_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img8);

                break;

        }
    }

    public void secondFragment(){
        presenter.displayFragments(false,LeagueDetailsPresenter.fragmentUpComingMatchesTag,league_id);
    }

    @Override
    public void onLeagueCategorySuccess(List<LeagueCategory> list) {
        leagueCategoryList.clear();
        leagueCategoryList.addAll(list);
        LeagueCategory leagueCategory = leagueCategoryList.get(0);
        leagueCategory.setSelected(true);
        leagueCategoryList.set(0,leagueCategory);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentSelected(String tag)
    {
        if (league_id.equals("12")){
            switch (tag){
                case "fragment_league_upComing_matches":
                    if (adapter!=null){
                        adapter.setSelected_pos(1);
                    }
                    break;
                case "fragment_league_previous_matches":
                    if (adapter!=null){
                        adapter.setSelected_pos(2);
                    }

                    break;
                case "fragment_league_tournament_history":
                    if (adapter!=null){
                        adapter.setSelected_pos(3);
                    }
                    break;
                default:
                    if (adapter!=null){
                        adapter.setSelected_pos(0);
                    }
                    break;
            }

        }else {
            switch (tag){
                case "fragment_league_upComing_matches":
                    if (adapter!=null){
                        adapter.setSelected_pos(1);
                    }
                    break;
                case "fragment_league_previous_matches":
                    if (adapter!=null){
                        adapter.setSelected_pos(2);
                    }
                    break;
                case"fragment_league_ranking_table":
                    if (adapter!=null){
                        adapter.setSelected_pos(3);
                    }
                    break;
                case "fragment_league_tournament_history":
                    if (adapter!=null){
                        adapter.setSelected_pos(4);
                    }
                    break;
                default:
                    if (adapter!=null){
                        adapter.setSelected_pos(0);
                    }
                    break;
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for(Fragment fragment:fragmentList){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        List<Fragment> fragmentList = fragmentManager.getFragments();
        for(Fragment fragment:fragmentList){
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackPress();
    }
}