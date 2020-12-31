package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.LeagueRow2Binding;
import com.haqwat.databinding.LeagueRowBinding;
import com.haqwat.models.InvitesLeagueDataModel;
import com.haqwat.models.LeagueModel;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2Presenter;
import com.haqwat.ui.activity_gifts.GiftsActivity;

import java.util.List;

public class InviteLeagueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List< InvitesLeagueDataModel.OtherLeague> list;
    private Context context;
    private LayoutInflater inflater;
    private GiftsActivity activity;

    public InviteLeagueAdapter(List< InvitesLeagueDataModel.OtherLeague> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (GiftsActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LeagueRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.league_row2, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        InvitesLeagueDataModel.OtherLeague leagueModel = list.get(position);
        myHolder.binding.setModel(leagueModel);
        myHolder.binding.btnJoin.setOnClickListener(view -> {
            InvitesLeagueDataModel.OtherLeague model = list.get(myHolder.getAdapterPosition());
            activity.joinLeague(model);

        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public LeagueRow2Binding binding;

        public MyHolder(@NonNull LeagueRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
