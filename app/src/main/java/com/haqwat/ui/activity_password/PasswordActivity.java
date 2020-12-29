package com.haqwat.ui.activity_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.haqwat.R;
import com.haqwat.databinding.ActivityPasswordBinding;
import com.haqwat.language.Language;

import io.paperdb.Paper;

public class PasswordActivity extends AppCompatActivity {
    private String lang;
    private ActivityPasswordBinding binding;
    private boolean canSend = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password);
        initView();

    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    canSend = true;
                } else {
                    canSend = false;
                }

                updateBtnUi(canSend);
            }
        });
        binding.llBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnUpdate.setOnClickListener(view -> {
            if (canSend) {
                String password = binding.edtPassword.getText().toString().trim();
                if (!password.isEmpty() && password.length() >= 6) {
                    binding.edtPassword.setError(null);
                    Intent intent = getIntent();
                    intent.putExtra("password", password);
                    setResult(RESULT_OK, intent);
                    finish();

                } else if (password.isEmpty()) {
                    binding.edtPassword.setError(getString(R.string.field_required));

                } else if (password.length() < 6) {
                    binding.edtPassword.setError(getString(R.string.password_short));

                }
            }
        });

    }

    private void updateBtnUi(boolean canSend) {
        if (canSend) {
            binding.btnUpdate.setBackgroundResource(R.color.colorPrimary);
            binding.btnUpdate.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            binding.btnUpdate.setBackgroundResource(R.color.color4);
            binding.btnUpdate.setTextColor(ContextCompat.getColor(this, R.color.color5));
        }
    }
}