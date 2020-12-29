package com.haqwat.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.SystemRowBinding;
import com.haqwat.databinding.UpgradeRowBinding;
import com.haqwat.models.SystemModel;
import com.haqwat.models.UpgradeModel;

import java.util.List;

public class UpgradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UpgradeModel> list;
    private Context context;
    private LayoutInflater inflater;

    public UpgradeAdapter(List<UpgradeModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        UpgradeRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.upgrade_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        UpgradeModel upgradeModel = list.get(position);
        myHolder.binding.setModel(upgradeModel);
        myHolder.binding.tvDescription.setText(upgradeModel.getDesc());
        if (position == 1) {
            myHolder.binding.tvDescription.append(" ");
            myHolder.binding.tvDescription.append(Html.fromHtml(context.getString(R.string.press_here)));
        } else if (position == 2) {
            myHolder.binding.tvDescription.append(" ");
            myHolder.binding.tvDescription.append(Html.fromHtml(context.getString(R.string.press_here)));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public UpgradeRowBinding binding;

        public MyHolder(@NonNull UpgradeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
