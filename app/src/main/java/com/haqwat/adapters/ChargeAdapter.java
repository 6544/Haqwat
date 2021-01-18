package com.haqwat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.haqwat.R;
import com.haqwat.databinding.ChargeHaqwatRowBinding;
import com.haqwat.databinding.FavoriteTeamRowBinding;
import com.haqwat.models.ChargeModel;
import com.haqwat.models.TeamOrderModel;

import java.util.List;

import io.paperdb.Paper;

public class ChargeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChargeModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    public ChargeAdapter(List<ChargeModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang","ar");

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ChargeHaqwatRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.charge_haqwat_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        ChargeModel chargeModel = list.get(position);
        myHolder.binding.setModel(chargeModel);
        myHolder.binding.setLang(lang);
        if (chargeModel.getIn_haqawat_competition().equals("no")){
            myHolder.binding.imageBg.setBorderColor(ContextCompat.getColor(context,R.color.white));

        }else {
            myHolder.binding.imageBg.setBorderColor(ContextCompat.getColor(context,R.color.color12));

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ChargeHaqwatRowBinding binding;

        public MyHolder(@NonNull ChargeHaqwatRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
