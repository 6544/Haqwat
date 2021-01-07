package com.haqwat.ui.activity_update_profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityPasswordBinding;
import com.haqwat.databinding.ActivityUpdateProfileBinding;
import com.haqwat.language.Language;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_home_mvp.ActivityHomePresenter;
import com.haqwat.mvp.activity_update_profile_mvp.ActivityUpdateProfilePresenter;
import com.haqwat.mvp.activity_update_profile_mvp.ActivityUpdateProfileView;
import com.haqwat.preferences.Preferences;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import io.paperdb.Paper;

public class UpdateProfileActivity extends AppCompatActivity implements ActivityUpdateProfileView {
    private String lang;
    private ActivityUpdateProfileBinding binding;
    private ActivityUpdateProfilePresenter presenter;
    private Uri uri=null;
    private Preferences preferences;
    private UserModel userModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile);
        initView();

    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setModel(userModel);
        presenter = new ActivityUpdateProfilePresenter(this,this);
        binding.llBack.setOnClickListener(view -> {
            finish();
        });

        binding.imageEdit.setOnClickListener(view -> {
            presenter.createImageDialog();
        });

        binding.btnUpdate.setOnClickListener(view -> {
            String name = binding.edtName.getText().toString();
            if (!name.isEmpty()){
                binding.edtName.setError(null);
                if (uri==null){
                    presenter.updateName(name);
                }else {
                    presenter.updateNameWithImage(uri,name);
                }
            }else {
                binding.edtName.setError(getString(R.string.field_required));
            }
        });

    }

    @Override
    public void onSuccess(UserModel userModel) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityHomePresenter.READ_REQ && resultCode == Activity.RESULT_OK && data != null) {

            uri = data.getData();
            Picasso.get().load(uri).into(binding.image);

        } else if (requestCode == ActivityHomePresenter.CAMERA_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uri = getUriFromBitmap(bitmap);
            Picasso.get().load(uri).into(binding.image);

        }
    }



    private Uri getUriFromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ActivityHomePresenter.READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.SelectImage(requestCode);
            } else {
                Toast.makeText(this, "Permission access photos denied", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == ActivityHomePresenter.CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                presenter.SelectImage(requestCode);
            } else {
                Toast.makeText(this, "Permission access photos denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}