package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.FavoriteTeamRowBinding;
import com.haqwat.databinding.MatchRowBinding;
import com.haqwat.databinding.RoundRowBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.models.TeamOrderModel;
import com.haqwat.ui.activity_matches.fragments.Fragment_UpComing_Matches;

import java.util.List;

public class RoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MatchesModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public RoundAdapter(List<MatchesModel> list, Context context,Fragment fragment) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        RoundRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.round_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        MatchesModel matchesModel = list.get(position);
        myHolder.binding.setModel(matchesModel);
        if (position==0){
            myHolder.binding.expandLayout.setExpanded(true,true);
            myHolder.binding.arrow.clearAnimation();
            myHolder.binding.arrow.animate().setDuration(800).rotation(180).start();
        }


        if (matchesModel.getNext_matches()!=null&&matchesModel.getNext_matches().size()>0){

            MatchAdapter adapter = new MatchAdapter(matchesModel.getNext_matches(),context,position,fragment);
            myHolder.binding.recView.setLayoutManager(new LinearLayoutManager(context));
            myHolder.binding.recView.setAdapter(adapter);

        }


        myHolder.itemView.setOnClickListener(view -> {
            if (myHolder.binding.expandLayout.isExpanded()){
                myHolder.binding.expandLayout.collapse(true);
                myHolder.binding.arrow.clearAnimation();
                myHolder.binding.arrow.animate().setDuration(800).rotation(0).start();

            }else {
                myHolder.binding.expandLayout.expand(true);
                myHolder.binding.arrow.animate().setDuration(800).rotation(180).start();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public RoundRowBinding binding;

        public MyHolder(@NonNull RoundRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
