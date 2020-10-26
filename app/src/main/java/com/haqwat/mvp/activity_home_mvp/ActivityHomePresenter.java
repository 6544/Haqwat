package com.haqwat.mvp.activity_home_mvp;

import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.haqwat.R;
import com.haqwat.ui.activity_home.fragments.Fragment_Champions;
import com.haqwat.ui.activity_home.fragments.Fragment_Charge;
import com.haqwat.ui.activity_home.fragments.Fragment_Home;
import com.haqwat.ui.activity_home.fragments.Fragment_Stars;

import java.util.List;

public class ActivityHomePresenter {
    private ActivityHomeView view;
    private Context context;
    private FragmentManager fragmentManager;
    private Fragment_Home fragment_home;
    private Fragment_Charge fragment_charge;
    private Fragment_Stars fragment_stars;
    private Fragment_Champions fragment_champions;
    public static final String tagHome="fragment_home";
    public static final String tagCharge="fragment_charge";
    public static final String tagStars="fragment_stars";
    public static final String tagChampion="fragment_champions";
    private int currentItemId = 0;


    public ActivityHomePresenter(ActivityHomeView view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        displayFragment(true,tagHome);
    }

    public void displayFragment(boolean isFirstFragment,String tag)
    {

        switch (tag){
            case tagCharge:
                if (fragment_charge==null){
                    fragment_charge = Fragment_Charge.newInstance();
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_charge, tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction.addToBackStack(null);
                }
                transaction.commit();
                break;
            case tagStars:
                if (fragment_stars==null){
                    fragment_stars = Fragment_Stars.newInstance();
                }
                FragmentTransaction transaction2 = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_stars, tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction2.addToBackStack(null);
                }
                transaction2.commit();
                break;
            case tagChampion:
                if (fragment_champions==null){
                    fragment_champions = Fragment_Champions.newInstance();
                }
                FragmentTransaction transaction3 = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_champions, tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction3.addToBackStack(null);
                }
                transaction3.commit();
                break;
            default:
                if (fragment_home==null){
                    fragment_home = Fragment_Home.newInstance();
                }
                FragmentTransaction transaction4 = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_home, tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction4.addToBackStack(null);
                }
                transaction4.commit();
                break;
        }


    }
    public void openFragment(MenuItem item)
    {
        currentItemId = item.getItemId();
        switch (currentItemId){
            case R.id.charge:
                displayFragment(false,tagCharge);
                break;
            case R.id.stars:
                displayFragment(false,tagStars);
                break;
            case R.id.championship:
                displayFragment(false,tagChampion);

                break;
            default:
                displayFragment(false,tagHome);

                break;
        }
    }
    public void onBackPress(){
        String tag = fragmentManager.findFragmentById(R.id.fragment_container).getTag();
        switch (tag){
            case tagCharge:
                currentItemId = R.id.charge;
                break;
            case tagStars:
                currentItemId = R.id.stars;
                break;
            case tagChampion:
                currentItemId = R.id.championship;
                break;
            default:
                currentItemId = R.id.home;
                break;
        }

        view.onBackPressed(currentItemId);
    }
    private boolean isInBackStack(String tag)
    {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment:fragmentList){
            if (fragment.getTag().equals(tag)){
                return true;
            }
        }
        return false;
    }
}
