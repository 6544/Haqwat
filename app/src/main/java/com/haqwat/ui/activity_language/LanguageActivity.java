package com.haqwat.ui.activity_language;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityLanguageBinding;
import com.haqwat.language.Language;

import java.io.IOException;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;
    private String lang;
    private boolean canSelect = false;
    private String selectedLang="";

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);
        initView();
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        selectedLang = lang;

        binding.setLang(lang);
        binding.close.setOnClickListener(v -> finish());
        if (lang.equals("ar"))
        {
            binding.tvAr.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
            binding.imageAr.setVisibility(View.VISIBLE);
            binding.tvEn.setTextColor(ContextCompat.getColor(this,R.color.white));
            binding.imageEn.setVisibility(View.GONE);

        }else {
            binding.tvAr.setTextColor(ContextCompat.getColor(this,R.color.white));
            binding.imageAr.setVisibility(View.GONE);
            binding.tvEn.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
            binding.imageEn.setVisibility(View.VISIBLE);
        }

        binding.consAr.setOnClickListener(v -> {

            binding.tvAr.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
            binding.imageAr.setVisibility(View.VISIBLE);
            binding.tvEn.setTextColor(ContextCompat.getColor(this,R.color.white));
            binding.imageEn.setVisibility(View.GONE);

            if (lang.equals("ar")){
                selectedLang = lang;
                canSelect = false;

            }else {

                canSelect = true;
                selectedLang = "ar";


            }

            updateBtnUi();
        });


        binding.consEn.setOnClickListener(v -> {
            binding.tvAr.setTextColor(ContextCompat.getColor(this,R.color.color4));
            binding.imageAr.setVisibility(View.GONE);
            binding.tvEn.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
            binding.imageEn.setVisibility(View.VISIBLE);

            if (lang.equals("ar")){

                canSelect = true;
                selectedLang = "en";

            }else {
                selectedLang = lang;
                canSelect = false;
            }

            updateBtnUi();

        });


        binding.btnConfirm.setOnClickListener(v -> {
            if (canSelect){

                Paper.book().write("lang",selectedLang);
                Language.updateResources(this,selectedLang);
                setResult(RESULT_OK);
                finish();


            }
        });
    }

    private void updateBtnUi() {
        if (canSelect){
            binding.btnConfirm.setTextColor(ContextCompat.getColor(this,R.color.white));
            binding.btnConfirm.setBackgroundResource(R.drawable.small_rounded_green);
        }else {
            binding.btnConfirm.setTextColor(ContextCompat.getColor(this,R.color.black));
            binding.btnConfirm.setBackgroundResource(R.drawable.small_rounded_white2);
        }
    }



}