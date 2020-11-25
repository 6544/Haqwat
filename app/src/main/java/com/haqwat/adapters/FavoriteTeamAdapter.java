package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.FavoriteTeamRowBinding;
import com.haqwat.databinding.TeamRowBinding;
import com.haqwat.models.TeamModel;
import com.haqwat.models.TeamOrderModel;
import com.haqwat.mvp.fragment_sign_up3_mvp.FragmentSignUp3Presenter;

import java.util.List;

public class FavoriteTeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TeamOrderModel> list;
    private Context context;
    private LayoutInflater inflater;
    private int [] background = {R.drawable.circle_color13,R.drawable.circle_color14,R.drawable.circle_color15};
    public FavoriteTeamAdapter(List<TeamOrderModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        FavoriteTeamRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.favorite_team_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        TeamOrderModel teamModel = list.get(position);
        myHolder.binding.setModel(teamModel);
        int pos = position%background.length;
        myHolder.binding.icon.setBackgroundResource(background[pos]);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public FavoriteTeamRowBinding binding;

        public MyHolder(@NonNull FavoriteTeamRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
