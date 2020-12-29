package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.ChargeHaqwatRowBinding;
import com.haqwat.databinding.HomeTeamsRowBinding;
import com.haqwat.models.ChargeModel;
import com.haqwat.models.HomeJoinedTeamsModel;
import com.haqwat.models.TeamOrderModel;

import java.util.List;
import java.util.Locale;

public class HomeJoinedTeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomeJoinedTeamsModel> list;
    private Context context;
    private LayoutInflater inflater;
    public HomeJoinedTeamAdapter(List<HomeJoinedTeamsModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        HomeTeamsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_teams_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        HomeJoinedTeamsModel model = list.get(position);
        myHolder.binding.setModel(model);
        FavoriteTeamAdapter adapter = new FavoriteTeamAdapter(model.getTeamsOrderInDesc(),context);
        myHolder.binding.recView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        myHolder.binding.recView.setAdapter(adapter);
        updateProgress(model.getTeamsOrderInDesc(),myHolder.binding);


    }


    private void updateProgress(List<TeamOrderModel> teamsOrderInDesc,HomeTeamsRowBinding binding)
    {
        int size = teamsOrderInDesc.size();
        int gap = 2;//%100
        double gap_degree = gap*360/100;// degree 360
        double base_progress = (360-(gap_degree*size))/360;
        if (size==0){
            binding.progBar1.setProgress(0);
            binding.progBar2.setProgress(0);
            binding.progBar3.setProgress(0);
        }else if (size==1){
            binding.tvPercentage.setText(String.format(Locale.ENGLISH,"%s%s",teamsOrderInDesc.get(0).getRate_to_display(),"%"));
            binding.progBar1.setProgress((int) teamsOrderInDesc.get(0).getRate_to_display());
            binding.progBar2.setProgress(0);
            binding.progBar3.setProgress(0);

        }else if (size==2){
            binding.tvPercentage.setText(String.format(Locale.ENGLISH,"%s%s",teamsOrderInDesc.get(0).getRate_to_display(),"%"));

            int progress1 = (int) Math.round(base_progress*teamsOrderInDesc.get(0).getRate_to_display());
            int progress2 = (int) Math.round(base_progress*teamsOrderInDesc.get(1).getRate_to_display());
            int end_first = (int) ((int) Math.round(progress1*3.6)+gap_degree);
            binding.progBar1.setProgress(progress1);
            binding.progBar2.setProgress(progress2);
            binding.progBar3.setProgress(0);
            binding.progBar2.setRotation(end_first);

        }else if (size==3){
            binding.tvPercentage.setText(String.format(Locale.ENGLISH,"%s%s",teamsOrderInDesc.get(0).getRate_to_display(),"%"));

            int progress1 = (int) Math.round(base_progress*teamsOrderInDesc.get(0).getRate_to_display());
            int progress2 = (int) Math.round(base_progress*teamsOrderInDesc.get(1).getRate_to_display());
            int progress3 = (int) Math.round(base_progress*teamsOrderInDesc.get(2).getRate_to_display());
            int end_first = (int) ((int) Math.round(progress1*3.6)+gap_degree);
            int end_second = end_first + (int) ((int) Math.round(progress2*3.6)+gap_degree);



            binding.progBar1.setProgress(progress1);
            binding.progBar2.setProgress(progress2);
            binding.progBar3.setProgress(progress3);
            binding.progBar2.setRotation(end_first);
            binding.progBar3.setRotation(end_second);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public HomeTeamsRowBinding binding;

        public MyHolder(@NonNull HomeTeamsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
