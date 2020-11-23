package com.haqwat.ui.activity_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haqwat.R;
import com.haqwat.databinding.ActivityHomeBinding;
import com.haqwat.language.Language;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_home_mvp.ActivityHomePresenter;
import com.haqwat.mvp.activity_home_mvp.ActivityHomeView;
import com.haqwat.preferences.Preferences;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_login.LoginActivity;
import com.haqwat.ui.activity_matches.MatchesActivity;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements ActivityHomeView {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private ActivityHomePresenter presenter;
    private boolean backPress = false;
    private ActionBarDrawerToggle toggle;
    private UserModel userModel;
    private Preferences preferences;
    private ProgressDialog dialog;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setModel(userModel);
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this,this,fragmentManager);
        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolBar,R.string.open,R.string.close);
        toggle.syncState();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if (!backPress){
                presenter.openFragment(item);
            }
            backPress = false;
            return true;
        });
        
        binding.btnUpComingMatches.setOnClickListener(view -> {
            navigateToMatchesActivity(0);
        });

        binding.btnPreviousMatches.setOnClickListener(view -> {
            navigateToMatchesActivity(1);
        });

        binding.llLogout.setOnClickListener(view -> {
            presenter.logout(userModel);
        });
        dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);

    }

    private void navigateToMatchesActivity(int pos) {
        Intent intent = new Intent(this, MatchesActivity.class);
        intent.putExtra("data",pos);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(int itemId) {
        binding.bottomNavigation.setSelectedItemId(itemId);
    }

    @Override
    public void onLogoutSuccess() {
        preferences.clear(this);
        Intent intent  = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLogoutFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress = true;
        presenter.onBackPress();
    }
}