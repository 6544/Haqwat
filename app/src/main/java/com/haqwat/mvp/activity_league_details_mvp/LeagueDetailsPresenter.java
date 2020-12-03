package com.haqwat.mvp.activity_league_details_mvp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.haqwat.R;
import com.haqwat.models.LeagueCategory;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Previous_Matches;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Ranking_Table;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Rating;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_Tournament_History;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_UpComing_Matches;

import java.util.ArrayList;
import java.util.List;

public class LeagueDetailsPresenter {
    private Context context;
    private LeagueDetailsView view;
    private FragmentManager fragmentManager;
    private Fragment_League_Rating fragment_league_rating;
    private Fragment_League_UpComing_Matches fragment_league_upComing_matches;
    private Fragment_League_Previous_Matches fragment_league_previous_matches;
    private Fragment_League_Ranking_Table fragment_league_ranking_table;
    private Fragment_League_Tournament_History fragment_league_tournament_history;
    public static final String fragmentRateTag="fragment_league_rating";
    public static final String fragmentUpComingMatchesTag="fragment_league_upComing_matches";
    public static final String fragmentPreviousMatchesTag="fragment_league_previous_matches";
    public static final String fragmentRankTag="fragment_league_ranking_table";
    public static final String fragmentHistoryTag="fragment_league_tournament_history";
    private String league_id="";


    public LeagueDetailsPresenter(Context context, LeagueDetailsView view,FragmentManager fragmentManager,String league_id) {
        this.context = context;
        this.view = view;
        this.fragmentManager = fragmentManager;
        this.league_id = league_id;
        displayFragments(true,fragmentRateTag,league_id);
    }

    public void getLeagueCategory(){
        List<LeagueCategory> list = new ArrayList<>();
        list.add(new LeagueCategory(R.drawable.ic_user_rating,context.getString(R.string.haqawat_rating),fragmentRateTag));
        list.add(new LeagueCategory(R.drawable.ic_double_right_arrows,context.getString(R.string.upcoming_matches),fragmentUpComingMatchesTag));
        list.add(new LeagueCategory(R.drawable.ic_double_left_arrows,context.getString(R.string.previous_matches),fragmentPreviousMatchesTag));
        list.add(new LeagueCategory(R.drawable.ic_list,context.getString(R.string.ranking_table),fragmentRankTag));
        list.add(new LeagueCategory(R.drawable.ic_privacy,context.getString(R.string.tournament_history),fragmentHistoryTag));
        view.onLeagueCategorySuccess(list);

    }

    public void displayFragments(boolean isFirstFragment,String tag,String league_id){
        switch (tag){
            case fragmentUpComingMatchesTag:
                if (fragment_league_upComing_matches==null){
                    fragment_league_upComing_matches = Fragment_League_UpComing_Matches.newInstance(league_id);
                }
                FragmentTransaction transaction1 = fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment_league_upComing_matches,tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction1.addToBackStack(null);
                }
                transaction1.commit();
                break;
            case fragmentPreviousMatchesTag:
                if (fragment_league_previous_matches==null){
                    fragment_league_previous_matches = Fragment_League_Previous_Matches.newInstance(league_id);
                }
                FragmentTransaction transaction2 = fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment_league_previous_matches,tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction2.addToBackStack(null);
                }
                transaction2.commit();
                break;
            case fragmentRankTag:

                if (fragment_league_ranking_table==null){
                    fragment_league_ranking_table = Fragment_League_Ranking_Table.newInstance(league_id);
                }
                FragmentTransaction transaction3 = fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment_league_ranking_table,tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction3.addToBackStack(null);
                }
                transaction3.commit();

                break;
            case fragmentHistoryTag:
                if (fragment_league_tournament_history==null){
                    fragment_league_tournament_history = Fragment_League_Tournament_History.newInstance(league_id);
                }
                FragmentTransaction transaction4 = fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment_league_tournament_history,tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction4.addToBackStack(null);
                }
                transaction4.commit();
                break;
            default:
                if (fragment_league_rating==null){
                    fragment_league_rating = Fragment_League_Rating.newInstance(league_id);
                }
                FragmentTransaction transaction5 = fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment_league_rating,tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction5.addToBackStack(null);
                }
                transaction5.commit();
                break;


        }

        view.onFragmentSelected(tag);

    }


    private boolean isInBackStack(String tag){
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment:fragmentList){
            String tag2 = fragment.getTag();
            if (tag.equals(tag2)){
                return true;
            }
        }
        return false;
    }

    public void onBackPress(){
        String tag = fragmentManager.findFragmentById(R.id.fragment_container).getTag();
        view.onFragmentSelected(tag);

    }
}
