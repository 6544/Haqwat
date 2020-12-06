package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.RewardRowBinding;
import com.haqwat.databinding.SystemRowBinding;
import com.haqwat.models.RewardModel;
import com.haqwat.models.SystemModel;

import java.util.List;

public class SystemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SystemModel> list;
    private Context context;
    private LayoutInflater inflater;
    public SystemAdapter(List<SystemModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        SystemRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.system_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        SystemModel systemModel = list.get(position);
        myHolder.binding.setModel(systemModel);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public SystemRowBinding binding;

        public MyHolder(@NonNull SystemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
