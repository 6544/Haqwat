package com.haqwat.ui.activity_home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.haqwat.ui.activity_contact_us.ContactUsActivity;
import com.haqwat.ui.activity_language.LanguageActivity;
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
        binding.llLogout.setOnClickListener(view -> {
            presenter.logout(userModel);
        });
        binding.llChangeLanguage.setOnClickListener(view -> {
            Intent intent = new Intent(this, LanguageActivity.class);
            startActivityForResult(intent,100);
        });

        binding.llContactUs.setOnClickListener(view -> {
            Intent intent = new Intent(this, ContactUsActivity.class);
            startActivity(intent);
        });

        dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            Paper.init(this);
            String lang = Paper.book().read("lang","ar");
            refreshActivity(lang);
        }
    }

    public void refreshActivity(String lang) {
        Log.e("ff","ggg");
        Paper.book().write("lang", lang);
        Language.updateResources(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 1050);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress = true;
        presenter.onBackPress();
    }
}