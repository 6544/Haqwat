package com.haqwat.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.ChargeHaqwatRowBinding;
import com.haqwat.databinding.TopChampionRowBinding;
import com.haqwat.models.ChargeModel;
import com.haqwat.models.UserModel;

import java.util.List;

public class TopChampionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserModel> list;
    private Context context;
    private LayoutInflater inflater;
    public TopChampionAdapter(List<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        TopChampionRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.top_champion_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        UserModel userModel = list.get(position);
        if (position==0){
            myHolder.binding.imagePos.setImageResource(R.color.color12);
        }else if (position==1)
        {
            myHolder.binding.imagePos.setImageResource(R.color.colorPrimaryDark);

        }else {
            myHolder.binding.imagePos.setImageResource(R.color.color5);

        }
        myHolder.binding.setModel(userModel);
        myHolder.binding.setPos(position+1);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public TopChampionRowBinding binding;

        public MyHolder(@NonNull TopChampionRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
