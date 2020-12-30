package com.haqwat.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.MatchPreviousRowBinding;
import com.haqwat.databinding.MatchRowBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.ui.activity_matches.MatchesActivity;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MatchPreviousAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MatchesModel.MatchModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    public MatchPreviousAdapter(List<MatchesModel.MatchModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang","ar");

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        MatchPreviousRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.match_previous_row, parent, false);
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

        if (matchModel.getMatch_expectation_result().equals("no_expect")){
            myHolder.binding.icon.setVisibility(View.VISIBLE);
            myHolder.binding.icon2.setVisibility(View.GONE);
            myHolder.binding.icon.setImageResource(R.drawable.ic_minus);
            myHolder.binding.icon.setColorFilter(ContextCompat.getColor(context,R.color.white));

        }else if (matchModel.getMatch_expectation_result().equals("true_expect")){
            myHolder.binding.icon.setVisibility(View.VISIBLE);
            myHolder.binding.icon.setImageResource(R.drawable.ic_correct);
            myHolder.binding.icon.setColorFilter(ContextCompat.getColor(context,R.color.white));
            if (matchModel.getUser_expectation().getPoints_count()>3){
                myHolder.binding.icon2.setVisibility(View.VISIBLE);
                myHolder.binding.icon2.setColorFilter(ContextCompat.getColor(context,R.color.white));


            }else {
                myHolder.binding.icon2.setVisibility(View.GONE);

            }


        }else {
            myHolder.binding.icon2.setVisibility(View.GONE);
            myHolder.binding.icon.setVisibility(View.VISIBLE);
            myHolder.binding.icon.setImageResource(R.drawable.ic_close2);
            myHolder.binding.icon.setColorFilter(ContextCompat.getColor(context,R.color.white));

        }

        if (matchModel.getWin_first_team_rate()==0){
            myHolder.binding.tvWin.setVisibility(View.GONE);
        }else {
            myHolder.binding.tvWin.setText(String.format(Locale.ENGLISH,"%s%s",matchModel.getWin_first_team_rate(),"%"));
            myHolder.binding.tvWin.setVisibility(View.VISIBLE);

        }

        if (matchModel.getWin_second_team_rate()==0){
            myHolder.binding.tvLose.setVisibility(View.GONE);
        }else {
            myHolder.binding.tvLose.setText(String.format(Locale.ENGLISH,"%s%s",matchModel.getWin_second_team_rate(),"%"));
            myHolder.binding.tvLose.setVisibility(View.VISIBLE);

        }

        if (matchModel.getWin_draw_rate()==0){
            myHolder.binding.tvMid.setVisibility(View.GONE);
        }else {
            myHolder.binding.tvMid.setText(String.format(Locale.ENGLISH,"%s%s",matchModel.getWin_draw_rate(),"%"));
            myHolder.binding.tvMid.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MatchPreviousRowBinding binding;

        public MyHolder(@NonNull MatchPreviousRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
