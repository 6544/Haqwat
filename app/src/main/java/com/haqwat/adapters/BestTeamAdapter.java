package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.BestTeamsRowBinding;
import com.haqwat.databinding.HomeTeamsRowBinding;
import com.haqwat.models.BestThreeTeamModel;
import com.haqwat.models.HomeJoinedTeamsModel;
import com.haqwat.models.TeamOrderModel;

import java.util.List;
import java.util.Locale;

public class BestTeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BestThreeTeamModel> list;
    private Context context;
    private LayoutInflater inflater;
    public BestTeamAdapter(List<BestThreeTeamModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        BestTeamsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.best_teams_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        BestThreeTeamModel model = list.get(position);
        myHolder.binding.setModel(model);
        TeamAdapter2 adapter = new TeamAdapter2(model.getTeams(),context);
        myHolder.binding.recView.setLayoutManager(new LinearLayoutManager(context));
        myHolder.binding.recView.setAdapter(adapter);


    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public BestTeamsRowBinding binding;

        public MyHolder(@NonNull BestTeamsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
