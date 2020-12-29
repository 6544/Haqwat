package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.AccountRowBinding;
import com.haqwat.databinding.RewardRowBinding;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.RewardModel;
import com.haqwat.ui.activity_accounts.AccountsActivity;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AccountsModel> list;
    private Context context;
    private LayoutInflater inflater;
    private AccountsActivity activity;
    int [] color = {R.color.color1,R.color.color12,R.color.color13,R.color.color14,R.color.color15,R.color.color8};
    public AccountsAdapter(List<AccountsModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (AccountsActivity) context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AccountRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.account_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int pos = position%color.length;
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.image.setImageResource(color[pos]);
        AccountsModel model = list.get(position);
        myHolder.binding.setModel(model);
        myHolder.binding.tvRemove.setOnClickListener(view -> {
            AccountsModel model2 = list.get(myHolder.getAdapterPosition());
            activity.removeAccount(model2,myHolder.getAdapterPosition());
        });

        myHolder.binding.tvSwitch.setOnClickListener(view -> {
            AccountsModel model2 = list.get(myHolder.getAdapterPosition());
            activity.switchAccount(model2);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public AccountRowBinding binding;

        public MyHolder(@NonNull AccountRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
