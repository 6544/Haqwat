package com.haqwat.ui.activity_more;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.provider.MediaStore;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityMoreBinding;
import com.haqwat.language.Language;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_home_mvp.ActivityHomePresenter;
import com.haqwat.mvp.activity_more_mvp.ActivityMorePresenter;
import com.haqwat.mvp.activity_more_mvp.ActivityMoreView;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_accounts.AccountsActivity;
import com.haqwat.ui.activity_champion_system.ChampionSystemActivity;
import com.haqwat.ui.activity_gifts.GiftsActivity;
import com.haqwat.ui.activity_password.PasswordActivity;
import com.haqwat.ui.activity_update_profile.UpdateProfileActivity;

import java.io.ByteArrayOutputStream;

import io.paperdb.Paper;

public class MoreActivity extends AppCompatActivity implements ActivityMoreView {
    private ActivityMoreBinding binding;
    private Preferences preference;
    private UserModel userModel;
    private int giftCount = 0;
    private String lang="ar";
    private ActivityMorePresenter presenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_more);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        giftCount = intent.getIntExtra("count", 0);

    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        preference = Preferences.getInstance();
        userModel = preference.getUserData(this);
        binding.setGiftsCount(giftCount);
        binding.setLang(lang);
        binding.setModel(userModel);
        presenter = new ActivityMorePresenter(this,this);
        binding.llSwitchAccount.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountsActivity.class);
            startActivityForResult(intent, 100);
        });
        binding.llGifts.setOnClickListener(view -> {
            Intent intent = new Intent(this, GiftsActivity.class);
            startActivityForResult(intent, 200);
        });

        binding.llChangePassword.setOnClickListener(view -> {
            Intent intent = new Intent(this, PasswordActivity.class);
            startActivityForResult(intent, 300);
        });

        binding.llEditProfile.setOnClickListener(view -> {
            Intent intent = new Intent(this, UpdateProfileActivity.class);
            startActivityForResult(intent, 400);
        });

        binding.llLogout.setOnClickListener(view -> {
            Intent intent = getIntent();
            intent.putExtra("type", "logout");
            setResult(RESULT_OK, intent);
            finish();
        });

        binding.llChampionSystem.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChampionSystemActivity.class);
            startActivity(intent);
        });

        binding.imageEdit.setOnClickListener(view ->presenter.createImageDialog());


        binding.llCode.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied new text", userModel.getCode());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
        });

        binding.close.setOnClickListener(view -> onBackPressed());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            AccountsModel accountsModel = (AccountsModel) data.getSerializableExtra("account");
            Intent intent = getIntent();
            intent.putExtra("type", "account");
            intent.putExtra("account", accountsModel);
            setResult(RESULT_OK, intent);
            finish();
        } else if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            giftCount = data.getIntExtra("gift", 0);
            binding.setGiftsCount(giftCount);

        } else if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
            String password = data.getStringExtra("password");
            Intent intent = getIntent();
            intent.putExtra("type", "password");
            intent.putExtra("password", password);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (requestCode == 400 && resultCode == RESULT_OK) {
           userModel = preference.getUserData(this);
           binding.setModel(userModel);
        }else if (requestCode == ActivityMorePresenter.READ_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Uri uri = data.getData();
            presenter.updateImage(uri);

        } else if (requestCode == ActivityMorePresenter.CAMERA_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri uri = getUriFromBitmap(bitmap);
            if (uri != null) {
                presenter.updateImage(uri);
            }


        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("type", "count");
        intent.putExtra("gift", giftCount);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onUserUpdate(UserModel userModel) {
        preference.create_update_userdata(this,userModel);
        this.userModel = userModel;
        binding.setModel(userModel);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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