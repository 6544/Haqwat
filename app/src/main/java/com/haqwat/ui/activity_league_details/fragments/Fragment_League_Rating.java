package com.haqwat.ui.activity_league_details.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.R;
import com.haqwat.databinding.DialogAlert2Binding;
import com.haqwat.databinding.FragmentLeagueRatingBinding;
import com.haqwat.models.LeagueRateModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_team_rate_mvp.FragmentTeamRatePresenter;
import com.haqwat.mvp.fragment_team_rate_mvp.FragmentTeamRateView;
import com.haqwat.preferences.Preferences;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

public class Fragment_League_Rating extends Fragment implements FragmentTeamRateView {
    private final static String TAG = "DATA";
    private LeagueDetailsActivity activity;
    private FragmentLeagueRatingBinding binding;
    private FragmentTeamRatePresenter presenter;
    private UserModel userModel;
    private Preferences preferences;
    private String league_id = "";
    private AlertDialog dialog;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LeagueDetailsActivity) context;
    }

    public static Fragment_League_Rating newInstance(String league_id) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, league_id);
        Fragment_League_Rating fragment_league_rating = new Fragment_League_Rating();
        fragment_league_rating.setArguments(bundle);
        return fragment_league_rating;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_rating, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            league_id = bundle.getString(TAG);
        }
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.setUserModel(userModel);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.white), PorterDuff.Mode.SRC_IN);
        presenter = new FragmentTeamRatePresenter(this, activity);
        presenter.getData(league_id, userModel);
        createDialogAlert();


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

        binding.imageShare.setOnClickListener(view -> {
            String shareLink = getString(R.string.app_name)+"\n"+"http://hqwat.com/api/leagueShare/"+league_id+"/"+userModel.getId();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,shareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

    }

    private void createDialogAlert() {
        dialog = new AlertDialog.Builder(activity)
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        DialogAlert2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_alert2, null, false);

        binding.tvMsg.setText(R.string.upgrade_account);
        binding.btnPayment.setOnClickListener(v -> {
                    dialog.dismiss();
                    activity.finish();
                }

        );

        binding.btnContinue.setOnClickListener(v -> {
                    dialog.dismiss();
                    activity.secondFragment();
                }

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
    }

    @Override
    public void onSuccess(LeagueRateModel leagueRateModel) {
        binding.flLoading.setVisibility(View.GONE);
        binding.setModel(leagueRateModel);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowAlertDialog() {
        try {
            dialog.show();

        }catch (Exception e){}
    }

    @Override
    public void showProgressBar() {
        try {
            binding.progBar.setVisibility(View.VISIBLE);

        }catch (Exception e){}
    }

    @Override
    public void hideProgressBar() {
        try {
            binding.progBar.setVisibility(View.GONE);

        }catch (Exception e){}

    }
}
