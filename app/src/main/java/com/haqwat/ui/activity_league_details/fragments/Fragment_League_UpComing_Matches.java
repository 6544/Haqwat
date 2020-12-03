package com.haqwat.ui.activity_league_details.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.haqwat.R;
import com.haqwat.adapters.MatchAdapter;
import com.haqwat.adapters.RoundAdapter;
import com.haqwat.databinding.DialogExpectBinding;
import com.haqwat.databinding.FragmentLeagueUpcomingMatchesBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_league_matches_mvp.FragmentMatchesPresenter;
import com.haqwat.mvp.fragment_league_matches_mvp.FragmentMatchesView;
import com.haqwat.preferences.Preferences;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_League_UpComing_Matches extends Fragment implements FragmentMatchesView {
    private static final String TAG="DATA";
    private FragmentLeagueUpcomingMatchesBinding binding;
    private LeagueDetailsActivity activity;
    private FragmentMatchesPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;
    private List<MatchesModel.MatchModel> matchesModelList;
    private MatchAdapter adapter;
    private ProgressDialog dialog;
    private String league_id="";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LeagueDetailsActivity) context;

    }

    public static Fragment_League_UpComing_Matches newInstance(String league_id){
        Bundle bundle = new Bundle();
        bundle.putString(TAG,league_id);
        Fragment_League_UpComing_Matches fragment_league_upComing_matches = new Fragment_League_UpComing_Matches();
        fragment_league_upComing_matches.setArguments(bundle);
        return fragment_league_upComing_matches;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_upcoming_matches,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle!=null) {
            league_id = bundle.getString(TAG);
        }
        matchesModelList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MatchAdapter(matchesModelList,activity,0,this);
        binding.recView.setAdapter(adapter);
        dialog = Common.createProgressDialog(activity,getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        presenter = new FragmentMatchesPresenter(this,activity);
        presenter.getRounds(userModel,league_id);
    }

    @Override
    public void onSuccess(MatchesModel matchesModel) {
        this.matchesModelList.clear();
        if (matchesModel.getNext_matches()!=null&&matchesModel.getNext_matches().size()>0){
            this.matchesModelList.addAll(matchesModel.getNext_matches());

        }
        if (this.matchesModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);

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
    public void onExpectationSuccess() {
        matchesModelList.clear();
        adapter.notifyDataSetChanged();
        presenter.getRounds(userModel,league_id);
    }

    public void showDialogExpectation(MatchesModel.MatchModel matchModel, int child_pos, int parent_pos) {
        CreateDialogAlert(matchModel,child_pos,parent_pos);
    }

    private   void CreateDialogAlert(MatchesModel.MatchModel model,int child_pos, int parent_pos) {
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .create();

        DialogExpectBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_expect, null, false);
        binding.setModel(model);
        binding.btnExpect.setOnClickListener(view -> {
            String firstExpect = binding.edtFirstExpect.getText().toString();
            String secondExpect = binding.edtSecondExpect.getText().toString();
            if (!firstExpect.isEmpty()&&!secondExpect.isEmpty()){
                binding.edtFirstExpect.setError(null);
                binding.edtSecondExpect.setError(null);
                dialog.dismiss();
                Common.CloseKeyBoard(activity,binding.edtFirstExpect);
                sendExpectation(firstExpect,secondExpect,model);
            }else {
                if (firstExpect.isEmpty()){
                    binding.edtFirstExpect.setError(getString(R.string.field_required));

                }else {
                    binding.edtFirstExpect.setError(null);

                }
                if (secondExpect.isEmpty()){
                    binding.edtSecondExpect.setError(getString(R.string.field_required));

                }else {
                    binding.edtSecondExpect.setError(null);

                }
            }
        });
        binding.close.setOnClickListener(v -> dialog.dismiss());
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    private void sendExpectation(String firstExpect, String secondExpect, MatchesModel.MatchModel model) {
        presenter.makeExpectation(userModel,model.getId(),Integer.parseInt(firstExpect),Integer.parseInt(secondExpect));

    }
}
