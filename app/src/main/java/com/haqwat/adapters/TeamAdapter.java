package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.LeagueRowBinding;
import com.haqwat.databinding.TeamRowBinding;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.TeamModel;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2Presenter;
import com.haqwat.mvp.fragment_sign_up3_mvp.FragmentSignUp3Presenter;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TeamModel> list;
    private Context context;
    private LayoutInflater inflater;
    private FragmentSignUp3Presenter presenter;
    private int selected_pos = -1;
    private int old_pos = -1;
    public TeamAdapter(List<TeamModel> list, Context context, FragmentSignUp3Presenter presenter) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.presenter = presenter;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        TeamRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.team_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        TeamModel teamModel = list.get(position);
        myHolder.binding.setModel(teamModel);
        if (teamModel.isSelected()){
            myHolder.binding.image.setBorderColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            myHolder.binding.image.setBorderWidth(R.dimen.border);
        }else {
            myHolder.binding.image.setBorderWidth(0f);

        }

        myHolder.itemView.setOnClickListener(view -> {
            selected_pos = holder.getAdapterPosition();
            if (old_pos!=-1){
                TeamModel teamModel2 = list.get(old_pos);
                teamModel2.setSelected(false);
                list.set(old_pos,teamModel2);
                notifyItemChanged(old_pos);
            }


            TeamModel teamModel3 = list.get(selected_pos);
            teamModel3.setSelected(true);
            list.set(selected_pos,teamModel3);
            notifyItemChanged(selected_pos);
            old_pos = selected_pos;
            presenter.setSelectedItem(teamModel3);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public TeamRowBinding binding;

        public MyHolder(@NonNull TeamRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
