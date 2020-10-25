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
import com.haqwat.models.LeagueModel;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2Presenter;

import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LeagueModel> list;
    private Context context;
    private LayoutInflater inflater;
    private FragmentSignUp2Presenter presenter;
    private int selected_pos = -1;
    private int old_pos = -1;
    public LeagueAdapter(List<LeagueModel> list, Context context, FragmentSignUp2Presenter presenter) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.presenter = presenter;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LeagueRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.league_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        LeagueModel leagueModel = list.get(position);
        myHolder.binding.setModel(leagueModel);
        if (leagueModel.isSelected()){
            myHolder.binding.image.setBorderColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            myHolder.binding.image.setBorderWidth(R.dimen.border);
        }else {
            myHolder.binding.image.setBorderWidth(0f);

        }

        myHolder.itemView.setOnClickListener(view -> {
            selected_pos = holder.getAdapterPosition();
            if (old_pos!=-1){
                LeagueModel leagueModel2 = list.get(old_pos);
                leagueModel2.setSelected(false);
                list.set(old_pos,leagueModel2);
                notifyItemChanged(old_pos);
            }


            LeagueModel leagueModel3 = list.get(selected_pos);
            leagueModel3.setSelected(true);
            list.set(selected_pos,leagueModel3);
            notifyItemChanged(selected_pos);
            old_pos = selected_pos;
            presenter.setSelectedItem(leagueModel3);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public LeagueRowBinding binding;

        public MyHolder(@NonNull LeagueRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
