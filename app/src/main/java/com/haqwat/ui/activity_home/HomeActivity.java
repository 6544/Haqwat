package com.haqwat.ui.activity_home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityHomeBinding;
import com.haqwat.language.Language;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.activity_home_mvp.ActivityHomePresenter;
import com.haqwat.mvp.activity_home_mvp.ActivityHomeView;
import com.haqwat.preferences.Preferences;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_accounts.AccountsActivity;
import com.haqwat.ui.activity_contact_us.ContactUsActivity;
import com.haqwat.ui.activity_language.LanguageActivity;
import com.haqwat.ui.activity_login.LoginActivity;
import com.haqwat.ui.activity_notification.NotificationActivity;
import com.haqwat.ui.activity_password.PasswordActivity;

import java.io.ByteArrayOutputStream;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements ActivityHomeView {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private ActivityHomePresenter presenter;
    private boolean backPress = false;
    private ActionBarDrawerToggle toggle;
    private UserModel userModel;
    private Preferences preferences;
    private ProgressDialog dialog;
    private String lang;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        initView();
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setModel(userModel);
        binding.setGiftsCount(0);
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this,this,fragmentManager);
        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolBar,R.string.open,R.string.close);
        toggle.syncState();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if (!backPress){
                presenter.openFragment(item);
            }
            backPress = false;
            return true;
        });
        binding.llLogout.setOnClickListener(view -> {
            presenter.logout(userModel, null);
        });
        binding.llChangeLanguage.setOnClickListener(view -> {
            Intent intent = new Intent(this, LanguageActivity.class);
            startActivityForResult(intent,100);
        });
        binding.llContactUs.setOnClickListener(view -> {
            Intent intent = new Intent(this, ContactUsActivity.class);
            startActivity(intent);
        });
        binding.imageNotification.setOnClickListener(view -> {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        });
        binding.llChangePassword.setOnClickListener(view -> {
            Intent intent = new Intent(this, PasswordActivity.class);
            startActivityForResult(intent,400);
        });
        binding.llSwitchAccount.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountsActivity.class);
            startActivityForResult(intent,500);
        });
        binding.imageEdit.setOnClickListener(view ->presenter.createImageDialog());
        binding.llCode.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied new text",userModel.getCode());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
        });
        binding.mSwitch.setOnClickListener(view -> {
            if (binding.mSwitch.isChecked()){
                presenter.updateNotificationStatus("on");
            }else {
                presenter.updateNotificationStatus("off");

            }
        });

        dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);



    }



    @Override
    public void onBackPressed(int itemId) {
        binding.bottomNavigation.setSelectedItemId(itemId);
    }

    @Override
    public void onLogoutSuccess(AccountsModel model) {

        AccountsModel currentAccount = preferences.getMyCurrentAccount(this,userModel.getEmail());
        if (currentAccount!=null){
            currentAccount.setLoggedIn(false);
            preferences.createAccount(this,currentAccount);
        }

        preferences.clear(this);
        Intent intent  = new Intent(this, LoginActivity.class);

        if (model!=null){
            intent.putExtra("account",model);
        }

        startActivity(intent);
        finish();

    }

    @Override
    public void onLogoutFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserUpdate(UserModel userModel) {
        this.userModel = userModel;
        preferences.create_update_userdata(this,userModel);
        binding.setModel(userModel);

    }

    @Override
    public void onStatusSuccess(UserModel userModel) {
        this.userModel = userModel;
        binding.setModel(userModel);
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            Paper.init(this);
            String lang = Paper.book().read("lang","ar");
            refreshActivity(lang);
        }else if (requestCode == ActivityHomePresenter.READ_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Uri uri = data.getData();
            presenter.updateImage(uri);

        } else if (requestCode == ActivityHomePresenter.CAMERA_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri uri = getUriFromBitmap(bitmap);
            if (uri != null) {
                presenter.updateImage(uri);
            }


        }else if (requestCode == 400 && resultCode == Activity.RESULT_OK && data != null) {

            String password = data.getStringExtra("password");
            presenter.changePassword(password);


        }else if (requestCode == 500 && resultCode == Activity.RESULT_OK && data != null) {

            AccountsModel model = (AccountsModel) data.getSerializableExtra("account");
            presenter.logout(userModel,model);


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

    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.updateResources(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 1050);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress = true;
        presenter.onBackPress();
    }
}