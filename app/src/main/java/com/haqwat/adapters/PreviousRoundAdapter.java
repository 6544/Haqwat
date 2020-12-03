package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.RoundPreviousRowBinding;
import com.haqwat.databinding.RoundRowBinding;
import com.haqwat.models.MatchesModel;
import com.haqwat.ui.activity_matches.fragments.Fragment_Previous_Matches;

import java.util.List;

import io.paperdb.Paper;

public class PreviousRoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MatchesModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public PreviousRoundAdapter(List<MatchesModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang","ar");

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        RoundPreviousRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.round_previous_row, parent, false);
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

        if (lang.equals("ar")){
            myHolder.binding.progBarRate.setRotation(180);
        }else {
            myHolder.binding.progBarRate.setRotation(0);

        }



        if (matchesModel.getPrev_matches()!=null&&matchesModel.getPrev_matches().size()>0){
            MatchPreviousAdapter adapter = new MatchPreviousAdapter(matchesModel.getPrev_matches(),context);
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
        public RoundPreviousRowBinding binding;

        public MyHolder(@NonNull RoundPreviousRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
