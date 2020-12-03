package com.haqwat.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.MatchRowBinding;
import com.haqwat.databinding.RoundRowBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.ui.activity_league_details.fragments.Fragment_League_UpComing_Matches;
import com.haqwat.ui.activity_matches.MatchesActivity;
import com.haqwat.ui.activity_matches.fragments.Fragment_UpComing_Matches;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MatchesModel.MatchModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private int parent_pos;
    private Fragment fragment;
    public MatchAdapter(List<MatchesModel.MatchModel> list, Context context, int position, Fragment fragment) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
        this.parent_pos = position;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MatchRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.match_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        MatchesModel.MatchModel matchModel = list.get(position);
        myHolder.binding.setModel(matchModel);
        myHolder.binding.setLang(lang);

        if (lang.equals("ar")){
            myHolder.binding.progBarWin.setRotation(0);
            myHolder.binding.progBarLose.setRotation(180);


        }else {
            myHolder.binding.progBarWin.setRotation(180);
            myHolder.binding.progBarLose.setRotation(0);
        }

        if (matchModel.getWin_first_team_rate()==0){
            myHolder.binding.tvWin.setVisibility(View.GONE);
        }else {
            myHolder.binding.progBarWin.setProgress(matchModel.getWin_first_team_rate());
            myHolder.binding.tvWin.setText(String.format(Locale.ENGLISH,"%s%s",matchModel.getWin_first_team_rate(),"%"));
            myHolder.binding.tvWin.setVisibility(View.VISIBLE);

        }

        if (matchModel.getWin_second_team_rate()==0){
            myHolder.binding.tvLose.setVisibility(View.GONE);
        }else {
            myHolder.binding.progBarLose.setProgress(matchModel.getWin_second_team_rate());

            myHolder.binding.tvLose.setText(String.format(Locale.ENGLISH,"%s%s",matchModel.getWin_second_team_rate(),"%"));
            myHolder.binding.tvLose.setVisibility(View.VISIBLE);

        }

        if (matchModel.getWin_draw_rate()==0){
            myHolder.binding.tvMid.setVisibility(View.GONE);
        }else {
            myHolder.binding.tvMid.setText(String.format(Locale.ENGLISH,"%s%s",matchModel.getWin_draw_rate(),"%"));
            myHolder.binding.tvMid.setVisibility(View.VISIBLE);
        }



        myHolder.binding.flExpect.setOnClickListener(view -> {
            if (fragment instanceof Fragment_UpComing_Matches){
                Fragment_UpComing_Matches fragment_upComing_matches = (Fragment_UpComing_Matches) fragment;
                MatchesModel.MatchModel matchModel2 = list.get(myHolder.getAdapterPosition());
                fragment_upComing_matches.showDialogExpectation(matchModel2,myHolder.getAdapterPosition(),parent_pos);
            }else if (fragment instanceof Fragment_League_UpComing_Matches){
                Fragment_League_UpComing_Matches fragment_league_upComing_matches = (Fragment_League_UpComing_Matches) fragment;
                MatchesModel.MatchModel matchModel2 = list.get(myHolder.getAdapterPosition());
                fragment_league_upComing_matches.showDialogExpectation(matchModel2,myHolder.getAdapterPosition(),parent_pos);
            }

        });



    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MatchRowBinding binding;

        public MyHolder(@NonNull MatchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
