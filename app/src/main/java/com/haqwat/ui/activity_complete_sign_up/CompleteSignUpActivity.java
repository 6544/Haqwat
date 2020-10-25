package com.haqwat.ui.activity_complete_sign_up;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haqwat.R;
import com.haqwat.databinding.ActivityCompleteSignUpBinding;
import com.haqwat.language.Language;
import com.haqwat.models.SignUpModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.sign_up_mpv.SignUpPresenter;
import com.haqwat.mvp.sign_up_mpv.SignUpView;
import com.haqwat.ui.activity_login.LoginActivity;
import com.haqwat.ui.activity_sign_up.SignUpActivity;

import java.util.List;

import io.paperdb.Paper;

public class CompleteSignUpActivity extends AppCompatActivity implements SignUpView {
    private ActivityCompleteSignUpBinding binding;
    private FragmentManager fragmentManager;
    private SignUpPresenter presenter;
    private SignUpModel signUpModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_complete_sign_up);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        signUpModel = (SignUpModel) intent.getSerializableExtra("data");
    }
    private void initView() {
        fragmentManager = getSupportFragmentManager();
        presenter = new SignUpPresenter(getApplicationContext(),this,fragmentManager,signUpModel);
        binding.btnNex1.setOnClickListener(view -> {
            presenter.manageFragments(SignUpPresenter.next);
        });
        binding.btnNex2.setOnClickListener(view -> {presenter.manageFragments(SignUpPresenter.next);});
        binding.btnPrevious.setOnClickListener(view -> {presenter.manageFragments(SignUpPresenter.previous);});

    }



    @Override
    public void onDisplayFragmentStep1() {
        binding.btnNex1.setVisibility(View.VISIBLE);
        binding.llAction.setVisibility(View.GONE);
        binding.imageStep1.setBackgroundResource(R.drawable.ic_hexagon_fill);
        binding.imageStep2.setBackgroundResource(R.drawable.ic_hexagon_empty);
        binding.imageStep3.setBackgroundResource(R.drawable.ic_hexagon_empty);

    }

    @Override
    public void onDisplayFragmentStep2() {
        binding.btnNex1.setVisibility(View.GONE);
        binding.llAction.setVisibility(View.VISIBLE);
        binding.imageStep1.setBackgroundResource(R.drawable.ic_hexagon_empty);
        binding.imageStep2.setBackgroundResource(R.drawable.ic_hexagon_fill);
        binding.imageStep3.setBackgroundResource(R.drawable.ic_hexagon_empty);
    }

    @Override
    public void onDisplayFragmentStep3() {
        binding.btnNex1.setVisibility(View.GONE);
        binding.llAction.setVisibility(View.VISIBLE);
        binding.imageStep1.setBackgroundResource(R.drawable.ic_hexagon_empty);
        binding.imageStep2.setBackgroundResource(R.drawable.ic_hexagon_empty);
        binding.imageStep3.setBackgroundResource(R.drawable.ic_hexagon_fill);
    }


    @Override
    public void onBack() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(UserModel userModel) {

    }

    @Override
    public void onFailed(String msg) {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressBar() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment:fragmentList){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment:fragmentList){
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        presenter.manageFragments(SignUpPresenter.previous);
    }
}