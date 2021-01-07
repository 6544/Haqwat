package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.ChargeHaqwatRowBinding;
import com.haqwat.databinding.RewardRowBinding;
import com.haqwat.models.ChargeModel;
import com.haqwat.models.RewardModel;
import com.haqwat.ui.activity_home.fragments.fragment_champions.Fragment_Rewards;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RewardModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment_Rewards fragment_rewards;
    public RewardAdapter(List<RewardModel> list, Context context,Fragment_Rewards fragment_rewards) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment_rewards = fragment_rewards;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        RewardRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.reward_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        RewardModel rewardModel = list.get(position);
        myHolder.binding.setModel(rewardModel);
        myHolder.binding.setCount(String.valueOf(position+1));
        myHolder.itemView.setOnClickListener(view -> {
            fragment_rewards.showDialog();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public RewardRowBinding binding;

        public MyHolder(@NonNull RewardRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
