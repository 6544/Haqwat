package com.haqwat.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.NominationRowBinding;
import com.haqwat.databinding.TopChampionRowBinding;
import com.haqwat.models.NominationModel;
import com.haqwat.models.TeamModel;
import com.haqwat.models.UserModel;
import com.haqwat.ui.activity_home.fragments.fragment_champions.Fragment_Nomination;

import java.util.List;

public class NominationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NominationModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment_Nomination fragment_nomination;
    public NominationAdapter(List<NominationModel> list, Context context,Fragment_Nomination fragment_nomination) {
        this.list = list;
        this.context = context;
        this.fragment_nomination = fragment_nomination;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        NominationRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.nomination_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        NominationModel nominationModel = list.get(position);
        myHolder.binding.setModel(nominationModel);


        TeamSpinnerAdapter adapter1 = new TeamSpinnerAdapter(nominationModel.getTeams(),context);
        TeamSpinnerAdapter adapter2 = new TeamSpinnerAdapter(nominationModel.getTeams(),context);
        myHolder.binding.spinnerFavTeam.setAdapter(adapter1);
        myHolder.binding.spinnerRecommendedTeam.setAdapter(adapter2);

        int pos1 = getFavoritePos(nominationModel.getTeams(),nominationModel.getSingle_nomination());
        int pos2 = getRecommendedPos(nominationModel.getTeams(),nominationModel.getSingle_nomination());
        if (pos1!=-1)
        {
            myHolder.binding.spinnerFavTeam.setSelection(pos1);
        }

        if (pos2!=-1)
        {
            myHolder.binding.spinnerRecommendedTeam.setSelection(pos2);

        }

        myHolder.binding.spinnerFavTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    myHolder.binding.btnSend.setVisibility(View.INVISIBLE);
                }else {
                    if (nominationModel.getSingle_nomination()!=null){
                        myHolder.binding.btnSend.setVisibility(View.INVISIBLE);

                    }else {
                        TeamModel teamModel = (TeamModel) myHolder.binding.spinnerRecommendedTeam.getSelectedItem();
                        if (teamModel.getId()==0){
                            myHolder.binding.btnSend.setVisibility(View.INVISIBLE);

                        }else {
                            myHolder.binding.btnSend.setVisibility(View.VISIBLE);

                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myHolder.binding.spinnerRecommendedTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    myHolder.binding.btnSend.setVisibility(View.INVISIBLE);
                }else {
                    if (nominationModel.getSingle_nomination()!=null){
                        myHolder.binding.btnSend.setVisibility(View.INVISIBLE);

                    }else {
                        TeamModel teamModel = (TeamModel) myHolder.binding.spinnerFavTeam.getSelectedItem();
                        if (teamModel.getId()==0){
                            myHolder.binding.btnSend.setVisibility(View.INVISIBLE);

                        }else {
                            myHolder.binding.btnSend.setVisibility(View.VISIBLE);

                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

        myHolder.binding.btnSend.setOnClickListener(view -> {
            NominationModel nominationModel2 = list.get(myHolder.getAdapterPosition());
            TeamModel teamModel1 = (TeamModel) myHolder.binding.spinnerFavTeam.getSelectedItem();
            TeamModel teamModel2 = (TeamModel) myHolder.binding.spinnerRecommendedTeam.getSelectedItem();

            if (teamModel1.getId()!=0&&teamModel2.getId()!=0){
                fragment_nomination.setItemsData(teamModel1,teamModel2,nominationModel2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public NominationRowBinding binding;

        public MyHolder(@NonNull NominationRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    public int getFavoritePos(List<TeamModel> teamModelList , NominationModel.SingleNomination singleNomination){
        int pos = -1;
        if (singleNomination!=null&&singleNomination.getFavorite_team()!=null){
            for (int index=0;index<teamModelList.size();index++){
                TeamModel teamModel = teamModelList.get(index);
                if (teamModel.getId()==singleNomination.getFavorite_team().getId()){
                    pos = index;
                    return pos;
                }
            }
        }

        return pos;
    }

    public int getRecommendedPos(List<TeamModel> teamModelList , NominationModel.SingleNomination singleNomination){
        int pos = -1;
        if (singleNomination!=null&&singleNomination.getRecommended_team()!=null){
            for (int index=0;index<teamModelList.size();index++){
                TeamModel teamModel = teamModelList.get(index);
                if (teamModel.getId()==singleNomination.getRecommended_team().getId()){
                    pos = index;
                    return pos;
                }
            }
        }

        return pos;
    }


}
