package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.LeagueCategoryRowBinding;
import com.haqwat.databinding.LeagueRowBinding;
import com.haqwat.models.LeagueCategory;
import com.haqwat.models.LeagueModel;
import com.haqwat.mvp.activity_league_details_mvp.LeagueDetailsPresenter;
import com.haqwat.mvp.fragment_sign_up2_mvp.FragmentSignUp2Presenter;

import java.util.List;

public class LeagueCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LeagueCategory> list;
    private Context context;
    private LayoutInflater inflater;
    private LeagueDetailsPresenter presenter;
    private String league_id="";
    private int selected_pos = 0;
    private int old_pos = 0;

    public LeagueCategoryAdapter(List<LeagueCategory> list, Context context,LeagueDetailsPresenter presenter,String league_id) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.presenter = presenter;
        this.league_id = league_id;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LeagueCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.league_category_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        LeagueCategory leagueCategory = list.get(position);
        myHolder.binding.setModel(leagueCategory);
        if (leagueCategory.isSelected()){
            myHolder.binding.icon.setColorFilter(ContextCompat.getColor(context,R.color.color12));
            myHolder.binding.image.setBorderWidth(3);
            myHolder.binding.image.setBorderColor(ContextCompat.getColor(context,R.color.color12));
            myHolder.binding.tvName.setTextColor(ContextCompat.getColor(context,R.color.color12));
        }else {
            myHolder.binding.icon.setColorFilter(ContextCompat.getColor(context,R.color.gray5));
            myHolder.binding.image.setBorderWidth(1);
            myHolder.binding.image.setBorderColor(ContextCompat.getColor(context,R.color.gray5));
            myHolder.binding.tvName.setTextColor(ContextCompat.getColor(context,R.color.gray5));

        }

        myHolder.itemView.setOnClickListener(view -> {
            selected_pos = holder.getAdapterPosition();
            if (old_pos!=-1){
                LeagueCategory leagueCategory2 = list.get(old_pos);
                leagueCategory2.setSelected(false);
                list.set(old_pos,leagueCategory2);
                notifyItemChanged(old_pos);
            }


            LeagueCategory leagueCategory3 = list.get(selected_pos);
            leagueCategory3.setSelected(true);
            list.set(selected_pos,leagueCategory3);
            notifyItemChanged(selected_pos);
            old_pos = selected_pos;
            presenter.displayFragments(false,leagueCategory3.getTag(),league_id);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public LeagueCategoryRowBinding binding;

        public MyHolder(@NonNull LeagueCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void setSelected_pos(int pos){
        this.selected_pos = pos;

        if (old_pos!=-1){
            LeagueCategory leagueCategory2 = list.get(old_pos);
            leagueCategory2.setSelected(false);
            list.set(old_pos,leagueCategory2);
            notifyItemChanged(old_pos);
        }


        LeagueCategory leagueCategory3 = list.get(selected_pos);
        leagueCategory3.setSelected(true);
        list.set(selected_pos,leagueCategory3);
        notifyItemChanged(selected_pos);
        old_pos = selected_pos;
    }



}
