package com.haqwat.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.haqwat.R;
import com.haqwat.databinding.SpinnerNationalityRowBinding;
import com.haqwat.databinding.SpinnerRowBinding;
import com.haqwat.models.NationalityModel;

import java.util.List;

import io.paperdb.Paper;

public class NationalitySpinnerAdapter extends BaseAdapter {
    private List<NationalityModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public NationalitySpinnerAdapter(List<NationalityModel> list, Context context) {
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
        @SuppressLint("ViewHolder") SpinnerNationalityRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.spinner_nationality_row,parent,false);
        NationalityModel model = list.get(position);
        binding.setLang(lang);
        binding.setModel(model);
        return binding.getRoot();
    }
}
