package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.LeagueHistoryRowBinding;
import com.haqwat.databinding.TableRankRowBinding;
import com.haqwat.models.LeagueHistoryModel;
import com.haqwat.models.TableArrangementModel;

import java.util.List;

public class LeagueHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LeagueHistoryModel.LeagueInformation> list;
    private Context context;
    private LayoutInflater inflater;
    public LeagueHistoryAdapter(List<LeagueHistoryModel.LeagueInformation> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LeagueHistoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.league_history_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        LeagueHistoryModel.LeagueInformation model = list.get(position);
        myHolder.binding.setModel(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public LeagueHistoryRowBinding binding;

        public MyHolder(@NonNull LeagueHistoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
