package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.StarRowBinding;
import com.haqwat.databinding.TopChampionRowBinding;
import com.haqwat.models.StarModel;
import com.haqwat.models.UserModel;

import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StarModel> list;
    private Context context;
    private LayoutInflater inflater;
    public StarAdapter(List<StarModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        StarRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.star_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        StarModel starModel = list.get(position);
        if (position==0){
            myHolder.binding.imagePos.setImageResource(R.color.color12);
        }else if (position==1)
        {
            myHolder.binding.imagePos.setImageResource(R.color.colorPrimaryDark);

        }else {
            myHolder.binding.imagePos.setImageResource(R.color.color5);

        }
        myHolder.binding.setModel(starModel);
        myHolder.binding.setPos(position+1);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public StarRowBinding binding;

        public MyHolder(@NonNull StarRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
