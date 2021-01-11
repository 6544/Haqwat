package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.BestLeagueRowBinding;
import com.haqwat.databinding.ChargeHaqwatRowBinding;
import com.haqwat.models.BestThreeLeagueModel;
import com.haqwat.models.ChargeModel;

import java.util.List;

import io.paperdb.Paper;

public class BestLeagueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BestThreeLeagueModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    public BestLeagueAdapter(List<BestThreeLeagueModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang","ar");

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        BestLeagueRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.best_league_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        BestThreeLeagueModel model = list.get(position);
        myHolder.binding.setModel(model);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public BestLeagueRowBinding binding;

        public MyHolder(@NonNull BestLeagueRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
