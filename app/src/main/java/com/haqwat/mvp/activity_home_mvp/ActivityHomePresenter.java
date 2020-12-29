package com.haqwat.mvp.activity_home_mvp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.haqwat.R;
import com.haqwat.databinding.DialogSelectImageBinding;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.UserModel;
import com.haqwat.preferences.Preferences;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_home.fragments.fragment_champions.Fragment_Champions;
import com.haqwat.ui.activity_home.fragments.Fragment_Charge;
import com.haqwat.ui.activity_home.fragments.Fragment_Home;
import com.haqwat.ui.activity_home.fragments.Fragment_Stars;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHomePresenter {
    private ActivityHomeView view;
    private Context context;
    private FragmentManager fragmentManager;
    private Fragment_Home fragment_home;
    private Fragment_Charge fragment_charge;
    private Fragment_Stars fragment_stars;
    private Fragment_Champions fragment_champions;
    public static final String tagHome="fragment_home";
    public static final String tagCharge="fragment_charge";
    public static final String tagStars="fragment_stars";
    public static final String tagChampion="fragment_champions";
    private int currentItemId = 0;

    private String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private String  camera_permission = Manifest.permission.CAMERA;
    public static final int READ_REQ = 200,CAMERA_REQ=300;
    private HomeActivity activity;
    private UserModel userModel;
    private Preferences preferences;


    public ActivityHomePresenter(ActivityHomeView view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        activity = (HomeActivity) context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        this.fragmentManager = fragmentManager;
        displayFragment(true,tagHome);
    }

    public void displayFragment(boolean isFirstFragment,String tag)
    {

        switch (tag){
            case tagCharge:
                if (fragment_charge==null){
                    fragment_charge = Fragment_Charge.newInstance();
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_charge, tag);

                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction.addToBackStack(null);
                }
                transaction.commit();
                break;
            case tagStars:
                if (fragment_stars==null){
                    fragment_stars = Fragment_Stars.newInstance();
                }
                FragmentTransaction transaction2 = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_stars, tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction2.addToBackStack(null);
                }
                transaction2.commit();
                break;
            case tagChampion:
                if (fragment_champions==null){
                    fragment_champions = Fragment_Champions.newInstance();
                }
                FragmentTransaction transaction3 = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_champions, tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction3.addToBackStack(null);
                }
                transaction3.commit();
                break;
            default:
                if (fragment_home==null){
                    fragment_home = Fragment_Home.newInstance();
                }
                FragmentTransaction transaction4 = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment_home, tag);
                if (!isFirstFragment&&!isInBackStack(tag)){
                    transaction4.addToBackStack(null);
                }
                transaction4.commit();
                break;
        }


    }
    public void openFragment(MenuItem item)
    {
        currentItemId = item.getItemId();
        switch (currentItemId){
            case R.id.charge:
                displayFragment(false,tagCharge);
                break;
            case R.id.stars:
                displayFragment(false,tagStars);
                break;
            case R.id.championship:
                displayFragment(false,tagChampion);

                break;
            default:
                displayFragment(false,tagHome);

                break;
        }
    }
    public void onBackPress(){
        String tag = fragmentManager.findFragmentById(R.id.fragment_container).getTag();
        switch (tag){
            case tagCharge:
                currentItemId = R.id.charge;
                break;
            case tagStars:
                currentItemId = R.id.stars;
                break;
            case tagChampion:
                currentItemId = R.id.championship;
                break;
            default:
                currentItemId = R.id.home;
                break;
        }

        view.onBackPressed(currentItemId);
    }
    private boolean isInBackStack(String tag)
    {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment:fragmentList){
            if (fragment.getTag().equals(tag)){
                return true;
            }
        }
        return false;
    }

    public void logout(UserModel userModel, AccountsModel model)
    {
        view.showProgressDialog();
        Api.getService(Tags.base_url).logout("Bearer "+userModel.getToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        view.hideProgressDialog();
                        if (response.isSuccessful()){

                            view.onLogoutSuccess(model);

                        }else {
                            view.hideProgressDialog();

                            try {
                                view.onLogoutFailed(context.getString(R.string.failed));
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            view.hideProgressDialog();

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onLogoutFailed(context.getString(R.string.something));

                                } else {
                                    view.onLogoutFailed(context.getString(R.string.failed));

                                }
                            }


                        }catch (Exception e){}

                    }
                });
    }


    public void createImageDialog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_select_image, null, false);

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss()

        );

        binding.btnGallery.setOnClickListener(v ->
                {
                    dialog.dismiss();
                    checkReadPermission();
                }
        );

        binding.btnCamera.setOnClickListener(v -> {
                    dialog.dismiss();
                    checkCameraPermission();
                }
        );


        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }
    private void checkReadPermission()
    {
        if (ActivityCompat.checkSelfPermission(context, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }
    private void checkCameraPermission()
    {


        if (ContextCompat.checkSelfPermission(context, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }
    public void SelectImage(int req)
    {

        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            activity.startActivityForResult(intent, req);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                activity.startActivityForResult(intent, req);
            } catch (SecurityException e) {
                Toast.makeText(context,"Permission access photos denied", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Permission access photos denied", Toast.LENGTH_SHORT).show();

            }


        }
    }


    public void updateImage(Uri uri)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Log.e("gender",userModel.getGender()+"__");

        RequestBody name_part = Common.getRequestBodyText(userModel.getName());
        RequestBody email_part = Common.getRequestBodyText(userModel.getEmail());
        RequestBody country_part = Common.getRequestBodyText(String.valueOf(userModel.getCountry_id()));
        RequestBody gender_part = Common.getRequestBodyText(userModel.getGender());
        RequestBody birthday_part = Common.getRequestBodyText(userModel.getBirthday());
        RequestBody league_id_part = Common.getRequestBodyText(String.valueOf(userModel.getLeague_id()));
        RequestBody team_id_part = Common.getRequestBodyText(String.valueOf(userModel.getTeam_id()));
        MultipartBody.Part logo = Common.getMultiPartImage(context,uri,"logo");

        Api.getService(Tags.base_url).updateProfileImage("Bearer "+userModel.getToken(),name_part,email_part,country_part,gender_part,birthday_part,league_id_part,team_id_part,logo)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()){

                            view.onUserUpdate(response.body());

                        }else {
                            dialog.dismiss();
                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));

                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }


                        }catch (Exception e){}

                    }
                });

    }


    public void changePassword(String password)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url).updatePassword("Bearer "+userModel.getToken(),userModel.getName(),userModel.getEmail(),String.valueOf(userModel.getCountry_id()),password,userModel.getGender(),userModel.getBirthday(),String.valueOf(userModel.getLeague_id()),String.valueOf(userModel.getTeam_id()))
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()){
                            AccountsModel model = preferences.getMyCurrentAccount(context,userModel.getEmail());
                            model.setPassword(password);
                            preferences.createAccount(context,model);
                            view.onUserUpdate(response.body());

                        }else {
                            dialog.dismiss();
                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));

                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }


                        }catch (Exception e){}

                    }
                });

    }

    public void updateNotificationStatus(String status){
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url).updateNotificationStatus("Bearer "+userModel.getToken(),status)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null){
                            userModel.setNotification_status(response.body().getNotification_status());
                            preferences.create_update_userdata(context,userModel);
                            view.onStatusSuccess(response.body());
                        }else {
                            dialog.dismiss();
                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error",response.code()+"_"+response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));

                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }


                        }catch (Exception e){}

                    }
                });
    }

}
