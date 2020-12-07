package com.haqwat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.haqwat.R;
import com.haqwat.databinding.SpinnerRow2Binding;
import com.haqwat.models.LeagueModel;
import com.haqwat.models.TeamModel;

import java.util.List;

import io.paperdb.Paper;

public class LeagueSpinnerAdapter extends BaseAdapter {
    private List<LeagueModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public LeagueSpinnerAdapter(List<LeagueModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") SpinnerRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.spinner_row2,parent,false);
        LeagueModel model = list.get(position);
        binding.setTitle(model.getTitle());
        return binding.getRoot();
    }
}
