package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.BestTeamRow2Binding;
import com.haqwat.databinding.FavoriteTeamRowBinding;
import com.haqwat.databinding.TeamRow2Binding;
import com.haqwat.models.TeamModel2;
import com.haqwat.models.TeamOrderModel;

import java.util.List;

public class TeamAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TeamModel2> list;
    private Context context;
    private LayoutInflater inflater;
    public TeamAdapter2(List<TeamModel2> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        BestTeamRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.best_team_row2, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        TeamModel2 teamModel = list.get(position);
        myHolder.binding.setModel(teamModel);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public BestTeamRow2Binding binding;

        public MyHolder(@NonNull BestTeamRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
