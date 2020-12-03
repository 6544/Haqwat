package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.ChargeHaqwatRowBinding;
import com.haqwat.databinding.TableRankRowBinding;
import com.haqwat.models.ChargeModel;
import com.haqwat.models.TableArrangementModel;

import java.util.List;

public class TableArrangementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TableArrangementModel> list;
    private Context context;
    private LayoutInflater inflater;
    public TableArrangementAdapter(List<TableArrangementModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        TableRankRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.table_rank_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        TableArrangementModel model = list.get(position);
        int count = position+1;
        myHolder.binding.setCount(String.valueOf(count));
        myHolder.binding.setModel(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public TableRankRowBinding binding;

        public MyHolder(@NonNull TableRankRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
