package com.haqwat.mvp.activity_update_profile_mvp;

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
import com.haqwat.mvp.activity_home_mvp.ActivityHomeView;
import com.haqwat.preferences.Preferences;
import com.haqwat.remote.Api;
import com.haqwat.share.Common;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_home.fragments.Fragment_Charge;
import com.haqwat.ui.activity_home.fragments.Fragment_Home;
import com.haqwat.ui.activity_home.fragments.Fragment_Stars;
import com.haqwat.ui.activity_home.fragments.fragment_champions.Fragment_Champions;
import com.haqwat.ui.activity_update_profile.UpdateProfileActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUpdateProfilePresenter {
    private ActivityUpdateProfileView view;
    private Context context;

    private String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private String camera_permission = Manifest.permission.CAMERA;
    public static final int READ_REQ = 200, CAMERA_REQ = 300;
    private UserModel userModel;
    private Preferences preferences;
    private UpdateProfileActivity activity;


    public ActivityUpdateProfilePresenter(ActivityUpdateProfileView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        activity = (UpdateProfileActivity) context;

    }

    public void createImageDialog() {
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

    private void checkReadPermission() {
        if (ActivityCompat.checkSelfPermission(context, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    private void checkCameraPermission() {


        if (ContextCompat.checkSelfPermission(context, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }

    public void SelectImage(int req) {

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
                Toast.makeText(context, "Permission access photos denied", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Permission access photos denied", Toast.LENGTH_SHORT).show();

            }


        }
    }


    public void updateImage(Uri uri)
    {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        RequestBody name_part = Common.getRequestBodyText(userModel.getName());
        RequestBody email_part = Common.getRequestBodyText(userModel.getEmail());
        RequestBody country_part = Common.getRequestBodyText(String.valueOf(userModel.getCountry_id()));
        RequestBody gender_part = Common.getRequestBodyText(userModel.getGender());
        RequestBody birthday_part = Common.getRequestBodyText(userModel.getBirthday());
        RequestBody league_id_part = Common.getRequestBodyText(String.valueOf(userModel.getLeague_id()));
        RequestBody team_id_part = Common.getRequestBodyText(String.valueOf(userModel.getTeam_id()));
        MultipartBody.Part logo = Common.getMultiPartImage(context, uri, "logo");

        Api.getService(Tags.base_url).updateProfileImage("Bearer " + userModel.getToken(), name_part, email_part, country_part, gender_part, birthday_part, league_id_part, team_id_part, logo)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            preferences.create_update_userdata(context,response.body());
                            view.onSuccess(response.body());

                        } else {
                            dialog.dismiss();
                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error", response.code() + "_" + response.errorBody().string());

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


                        } catch (Exception e) {
                        }

                    }
                });

    }

    public void updateName(String name)
    {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        RequestBody name_part = Common.getRequestBodyText(name);
        RequestBody email_part = Common.getRequestBodyText(userModel.getEmail());
        RequestBody country_part = Common.getRequestBodyText(String.valueOf(userModel.getCountry_id()));
        RequestBody gender_part = Common.getRequestBodyText(userModel.getGender());
        RequestBody birthday_part = Common.getRequestBodyText(userModel.getBirthday());
        RequestBody league_id_part = Common.getRequestBodyText(String.valueOf(userModel.getLeague_id()));
        RequestBody team_id_part = Common.getRequestBodyText(String.valueOf(userModel.getTeam_id()));

        Api.getService(Tags.base_url).updateName("Bearer " + userModel.getToken(), name_part, email_part, country_part, gender_part, birthday_part, league_id_part, team_id_part)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            preferences.create_update_userdata(context,response.body());

                            view.onSuccess(response.body());

                        } else {
                            dialog.dismiss();
                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error", response.code() + "_" + response.errorBody().string());

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


                        } catch (Exception e) {
                        }

                    }
                });

    }

    public void updateNameWithImage(Uri uri, String name)
    {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        RequestBody name_part = Common.getRequestBodyText(name);
        RequestBody email_part = Common.getRequestBodyText(userModel.getEmail());
        RequestBody country_part = Common.getRequestBodyText(String.valueOf(userModel.getCountry_id()));
        RequestBody gender_part = Common.getRequestBodyText(userModel.getGender());
        RequestBody birthday_part = Common.getRequestBodyText(userModel.getBirthday());
        RequestBody league_id_part = Common.getRequestBodyText(String.valueOf(userModel.getLeague_id()));
        RequestBody team_id_part = Common.getRequestBodyText(String.valueOf(userModel.getTeam_id()));
        MultipartBody.Part logo = Common.getMultiPartImage(context, uri, "logo");

        Api.getService(Tags.base_url).updateProfileImage("Bearer " + userModel.getToken(), name_part, email_part, country_part, gender_part, birthday_part, league_id_part, team_id_part, logo)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            preferences.create_update_userdata(context,response.body());
                            view.onSuccess(response.body());

                        } else {
                            dialog.dismiss();
                            try {
                                view.onFailed(context.getString(R.string.failed));
                                Log.e("error", response.code() + "_" + response.errorBody().string());

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


                        } catch (Exception e) {
                        }

                    }
                });

    }


}
