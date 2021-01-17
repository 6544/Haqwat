package com.haqwat.ui.activity_league_details.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.BuildConfig;
import com.haqwat.R;
import com.haqwat.databinding.DialogAlert2Binding;
import com.haqwat.databinding.FragmentLeagueRatingBinding;
import com.haqwat.models.LeagueRateModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_team_rate_mvp.FragmentTeamRatePresenter;
import com.haqwat.mvp.fragment_team_rate_mvp.FragmentTeamRateView;
import com.haqwat.preferences.Preferences;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Fragment_League_Rating extends Fragment implements FragmentTeamRateView {
    private final static String TAG = "DATA";
    private LeagueDetailsActivity activity;
    private FragmentLeagueRatingBinding binding;
    private FragmentTeamRatePresenter presenter;
    private String write_perm = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private int write_req = 100;
    private UserModel userModel;
    private Preferences preferences;
    private String league_id = "";
    private AlertDialog dialog;
    private String sharePathFile="";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LeagueDetailsActivity) context;
    }

    public static Fragment_League_Rating newInstance(String league_id) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, league_id);
        Fragment_League_Rating fragment_league_rating = new Fragment_League_Rating();
        fragment_league_rating.setArguments(bundle);
        return fragment_league_rating;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_rating, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            league_id = bundle.getString(TAG);
        }
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        binding.setUserModel(userModel);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.white), PorterDuff.Mode.SRC_IN);
        presenter = new FragmentTeamRatePresenter(this, activity);
        presenter.getData(league_id, userModel);
        createDialogAlert();


        switch (league_id) {
            case Tags.CHAMPIONS_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img1);
                break;
            case Tags.EGYPTIAN_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img6);

                break;
            case Tags.FRANCE_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img2);

                break;
            case Tags.JERMAN_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img7);

                break;
            case Tags.LA_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img3);

                break;
            case Tags.SAUDI_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img5);

                break;
            case Tags.SERIA_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img4);

                break;
            case Tags.PREMIER_LEAGUE:
                binding.leagueImage.setImageResource(R.drawable.img8);

                break;

        }

        binding.imageShare.setOnClickListener(view -> {
            checkWritePermission();
        });

    }

    private void share() {
        boolean fileCreated;
        Bitmap bitmap =getBitmapFromView(binding.llShareView);
        File cachePath = new File(sharePathFile);
        if (!cachePath.exists()){
            fileCreated = cachePath.mkdirs();

        }else {
            fileCreated = true;
        }


        if (fileCreated){
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(cachePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri myImageFileUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", cachePath);

            String shareLink = getString(R.string.app_name) + "\n" + "http://hqwat.com/api/leagueShare/" + league_id + "/" + userModel.getId();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.putExtra(Intent.EXTRA_STREAM, myImageFileUri);
            sendIntent.setType("*/*");
            startActivity(sendIntent);
        }else {
            Toast.makeText(activity, "Cant't create file on your device", Toast.LENGTH_SHORT).show();
        }

    }

    private Bitmap getBitmapFromView(View view) {
        long now = System.currentTimeMillis();
        Bitmap returnBitmap = null;

        try {

            File dir = new File(Environment.getExternalStorageDirectory().toString(),"haqawat");
            if (!dir.exists()){
                dir.mkdirs();
            }
            sharePathFile = new File(dir,now+".jpg").getAbsolutePath();




            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(sharePathFile);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            if (imageFile.exists()) {
                returnBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return returnBitmap;
    }

    private void checkWritePermission() {


        if (ContextCompat.checkSelfPermission(activity, write_perm) == PackageManager.PERMISSION_GRANTED
        ) {
            share();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{write_perm}, write_req);
        }
    }


    private void createDialogAlert() {
        dialog = new AlertDialog.Builder(activity)
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        DialogAlert2Binding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_alert2, null, false);

        binding.tvMsg.setText(R.string.upgrade_account);
        binding.btnPayment.setOnClickListener(v -> {
                    dialog.dismiss();
                    activity.finish();
                }

        );

        binding.btnContinue.setOnClickListener(v -> {
                    dialog.dismiss();
                    presenter.getData2(league_id, String.valueOf(userModel.getId()));
                    //activity.secondFragment();
                }

        );
        binding.btnInvite.setOnClickListener(view -> {

            String shareLink = getString(R.string.app_name) + "\n" + "يمكنك الاشتراك في الدوريات بإستخدام كود صديقك." + " \n" + userModel.getCode();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
    }

    @Override
    public void onSuccess(LeagueRateModel leagueRateModel) {
        binding.flLoading.setVisibility(View.GONE);
        binding.setModel(leagueRateModel);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowAlertDialog() {
        try {
            dialog.show();

        } catch (Exception e) {
        }
    }

    @Override
    public void showProgressBar() {
        try {
            binding.progBar.setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
    }

    @Override
    public void hideProgressBar() {
        try {
            binding.progBar.setVisibility(View.GONE);

        } catch (Exception e) {
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==write_req){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                share();
            }else {
                Toast.makeText(activity, "Can't access device storage", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
