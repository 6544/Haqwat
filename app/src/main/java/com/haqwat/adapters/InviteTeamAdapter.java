package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.TeamRow2Binding;
import com.haqwat.databinding.TeamRowBinding;
import com.haqwat.models.TeamModel;
import com.haqwat.mvp.fragment_sign_up3_mvp.FragmentSignUp3Presenter;
import com.haqwat.ui.activity_invite_teams.InvitesTeamsActivity;

import java.util.List;

public class InviteTeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TeamModel> list;
    private Context context;
    private LayoutInflater inflater;
    private InvitesTeamsActivity activity;
    public InviteTeamAdapter(List<TeamModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (InvitesTeamsActivity) context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TeamRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.team_row2, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        TeamModel teamModel = list.get(position);
        myHolder.binding.setModel(teamModel);
        myHolder.binding.btnJoin.setOnClickListener(view -> {
            TeamModel model = list.get(myHolder.getAdapterPosition());
            activity.join(model);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public TeamRow2Binding binding;

        public MyHolder(@NonNull TeamRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
