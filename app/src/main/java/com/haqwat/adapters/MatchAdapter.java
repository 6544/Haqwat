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
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.MatchRowBinding;
import com.haqwat.databinding.RoundRowBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.ui.activity_matches.MatchesActivity;

import java.util.List;

import io.paperdb.Paper;

public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MatchesModel.MatchModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private MatchesActivity activity;
    public MatchAdapter(List<MatchesModel.MatchModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
        activity = (MatchesActivity) context;

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
        if (matchModel.getWin_draw_rate()==0){
            myHolder.binding.tvMid.setVisibility(View.GONE);
        }else {
            myHolder.binding.tvMid.setVisibility(View.VISIBLE);
        }

        myHolder.binding.progBarWin.setProgress(40);
        myHolder.binding.progBarLose.setProgress(40);

        myHolder.binding.edtFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.isEmpty()){
                    myHolder.binding.edtFirst.setText("0");
                }
            }
        });
        myHolder.binding.editSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.isEmpty()){
                    myHolder.binding.editSecond.setText("0");
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MatchRowBinding binding;

        public MyHolder(@NonNull MatchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
