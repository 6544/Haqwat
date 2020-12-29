package com.haqwat.ui.activity_accounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.haqwat.R;
import com.haqwat.adapters.AccountsAdapter;
import com.haqwat.databinding.ActivityAccountsBinding;
import com.haqwat.databinding.ActivityPasswordBinding;
import com.haqwat.language.Language;
import com.haqwat.models.AccountsModel;
import com.haqwat.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class AccountsActivity extends AppCompatActivity {
    private String lang;
    private ActivityAccountsBinding binding;
    private List<AccountsModel> accountsModelList;
    private AccountsAdapter adapter;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_accounts);
        initView();

    }


    private void initView() {
        accountsModelList = new ArrayList<>();
        preferences = Preferences.getInstance();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        accountsModelList.addAll(preferences.getAccounts(this));
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AccountsAdapter(accountsModelList,this);
        binding.recView.setAdapter(adapter);

        if (accountsModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

        binding.llBack.setOnClickListener(view -> {
            finish();
        });


    }


    public void removeAccount(AccountsModel model, int adapterPosition) {
        preferences.removeAccount(this,adapterPosition);
        accountsModelList.remove(adapterPosition);
        adapter.notifyItemRemoved(adapterPosition);
        if (accountsModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    public void switchAccount(AccountsModel model) {
        Intent intent = getIntent();
        intent.putExtra("account",model);
        setResult(RESULT_OK,intent);
        finish();
    }
}